package com.example.snapquest.quest.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.snapquest.R
import com.example.snapquest.core.presentation.component.CenteredErrorText
import com.example.snapquest.core.presentation.component.CenteredInfiniteCircularProgressIndicator
import com.example.snapquest.quest.presentation.component.BackButton
import com.example.snapquest.quest.presentation.component.RoundedImageLarge
import com.example.snapquest.quest.presentation.component.RoundedImageSmall
import com.example.snapquest.quest.presentation.component.SectionTitle
import com.example.snapquest.ui.theme.SnapQuestTheme

@Composable
fun QuestDetailScreen(
    uiModel: QuestDetailUiModel,
    onBackButtonPressed: () -> Unit,
    onSubmitPhotoPressed: () -> Unit,
    modifier: Modifier = Modifier,
) = Box(
    modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
) {
    when (uiModel) {
        is QuestDetailUiModel.Content -> Content(
            uiModel = uiModel,
            onSubmitPhotoPressed = onSubmitPhotoPressed,
            modifier = modifier.fillMaxSize()
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
private fun Content(
    uiModel: QuestDetailUiModel.Content,
    onSubmitPhotoPressed: () -> Unit,
    modifier: Modifier = Modifier,
) = Box(modifier) {
    LazyColumn(modifier = modifier) {

        item {
            QuestDescription(
                uiModel = uiModel,
                modifier = Modifier
                    .padding(top = 72.dp)
                    .padding(horizontal = 24.dp)
            )
        }

        uiModel.mostUpVoted?.let { mostUpVoted ->
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    SectionTitle(stringResource(R.string.section_title_most_upvoted))

                    Box {
                        RoundedImageLarge(
                            imageUrl = mostUpVoted.url,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        LikeButtonLarge(
                            onClick = {},
                            voteCount = mostUpVoted.voteCount,
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
            }
        }

        uiModel.yourSubmission?.let { yourSubmission ->
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    SectionTitle(stringResource(R.string.section_title_your_submission))

                    Box {
                        RoundedImageLarge(
                            imageUrl = yourSubmission.url,
                            Modifier
                                .fillMaxWidth(0.8f)
                        )
                        LikeButtonLarge(
                            onClick = {},
                            voteCount = yourSubmission.voteCount,
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.BottomEnd),
                            enabled = false
                        )
                    }
                }
            }
        }

        if (uiModel.allSubmission.isNotEmpty()) {
            item {
                SectionTitle(stringResource(R.string.section_title_all_submissions), Modifier.padding(horizontal = 24.dp))
            }

            uiModel.allSubmission.chunked(2).forEach { pair ->
                item {
                    Row(
                        Modifier
                            .padding(bottom = 8.dp)
                            .padding(horizontal = 24.dp)
                    ) {
                        RoundedImageSmall(
                            imageUrl = pair[0].url,
                            Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        )

                        if (pair.size == 2) {
                            RoundedImageSmall(
                                imageUrl = pair[1].url,
                                Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                            )
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                            )
                        }
                    }
                }

            }
        }

        item {
            val bottomSpace = if(uiModel.isJoinButtonVisible) 144.dp else 48.dp
            Spacer(Modifier.height(bottomSpace))
        }
    }

    if(uiModel.isJoinButtonVisible) {
        Button(
            onClick = onSubmitPhotoPressed,
            colors = ButtonDefaults.elevatedButtonColors(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 24.dp)
                .height(72.dp)
                .align(Alignment.BottomCenter),
            elevation = ButtonDefaults.elevatedButtonElevation(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = stringResource(R.string.join_contest),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}


@Composable
fun QuestDescription(
    uiModel: QuestDetailUiModel.Content,
    modifier: Modifier = Modifier,
) = Column(modifier) {
    Text(
        text = uiModel.title,
        Modifier
            .padding(bottom = 24.dp)
            .align(Alignment.CenterHorizontally),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
    )
    RoundedImageLarge(
        imageUrl = uiModel.questImageUrl,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = uiModel.description,
        Modifier.padding(top = 24.dp),
    )
    Surface(
        modifier = Modifier
            .padding(top = 24.dp)
            .align(Alignment.CenterHorizontally),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(24.dp),
    ) {
        Text(
            text = uiModel.timeLeft,
            modifier = Modifier
                .padding(all = 18.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
private fun LikeButtonLarge(
    onClick: (Boolean) -> Unit,
    voteCount: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    liked: Boolean = false,
) = Button(
    onClick = { onClick(!liked) },
    modifier = modifier.wrapContentSize(),
    enabled = enabled,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.ThumbUp,
            contentDescription = "Like",
        )
        Text(
            text = "$voteCount",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
}

@Preview
@Composable
private fun ContentPreview() {
    SnapQuestTheme {
        QuestDetailScreen(
            uiModel = QuestDetailUiModel.Content(
                title = "something blue",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex.",
                questImageUrl = null,
                timeLeft = "5:22:04",
                yourSubmission = SubmissionUiModel(1, "", 7),
                mostUpVoted = SubmissionUiModel(1, "", 18),
                allSubmission = listOf(SubmissionUiModel(1, "", 3)),
                isJoinButtonVisible = true,
            ),
            onBackButtonPressed = {},
            onSubmitPhotoPressed = {},
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
            onSubmitPhotoPressed = {},
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
            onSubmitPhotoPressed = {},
        )
    }
}
