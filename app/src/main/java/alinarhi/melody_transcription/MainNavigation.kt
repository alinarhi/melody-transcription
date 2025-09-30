package alinarhi.melody_transcription

import alinarhi.melody_transcription.service.AudioPlayerManager
import alinarhi.melody_transcription.service.AudioRecorderManager
import alinarhi.melody_transcription.ui.screen.ChooseAudioScreen
import alinarhi.melody_transcription.ui.screen.PlaybackWithKeyboardScreen
import alinarhi.melody_transcription.viewmodel.ViewModelWithPlayerFactory
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainNavigation(playerManager: AudioPlayerManager, recorderManager: AudioRecorderManager) {
    val navController = rememberNavController()
    val context = LocalContext.current


    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            ChooseAudioScreen(
                playerManager = playerManager,
                onAudioChosen = { uri ->
                    playerManager.pause()
                    navController.navigate("main?uri=${Uri.encode(uri.toString())}")
                },
                recorderManager = recorderManager,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            )
        }
        composable("main?uri={uri}",
            arguments = listOf(
                navArgument("uri") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val factory = remember(context, playerManager) {
                ViewModelWithPlayerFactory(playerManager, context)
            }

            PlaybackWithKeyboardScreen(
                viewModel = viewModel(
                    backStackEntry,
                    factory = factory
                ),
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}