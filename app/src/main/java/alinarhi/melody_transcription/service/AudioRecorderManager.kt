package alinarhi.melody_transcription.service

import android.content.Context
import android.media.MediaRecorder
import android.media.MediaRecorder.AudioSource
import android.os.Build
import java.io.File

class AudioRecorderManager(private val context: Context) {
    private val audioRecorder =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MediaRecorder(context) else MediaRecorder()

    val outputFile: File = File.createTempFile("recorded_audio", "", context.cacheDir)


    fun start() {
        audioRecorder.apply {
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioSource(AudioSource.UNPROCESSED)
            setOutputFile(outputFile)
            start()
        }
    }

    fun stop() {
        audioRecorder.stop()
    }

    fun release() {
        audioRecorder.release()
        outputFile.delete()
    }
}