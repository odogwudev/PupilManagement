package com.bridge.androidtechnicaltest.domain.repository

import androidx.paging.PagingData
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.domain.entity.Response
import kotlinx.coroutines.flow.Flow

interface IPupilRepository {
    fun getPupilList(): Flow<PagingData<Pupil>>
    fun getPupil(id: Int): Flow<Response<Pupil>>
}
