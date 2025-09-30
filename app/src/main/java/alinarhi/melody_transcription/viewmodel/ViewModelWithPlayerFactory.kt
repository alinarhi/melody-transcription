package alinarhi.melody_transcription.viewmodel

import alinarhi.melody_transcription.service.AudioPlayerManager
import alinarhi.melody_transcription.data.MelodyTranscriptionRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras

class ViewModelWithPlayerFactory(
    private val playerManager: AudioPlayerManager,
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        if (modelClass.isAssignableFrom(MelodyTranscriptionViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return MelodyTranscriptionViewModel(
                playerManager = playerManager,
                repository = MelodyTranscriptionRepository(context),
                savedStateHandle = extras.createSavedStateHandle()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


