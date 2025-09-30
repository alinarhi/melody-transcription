package alinarhi.melody_transcription.service

import android.content.Context
import android.net.Uri
import android.os.Looper
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.PlayerMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AudioPlayerManager(context: Context) {
    val player: ExoPlayer = ExoPlayer.Builder(context)
        .setHandleAudioBecomingNoisy(true)
        .build()
    private val _playerMessages = mutableListOf<PlayerMessage>()

    private val _playbackEvents = MutableSharedFlow<AudioPlayerEvent>(replay = 1)
    val playbackEvents: SharedFlow<AudioPlayerEvent> = _playbackEvents.asSharedFlow()

    init {
        player.addListener(object : Player.Listener {
            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                _playbackEvents.tryEmit(AudioPlayerEvent.PositionForcedChanged(newPosition.positionMs))
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    _playbackEvents.tryEmit(AudioPlayerEvent.Ended)
                }
            }
        })
    }

    @OptIn(UnstableApi::class)
    fun scheduleEventAtPosition(positionMs: Long, event: () -> Unit) {
        val msg = player.createMessage { _, _ -> event() }.setLooper(Looper.getMainLooper())
            .setPosition(positionMs).setDeleteAfterDelivery(true).send()
        _playerMessages.add(msg)
    }

    @OptIn(UnstableApi::class)
    fun cancelScheduledEvents() {
        _playerMessages.forEach { msg -> msg.cancel() }
        _playerMessages.clear()
    }

    fun setAudio(uri: Uri) {
        player.setMediaItem(MediaItem.fromUri(uri))
        player.prepare()
    }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun release() {
        cancelScheduledEvents()
        player.release()
    }
}

sealed interface AudioPlayerEvent {
    data class PositionForcedChanged(val positionMs: Long) : AudioPlayerEvent
    data object Ended : AudioPlayerEvent
}