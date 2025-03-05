package com.bridge.androidtechnicaltest.ui.pupillslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun PupilsListScreen(
    navigateToDetail: (Int) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel = hiltViewModel<PupilsListViewModel>()
    val pupilPagingItems = viewModel.pupilsPagingDataFlow.collectAsLazyPagingItems()

    var isRefreshing by remember { mutableStateOf(false) }

    // Handle pull-to-refresh logic
    LaunchedEffect(pupilPagingItems.loadState.refresh) {
        isRefreshing = pupilPagingItems.loadState.refresh is LoadState.Loading
    }

    if (pupilPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                (pupilPagingItems.loadState.refresh as LoadState.Error).error.message.orEmpty()
            )
        }
    }

    PupilListContent(
        pupilPagingItems = pupilPagingItems,
        navigateToDetail = navigateToDetail,
        isRefreshing = isRefreshing,
        onRefresh = { pupilPagingItems.refresh() }
    )
}
@Composable
fun PupilListContent(
    pupilPagingItems: LazyPagingItems<Pupil>,
    navigateToDetail: (Int) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = onRefresh
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (pupilPagingItems.loadState.refresh is LoadState.Loading) {
                LinearProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        count = pupilPagingItems.itemCount,
                        key = pupilPagingItems.itemKey { it.pupilId },
                    ) { index ->
                        pupilPagingItems[index]?.let { pupil ->
                            PupilListItem(
                                pupil,
                                onClick = {
                                    navigateToDetail(pupil.pupilId.toInt())
                                },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.secondary,
                                thickness = 0.2.dp,
                                modifier = Modifier.padding(horizontal = 20.dp),
                            )
                        }
                    }
                    item {
                        if (pupilPagingItems.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}