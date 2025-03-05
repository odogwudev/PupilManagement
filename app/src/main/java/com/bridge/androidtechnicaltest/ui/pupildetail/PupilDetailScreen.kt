package com.bridge.androidtechnicaltest.ui.pupildetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.bridge.androidtechnicaltest.domain.entity.Response

@Composable
fun PupilDetailScreen(snackbarHostState: SnackbarHostState) {
    val viewModel = hiltViewModel<PupilDetailViewModel>()
    val pupilResponse by viewModel.pupilResponse.collectAsStateWithLifecycle()

    if (pupilResponse is Response.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar((pupilResponse as Response.Error).errorMessage)
        }
    }

    PupilDetailContent(pupilResponse = pupilResponse)
}

@Composable
fun PupilDetailContent(pupilResponse: Response<Pupil>) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (pupilResponse.data != null) {
            val pupil = pupilResponse.data!!

            Row(modifier = Modifier.padding(20.dp)) {
                AsyncImage(
                    model = pupil.image,
                    modifier = Modifier.width(100.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(pupil.image)
                            .crossfade(true).build(),
                        placeholder = painterResource(R.drawable.placeholderimage),
                        contentDescription = pupil.image,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp),
                    )

                    Spacer(Modifier.height(16.dp))

                    // ID
                    DetailRow(label = "ID", value = pupil.pupilId.toString())

                    // Name
                    DetailRow(label = "Name", value = pupil.name)

                    // Country
                    DetailRow(label = "Country", value = pupil.country)
                }
            }
        }
        if (pupilResponse is Response.Loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}


@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.Bold)
        Text(text = value)
    }
    Spacer(Modifier.height(8.dp))
}
