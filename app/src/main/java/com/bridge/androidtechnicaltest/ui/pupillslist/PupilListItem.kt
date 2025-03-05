package com.bridge.androidtechnicaltest.ui.pupillslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bridge.androidtechnicaltest.data.local.Pupil


@Composable
fun PupilListItem(
    pupil: Pupil,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = pupil.image,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
        Text(
            "#${pupil.pupilId} - ${pupil.name}",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
        )
        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun PupilItemPreview() {
    MaterialTheme {
        Surface {
            PupilListItem(
                pupil = Pupil(
                    pupilId = 1,
                    name = "Enoma Michael",
                    image = "",
                    country = "Nigeria",
                    longitude = 0.0,
                    latitude = 0.0
                ),
                onClick = {},
            )
        }
    }
}