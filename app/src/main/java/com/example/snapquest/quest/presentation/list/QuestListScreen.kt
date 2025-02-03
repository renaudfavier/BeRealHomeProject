package com.example.snapquest.quest.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.snapquest.core.presentation.component.CenteredErrorText
import com.example.snapquest.core.presentation.component.CenteredInfiniteCircularProgressIndicator
import com.example.snapquest.ui.theme.SnapQuestTheme

@Composable
fun QuestListScreen(
    uiModel: QuestListUiModel,
    onQuestClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(top = 24.dp)
) {
    Text(
        text = "SnapQuest",
        modifier = Modifier.padding(horizontal = 24.dp),
        style = MaterialTheme.typography.headlineLarge
    )

    when (uiModel) {
        is QuestListUiModel.Content -> Content(
            uiModel = uiModel,
            onQuestClick = onQuestClick,
            modifier = modifier.fillMaxSize().padding(all = 24.dp)
        )
        is QuestListUiModel.Error -> CenteredErrorText(uiModel.message, modifier.fillMaxSize())
        QuestListUiModel.Loading -> CenteredInfiniteCircularProgressIndicator(modifier.fillMaxSize())
    }
}

@Composable
private fun Content(
    uiModel: QuestListUiModel.Content,
    onQuestClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) = Column(modifier = modifier) {

    Text("Today's quest:")

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onQuestClick(uiModel.dailyQuest.id) }
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = uiModel.dailyQuest.name,
            modifier  = Modifier.padding(12.dp)
        )
    }
}

@Preview
@Composable
private fun ContentPreview() {
    SnapQuestTheme {
        QuestListScreen(
            uiModel = QuestListUiModel.Content(
                dailyQuest = QuestUiModel(1, "something blue")
            ),
            onQuestClick = { },
        )
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    SnapQuestTheme {
        QuestListScreen(
            uiModel = QuestListUiModel.Error("message"),
            onQuestClick = { },
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    SnapQuestTheme {
        QuestListScreen(
            uiModel = QuestListUiModel.Loading,
            onQuestClick = { },
        )
    }
}
