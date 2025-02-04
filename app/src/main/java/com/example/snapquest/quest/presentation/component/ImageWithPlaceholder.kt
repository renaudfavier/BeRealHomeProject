package com.example.snapquest.quest.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.snapquest.R
import com.example.snapquest.core.presentation.component.ErrorImage
import com.example.snapquest.ui.theme.SnapQuestTheme

@Composable
fun ImageWithPlaceholder(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
) = if (imageUrl.isNullOrEmpty()) {
    Image(
        painter = painterResource(R.drawable.placeholder),
        contentDescription = "placeholder",
        modifier = modifier,
        contentScale = ContentScale.Crop,
        colorFilter = colorFilter
    )
} else {
    QuestImageNotNull(imageUrl, modifier, colorFilter)
}

@Composable
private fun QuestImageNotNull(
    imageUrl: String,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
) = SubcomposeAsyncImage(
    model = imageUrl,
    contentDescription = stringResource(R.string.content_description_quest_detail_photo),
    modifier = modifier,
    colorFilter = colorFilter,
    loading = {
        Image(
            painter = painterResource(R.drawable.placeholder),
            contentDescription = "placeholder",
            contentScale = ContentScale.Crop,
            colorFilter = colorFilter
        )
    },
    error = {
        if (LocalInspectionMode.current) {
            Image(
                painter = painterResource(R.drawable.debug_photo),
                contentDescription = "placeholder",
                contentScale = ContentScale.Crop,
                colorFilter = colorFilter
            )
        } else {
            ErrorImage()
        }
    },
    contentScale = ContentScale.Crop,
)

@Preview
@Composable
private fun QuestImagePreview() {
    SnapQuestTheme {
        ImageWithPlaceholder(null, Modifier.size(400.dp, 200.dp))
    }
}