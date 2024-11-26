package rezaei.mohammad.jalalidatepickerkmm.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
//    dialogNavigationButton = WaterBlue

)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)
val ColorScheme.dialogNavigationButton: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) WaterBlue else OuterSpaceBlue

val ColorScheme.textColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Black else Color.White

val ColorScheme.textColorHighlight: Color
    @Composable
//    get() = if (!isSystemInDarkTheme()) Color(0xFF55B5FF) else Color(0xFF209EFF)
    get() = if (!isSystemInDarkTheme()) Color(0xFF45AEFF) else Color(0xFF209EFF)

val ColorScheme.selectedIconColor: Color
    @Composable
//    get() = if (!isSystemInDarkTheme()) Color(0xFFC2D6DC) else Color(0xFF5B6E71)
//get() = if (!isSystemInDarkTheme()) Color(0xFFCCC2DC) else Color(0xFF625b71)
    get() = if (!isSystemInDarkTheme()) Color(0xFFC5E5FF) else Color(0xFF1C394E)

val ColorScheme.backgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFFEDF7FF) else Color(0xFF0F1F2B)

@Composable
fun PersianCalendarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}