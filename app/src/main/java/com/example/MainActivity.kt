package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.database.AppDatabase
import com.example.data.repository.AppRepository
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ThemeConfig
import com.example.viewmodel.TribuMentalViewModel
import com.example.viewmodel.TribuMentalViewModelFactory
import android.content.Context
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    ThemeConfig.initialize(applicationContext)
    enableEdgeToEdge()

    // Initialize Firebase safely
    try {
        com.google.firebase.FirebaseApp.initializeApp(applicationContext)
    } catch (e: Exception) {
        android.util.Log.e("MainActivity", "FirebaseApp initialization failed: ${e.message}")
    }

    // Initialize Room Database and Repository Layer
    val database = AppDatabase.getDatabase(applicationContext)
    val appRepository = AppRepository(
      profileDao = database.userProfileDao(),
      moodDao = database.moodCheckInDao(),
      appointmentDao = database.appointmentDao(),
      documentDao = database.medicalDocumentDao(),
      contactDao = database.supportContactDao(),
      whatsappDao = database.whatsAppLogDao()
    )

    val viewModelFactory = TribuMentalViewModelFactory(appRepository)

    setContent {
      MyApplicationTheme {
        val mainViewModel: TribuMentalViewModel = viewModel(factory = viewModelFactory)
        val context = LocalContext.current
        LaunchedEffect(Unit) {
          val prefs = context.getSharedPreferences("tribumental_prefs", Context.MODE_PRIVATE)
          val savedLang = prefs.getString("app_language", "es") ?: "es"
          mainViewModel.setLanguage(savedLang)
        }
        val profileState by mainViewModel.userProfile.collectAsStateWithLifecycle()
        val isAuthLoading by mainViewModel.isAuthLoading.collectAsStateWithLifecycle()
        val isSyncing by com.example.services.FirebaseSyncService.isSyncing.collectAsStateWithLifecycle()
        val activeWompiPlan by mainViewModel.activeWompiPaymentPlan.collectAsStateWithLifecycle()
        val isBreathingActive by mainViewModel.isBreathingActive.collectAsStateWithLifecycle()

        // Adaptive custom state backstack router
        var currentScreen by remember { mutableStateOf("landing") }
        val backStack = remember { mutableStateListOf<String>() }
        
        // Track the selected plan through Auth/Onboarding flow
        var onboardingPreselectedPlan by remember { mutableStateOf("GRATIS") }

        fun navigateTo(screen: String) {
          backStack.add(currentScreen)
          currentScreen = screen
        }

        fun navigateBack() {
          if (backStack.isNotEmpty()) {
            currentScreen = backStack.removeAt(backStack.size - 1)
          } else {
            currentScreen = "landing"
          }
        }

        // Direct linking to document scanner with pre-selected appointment ID
        var preselectedAppointmentIdForScan by remember { mutableStateOf<Int?>(null) }

        // Automatically route to Onboarding or Dashboard if profile is already configured
        LaunchedEffect(profileState) {
          profileState?.let {
            if (it.isOnboarded && currentScreen == "landing") {
              currentScreen = "dashboard"
            }
          }
        }

        Box(modifier = Modifier.fillMaxSize()) {
          Scaffold(
            modifier = Modifier
              .fillMaxSize()
              .testTag("app_scaffold")
          ) { innerPadding ->
            Surface(
              modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
              AnimatedContent(
                targetState = currentScreen,
                label = "screen_transition",
                transitionSpec = {
                  fadeIn() togetherWith fadeOut()
                }
              ) { screen ->
                when (screen) {
                  "landing" -> LandingScreen(
                    onNavigateToOnboarding = {
                      onboardingPreselectedPlan = "GRATIS"
                      if (profileState?.isOnboarded == true) {
                        navigateTo("dashboard")
                      } else {
                        navigateTo("auth")
                      }
                    },
                    onNavigateToDirectUpgrade = { plan ->
                      onboardingPreselectedPlan = plan
                      if (profileState?.isOnboarded == true) {
                        mainViewModel.upgradeSubscription(plan)
                        navigateTo("dashboard")
                      } else {
                        navigateTo("auth")
                      }
                    },
                    profile = profileState,
                    viewModel = mainViewModel
                  )

                  "auth" -> AuthScreen(
                    viewModel = mainViewModel,
                    onAuthSuccess = { email, isRegistered ->
                      if (profileState?.isOnboarded == true) {
                        navigateTo("dashboard")
                      } else {
                        navigateTo("onboarding")
                      }
                    },
                    onBack = { navigateBack() }
                  )

                  "onboarding" -> OnboardingScreen(
                    preselectedPlan = onboardingPreselectedPlan,
                    onFinished = { name, stage, weeks, concern, optWa, freq, plan, age, supportNetwork, previousPregnancies, location ->
                      mainViewModel.completeOnboarding(
                        name, stage, weeks, concern, optWa, freq, plan, age, supportNetwork, previousPregnancies, location
                      )
                      currentScreen = "psytest"
                    },
                    onBack = { navigateBack() }
                  )

                  "psytest" -> TribuPsychologicalTestScreen(
                    viewModel = mainViewModel,
                    onFinished = {
                      currentScreen = "dashboard"
                    }
                  )

                  "dashboard" -> DashboardScreen(
                    viewModel = mainViewModel,
                    onNavigateToCheckIn = { navigateTo("checkin") },
                    onNavigateToCalendar = { navigateTo("calendar") },
                    onNavigateToDocuments = {
                      preselectedAppointmentIdForScan = null
                      navigateTo("documents")
                    },
                    onNavigateToWhatsApp = { navigateTo("whatsapp_logs") },
                    onNavigateToSubscriptions = { navigateTo("subscriptions") },
                    onNavigateEmergency = { navigateTo("emergency") },
                    onNavigateToProfile = { navigateTo("profile") },
                    onNavigateToPsyTest = { navigateTo("psytest") }
                  )

                  "checkin" -> MoodCheckInScreen(
                    viewModel = mainViewModel,
                    onBack = { navigateBack() }
                  )

                  "calendar" -> CalendarControlScreen(
                    viewModel = mainViewModel,
                    onBack = { navigateBack() },
                    onAttachDocumentPrompt = { appointmentId ->
                      preselectedAppointmentIdForScan = appointmentId
                      navigateTo("documents")
                    }
                  )

                  "documents" -> DocumentsScannerScreen(
                    viewModel = mainViewModel,
                    onBack = { navigateBack() },
                    prefilledAppointmentId = preselectedAppointmentIdForScan
                  )

                  "whatsapp_logs" -> WhatsAppLogScreen(
                    viewModel = mainViewModel,
                    onBack = { navigateBack() }
                  )

                  "subscriptions" -> SubscriptionsCenterScreen(
                    viewModel = mainViewModel,
                    onBack = { navigateBack() }
                  )

                  "emergency" -> EmergencyScreen(
                    viewModel = mainViewModel,
                    onBack = { navigateBack() }
                  )

                  "profile" -> ProfileScreen(
                    viewModel = mainViewModel,
                    onBack = { navigateBack() },
                    onRetakeTest = { navigateTo("psytest") },
                    onLogout = { navigateTo("auth") }
                  )
                }
              }
            }
          }

          // Global loading spinner overlay for Authentication and Firestore Realtime Sync
          if (isAuthLoading || isSyncing) {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.56f))
                .testTag("global_loading_overlay"),
              contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
              Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                  .padding(32.dp)
                  .widthIn(max = 280.dp)
                  .testTag("global_loading_card")
              ) {
                Column(
                  modifier = Modifier.padding(24.dp),
                  horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp,
                    modifier = Modifier
                      .size(48.dp)
                      .testTag("global_loading_spinner")
                  )
                  Spacer(modifier = Modifier.height(20.dp))
                  Text(
                    text = if (isAuthLoading) "Autenticando..." else "Sincronizando...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.testTag("global_loading_title")
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = if (isAuthLoading) "Estamos validando tus credenciales con Firebase de forma segura." else "Tu perfil y registros se están guardando de forma encriptada en Firestore.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier
                      .padding(horizontal = 4.dp)
                      .testTag("global_loading_subtitle")
                  )
                }
              }
            }
          }

          // Wompi Interactive Gateway Checkout Overlay
          activeWompiPlan?.let { plan ->
             WompiCheckoutDialog(
                planName = plan,
                onDismiss = { mainViewModel.cancelWompiPayment() },
                onPaymentSuccess = { txId, method ->
                   mainViewModel.completeWompiPayment(plan)
                }
             )
          }

          // Interactive Breathing Exercise with AI Guide Overlay
          if (isBreathingActive) {
             InteractiveBreathingDialog(
                viewModel = mainViewModel,
                onDismiss = { mainViewModel.setBreathingActive(false) }
             )
          }
        }
      }
    }
  }
}
