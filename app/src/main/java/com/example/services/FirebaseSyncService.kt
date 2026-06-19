package com.example.services

import android.content.Context
import android.util.Log
import com.example.data.model.UserProfile
import com.example.data.model.MedicalDocument
import com.example.data.model.MoodCheckIn
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

object FirebaseSyncService {
    private const val TAG = "FirebaseSyncService"
    private var isInitialized = false

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    private var activeSyncs = 0

    @Synchronized
    private fun incrementSync() {
        activeSyncs++
        _isSyncing.value = true
        Log.d(TAG, "Increment Sync: $activeSyncs")
    }

    @Synchronized
    private fun decrementSync() {
        activeSyncs--
        if (activeSyncs <= 0) {
            activeSyncs = 0
            _isSyncing.value = false
        }
        Log.d(TAG, "Decrement Sync: $activeSyncs")
    }

    private val firestore: FirebaseFirestore?
        get() {
            return try {
                if (!isInitialized) {
                    FirebaseApp.getInstance()
                    isInitialized = true
                }
                FirebaseFirestore.getInstance()
            } catch (e: Exception) {
                Log.e(TAG, "Error obtaining Firestore instance (Firebase might not be initialized): ${e.message}")
                null
            }
        }

    fun isCloudSyncEnabled(): Boolean {
        return firestore != null
    }

    suspend fun syncProfileToCloud(profile: UserProfile) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            incrementSync()
            // Use authenticated User UID if logged in via Firebase Auth, else email, else fallback
            val docId = if (FirebaseAuthService.isUserLoggedIn()) {
                FirebaseAuthService.getUserId()
            } else if (profile.isGoogleLinked && profile.email.isNotBlank()) {
                profile.email
            } else {
                "profile_${profile.id}"
            }

            val data = mapOf(
                "id" to profile.id,
                "name" to profile.name,
                "stage" to profile.stage,
                "weeksOrMonths" to profile.weeksOrMonths,
                "concern" to profile.concern,
                "optWhatsApp" to profile.optWhatsApp,
                "reminderFrequency" to profile.reminderFrequency,
                "isPremium" to profile.isPremium,
                "billingPlan" to profile.billingPlan,
                "isOnboarded" to profile.isOnboarded,
                "email" to profile.email,
                "isGoogleLinked" to profile.isGoogleLinked,
                "age" to profile.age,
                "supportNetwork" to profile.supportNetwork,
                "previousPregnancies" to profile.previousPregnancies,
                "location" to profile.location,
                "testScore" to profile.testScore,
                "testRecommendations" to profile.testRecommendations,
                "trackedRecommendations" to profile.trackedRecommendations,
                "avatarUri" to profile.avatarUri,
                "lastUpdated" to System.currentTimeMillis()
            )

            db.collection("users").document(docId)
                .set(data)
                .addOnCompleteListener {
                    decrementSync()
                }
                .addOnSuccessListener {
                    Log.d(TAG, "UserProfile successfully synced to Firestore!")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error syncing UserProfile to Firestore: ${e.message}")
                }
        } catch (e: Exception) {
            decrementSync()
            Log.e(TAG, "Exception during profile cloud sync: ${e.message}")
        }
    }

    suspend fun syncDocumentToCloud(userId: String, document: MedicalDocument) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            incrementSync()
            val data = mapOf(
                "id" to document.id,
                "name" to document.name,
                "timestamp" to document.timestamp,
                "appointmentId" to document.appointmentId,
                "type" to document.type,
                "imageContentUri" to document.imageContentUri,
                "documentText" to document.documentText,
                "syncedAt" to System.currentTimeMillis()
            )

            db.collection("users").document(userId)
                .collection("documents").document("doc_${document.id}")
                .set(data)
                .addOnCompleteListener {
                    decrementSync()
                }
                .addOnSuccessListener {
                    Log.d(TAG, "MedicalDocument references successfully synced to Firestore!")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error syncing MedicalDocument to Firestore: ${e.message}")
                }
        } catch (e: Exception) {
            decrementSync()
            Log.e(TAG, "Exception during document cloud sync: ${e.message}")
        }
    }

    suspend fun deleteDocumentFromCloud(userId: String, documentId: Int) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            incrementSync()
            db.collection("users").document(userId)
                .collection("documents").document("doc_$documentId")
                .delete()
                .addOnCompleteListener {
                    decrementSync()
                }
                .addOnSuccessListener {
                    Log.d(TAG, "MedicalDocument reference deleted from Firestore!")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error deleting MedicalDocument from Firestore: ${e.message}")
                }
        } catch (e: Exception) {
            decrementSync()
            Log.e(TAG, "Exception during document cloud delete: ${e.message}")
        }
    }

    suspend fun syncMoodToCheckInCloud(userId: String, mood: MoodCheckIn) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            incrementSync()
            val data = mapOf(
                "id" to mood.id,
                "moodScore" to mood.moodScore,
                "moodNotes" to mood.moodNotes,
                "timestamp" to mood.timestamp,
                "advice" to mood.advice,
                "syncedAt" to System.currentTimeMillis()
            )

            db.collection("users").document(userId)
                .collection("mood_check_ins").document("mood_${mood.id}")
                .set(data)
                .addOnCompleteListener {
                    decrementSync()
                }
                .addOnSuccessListener {
                    Log.d(TAG, "MoodCheckIn successfully synced to Firestore!")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error syncing MoodCheckIn to Firestore: ${e.message}")
                }
        } catch (e: Exception) {
            decrementSync()
            Log.e(TAG, "Exception during mood cloud sync: ${e.message}")
        }
    }
}
