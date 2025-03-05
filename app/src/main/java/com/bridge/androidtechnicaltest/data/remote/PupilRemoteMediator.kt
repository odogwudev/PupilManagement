package com.bridge.androidtechnicaltest.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bridge.androidtechnicaltest.data.local.AppDatabase
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.data.local.RemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PupilRemoteMediator @Inject constructor(
    private val pupilDatabase: AppDatabase,
    private val pupilApi: PupilApi,
) : RemoteMediator<Int, Pupil>() {

    private val REMOTE_KEY_ID = "pupil"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pupil>,
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = pupilDatabase.remoteKeyDao.getById(REMOTE_KEY_ID)
                    if (remoteKey == null || remoteKey.nextOffset == 0)
                        return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.nextOffset
                }
            }
            val apiResponse = pupilApi.getPupils(page = offset)

            val results = apiResponse?.items ?: emptyList()
            val nextOffset = apiResponse?.pageNumber ?: 0

            pupilDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pupilDatabase.pupilDao.clearAll()
                    pupilDatabase.remoteKeyDao.deleteById(REMOTE_KEY_ID)
                }
                pupilDatabase.pupilDao.insertAll(results)
                pupilDatabase.remoteKeyDao.insert(RemoteKeyEntity(id = REMOTE_KEY_ID, nextOffset = nextOffset))
            }

            MediatorResult.Success(endOfPaginationReached = results.size < state.config.pageSize)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                MediatorResult.Success(endOfPaginationReached = true) // Stop pagination
            } else {
                MediatorResult.Error(e)
            }
        } catch (e: IOException) {
            MediatorResult.Error(e) // Network issue
        }
    }
}