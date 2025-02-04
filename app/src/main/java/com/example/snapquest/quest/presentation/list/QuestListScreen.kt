package com.example.snapquest.quest.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.snapquest.core.presentation.component.CenteredErrorText
import com.example.snapquest.core.presentation.component.CenteredInfiniteCircularProgressIndicator
import com.example.snapquest.quest.presentation.component.ImageWithPlaceholder
import com.example.snapquest.quest.presentation.component.SectionTitle
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
            modifier = modifier
                .fillMaxSize()
                .padding(all = 24.dp)
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
) = LazyColumn (modifier = modifier) {

    uiModel.categories.forEach { category ->
        item { SectionTitle(category.title) }
        items(category.quests) { quest ->
            ContestCard(
                questUiModel = quest,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .aspectRatio(4/1f)
                    .clickable { onQuestClick(quest.id) },
            )
        }
    }
}


@Composable
private fun ContestCard(
    questUiModel: QuestUiModel,
    modifier: Modifier = Modifier
) = Surface(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.primary
) {
    val desaturatedColorFilter = remember { ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) }

    Box {
        ImageWithPlaceholder(
            imageUrl = questUiModel.imageUrl,
            modifier = Modifier.fillMaxSize(),
            colorFilter = if(questUiModel.isDesaturated) desaturatedColorFilter else null
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                .align(Alignment.BottomCenter),
        ) {
            Text(
                text = questUiModel.name,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterStart),
                color = if(questUiModel.isDesaturated) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )
            questUiModel.timeLeft?.let {
                Text(
                    text = questUiModel.timeLeft,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    SnapQuestTheme {
        QuestListScreen(
            uiModel = QuestListUiModel.Content(
                listOf(
                    CategoryUiModel(
                        title = "daily quest",
                        quests = listOf(
                            QuestUiModel(1, "something blue", "", "", false)
                        )
                    ),
                    CategoryUiModel(
                        title = "daily quest",
                        quests = listOf(
                            QuestUiModel(1, "something blue", "", "", true),
                            QuestUiModel(1, "something blue", "", "", true),
                        )
                    ),
                )
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
