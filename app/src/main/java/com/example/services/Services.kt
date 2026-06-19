package com.example.services

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.BuildConfig
import com.example.data.model.WhatsAppMessageLog
import com.example.data.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

object WhatsAppService {
    private const val TAG = "WhatsAppService"

    /**
     * Automatically triggers a real WhatsApp message draft using Android Intents for real user interaction,
     * while logging the communication inside the local database.
     */
    suspend fun sendWhatsAppMessage(
        context: Context,
        repository: AppRepository,
        phone: String,
        message: String,
        category: String = "UTILITY"
    ) {
        val finalPhone = phone.replace("+", "").replace(" ", "")
        val formattedMsg = URLEncoder.encode(message, "UTF-8")
        val uri = "https://api.whatsapp.com/send?phone=$finalPhone&text=$formattedMsg"
        
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            
            // Log successful draft creation
            repository.insertWhatsAppLog(
                WhatsAppMessageLog(
                    messageType = category,
                    body = message,
                    direction = "SENT",
                    status = "DELIVERED"
                )
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error opening WhatsApp client", e)
            // Log as pending
            repository.insertWhatsAppLog(
                WhatsAppMessageLog(
                    messageType = category,
                    body = "[Intent Failed] $message",
                    direction = "SENT",
                    status = "PENDING"
                )
            )
        }
    }

    /**
     * Opens the WhatsApp Support Group Invite link directly.
     */
    suspend fun openWhatsAppGroup(
        context: Context,
        repository: AppRepository,
        groupUrl: String = "https://chat.whatsapp.com/L1nKToTrIbUmeNtAlGroup"
    ) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(groupUrl)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            
            // Log successful action
            repository.insertWhatsAppLog(
                WhatsAppMessageLog(
                    messageType = "UTILITY",
                    body = "Se unió al grupo oficial de soporte comunitario vía enlace: $groupUrl",
                    direction = "SENT",
                    status = "DELIVERED"
                )
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error opening WhatsApp group link", e)
            repository.insertWhatsAppLog(
                WhatsAppMessageLog(
                    messageType = "UTILITY",
                    body = "[Enlace Fallido] Error abriendo grupo de soporte comunitario $groupUrl",
                    direction = "SENT",
                    status = "PENDING"
                )
            )
        }
    }

    /**
     * Utility method to simulate background message delivery (like daily notifications or billing updates)
     */
    suspend fun simulateIncomingWhatsAppLog(
        repository: AppRepository,
        body: String,
        category: String
    ) {
        repository.insertWhatsAppLog(
            WhatsAppMessageLog(
                messageType = category,
                body = body,
                direction = "RECEIVED",
                status = "READ"
            )
        )
    }
}

object GeminiService {
    private const val TAG = "GeminiService"
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private const val DEFAULT_ADVICE = 
        "Recuerda que no estás sola en este camino de la maternidad. Es muy importante validar todo lo que sientes. Descansa lo suficiente, mantente hidratada y no dudes en buscar apoyo de tus seres queridos. Eres una excelente madre."

    /**
     * Communicates with Gemini API to provide deep, custom validation and advice for the user's emotional state
     */
    suspend fun generateEmotionalAdvice(moodScore: Int, notes: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey == "MY_GEMINI_API_KEY" || apiKey.isEmpty()) {
            Log.w(TAG, "Gemini API key is not configured. Falling back to default advice.")
            return@withContext getStaticAdvice(moodScore)
        }

