package alinarhi.melody_transcription.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun PianoKeyboard(
    modifier: Modifier = Modifier,
    startMidi: Int = 48, // C3
    endMidi: Int = 83, // B5
    activeMidi: Int? = null,
) {
    val keys = remember(startMidi, endMidi) {
        (startMidi until endMidi + 1).map { midi -> PianoKey(midi) }
    }

    BoxWithConstraints(modifier.aspectRatio(4f)) {
        val whiteKeys = keys.filter { it.isWhite }
        val whiteKeyWidth = maxWidth / whiteKeys.size
        val whiteKeyHeight = maxHeight
        val blackKeyWidth = whiteKeyWidth * 0.6f
        val blackKeyHeight = maxHeight * 0.6f

        Row(Modifier.fillMaxWidth()) {
            whiteKeys.forEach { key ->
                val isHighlighted = key.midi == activeMidi
                Box(
                    modifier = Modifier
                        .width(whiteKeyWidth)
                        .height(whiteKeyHeight)
                        .background(
                            if (isHighlighted) Color.Green else Color.White
                        )
                        .border(Dp.Hairline, Color.DarkGray, RoundedCornerShape(2.dp))
                        .clickable {}
                ) {
                    Text(
                        key.toLetterNotation(),
                        Modifier
                            .align(Alignment.BottomCenter),
                        color = Color.DarkGray,
                        fontSize = with(LocalDensity.current) {(whiteKeyWidth * 0.4f).toSp()},
                        fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
        var whitesCount = 0
        keys.forEach { key ->
            if (key.isWhite) {
                whitesCount++
            } else {
                val offset = whitesCount * whiteKeyWidth - blackKeyWidth * 0.5f
                val isHighlighted = key.midi == activeMidi
                Box(
                    modifier = Modifier
                        .offset(x = offset)
                        .width(blackKeyWidth)
                        .height(blackKeyHeight)
                        .background(if (isHighlighted) Color.Green else Color.Black, RoundedCornerShape(2.dp))
                        .clickable { }
                )
            }
        }
    }
}

data class PianoKey(val midi: Int) {
    val pitchClass = midi % 12
    val octave = midi / 12 - 1
    val isWhite = pitchClass in listOf(0, 2, 4, 5, 7, 9, 11) // C, D, E, F, G, A, B
//    val hasBlackAfter = pitchClass in listOf(0, 2, 5, 7, 9) // C, D, F, G, A

    fun toLetterNotation(): String {
        return "${LETTERS[pitchClass]}${octave}"
    }

    companion object {
        private val LETTERS = listOf(
            "C", "C#/Db", "D", "D#/Eb", "E", "F",
            "F#/Gb", "G", "G#/Ab", "A", "A#/Bb", "B"
        )
    }
}