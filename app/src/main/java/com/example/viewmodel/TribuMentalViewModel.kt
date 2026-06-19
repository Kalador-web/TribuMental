package com.example.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.UserProfile
import com.example.data.model.MoodCheckIn
import com.example.data.model.Appointment
import com.example.data.model.MedicalDocument
import com.example.data.model.SupportContact
import com.example.data.model.WhatsAppMessageLog
import com.example.data.repository.AppRepository
import com.example.services.GeminiService
import com.example.services.WhatsAppService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TribuMentalViewModel(private val repository: AppRepository) : ViewModel() {

    // App language preference state ("es" or "en")
    private val _appLanguage = MutableStateFlow("es")
    val appLanguage: StateFlow<String> = _appLanguage.asStateFlow()

    fun setLanguage(lang: String) {
        _appLanguage.value = lang
    }

    // Reactive streams from Database
    val userProfile: StateFlow<UserProfile?> = repository.profile
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val moodHistory: StateFlow<List<MoodCheckIn>> = repository.allMoods
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val appointments: StateFlow<List<Appointment>> = repository.allAppointments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val documents: StateFlow<List<MedicalDocument>> = repository.allDocuments
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val contacts: StateFlow<List<SupportContact>> = repository.allContacts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val whatsappLogs: StateFlow<List<WhatsAppMessageLog>> = repository.allWhatsAppLogs
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Wompi simulated gateway integration state
    private val _activeWompiPaymentPlan = MutableStateFlow<String?>(null)
    val activeWompiPaymentPlan: StateFlow<String?> = _activeWompiPaymentPlan.asStateFlow()

    fun initiateWompiPayment(planName: String) {
        _activeWompiPaymentPlan.value = planName
    }

    fun cancelWompiPayment() {
        _activeWompiPaymentPlan.value = null
    }

    fun completeWompiPayment(planName: String) {
        _activeWompiPaymentPlan.value = null
        upgradeSubscription(planName)
    }

    // Interactive Breathing Exercise States
    private val _isBreathingActive = MutableStateFlow(false)
    val isBreathingActive: StateFlow<Boolean> = _isBreathingActive.asStateFlow()

    private val _breathingAdviceResult = MutableStateFlow<String?>(null)
    val breathingAdviceResult: StateFlow<String?> = _breathingAdviceResult.asStateFlow()

    private val _isAnalyzingBreathingAnswers = MutableStateFlow(false)
    val isAnalyzingBreathingAnswers: StateFlow<Boolean> = _isAnalyzingBreathingAnswers.asStateFlow()

    fun setBreathingActive(active: Boolean) {
        _isBreathingActive.value = active
        if (!active) {
            _breathingAdviceResult.value = null
        }
    }

    fun analyzeBreathingAnswers(antesSentimiento: String, retencionSensacion: String, calificacionMejora: Int) {
        viewModelScope.launch {
            _isAnalyzingBreathingAnswers.value = true
            val feedback = GeminiService.analyzeBreathingSessionFeedback(
                antesSentimiento = antesSentimiento,
                retencionSensacion = retencionSensacion,
                calificacionMejora = calificacionMejora
            )
            _breathingAdviceResult.value = feedback
            _isAnalyzingBreathingAnswers.value = false
        }
    }

    // UI state for operations
    private val _isGeneratingAdvice = MutableStateFlow(false)
    val isGeneratingAdvice: StateFlow<Boolean> = _isGeneratingAdvice.asStateFlow()

    private val _isAuthLoading = MutableStateFlow(false)
    val isAuthLoading: StateFlow<Boolean> = _isAuthLoading.asStateFlow()

    fun setAuthLoading(loading: Boolean) {
        _isAuthLoading.value = loading
    }

    private val _lastGeneratedAdvice = MutableStateFlow<String?>(null)
    val lastGeneratedAdvice: StateFlow<String?> = _lastGeneratedAdvice.asStateFlow()

    // Interactive WhatsApp Guided Onboarding State Machine & Backend Lógica de Seguimiento
    private val _chatbotStep = MutableStateFlow("IDLE") // "IDLE", "AWAITING_NAME", "AWAITING_CONSENT", "AWAITING_STAGE", "AWAITING_WEEKS_OR_MONTHS", "AWAITING_CONCERN", "AWAITING_FREQUENCY", "COMPLETED"
    val chatbotStep: StateFlow<String> = _chatbotStep.asStateFlow()

    private var chatName = ""
    private var chatStage = "EMBARAZADA"
    private var chatWeeksOrMonths = 12
    private var chatConcern = ""
    private var chatOptIn = false
    private var chatFreq = "Diario"

    fun resetAndStartWhatsAppOnboarding() {
        viewModelScope.launch {
            _chatbotStep.value = "AWAITING_NAME"
            repository.clearWhatsAppLogs()
            
            // Simula primer mensaje enviado desde el backend de la API de WhatsApp Business
            val welcomeText = "¡Hola! 🌸 Te damos la bienvenida a TribuMental Chatbot. He detectado que deseas iniciar tu acompañamiento emocional exclusivo por aquí. Primero, por favor ingresa tu Nombre Completo para registrar tu cuenta."
            repository.insertWhatsAppLog(
                WhatsAppMessageLog(
                    messageType = "AUTHENTICATION",
                    body = welcomeText,
                    direction = "SENT",
                    status = "READ"
                )
            )
        }
    }

    fun abortWhatsAppOnboarding() {
        _chatbotStep.value = "IDLE"
    }

    fun sendUserWhatsAppMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            // Log user raw message in local storage (direction = RECEIVED)
            repository.insertWhatsAppLog(
                WhatsAppMessageLog(
                    messageType = "UTILITY",
                    body = text,
                    direction = "RECEIVED",
                    status = "READ"
                )
            )

            // Dynamic logic checking of chatbot current state
            when (_chatbotStep.value) {
                "AWAITING_NAME" -> {
                    chatName = text.trim()
                    _chatbotStep.value = "AWAITING_CONSENT"
                    val promptConsent = "¡Mucho gusto, $chatName! 👶 Para poder guiarte y enviarte contenido de bienestar diario directamente, ¿nos das tu consentimiento para comunicarnos contigo a través de WhatsApp? Responde 'SÍ' o 'NO'."
                    repository.insertWhatsAppLog(
                        WhatsAppMessageLog(
                            messageType = "AUTHENTICATION",
                            body = promptConsent,
                            direction = "SENT",
                            status = "SENT"
                        )
                    )
                }
                "AWAITING_CONSENT" -> {
                    val cleanText = text.trim().uppercase()
                    chatOptIn = cleanText.contains("SI") || cleanText.contains("SÍ") || cleanText.contains("YES")
                    
                    _chatbotStep.value = "AWAITING_STAGE"
                    val promptStage = "Perfecto. 🌸 Para personalizar tus consejos diarios de la Tribu, ¿en qué etapa de maternidad te encuentras actualmente?\n\nResponde con el número de tu opción:\n1. Embarazada\n2. Posparto"
                    repository.insertWhatsAppLog(
                        WhatsAppMessageLog(
                            messageType = "AUTHENTICATION",
                            body = promptStage,
                            direction = "SENT",
                            status = "SENT"
                        )
                    )
                }
                "AWAITING_STAGE" -> {
                    val response = text.trim()
                    chatStage = if (response.contains("2") || response.uppercase().contains("POST") || response.uppercase().contains("POS")) {
                        "POSPARTO"
                    } else {
                        "EMBARAZADA"
                    }
                    
                    _chatbotStep.value = "AWAITING_WEEKS_OR_MONTHS"
                    val promptWeeksOrMonths = if (chatStage == "EMBARAZADA") {
                        "¡Qué hermosa espera! 🤰 ¿Cuántas semanas aproximadas tienes de embarazo? Por favor, responde con el número de semanas (ej. 12)"
                    } else {
                        "¡Felicidades por tu bebé! 🤱 ¿Cuántos meses hace que nació tu pequeño? Por favor, responde con el número de meses (ej. 3)"
                    }
                    repository.insertWhatsAppLog(
                        WhatsAppMessageLog(
                            messageType = "AUTHENTICATION",
                            body = promptWeeksOrMonths,
                            direction = "SENT",
                            status = "SENT"
                        )
                    )
                }
                "AWAITING_WEEKS_OR_MONTHS" -> {
                    val response = text.trim()
                    chatWeeksOrMonths = response.filter { it.isDigit() }.toIntOrNull() ?: 12
                    
                    _chatbotStep.value = "AWAITING_CONCERN"
                    val promptConcern = "Excelente. 🌸 ¿Cuál es tu principal preocupación o área de enfoque hoy?\n\nResponde con el número de tu opción:\n1. Cambios de humor o ansiedad\n2. Calidad del sueño\n3. Lactancia y nutrición del bebé\n4. Dolores y cansancio físico\n5. Sentimiento de soledad"
                    repository.insertWhatsAppLog(
                        WhatsAppMessageLog(
                            messageType = "AUTHENTICATION",
                            body = promptConcern,
                            direction = "SENT",
                            status = "SENT"
                        )
                    )
                }
                "AWAITING_CONCERN" -> {
                    val response = text.trim()
                    val code = response.filter { it.isDigit() }.toIntOrNull() ?: 1
                    chatConcern = when (code) {
                        1 -> "Cambios de humor / Ansiedad"
                        2 -> "Calidad del sueño"
                        3 -> "Lactancia y nutrición"
                        4 -> "Cansancio físico"
                        5 -> "Soledad y red de apoyo"
                        else -> "Cambios de humor / Ansiedad"
                    }
                    
                    _chatbotStep.value = "AWAITING_FREQUENCY"
                    val promptFreq = "Entendido, tomamos nota para darte el mejor apoyo con Gemini IA. ¿Con qué frecuencia de recordatorios y tips por WhatsApp te sientes más cómoda?\n\nResponde con el número de tu opción:\n1. Diario\n2. Dos veces por semana\n3. Semanal"
                    repository.insertWhatsAppLog(
                        WhatsAppMessageLog(
                            messageType = "AUTHENTICATION",
                            body = promptFreq,
                            direction = "SENT",
                            status = "SENT"
                        )
                    )
                }
                "AWAITING_FREQUENCY" -> {
                    val response = text.trim()
                    val code = response.filter { it.isDigit() }.toIntOrNull() ?: 1
                    chatFreq = when (code) {
                        1 -> "Diario"
                        2 -> "Dos veces por semana"
                        3 -> "Semanal"
                        else -> "Diario"
                    }
                    
                    // SAVE PROFILE & COMPLETE ONBOARDING AUTOMATICALLY (BACKEND SYNC)
                    val finalizedProfile = UserProfile(
                        id = 1,
                        name = chatName,
                        stage = chatStage,
                        weeksOrMonths = chatWeeksOrMonths,
                        concern = chatConcern,
                        optWhatsApp = chatOptIn,
                        reminderFrequency = chatFreq,
                        isPremium = true,
                        billingPlan = "TRIBU PREMIUM (WhatsApp Flow)",
                        isOnboarded = true
                    )
                    repository.saveProfile(finalizedProfile)
                    
                    _chatbotStep.value = "COMPLETED"
                    val completionMsg = """
                        🎉 ¡Onboarding Conversacional completado con éxito! Tu cuenta de TribuMental ha sido activada en el Plan Premium. 

                        📋 Datos ingresados:
                        • Madre: $chatName
                        • Etapa: $chatStage (${if (chatStage == "EMBARAZADA") "$chatWeeksOrMonths Semanas" else "$chatWeeksOrMonths Meses"})
                        • Interés: $chatConcern
                        • Consentimiento WA: ${if (chatOptIn) "Aceptado ($chatFreq)" else "No aceptado"}

                        ¡Soporte activo! Puedes dirigirte de inmediato a tu App Dashboard haciendo clic en 'Volver al Inicio'.
                    """.trimIndent()
                    
                    repository.insertWhatsAppLog(
                        WhatsAppMessageLog(
                            messageType = "AUTHENTICATION",
                            body = completionMsg,
                            direction = "SENT",
                            status = "DELIVERED"
                        )
                    )
                }
            }
        }
    }

    init {
        // Pre-populate some essential resources if Empty
        viewModelScope.launch {
            if (repository.allContacts.first().isEmpty()) {
                repository.insertContact(SupportContact(name = "Línea Nacional de Apoyo", phone = "911", relationship = "Gubernamental / Emergencias"))
                repository.insertContact(SupportContact(name = "Dra. Sofía Martínez (Mera Ginecobstetra)", phone = "+54911223344", relationship = "Médico Obstetra"))
            }
        }
    }

    // Onboarding and User Settings updates
    fun completeOnboarding(
        name: String,
        stage: String,
        weeksOrMonths: Int,
        concern: String,
        optWhatsApp: Boolean,
        reminderFreq: String,
        plan: String,
        age: Int = 28,
        supportNetwork: String = "Pareja",
        previousPregnancies: Int = 0,
        location: String = "Sin especificar"
    ) {
        viewModelScope.launch {
            val current = repository.getProfileSync()
            val isPremium = plan != "GRATIS"
            val newProfile = UserProfile(
                id = 1,
                name = name,
                stage = stage,
                weeksOrMonths = weeksOrMonths,
                concern = concern,
                optWhatsApp = optWhatsApp,
                reminderFrequency = reminderFreq,
                isPremium = isPremium,
                billingPlan = plan,
                isOnboarded = true,
                age = age,
                supportNetwork = supportNetwork,
                previousPregnancies = previousPregnancies,
                location = location,
                avatarUri = current?.avatarUri
            )
            repository.saveProfile(newProfile)

            // Dynamic onboarding template message over WhatsApp if opted in
            if (optWhatsApp) {
                val greeting = if (stage == "EMBARAZADA") {
                    "¡Bienvenida a TribuMental, $name! 🌸 Nos alegra acompañarte en estas $weeksOrMonths semanas de dulce espera. Tu plan $plan ha sido activado con éxito. Este es tu canal directo para check-ins emocionales."
                } else {
                    "¡Bienvenida a TribuMental, $name! 🤱 Te acompañamos en estos $weeksOrMonths meses posparto que requieren tanto cariño. Tu plan $plan está activo. Sentirte bien es prioridad para nosotros."
                }
                // Insert logs and trigger intent mock or real
                WhatsAppService.simulateIncomingWhatsAppLog(repository, greeting, "AUTHENTICATION")
            }
        }
    }

    fun updateProfileAvatar(newPath: String?) {
        viewModelScope.launch {
            val current = repository.getProfileSync() ?: UserProfile(id = 1)
            val updated = current.copy(avatarUri = newPath)
            repository.saveProfile(updated)
        }
    }

    /**
     * Authenticates or registers a user using their Google account payload.
     * Auto-detects if they were already onboarded.
     */
    fun loginGoogleUser(email: String, name: String, onFinished: (Boolean) -> Unit) {
        viewModelScope.launch {
            com.example.services.FirebaseAuthService.loginOrRegisterGoogleInFirebase(email, name) { success, errorMsg ->
                viewModelScope.launch {
                    val existing = repository.getProfileSync()
                    if (existing != null && existing.email == email && existing.isOnboarded) {
                        onFinished(true)
                    } else {
                        val newProfile = existing?.copy(
                            name = if (name.isNotBlank()) name else (existing.name),
                            email = email,
                            isGoogleLinked = true
                        ) ?: UserProfile(
                            id = 1,
                            name = name,
                            email = email,
                            isGoogleLinked = true,
                            isOnboarded = false
                        )
                        repository.saveProfile(newProfile)
                        onFinished(newProfile.isOnboarded)
                    }
                }
            }
        }
    }

    // Toggle Premium / Settle Subscriptions (Paywall Upgrade)
    fun upgradeSubscription(planName: String) {
        viewModelScope.launch {
            val current = repository.getProfileSync() ?: UserProfile(id = 1)
            val updated = current.copy(
                isPremium = planName != "GRATIS",
                billingPlan = planName,
                isOnboarded = true
            )
            repository.saveProfile(updated)

            // Trigger WhatsApp confirmation (MARKETING / AUTHENTICATION)
            if (updated.optWhatsApp) {
                val upgradeMsg = "✨ ¡Felicidades! Surtiste la suscripción al Plan $planName de TribuMental de forma exitosa. Ahora tienes acceso a check-ins prioritarios con consejería de Inteligencia Artificial Gemini."
                WhatsAppService.simulateIncomingWhatsAppLog(repository, upgradeMsg, "UTILITY")
            }
        }
    }

    fun downgradeSubscription() {
        viewModelScope.launch {
            val current = repository.getProfileSync() ?: UserProfile(id = 1)
            val updated = current.copy(
                isPremium = false,
                billingPlan = "GRATIS"
            )
            repository.saveProfile(updated)
            // Trigger log
            WhatsAppService.simulateIncomingWhatsAppLog(repository, "Tu suscripción a TribuMental ha sido cambiada al Plan Gratis. Conservas tus registros médicos locales.", "UTILITY")
        }
    }

    // PSYCHOLOGICAL TEST AND TRACKED RECOMMENDATIONS
    fun savePsychologicalTestResult(score: Int, recommendations: List<String>) {
        viewModelScope.launch {
            val current = repository.getProfileSync() ?: UserProfile(id = 1)
            val recString = recommendations.joinToString("||")
            val trackedString = recommendations.joinToString("||") { "$it:false" }
            val updated = current.copy(
                testScore = score,
                testRecommendations = recString,
                trackedRecommendations = trackedString
            )
            repository.saveProfile(updated)
            
            // Also notify user via simulated WhatsApp if opted-in
            if (updated.optWhatsApp) {
                val waMsg = "📊 ¡Tu Test Psicológico ha sido procesado! Obtuviste un puntaje de bienestar mental de $score/10. Hemor determinado las siguientes recomendaciones clave: \n" +
                        recommendations.mapIndexed { idx, rec -> "${idx + 1}. $rec" }.joinToString("\n") +
                        "\n\nPuedes darles seguimiento y marcarlas como completadas directamente en tu Perfil de la aplicación."
                WhatsAppService.simulateIncomingWhatsAppLog(repository, waMsg, "UTILITY")
            }
        }
    }

    fun toggleTrackedRecommendation(recText: String, completed: Boolean) {
        viewModelScope.launch {
            val current = repository.getProfileSync() ?: return@launch
            val trackedList = current.trackedRecommendations.split("||").toMutableList()
            val index = trackedList.indexOfFirst { it.startsWith("$recText:") }
            if (index != -1) {
                trackedList[index] = "$recText:$completed"
                val updated = current.copy(
                    trackedRecommendations = trackedList.joinToString("||")
                )
                repository.saveProfile(updated)
            }
        }
    }

    // Mood Check-in flow
    fun performMoodCheckIn(score: Int, notes: String, onFinished: (String) -> Unit) {
        viewModelScope.launch {
            _isGeneratingAdvice.value = true
            val advice = GeminiService.generateEmotionalAdvice(score, notes)
            val checkIn = MoodCheckIn(
                moodScore = score,
                moodNotes = notes,
                advice = advice
            )
            repository.insertMood(checkIn)
            _lastGeneratedAdvice.value = advice
            _isGeneratingAdvice.value = false
            onFinished(advice)

            // Sigue con WhatsApp si cuenta con Premium y optó por recibir mensajes
            val currentProfile = repository.getProfileSync()
            if (currentProfile?.optWhatsApp == true && currentProfile.isPremium) {
                val waBody = "💜 Check-in registrado. Puntuación: $score/5. Un consejo para hoy: $advice"
                repository.insertWhatsAppLog(
                    WhatsAppMessageLog(
                        messageType = "PREMIUM_FOLLOW",
                        body = waBody,
                        direction = "SENT",
                        status = "DELIVERED"
                    )
                )
            }
        }
    }

    // Apppointments management
    fun addAppointment(title: String, dateTime: Long, type: String, provider: String, notes: String) {
        viewModelScope.launch {
            val appointment = Appointment(
                title = title,
                dateTime = dateTime,
                type = type,
                provider = provider,
                notes = notes,
                status = "PROGRAMADA"
            )
            repository.insertAppointment(appointment)
        }
    }

    fun changeAppointmentStatus(id: Int, status: String) {
        viewModelScope.launch {
            repository.updateAppointmentStatus(id, status)
        }
    }

    fun deleteAppointment(id: Int) {
        viewModelScope.launch {
            repository.deleteAppointment(id)
        }
    }

    // Documents management with Gemini Simulated OCR Analysis
    fun scanCameraAndAnalyzeDocument(
        name: String,
        extractedContentText: String,
        appointmentId: Int?,
        onCompleted: (detectedCategory: String, cleanAnalysis: String) -> Unit
    ) {
        viewModelScope.launch {
            val (category, analysisText) = GeminiService.classifyAndAnalyzeMedicalDocument(name, extractedContentText)
            
            val doc = MedicalDocument(
                name = name,
                type = category,
                appointmentId = appointmentId,
                documentText = analysisText,
                imageContentUri = "android.resource://com.example/drawable/ic_launcher_foreground"
            )
            repository.insertDocument(doc)
            onCompleted(category, analysisText)
        }
    }

    fun scanAndAttachDocument(
        name: String,
        type: String, // "Orden médica", "Resultado de laboratorio", etc.
        appointmentId: Int?,
        mockText: String = "",
        onCompleted: () -> Unit
    ) {
        viewModelScope.launch {
            // Apply real or simulated Gemini OCR analysis based on parameters
            val textToUse = if (mockText.isNotBlank()) mockText else "Receta médica analizada"
            val ocrAdvice = GeminiService.analyzeMedicalDocument(type, name)
            
            val doc = MedicalDocument(
                name = name,
                type = type,
                appointmentId = appointmentId,
                documentText = ocrAdvice,
                imageContentUri = "android.resource://com.example/drawable/ic_launcher_foreground"
            )
            repository.insertDocument(doc)
            onCompleted()
        }
    }

    fun deleteDocument(id: Int) {
        viewModelScope.launch {
            repository.deleteDocument(id)
        }
    }

    fun unlinkDocumentFromAppointment(id: Int) {
        viewModelScope.launch {
            val doc = documents.value.firstOrNull { it.id == id }
            if (doc != null) {
                val updatedDoc = doc.copy(appointmentId = null)
                repository.insertDocument(updatedDoc)
            }
        }
    }

    // Support contacts
    fun addContact(name: String, phone: String, relationship: String) {
        viewModelScope.launch {
            repository.insertContact(SupportContact(name = name, phone = phone, relationship = relationship))
        }
    }

    fun deleteContact(id: Int) {
        viewModelScope.launch {
            repository.deleteContact(id)
        }
    }

    // Clear logs helper
    fun clearLogs() {
        viewModelScope.launch {
            repository.clearWhatsAppLogs()
        }
    }

    // Trigger explicit WhatsApp redirect
    fun triggerWhatsAppDirectIntent(context: Context, customText: String = "") {
        viewModelScope.launch {
            val profile = repository.getProfileSync()
            val phone = "54911223344" // Default support TribuMental line (or custom partner)
            val greeting = if (customText.isNotBlank()) {
                customText
            } else {
                "Hola TribuMental, soy ${profile?.name ?: "una mamá"}. Necesito acompañamiento emocional y soporte."
            }
            WhatsAppService.sendWhatsAppMessage(context, repository, phone, greeting, "MARKETING")
        }
    }

    // Trigger explicit WhatsApp group redirect
    fun triggerWhatsAppGroupIntent(context: Context) {
        viewModelScope.launch {
            WhatsAppService.openWhatsAppGroup(context, repository)
        }
    }

    // Sign out user and clean local profile cache
    fun logoutUser(onComplete: () -> Unit) {
        viewModelScope.launch {
            com.example.services.FirebaseAuthService.signOut()
            repository.saveProfile(
                com.example.data.model.UserProfile(
                    id = 1,
                    name = "",
                    email = "",
                    isOnboarded = false,
                    isGoogleLinked = false
                )
            )
            onComplete()
        }
    }
}

class TribuMentalViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TribuMentalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TribuMentalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