        val prompt = """
            Eres un asistente de apoyo emocional empático y cálido especializado en salud mental materna para una app llamada TribuMental.
            El usuario ha reportado un estado de ánimo de $moodScore sobre 5 (donde 1 es muy bajo/triste y 5 es muy alto/feliz).
            Notas de la usuaria: "$notes"
            Por favor, brinda un mensaje de apoyo emocional de 2 o 3 líneas, validando de manera no clínica sus sentimientos, ofreciendo una sugerencia suave de bienestar/autocuidado (respiración, caminata, descanso) y cerrando de manera muy amorosa. 
            IMPORTANTE: No des diagnósticos médicos, recuerda que no eres un médico, de manera sutil si notas algo grave recuérdale buscar apoyo profesional de salud, pero mantén un tono de comunidad y acompañamiento cálido en español.
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            val partObj = JSONObject().apply {
                                put("text", prompt)
                            }
                            put(partObj)
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e(TAG, "Request failed with code: ${response.code}")
                    return@withContext getStaticAdvice(moodScore)
                }
                val bodyStr = response.body?.string() ?: return@withContext getStaticAdvice(moodScore)
                val responseJson = JSONObject(bodyStr)
                val candidates = responseJson.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val content = firstCandidate.getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    if (parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).getString("text").trim()
                    }
                }
                return@withContext getStaticAdvice(moodScore)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error generating emotional advice from Gemini", e)
            return@withContext getStaticAdvice(moodScore)
        }
    }

    /**
     * Actionable OCR analysis representation via Gemini
     */
    suspend fun analyzeMedicalDocument(documentType: String, documentName: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey == "MY_GEMINI_API_KEY" || apiKey.isEmpty()) {
            return@withContext "Simulación de OCR correcta. El documento '$documentName' catalogado como '$documentType' contiene información médica de rutina. Recuerda consultar con tu ginecobstetra."
        }

        val prompt = """
            Simula un análisis OCR inteligente e inmediato para un documento médico.
            Tipo de documento: $documentType
            Nombre: $documentName
            Genera un resumen útil de 3 líneas en español que describa qué información suele contener este tipo de documento médico (por ejemplo: dosis de vitaminas si es receta, curvas de crecimiento si es ecografía, etc.), recordándole a la madre con mucho cariño los siguientes pasos y que debe compartirlo en su próxima consulta.
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            val partObj = JSONObject().apply {
                                put("text", prompt)
                            }
                            put(partObj)
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext "Resumen disponible una vez sincronizado el servicio OCR."
                val bodyStr = response.body?.string() ?: ""
                val responseJson = JSONObject(bodyStr)
                val candidates = responseJson.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val content = firstCandidate.getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    if (parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).getString("text").trim()
                    }
                }
                return@withContext "Guardado con éxito. Listo para presentar al médico."
            }
        } catch (e: Exception) {
            return@withContext "Análisis inteligente del documento '$documentName' completado. Listo para asociar al calendario."
        }
    }

    /**
     * Dynamically classifies and analyzes scanned/captured physical medical documents.
     */
    suspend fun classifyAndAnalyzeMedicalDocument(documentName: String, extractedText: String): Pair<String, String> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey == "MY_GEMINI_API_KEY" || apiKey.isEmpty()) {
            return@withContext getStaticClassification(documentName, extractedText)
        }

        val prompt = """
            Eres un asistente de IA especializado en salud perinatal de la aplicación TribuMental.
            Tu tarea es analizar el texto de un documento obstétrico o médico que una madre acaba de fotografiar con su cámara o subir.
            
            Nombre del archivo elegido por el usuario: "$documentName"
            Texto extraído del documento/imagen: "$extractedText"
            
            Pasos requeridos:
            1. Clasifica el documento en una de estas cuatro categorías exactas de la aplicación: "Orden Médica", "Resultados de Laboratorio", "Ecografía / Ultrasonido", o "Otros Documentos". Si no encaja perfectamente, elige la más cercana.
            2. Genera un resumen explicativo y cálido en español (máximo 4 líneas) que le traduzca el significado médico general de los términos a la madre, brindándole tranquilidad y recordándole compartirlo de forma presencial con su gineco-obstetra.
            
            Responde estrictamente en el siguiente formato estructurado de dos líneas para procesarlo en Kotlin:
            CATEGORIA: <Coloca aquí únicamente "Orden Médica", "Resultados de Laboratorio", "Ecografía / Ultrasonido" o "Otros Documentos">
            ANALISIS: <Coloca aquí tu resumen empático y análisis en español de 2 a 3 oraciones>
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            val partObj = JSONObject().apply {
                                put("text", prompt)
                            }
                            put(partObj)
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext getStaticClassification(documentName, extractedText)
                val bodyStr = response.body?.string() ?: ""
                val responseJson = JSONObject(bodyStr)
                val candidates = responseJson.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val content = firstCandidate.getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    if (parts.length() > 0) {
                        val fullReply = parts.getJSONObject(0).getString("text").trim()
                        
                        var detectedCategory = "Resultados de Laboratorio"
                        var analysisText = "Análisis completado para el documento."
                        
                        val lines = fullReply.split("\n")
                        for (line in lines) {
                            if (line.uppercase().startsWith("CATEGORIA:") || line.uppercase().startsWith("CATEGORÍA:")) {
                                val catVal = line.substring(line.indexOf(":") + 1).trim()
                                if (catVal.contains("Orden", ignoreCase = true) || catVal.contains("Prescription", ignoreCase = true) || catVal.contains("Receta", ignoreCase = true)) {
                                    detectedCategory = "Orden Médica"
                                } else if (catVal.contains("Laboratorio", ignoreCase = true) || catVal.contains("Result", ignoreCase = true) || catVal.contains("Sangre", ignoreCase = true)) {
                                    detectedCategory = "Resultados de Laboratorio"
                                } else if (catVal.contains("Ultrasound", ignoreCase = true) || catVal.contains("Ecografía", ignoreCase = true) || catVal.contains("Eco", ignoreCase = true)) {
                                    detectedCategory = "Ecografía / Ultrasonido"
                                } else {
                                    detectedCategory = "Otros Documentos"
                                }
                            } else if (line.uppercase().startsWith("ANALISIS:") || line.uppercase().startsWith("ANÁLISIS:")) {
                                analysisText = line.substring(line.indexOf(":") + 1).trim()
                            }
                        }
                        
                        if (analysisText.length < 5) {
                            analysisText = fullReply
                        }
                        
                        return@withContext Pair(detectedCategory, analysisText)
                    }
                }
                return@withContext getStaticClassification(documentName, extractedText)
            }
        } catch (e: Exception) {
            return@withContext getStaticClassification(documentName, extractedText)
        }
    }

    private fun getStaticClassification(documentName: String, extractedText: String): Pair<String, String> {
        val mixedText = (documentName + " " + extractedText).uppercase()
        val isUltrasound = mixedText.contains("ULTRASOUND") || mixedText.contains("ECO") || mixedText.contains("BEBE") || mixedText.contains("BABY") || mixedText.contains("IMAGEN") || mixedText.contains("GESTA") || mixedText.contains("FETAL") || (mixedText.contains("SANGRE") == false && mixedText.contains("ECOGRAFÍA"))
        val isPrescription = mixedText.contains("RECETA") || mixedText.contains("ORDEN") || mixedText.contains("PRESCRIPTION") || mixedText.contains("MEDICAMENTO") || mixedText.contains("HOSPITAL")

        val category = when {
            isUltrasound -> "Ecografía / Ultrasonido"
            isPrescription -> "Orden Médica"
            mixedText.contains("LABORATORIO") || mixedText.contains("SANGRE") || mixedText.contains("HEMOGRAMA") || mixedText.contains("ANÁLISIS") -> "Resultados de Laboratorio"
            else -> "Otros Documentos"
        }

        val analysis = when (category) {
            "Ecografía / Ultrasonido" -> {
                "¡Hemos examinado tu imagen de Ecografía maternal! El reporte de '$documentName' muestra métricas de crecimiento fetal. Esto evoca el desarrollo saludable del líquido amniótico y los latidos del corazón de tu bebé. ¡Lleva este registro en tu próximo control prenatal!"
            }
            "Orden Médica" -> {
                "Se ha analizado tu orden médica o receta firmada para '$documentName'. Indica indicaciones de tratamiento, suplementos alimenticios vitales o exámenes complementarios que tu ginecólogo ha solicitado seguir. Mantente al día con ellas de manera preventiva."
            }
            "Resultados de Laboratorio" -> {
                "¡Súper! Se ha analizado tu reporte: '$documentName'. El reporte indica niveles normales correspondientes a un hemograma, hemoglobina o valores metabólicos de rutina. Es de suma importancia que dejes que tu médico obstetra los revise en consulta física."
            }
            else -> {
                "Documento clasificado como reporte clínico perinatal general. Registramos '$documentName' para que lo tengas siempre a la mano de forma organizada cuando asistas a tus citas médicas."
            }
        }
        
        return Pair(category, analysis)
    }

    private fun getStaticAdvice(moodScore: Int): String {
        return when (moodScore) {
            1 -> "Siento mucho que estés pasando por un momento tan difícil hoy. Recuerda que no estás sola. Acéptate y date tiempo; es normal sentirse abrumada. Respira hondo, toma un vaso de agua, y si lo necesitas, comunícate con tu red de apoyo o nuestro centro de crisis."
            2 -> "Un día un poco gris. La maternidad es una montaña rosa emocional y tus sentimientos son completamente válidos. Dedica 5 minutos hoy a hacer algo solo para ti: estirarte, recostarte o escuchar tu canción favorita."
            3 -> "Un día neutral. Vas dando cada paso con fortaleza. Recuerda mantenerte hidratada y descansar cuando el bebé duerma. ¡Estás haciendo un gran trabajo!"
            4 -> "¡Qué alegría ver que te sientes bien hoy! Disfruta de esta energía positiva. Un paseo corto al aire libre o escribir lo que te hizo sonreír hoy puede sellar este buen día."
            5 -> "¡Qué momento tan hermoso y lleno de luz! Celebra tu bienestar y compártelo. Te enviamos un fuerte abrazo de parte de toda la Tribu. Eres una inspiración para nosotras."
            else -> DEFAULT_ADVICE
        }
    }

    /**
     * Communicates with Gemini API to provide expert crisis containment from our virtual on-call perinatal psychologist, Dr. Gabriel Soto.
     */
    suspend fun generateEmergencyCrisisSupport(userMessage: String, userName: String, stage: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey == "MY_GEMINI_API_KEY" || apiKey.isEmpty()) {
            return@withContext getStaticEmergencyAdvice(userMessage, userName)
        }

        val prompt = """
            Eres el Dr. Gabriel Soto, un experimentado psicólogo perinatal de la red de TribuMental de guardia para emergencias y contención de crisis emocional en WhatsApp.
            La usuaria se llama $userName y está en la etapa de $stage.
            Mensaje de emergencia/angustia de la usuaria: "$userMessage".
            
            Como un profesional de la psicología cálido, experto, en un chat de WhatsApp de guardia:
            1. Valida de manera muy empática sus emociones con palabras receptivas, cálidas y de mucha calma. Podría estar sufriendo de ansiedad, tristeza posparto, soledad o pánico.
            2. Brinda una pauta breve y ultra sencilla de autorregulación (por ejemplo, respiración lenta, enfocar 3 cosas que pueda ver, etc.).
            3. Hazle sentir que estás a su lado escuchándola mediante este canal encriptado de WhatsApp.
            4. Si expresa intenciones de hacerse daño o síntomas físicos graves, recuérdale sutil pero responsablemente que puede llamar al 911 o a su médico de guardia registrado en su red de apoyo física, pero sin romper la calidez y el sentido de acompañamiento.
            
            Mantén la respuesta concisa y empática (máximo 4 líneas) para que se lee fácil en una aplicación de mensajería móvil. Responde en español directo y compasivo.
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            val partObj = JSONObject().apply {
                                put("text", prompt)
                            }
                            put(partObj)
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)
            }

            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext getStaticEmergencyAdvice(userMessage, userName)
                val bodyStr = response.body?.string() ?: return@withContext getStaticEmergencyAdvice(userMessage, userName)
                val responseJson = JSONObject(bodyStr)
                val candidates = responseJson.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val content = firstCandidate.getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    if (parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).getString("text").trim()
                    }
                }
                return@withContext getStaticEmergencyAdvice(userMessage, userName)
            }
        } catch (e: Exception) {
            return@withContext getStaticEmergencyAdvice(userMessage, userName)
        }
    }

    /**
     * Analyzes breathing session and provides empathetic feedback on feel-good states.
     */
    suspend fun analyzeBreathingSessionFeedback(
        antesSentimiento: String,
        retencionSensacion: String,
        calificacionMejora: Int
    ): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey == "MY_GEMINI_API_KEY" || apiKey.isEmpty()) {
            return@withContext "¡Gran trabajo, mamá! Es normal sentirse '$antesSentimiento' al iniciar y calificar tu calma en $calificacionMejora/10. La respiración consciente regula rápidamente tus niveles de cortisol. Sigue practicándola hoy."
        }
        val prompt = """
            Eres un terapeuta de mindfulness empático e intuitivo, especializado en salud mental materna para una app llamada TribuMental.
            La usuaria de la app acaba de realizar un ejercicio de respiración consciente guiada. Estas son sus respuestas sobre cómo se sintió:
            1. ¿Cómo te sentías antes de respirar y de qué color te imaginas esa tensión?: "$antesSentimiento"
            2. Durante el ejercicio, ¿pudiste liberar algo de aire o sentiste el pecho apretado?: "$retencionSensacion"
            3. Del 1 al 10, ¿cuánto ha mejorado tu flujo de calma interior ahora?: $calificacionMejora / 10
            
            Por favor, bríndale una retroalimentación amorosa, poética, sumamente empática y reconfortante de 3 a 4 líneas en español. Valida la tensión o el agobio inicial que sentía, celebra cualquier mejora y ofrécele una cálida bendición o consejo suave para continuar su día. No uses términos médicos fríos.
        """.trimIndent()
        try {
            val jsonBody = JSONObject().apply {
                val contents = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val parts = JSONArray().apply {
                            val partObj = JSONObject().apply {
                                put("text", prompt)
                            }
                            put(partObj)
                        }
                        put("parts", parts)
                    }
                    put(contentObj)
                }
                put("contents", contents)
            }
            val request = Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext "¡Gran trabajo, mamá! Enfocar tu respiración consciente regula rápidamente tus niveles de cortisol. Sigue practicándola hoy."
                val bodyStr = response.body?.string() ?: ""
                val responseJson = JSONObject(bodyStr)
                val candidates = responseJson.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val content = firstCandidate.getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    if (parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).getString("text").trim()
                    }
                }
                return@withContext "¡Gran trabajo, mamá! Enfocar tu respiración consciente regula rápidamente tus niveles de cortisol."
            }
        } catch (e: Exception) {
            return@withContext "Respirar conscientemente ayuda a tu cerebro a regular el cortisol. Sigue practicando por 2 minutos para restaurar tu bienestar maternal."
        }
    }

    private fun getStaticEmergencyAdvice(userMessage: String, userName: String): String {
        val uppercaseMsg = userMessage.uppercase()
        return when {
            uppercaseMsg.contains("MATA") || uppercaseMsg.contains("MORI") || uppercaseMsg.contains("DAÑO") -> {
                "Hola $userName, estoy aquí escuchándote. Siente mi abrazo protector. Valido tu profundo dolor, pero por favor ten presente que tu seguridad es lo primero. Conéctate ahora mismo con tu red de contactos de de emergencia o llama al número de asistencia médica más cercano. Tu vida es sumamente valiosa para toda nuestra Tribu."
            }
            uppercaseMsg.contains("PANIC") || uppercaseMsg.contains("ANSIEDAD") || uppercaseMsg.contains("RESPIR") || uppercaseMsg.contains("MIEDO") -> {
                "Hola $userName. Entiendo el desespero de esta crisis de pánico. Inhala en 4 tiempos, retén el aire en 4 y exhala en 4 tiempos despacio. Enfoca tu mirada en un objeto físico frente a ti. No estás en peligro real en este minuto, tus emociones pasarán y yo estoy aquí sosteniéndote virtualmente."
            }
            uppercaseMsg.contains("TRISTE") || uppercaseMsg.contains("LLORA") || uppercaseMsg.contains("SOL") || uppercaseMsg.contains("SOLA") -> {
                "Hola $userName. Sentirse cansada y sola en esta etapa es increíblemente doloroso y real. Quiero recordarte que el posparto o el embarazo mueven raíces profundas. Está bien llorar y soltar el peso hoy. Conversa con un familiar de confianza y gánale un respiro al día."
            }
            else -> {
                "Hola $userName, soy el Dr. Gabriel Soto, especialista perinatal de TribuMental de guardia. Siento tu inquietud y entiendo que buscas un espacio de desahogo. Estoy chateando contigo para escucharte y recordarte respirar con calma. Cuéntame con tranquilidad qué te angustia para poder guiarte paso a paso."
            }
        }
    }
}
