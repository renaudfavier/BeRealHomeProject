package com.example.snapquest.quest.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.snapquest.core.presentation.component.CenteredErrorText
import com.example.snapquest.core.presentation.component.CenteredInfiniteCircularProgressIndicator
import com.example.snapquest.ui.theme.SnapQuestTheme

@Composable
fun QuestDetailScreen(
    uiModel: QuestDetailUiModel,
    onBackButtonPressed: () -> Unit,
    modifier: Modifier = Modifier,
) = Box(
    modifier = modifier
        .fillMaxSize()
        .background(Color.White),
) {

    when (uiModel) {
        is QuestDetailUiModel.Content -> Content(
            uiModel = uiModel,
            modifier = modifier.fillMaxSize().padding(all = 24.dp)
        )
        is QuestDetailUiModel.Error -> CenteredErrorText(uiModel.message, modifier.fillMaxSize())
        QuestDetailUiModel.Loading -> CenteredInfiniteCircularProgressIndicator(modifier.fillMaxSize())
    }
    BackButton(
        onClick = onBackButtonPressed,
        modifier = Modifier
            .padding(vertical = 12.dp, horizontal = 18.dp)
            .size(48.dp)
    )
}

@Composable
private fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = IconButton(
    onClick = onClick,
    modifier = modifier
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = CircleShape,
        color = Color.White,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "back icon",
            modifier = Modifier.padding(6.dp),
        )
    }
}

@Composable
private fun Content(
    uiModel: QuestDetailUiModel.Content,
    modifier: Modifier = Modifier,
) = Column(modifier = modifier) {

    Text(uiModel.name)

}

@Preview
@Composable
private fun ContentPreview() {
    SnapQuestTheme {
        QuestDetailScreen(
            uiModel = QuestDetailUiModel.Content(
                name = "something blue"
            ),
            onBackButtonPressed = {},
        )
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    SnapQuestTheme {
        QuestDetailScreen(
            uiModel = QuestDetailUiModel.Error("message"),
            onBackButtonPressed = {},
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    SnapQuestTheme {
        QuestDetailScreen(
            uiModel = QuestDetailUiModel.Loading,
            onBackButtonPressed = {},
        )
    }
}
