package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.ui.theme.ThemeConfig
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("TribuMental", appName)
  }

  @Test
  fun `test ThemeConfig saves and updates dark mode correctly`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    
    // ThemeConfig must always remain on light mode to eliminate dark mode
    ThemeConfig.saveThemeMode(context, "light")
    assertEquals("light", ThemeConfig.themeMode)
    assertEquals(false, ThemeConfig.isDarkMode)
    
    // Saving dark mode should still resolve to light mode
    ThemeConfig.saveThemeMode(context, "dark")
    assertEquals("light", ThemeConfig.themeMode)
    assertEquals(false, ThemeConfig.isDarkMode)
    
    // Saving system mode should still resolve to light mode
    ThemeConfig.saveThemeMode(context, "system")
    assertEquals("light", ThemeConfig.themeMode)
    assertEquals(false, ThemeConfig.isDarkMode)
  }
}
