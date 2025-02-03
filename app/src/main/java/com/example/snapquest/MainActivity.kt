package com.example.snapquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snapquest.quest.presentation.detail.QuestDetailScreen
import com.example.snapquest.quest.presentation.detail.QuestDetailUiModel
import com.example.snapquest.quest.presentation.list.QuestListScreen
import com.example.snapquest.quest.presentation.list.QuestListUiModel
import com.example.snapquest.quest.presentation.list.QuestUiModel
import com.example.snapquest.ui.theme.SnapQuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnapQuestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = QuestListRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<QuestListRoute> {
                            QuestListScreen(
                                uiModel = QuestListUiModel.Content(QuestUiModel(1, "Something blue")),
                                onQuestClick = { id ->
                                    navController.navigate(QuestDetailRoute(id))
                                }
                            )
                        }
                        composable<QuestDetailRoute> {
                            QuestDetailScreen(
                                uiModel = QuestDetailUiModel.Content("Something blue"),
                                onBackButtonPressed = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
