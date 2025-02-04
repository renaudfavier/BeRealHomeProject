package com.example.snapquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snapquest.quest.presentation.detail.DetailViewModel
import com.example.snapquest.quest.presentation.detail.QuestDetailScreen
import com.example.snapquest.quest.presentation.fullscreen.FullScreenImageScreen
import com.example.snapquest.quest.presentation.fullscreen.FullScreenViewModel
import com.example.snapquest.quest.presentation.list.ListViewModel
import com.example.snapquest.quest.presentation.list.QuestListScreen
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
                            val listViewModel = hiltViewModel<ListViewModel>()
                            val uiModel by listViewModel.uiState.collectAsStateWithLifecycle()

                            QuestListScreen(
                                uiModel = uiModel,
                                onQuestClick = { id ->
                                    navController.navigate(QuestDetailRoute(id))
                                }
                            )
                        }
                        composable<QuestDetailRoute> {
                            val detailViewModel = hiltViewModel<DetailViewModel>()
                            val uiModel by detailViewModel.uiState.collectAsStateWithLifecycle()

                            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.PickVisualMedia(),
                            ) {
                                detailViewModel.onPhotoSubmitted(it)
                            }

                            QuestDetailScreen(
                                uiModel = uiModel,
                                onBackButtonPressed = {
                                    navController.navigateUp()
                                },
                                onPhotoPressed = { id -> navController.navigate(FullScreenRoute(id))},
                                onSubmitPhotoPressed = {
                                    singlePhotoPickerLauncher.launch(
                                        input = PickVisualMediaRequest(ImageOnly)
                                    )
                                }
                            )
                        }

                        composable<FullScreenRoute> {

                            val fullscreenViewModel = hiltViewModel<FullScreenViewModel>()
                            val uiModel by fullscreenViewModel.uiState.collectAsStateWithLifecycle()
                            
                            FullScreenImageScreen(
                                uiModel = uiModel,
                                onBackPressed = {
                                    navController.navigateUp()
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
