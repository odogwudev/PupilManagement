package com.bridge.androidtechnicaltest.domain.usecase

import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.data.repository.PupilRepository
import com.bridge.androidtechnicaltest.domain.entity.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPupil @Inject constructor(private val pupilRepository: PupilRepository) {
    operator fun invoke(id: Int): Flow<Response<Pupil>> {
        return pupilRepository.getPupil(id).flowOn(Dispatchers.IO)
    }
}