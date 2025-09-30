package alinarhi.melody_transcription.fake

import alinarhi.melody_transcription.data.NoteEvent

object FakeDataSource {
    val noteEvents = listOf(
        NoteEvent(
            midi = 64,
            onset = 1000,
            duration = 1000
        ),
        NoteEvent(
            midi = 67,
            onset = 2000,
            duration = 2000
        ),
        NoteEvent(
            midi = 60,
            onset = 4000,
            duration = 1200
        ),
    )
}