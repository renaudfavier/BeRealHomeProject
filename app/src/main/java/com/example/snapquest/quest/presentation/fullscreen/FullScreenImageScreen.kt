package com.example.snapquest.quest.presentation.fullscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.snapquest.R
import com.example.snapquest.core.presentation.component.ErrorImage
import com.example.snapquest.quest.presentation.component.BackButton
import com.example.snapquest.ui.theme.SnapQuestTheme

@Composable
fun FullScreenImageScreen(
    uiModel: FullScreenUiModel?,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier) {

    uiModel?.let {
        SubcomposeAsyncImage(
            model = uiModel.imageUrl,
            contentDescription = "full screen image",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
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
        )
    }

    BackButton(
        onClick = onBackPressed,
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 18.dp)
            .size(48.dp)
    )
}

@Preview
@Composable
private fun FullScreenPreview() {
    SnapQuestTheme {
        FullScreenImageScreen(
            uiModel = FullScreenUiModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVeunkhmW2i1XpN4Al2i0wJg2g6EXFH1EyUw&s"),
            onBackPressed = {}
        )
    }
}
