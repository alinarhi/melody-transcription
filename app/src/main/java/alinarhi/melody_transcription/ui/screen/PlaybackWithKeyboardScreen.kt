package alinarhi.melody_transcription.ui.screen

import alinarhi.melody_transcription.ui.component.AudioPlayer
import alinarhi.melody_transcription.ui.component.ErrorMessage
import alinarhi.melody_transcription.ui.component.LoadingIndicator
import alinarhi.melody_transcription.ui.component.PianoKeyboard
import alinarhi.melody_transcription.viewmodel.MelodyTranscriptionUiState
import alinarhi.melody_transcription.viewmodel.MelodyTranscriptionViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun PlaybackWithKeyboardScreen(
    viewModel: MelodyTranscriptionViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val activeMidi by viewModel.activeNoteMidi.collectAsState()

    when (uiState) {
        is MelodyTranscriptionUiState.Loading -> LoadingIndicator(Modifier.fillMaxSize())
        is MelodyTranscriptionUiState.Error -> ErrorMessage(
            (uiState as MelodyTranscriptionUiState.Error).message ?: "Unexpected error",
            Modifier.fillMaxSize()
        )

        is MelodyTranscriptionUiState.Success -> {
            Column {
                PianoKeyboard(activeMidi = activeMidi)
                AudioPlayer(viewModel.player)
            }
        }
    }
}




