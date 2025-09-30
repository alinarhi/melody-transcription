package alinarhi.melody_transcription

import alinarhi.melody_transcription.service.AudioPlayerManager
import alinarhi.melody_transcription.service.AudioRecorderManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import alinarhi.melody_transcription.ui.theme.MelodyTranscriptionTheme


class MainActivity : ComponentActivity() {
    private lateinit var audioPlayerManager: AudioPlayerManager
    private lateinit var audioRecorderManager: AudioRecorderManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        audioPlayerManager = AudioPlayerManager(this)
        audioRecorderManager = AudioRecorderManager(this)
        setContent {
            MelodyTranscriptionTheme {
                MainNavigation(audioPlayerManager, audioRecorderManager)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        audioRecorderManager.release()
        audioPlayerManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayerManager.release()
    }
}


