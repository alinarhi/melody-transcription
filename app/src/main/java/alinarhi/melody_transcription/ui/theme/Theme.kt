package alinarhi.melody_transcription.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

//    background = Color(0xFFC2C7F1),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),

)

private val BlueColorScheme = lightColorScheme(
    primary = Color(0xFF1565C0),        // Deep Blue
    onPrimary = Color.White,            // Text/icons on primary
    primaryContainer = Color(0xFF90CAF9), // Lighter blue container
    onPrimaryContainer = Color(0xFF0D47A1),

    secondary = Color(0xFF0288D1),      // Cyan/blue secondary
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB3E5FC),
    onSecondaryContainer = Color(0xFF01579B),

    tertiary = Color(0xFF3EC6D7),       // Teal-ish accent
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF80DEEA),
    onTertiaryContainer = Color(0xFF004D40),

    error = Color(0xFFB00020),          // Default Material error
    onError = Color.White,
    errorContainer = Color(0xFFFCD8DF),
    onErrorContainer = Color(0xFF370617),

    background = Color(0xFFFDFEFF),     // Almost white with blue hint
    onBackground = Color(0xFF1B1B1B),

    surface = Color(0xFFF8FBFF),        // Slightly tinted surface
    onSurface = Color(0xFF1B1B1B),
    surfaceVariant = Color(0xFFE3F2FD), // Muted light blue for surfaces
    onSurfaceVariant = Color(0xFF455A64),

    outline = Color(0xFF90A4AE),        // Subtle blue-gray outline
    outlineVariant = Color(0xFFB0BEC5),
)

@Composable
fun MelodyTranscriptionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> BlueColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}