package com.bridge.androidtechnicaltest.domain.usecase

import androidx.paging.PagingData
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.domain.repository.IPupilRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPupilList @Inject constructor(
    private val pupilRepository: IPupilRepository
) {
    operator fun invoke(): Flow<PagingData<Pupil>> {
        return pupilRepository.getPupilList().flowOn(Dispatchers.IO)
    }
}
