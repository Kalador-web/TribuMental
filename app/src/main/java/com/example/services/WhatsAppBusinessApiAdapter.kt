package com.example.services

import android.util.Log
import com.example.data.model.WhatsAppMessageLog
import com.example.data.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

/**
 * Interface defining primary operations for the WhatsApp Business Cloud API.
 * In a real production codebase, this would bind to Retrofit or Ktor calling the Meta Graph API endpoint:
 * https://graph.facebook.com/v19.0/PHONE_NUMBER_ID/messages
 */
interface WhatsAppBusinessApi {
    suspend fun sendWhatsAppMessage(phoneNumber: String, text: String, repository: AppRepository): Boolean
    suspend fun sendTemplateMessage(phoneNumber: String, templateName: String, params: List<String>, repository: AppRepository): Boolean
    suspend fun syncWhatsAppStatus(messageId: String, repository: AppRepository): String
    suspend fun scheduleReminder(phoneNumber: String, delayMinutes: Long, message: String, repository: AppRepository): String
}

/**
 * Production-ready Mock Adapter representing a robust testing environment for TribuMental.
 * Simulates latency, state machine behavior, status receipts, and generates standard Meta Business JSON API schemas.
 */
object MockWhatsAppBusinessApiAdapter : WhatsAppBusinessApi {
    private const val TAG = "MockWhatsAppAPI"
    private val activeHandlers = CoroutineScope(Dispatchers.IO)

    /**
     * Simulates sending a regular text message over Meta Business API.
     * Generates and prints the actual HTTP payload schema to logcat.
     */
    override suspend fun sendWhatsAppMessage(phoneNumber: String, text: String, repository: AppRepository): Boolean {
        Log.i(TAG, "Requesting sendWhatsAppMessage to: $phoneNumber")
        
        // Construct simulated payload corresponding exactly to Meta Business standard payload
        val jsonPayload = JSONObject().apply {
            put("messaging_product", "whatsapp")
            put("recipient_type", "individual")
            put("to", phoneNumber)
            put("type", "text")
            put("text", JSONObject().apply {
                put("preview_url", true)
                put("body", text)
            })
        }
        
        // Log clean JSON representation for development verification
        Log.d(TAG, "META API REQUEST PAYLOAD POST /v19.0/messages:\n${jsonPayload.toString(2)}")
        
        // Simulate network latency (250ms - 500ms)
        delay(350)

        val success = true
        if (success) {
            val log = WhatsAppMessageLog(
                messageType = "UTILITY",
                body = text,
                direction = "SENT",
                status = "SENT"
            )
            repository.insertWhatsAppLog(log)
            Log.i(TAG, "Message successfully logged and dispatched via Mock Business Gateway.")
        }
        return success
    }

    /**
     * Simulates transmitting a pre-approved WhatsApp Business message template.
     * Meta API enforces templates for outbound messaging initiated outside of the 24-hour support window.
     */
    override suspend fun sendTemplateMessage(
        phoneNumber: String,
        templateName: String,
        params: List<String>,
        repository: AppRepository
    ): Boolean {
        Log.i(TAG, "Requesting sendTemplateMessage: '$templateName' to: $phoneNumber")

        // Construct standard Meta JSON parameters payload
        val parametersArray = JSONArray().apply {
            params.forEach { param ->
                put(JSONObject().apply {
                    put("type", "text")
                    put("text", param)
                })
            }
        }

        val jsonPayload = JSONObject().apply {
            put("messaging_product", "whatsapp")
            put("to", phoneNumber)
            put("type", "template")
            put("template", JSONObject().apply {
                put("name", templateName)
                put("language", JSONObject().apply { put("code", "es") })
                put("components", JSONArray().apply {
                    put(JSONObject().apply {
                        put("type", "body")
                        put("parameters", parametersArray)
                    })
                })
            })
        }

        Log.d(TAG, "META TEMPLATE API PAYLOAD:\n${jsonPayload.toString(2)}")
        delay(400) // simulated roundtrip

        // Resolve template body for database logging
        val syntheticBody = when (templateName) {
            "tribu_welcome_pregnant" -> "¡Hola, ${params.getOrNull(0) ?: "Mamá"}! 🌸 Te damos la bienvenida a TribuMental. Nos emociona acompañarte en tus ${params.getOrNull(1) ?: "12"} semanas de embarazo."
            "tribu_welcome_postpartum" -> "¡Hola, ${params.getOrNull(0) ?: "Mamá"}! 🤱 Te damos la bienvenida de vuelta a TribuMental en tu mes ${params.getOrNull(1) ?: "1"} posparto."
            "tribu_daily_reminder" -> "Hola de nuevo. Es tu momento Tribu de respirar profundamente. 🌸 ¿Cómo te has sentido hoy? Responde con tu ánimo del 1 al 5."
            else -> "Mensaje de plantilla [$templateName] enviado con parámetros: $params"
        }

        repository.insertWhatsAppLog(
            WhatsAppMessageLog(
                messageType = "AUTHENTICATION",
                body = syntheticBody,
                direction = "SENT",
                status = "SENT"
            )
        )

        return true
    }

    /**
     * Simulates retrieving status receipts (SENT -> DELIVERED -> READ) for a specific log item.
     * This keeps the dashboard synchronization indicators live.
     */
    override suspend fun syncWhatsAppStatus(messageId: String, repository: AppRepository): String {
        Log.i(TAG, "Sincronizando estado para el mensaje ID: $messageId")
        // Typically pulls webhook tracking or polls Graph endpoint. 
        // We simulate a progressive upgrade of state
        delay(200)
        return "READ"
    }

    /**
     * Simulates scheduling highly vital wellbeing check-ins or medication alarms.
     * In production, this connects to Android WorkManager or a server-side Redis BullMQ task queue.
     */
    override suspend fun scheduleReminder(
        phoneNumber: String,
        delayMinutes: Long,
        message: String,
        repository: AppRepository
    ): String {
        val taskId = UUID.randomUUID().toString()
        Log.i(TAG, "Programando recordatorio ID: $taskId en $delayMinutes minutos para $phoneNumber: '$message'")
        
        // Launch dynamic asynchronous delay runner to auto-populate response log on trigger
        activeHandlers.launch {
            // Scale time: for demo purposes inside the app, 1 minute is simulated as 8 seconds
            val delayMillis = delayMinutes * 8000L
            delay(delayMillis)
            
            // Dispatch message once timer is up
            repository.insertWhatsAppLog(
                WhatsAppMessageLog(
                    messageType = "UTILITY",
                    body = "🔔 [Recordatorio Automático] $message",
                    direction = "SENT",
                    status = "DELIVERED"
                )
            )
            Log.i(TAG, "Cron-Task de Alerta activada para $phoneNumber.")
        }

        return taskId
    }
}
