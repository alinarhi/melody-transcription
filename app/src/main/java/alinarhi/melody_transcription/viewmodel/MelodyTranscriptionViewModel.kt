package alinarhi.melody_transcription.viewmodel

import alinarhi.melody_transcription.service.AudioPlayerManager
import alinarhi.melody_transcription.service.AudioPlayerEvent
import alinarhi.melody_transcription.data.MelodyTranscriptionRepository
import alinarhi.melody_transcription.data.NoteEvent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MelodyTranscriptionViewModel(
    private val playerManager: AudioPlayerManager,
    private val repository: MelodyTranscriptionRepository,
    savedStateHandle: SavedStateHandle

) : ViewModel() {
    private val uriArg: String? = savedStateHandle["uri"]

    private var _uiState: MutableStateFlow<MelodyTranscriptionUiState> =
        MutableStateFlow(MelodyTranscriptionUiState.Loading)
    val uiState: StateFlow<MelodyTranscriptionUiState> = _uiState

    private val _activeNoteMidi = MutableStateFlow<Int?>(null)
    val activeNoteMidi: StateFlow<Int?> = _activeNoteMidi

    // Exposed for usage in PlayerView ONLY! All logic is encapsulated in playerManager
    val player = playerManager.player

    private var noteEvents: List<NoteEvent> = emptyList()

    init {
        uriArg?.let { uriString ->
            val uri = Uri.parse(uriString)
            transcribeMelody(uri)
        }
    }

    fun transcribeMelody(audioUri: Uri) {
        viewModelScope.launch {
            try {
                noteEvents = repository.transcribeMelody(audioUri)
                Log.i("transcription", "${noteEvents.size} note events received")
                configureSyncWithPlayback()
                _uiState.value = MelodyTranscriptionUiState.Success
            } catch (e: Exception) {
                _uiState.value = MelodyTranscriptionUiState.Error(e.message)
            }
        }
    }

    private fun configureSyncWithPlayback() {
        noteEvents.forEach { note ->
            playerManager.scheduleEventAtPosition(note.onset) {
                _activeNoteMidi.value = note.midi
            }
            playerManager.scheduleEventAtPosition(note.offset) {
                _activeNoteMidi.value = null
            }
        }

        viewModelScope.launch {
            playerManager.playbackEvents.collect { event ->
                when (event) {
                    is AudioPlayerEvent.PositionForcedChanged -> {
                        _activeNoteMidi.value = null
                        playerManager.cancelScheduledEvents()

                        noteEvents
                            .filter { it.onset >= event.positionMs }
                            .forEach { note ->
                                playerManager.scheduleEventAtPosition(
                                    if (note.offset < event.positionMs) event.positionMs else note.onset
                                ) {
                                    _activeNoteMidi.value = note.midi
                                }
                                playerManager.scheduleEventAtPosition(
                                    note.offset
                                ) {
                                    _activeNoteMidi.value = null
                                }
                            }
                    }

                    is AudioPlayerEvent.Ended -> _activeNoteMidi.value = null
                }
            }
        }

    }

}

sealed interface MelodyTranscriptionUiState {
    data object Success : MelodyTranscriptionUiState
    data class Error(val message: String?) : MelodyTranscriptionUiState
    data object Loading : MelodyTranscriptionUiState
}