package alinarhi.melody_transcription.data

import alinarhi.melody_transcription.data.network.MelodyTranscriptionApi
import android.content.Context
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream


class MelodyTranscriptionRepository(private val context: Context) {

    suspend fun transcribeMelody(audioFile: File): List<NoteEvent> {
        try {
            return MelodyTranscriptionApi.service.transcribeMelody(
                MultipartBody.Part.createFormData(
                    "audio",
                    audioFile.name,
                    audioFile.asRequestBody("audio".toMediaTypeOrNull())
                )
            )
        } catch (e: Exception) {
            Log.e("network", e.message, e)
            throw Exception("Network error: "+e.message)
        }
    }

    suspend fun transcribeMelody(audioUri: Uri): List<NoteEvent> {
        var tempFile: File? = null
        try {
            context.contentResolver.openInputStream(audioUri).use { inputStream ->
                tempFile = File.createTempFile("upload_audio", null, context.cacheDir)
                tempFile!!.outputStream().use { output ->
                    inputStream!!.copyTo(output)
                }
                return transcribeMelody(tempFile!!)
            }
        } catch (e: FileNotFoundException) {
            Log.e("file", e.message, e)
            throw Exception("Audio file not found")
        } catch (e: IOException) {
            Log.e("file", e.message, e)
            throw Exception("Error while copying file")
        }
        finally {
            tempFile?.delete()
        }
    }

    suspend fun transcribeMelody(audioStream: InputStream): List<NoteEvent> {
        try {
            return MelodyTranscriptionApi.service.transcribeMelody(
                MultipartBody.Part.createFormData(
                    "audio",
                    "uploaded",
                    audioStream.readBytes().toRequestBody("audio".toMediaTypeOrNull())
                )
            )
        } catch (e: Exception) {
            Log.e("network", e.message, e)
            throw Exception("Network error")
        }
    }
}