package alinarhi.melody_transcription.ui.screen

import alinarhi.melody_transcription.service.AudioPlayerManager
import alinarhi.melody_transcription.service.AudioRecorderManager
import alinarhi.melody_transcription.R
import alinarhi.melody_transcription.ui.component.AudioPlayer
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChooseAudioScreen(
    playerManager: AudioPlayerManager,
    recorderManager: AudioRecorderManager,
    onAudioChosen: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    var audioUri by remember { mutableStateOf<Uri?>(null) }
    var showPlayer by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            playerManager.setAudio(it)
            audioUri = it
            showPlayer = true
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text="Melody Transcription",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
        )
        if (audioUri != null && showPlayer) {
            AudioPlayer(playerManager.player, Modifier
                .fillMaxWidth()
                .aspectRatio(16/9f)
                .background(Color.Red, RoundedCornerShape(4.dp))
            )
            Spacer(Modifier.size(4.dp))
            ExtendedFloatingActionButton(onClick = { onAudioChosen(audioUri!!) }) {
                Icon(painter = painterResource(R.drawable.baseline_audio_24), contentDescription = "Audio")
                Text("Transcribe!")
            }
            Spacer(Modifier.size(10.dp))
        }
//        IconButton(onClick = { /* todo */ }, modifier=Modifier.scale(1f)) {
//            Icon(
//                painter = painterResource(R.drawable.baseline_mic_24),
//                contentDescription = "Record",
//                tint = MaterialTheme.colorScheme.primary,
//            )
//        }
        TextButton(onClick = { launcher.launch(arrayOf("audio/*")) }) {
            Text("Open a file")
        }
    }
}

