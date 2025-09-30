package alinarhi.melody_transcription.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class NoteEvent(
    val midi: Int,
    val onset: Long, // ms
    val duration: Long, // ms
    @Transient
    val offset: Long = onset + duration
)
