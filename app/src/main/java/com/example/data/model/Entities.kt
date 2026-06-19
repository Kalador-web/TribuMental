package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "",
    val stage: String = "EMBARAZADA", // "EMBARAZADA" or "POSPARTO"
    val weeksOrMonths: Int = 1,
    val concern: String = "",
    val optWhatsApp: Boolean = false,
    val reminderFrequency: String = "Diario",
    val isPremium: Boolean = false,
    val billingPlan: String = "GRATIS", // "GRATIS", "MENSUAL", "FAMILIAR", "ANUAL"
    val isOnboarded: Boolean = false,
    val email: String = "",
    val isGoogleLinked: Boolean = false,
    val age: Int = 28,
    val supportNetwork: String = "Pareja", // e.g. "Ninguna", "Familiar", "Pareja", "Apoyo total"
    val previousPregnancies: Int = 0,
    val location: String = "Sin especificar",
    val testScore: Int? = null, // Mental state score: 1 to 10
    val testRecommendations: String = "", // Semicolon or pipe separated list of recommendations
    val trackedRecommendations: String = "", // e.g. "Rec1:status||Rec2:status" mapping toggles
    val avatarUri: String? = null
)

@Entity(tableName = "mood_check_in")
data class MoodCheckIn(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val moodScore: Int, // 1 to 5
    val moodNotes: String,
    val timestamp: Long = System.currentTimeMillis(),
    val advice: String = ""
)

@Entity(tableName = "appointment")
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val dateTime: Long,
    val type: String, // "Control Prenatal", "Pediatría", "Psicología", "Laboratorio", "Otro"
    val provider: String,
    val notes: String = "",
    val status: String = "PROGRAMADA" // "PROGRAMADA", "COMPLETADA", "CANCELADA"
)

@Entity(tableName = "medical_document")
data class MedicalDocument(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val timestamp: Long = System.currentTimeMillis(),
    val appointmentId: Int? = null,
    val type: String, // "Orden", "Receta", "Incapacidad", "Ecografía", "Laboratorio", "Otro"
    val imageContentUri: String = "", // Holds local file or mock base64/placeholder
    val documentText: String = "" // OCR Text output/result
)

@Entity(tableName = "support_contact")
data class SupportContact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val phone: String,
    val relationship: String // "Pareja", "Ginecóloga", "Terapeuta", "Amiga"
)

@Entity(tableName = "whatsapp_log")
data class WhatsAppMessageLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val messageType: String, // "UTILITY", "MARKETING", "AUTHENTICATION", "PREMIUM_FOLLOW"
    val body: String,
    val timestamp: Long = System.currentTimeMillis(),
    val direction: String = "SENT", // "SENT" or "RECEIVED"
    val status: String = "DELIVERED" // "PENDING", "SENT", "DELIVERED", "READ"
)
