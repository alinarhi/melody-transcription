package alinarhi.melody_transcription.ui.component

import alinarhi.melody_transcription.R
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.ARTWORK_DISPLAY_MODE_FIT

@OptIn(UnstableApi::class)
@Composable
fun AudioPlayer(player: Player, modifier: Modifier = Modifier) {
    AndroidView(
        factory = {
                context ->
            PlayerView(context).apply {
                this.player = player
                artworkDisplayMode = ARTWORK_DISPLAY_MODE_FIT
//                defaultArtwork = ContextCompat.getDrawable(context, R.drawable.baseline_audio_file_24)
            }
        },
        modifier = modifier
    )
}