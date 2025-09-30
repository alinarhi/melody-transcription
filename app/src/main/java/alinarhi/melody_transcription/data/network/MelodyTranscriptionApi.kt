package alinarhi.melody_transcription.data.network

import alinarhi.melody_transcription.data.NoteEvent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

interface MelodyTranscriptionApi {
    @Multipart
    @POST("/transcribe")
    suspend fun transcribeMelody(
        @Part audioContent: MultipartBody.Part
    ): List<NoteEvent>

    companion object {
        private val client: OkHttpClient by lazy {
            OkHttpClient.Builder()
                // increased timeouts because of file uploading (write) & transcription (read)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build()
        }
        val service: MelodyTranscriptionApi by lazy {
            Retrofit.Builder()
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .client(client)
                .baseUrl("http://10.0.2.2:8000")
                .build()
                .create(MelodyTranscriptionApi::class.java)
        }
    }
}