package com.bridge.androidtechnicaltest.ui.pupillslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.domain.usecase.GetPupilList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PupilsListViewModel @Inject constructor(
    getPupilList: GetPupilList,
) : ViewModel() {

    val pupilsPagingDataFlow: Flow<PagingData<Pupil>> = getPupilList().cachedIn(viewModelScope)
}

/**
 * Convert an exception from the repository (e.g. 500 errors, timeouts)
 * into a user-friendly message for PupilsUiState.Error.
 */
fun handleNetworkError(e: Exception): String {
    return when (e) {
        is retrofit2.HttpException -> {
            when (e.code()) {
                404 -> "Resource not found."
                in 500..599 -> "Server is currently unavailable. Please try again."
                else -> "Something went wrong (HTTP ${e.code()})."
            }
        }

        is java.net.SocketTimeoutException -> {
            "Network timed out. Please check your connection and retry."
        }

        is java.io.IOException -> {
            "No Internet connection or network error occurred."
        }

        else -> e.message ?: "Unknown error occurred."
    }
}