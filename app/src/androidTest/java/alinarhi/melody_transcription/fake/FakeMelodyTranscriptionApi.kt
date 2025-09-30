package alinarhi.melody_transcription.fake

import alinarhi.melody_transcription.data.NoteEvent
import alinarhi.melody_transcription.data.network.MelodyTranscriptionApi
import okhttp3.MultipartBody

class FakeMelodyTranscriptionApi : MelodyTranscriptionApi {
    override suspend fun transcribeMelody(audioFile: MultipartBody.Part): List<NoteEvent> {
        return FakeDataSource.noteEvents
    }
}