package com.example.newspaperapp.ui.theme

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
    tertiary = Pink40


)

val LightColorScheme2 = lightColorScheme(
    primary = Color.Black,         // Màu chính
    onPrimary = Color(0xFFD9D9D9),       // Màu của nội dung hiển thị trên primary
    primaryContainer = Color(0xFFBB86FC), // Màu nền cho các phần tử chứa nội dung primary
    onPrimaryContainer = Color(0xFF3700B3), // Màu nội dung bên trong primaryContainer

    secondary = Color(0xFF0319DA),       // Màu phụ
    onSecondary = Color(0xFF000000),     // Màu của nội dung hiển thị trên secondary
    secondaryContainer = Color(0xFF018786), // Màu nền cho secondaryContainer
    onSecondaryContainer = Color(0xFFFFFFFF), // Màu nội dung bên trong secondaryContainer

    tertiary = Color(0xFF6200EA),        // Màu bổ sung (thường dùng cho accent hoặc UI phụ)
    onTertiary = Color(0xFFFFFFFF),      // Màu của nội dung hiển thị trên tertiary
    tertiaryContainer = Color(0xFF3700B3), // Màu nền cho tertiaryContainer
    onTertiaryContainer = Color(0xFFFFFFFF), // Màu nội dung bên trong tertiaryContainer

    background = Color(0xFFFFFFFF),      // Màu nền chính của ứng dụng
    onBackground = Color(0xFF000000),    // Màu của nội dung hiển thị trên background

    surface = Color(0xFFFFFFFF),         // Màu nền của các thành phần UI (Card, Dialog, ...)
    onSurface = Color(0xFF000000),       // Màu nội dung hiển thị trên surface
    surfaceVariant = Color(0xFFE0E0E0),  // Biến thể của surface (dùng để phân biệt giữa nhiều layers)
    onSurfaceVariant = Color(0xFF424242), // Màu nội dung trên surfaceVariant

    error = Color(0xFFB00020),           // Màu dùng cho thông báo lỗi
    onError = Color(0xFFFFFFFF),         // Màu nội dung hiển thị trên error
    errorContainer = Color(0xFFFFCDD2),  // Màu nền khi có lỗi
    onErrorContainer = Color(0xFFB00020), // Màu nội dung trong errorContainer

    outline = Color(0xFFD90000),         // Màu đường viền cho các phần tử
    outlineVariant = Color(0xFFBDBDBD),  // Biến thể của outline

    inverseSurface = Color(0xFF121212),  // Màu đối lập của surface (dùng cho Dark Mode)
    inverseOnSurface = Color(0xFFFFFFFF), // Màu nội dung hiển thị trên inverseSurface
    inversePrimary = Color(0xFFBB86FC),  // Màu primary khi ở chế độ đối lập (Dark Mode)

    scrim = Color(0xFF000000),           // Màu overlay (dùng cho hiệu ứng mờ trong modal)
)

val DarkColorScheme2 = darkColorScheme(
    primary = green,         // Màu chính trong chế độ tối
    onPrimary = Color(0xFFF9F9FA),       // Màu chữ/icon trên primary
    primaryContainer = Color(0xFF6200EE),
    onPrimaryContainer = Color(0xFFFFFFFF),

    secondary = Color(0xFF03DAC5),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFF018786),
    onSecondaryContainer = Color(0xFFFFFFFF),

    tertiary = Color(0xFFCF6679),
    onTertiary = Color(0xFF000000),
    tertiaryContainer = Color(0xFFB00020),
    onTertiaryContainer = Color(0xFFFFFFFF),

    background = Color(0xFF121212),      // Màu nền tối
    onBackground = Color(0xFFFFFFFF),    // Màu chữ trên nền tối

    surface = Color(0xFF121212),         // Màu nền của các thành phần UI
    onSurface = Color(0xFFFFFFFF),       // Màu chữ hiển thị trên surface
    surfaceVariant = Color(0xFF424242),
    onSurfaceVariant = Color(0xFFE0E0E0),

    error = Color(0xFFCF6679),
    onError = Color(0xFF000000),
    errorContainer = Color(0xFFB00020),
    onErrorContainer = Color(0xFFFFFFFF),

    outline = Color(0xFFBDBDBD),
    outlineVariant = Color(0xFF757575),

    inverseSurface = Color(0xFFFFFFFF),  // Đối lập với surface
    inverseOnSurface = Color(0xFF121212),
    inversePrimary = Color(0xFF3700B3),

    scrim = Color(0xFF000000)

)


@Composable
fun NewsPaperAppTheme2(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {

        darkTheme -> DarkColorScheme2
        else -> LightColorScheme2
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
@Composable
fun NewsPaperAppTheme(
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
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}