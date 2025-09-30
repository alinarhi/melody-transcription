package alinarhi.melody_transcription.ui.component

import alinarhi.melody_transcription.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ErrorMessage(message: String = "Unexpected error", modifier: Modifier = Modifier) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_error_24),
            colorFilter = ColorFilter.tint(Color.Gray),
            contentDescription = "error",
            modifier = Modifier.requiredSize(20.dp)
        )
        Spacer(Modifier.size(4.dp))
        Text(message, color = Color.Gray)
    }
}