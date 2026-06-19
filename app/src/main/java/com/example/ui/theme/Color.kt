package com.example.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import android.content.Context

object ThemeConfig {
    var themeMode by mutableStateOf("light") // Forced light theme
    var isDarkMode by mutableStateOf(false)   // Always false to eliminate dark mode

    fun initialize(context: Context) {
        themeMode = "light"
        isDarkMode = false
    }

    fun updateIsDarkMode(context: Context) {
        themeMode = "light"
        isDarkMode = false
    }

    fun saveThemeMode(context: Context, mode: String) {
        themeMode = "light"
        isDarkMode = false
    }

    fun copyUriToLocalStorage(context: Context, uri: android.net.Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val file = java.io.File(context.filesDir, "custom_avatar.png")
            val outputStream = java.io.FileOutputStream(file)
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

// TribuMental Signature Natural Tones Color Scheme for Mothers
val CreamBackground: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFF1E1A17) else Color(0xFFFCF9F6) // Warm soothing cream backplate bg-[#FCF9F6]

val SoftLila: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFFE0A96D) else Color(0xFFF2CC8F) // Warm Sand Accent #F2CC8F

val PowderPink: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFF28231F) else Color(0xFFF8F4F0) // Soft neutral warm stone bg-[#F8F4F0]

val SageGreen: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFF2B3A36) else Color(0xFFE9F0EE) // Safe healing soft sage bg-[#E9F0EE]

// Interactive & Primary Tone Details
val PrimaryDeepPurple: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFFF5EFE9) else Color(0xFF3D3834) // Dark slate headings text-[#3D3834]

val SecondaryDustPink: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFFE78F78) else Color(0xFFE07A5F) // Soft organic coral text-[#E07A5F]

val TerciaryWarmTeal: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFF98B3AC) else Color(0xFF84A59D) // Calming modern master-teal text-[#84A59D]

// Neutral Tonal Values (Slightly tinted to avoid raw clinical black/gray)
val WarmCardWhite: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFF2A2420) else Color(0xFFFFFFFF) // Clean white cards bg-white

val DeepCharcoalText: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFFE8E1DA) else Color(0xFF4A443F) // Comforting deep charcoal body text text-[#4A443F]

val MutedSlateSub: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFFCDC4B9) else Color(0xFF8A8279) // Secondary warm gray labels with opacity

val SoftBorderPlum: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFF3D3530) else Color(0xFFF2EDE7) // Soft warm sand details/borders border-[#F2EDE7]

val AlertSoftRed: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFFE78F78) else Color(0xFFE07A5F) // Warm sunset alert indicator

val MoodVibrantGreen: Color
    get() = if (ThemeConfig.isDarkMode) Color(0xFF98B3AC) else Color(0xFF84A59D) // Sage healing positive wellness state
