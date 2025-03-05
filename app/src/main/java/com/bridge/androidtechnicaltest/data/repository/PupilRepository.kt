package com.bridge.androidtechnicaltest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.bridge.androidtechnicaltest.data.local.AppDatabase
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.data.remote.PupilApi
import com.bridge.androidtechnicaltest.domain.entity.Response
import com.bridge.androidtechnicaltest.domain.repository.IPupilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PupilRepository @Inject constructor(
    private val pupilPager: Pager<Int, Pupil>,
    private val database: AppDatabase,
    private val pupilApi: PupilApi
) : IPupilRepository {
    override fun getPupilList(): Flow<PagingData<Pupil>> {
        return pupilPager.flow.map { pagingData ->
            pagingData.map { it }
        }
    }

    override fun getPupil(id: Int): Flow<Response<Pupil>> = flow {
        val localData = database.pupilDao.getById(id)
        if (localData != null) emit(Response.Loading(localData)) // Emit local while fetching

        try {
            val remoteData = pupilApi.getPupil(id)
            database.pupilDao.insert(remoteData) // Save latest data
            emit(Response.Success(remoteData))
        } catch (e: HttpException) {
            if (e.code() == 404) {
                // Handle 404 (not found) separately
                database.pupilDao.deletePupilById(id) // Remove stale local data
                emit(
                    Response.Error(
                        exception = e,
                        errorMessage = "Pupil not found (404)",
                        httpCode = 404
                    )
                )
            } else {
                emit(
                    Response.Error(
                        exception = e,
                        errorMessage = "Server error: ${e.code()}",
                        httpCode = e.code()
                    )
                )
            }
        } catch (e: IOException) {
            emit(
                Response.Error(
                    exception = e,
                    errorMessage = "Network issue - check your internet",
                    httpCode = null,
                    data = localData
                )
            )
        } catch (e: Exception) {
            emit(
                Response.Error(
                    exception = e,
                    errorMessage = "Unexpected error",
                    httpCode = null,
                    data = localData
                )
            )
        }
    }
}