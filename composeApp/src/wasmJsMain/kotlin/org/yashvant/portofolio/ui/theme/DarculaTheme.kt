package org.yashvant.portofolio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object DarculaColors {
    // Main background colors - making them darker and more modern
    val Background = Color(0xFF1E1E1E)
    val Surface = Color(0xFF252526)
    val SurfaceVariant = Color(0xFF2D2D30)

    // Text colors
    val OnBackground = Color(0xFFD4D4D4)
    val OnSurface = Color(0xFFCCCCCC)

    // Syntax highlighting colors
    val Keyword = Color(0xFF569CD6)
    val String = Color(0xFF6A9955)
    val Comment = Color(0xFF6A9955)
    val Number = Color(0xFFB5CEA8)
    val Type = Color(0xFF4EC9B0)

    // UI element colors
    val Primary = Color(0xFF007ACC)
    val Secondary = Color(0xFF37373D)

    // Tab colors
    val TabActive = Color(0xFF1E1E1E)
    val TabInactive = Color(0xFF2D2D30)
    val TabBorder = Color(0xFF3E3E42)

    // File icon colors
    val FolderIcon = Color(0xFFDCB67A)
    val KotlinFileIcon = Color(0xFF7F52FF)
    val MarkdownFileIcon = Color(0xFF0366D6)

    // Status bar
    val StatusBar = Color(0xFF007ACC)
}

private val DarkColorScheme = darkColorScheme(
    primary = DarculaColors.Primary,
    onPrimary = Color.White,
    primaryContainer = DarculaColors.Primary,
    onPrimaryContainer = Color.White,
    secondary = DarculaColors.Secondary,
    onSecondary = Color.White,
    background = DarculaColors.Background,
    onBackground = DarculaColors.OnBackground,
    surface = DarculaColors.Surface,
    onSurface = DarculaColors.OnSurface,
    surfaceVariant = DarculaColors.SurfaceVariant,
    onSurfaceVariant = DarculaColors.OnSurface
)

@Composable
fun DarculaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography(),
        content = content
    )
}
