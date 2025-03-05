package com.bridge.androidtechnicaltest.ui.pupildetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.domain.entity.Response
import com.bridge.androidtechnicaltest.domain.usecase.GetPupil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PupilDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPupil: GetPupil,
) : ViewModel() {

    val pupilResponse: StateFlow<Response<Pupil>> =
        getPupil(savedStateHandle.get<Int>("id")!!).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            Response.Loading(),
        )
}