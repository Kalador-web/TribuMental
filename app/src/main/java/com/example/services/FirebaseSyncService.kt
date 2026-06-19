package com.example.services

import android.util.Log
import com.example.data.model.UserProfile
import com.example.data.model.MedicalDocument
import com.example.data.model.MoodCheckIn
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Service responsible for synchronizing local data to Firebase Firestore.
 * Operations are designed to be resilient and fail-safe.
 */
object FirebaseSyncService {
    private const val TAG = "FirebaseSyncService"
    private var isInitialized = false

    private val firestore: FirebaseFirestore?
        get() {
            return try {
                if (!isInitialized) {
                    FirebaseApp.getInstance()
                    // Enable offline persistence explicitly
                    val settings = com.google.firebase.firestore.FirebaseFirestoreSettings.Builder()
                        .setPersistenceEnabled(true)
                        .setCacheSizeBytes(com.google.firebase.firestore.FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                        .build()
                    FirebaseFirestore.getInstance().firestoreSettings = settings
                    isInitialized = true
                }
                FirebaseFirestore.getInstance()
            } catch (e: Exception) {
                Log.e(TAG, "Error obtaining Firestore instance: ${e.message}")
                null
            }
        }

    /**
     * Syncs user profile to the cloud. This happens in the background.
     */
    suspend fun syncProfileToCloud(profile: UserProfile) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            val docId = if (FirebaseAuthService.isUserLoggedIn()) {
                FirebaseAuthService.getUserId()
            } else if (profile.isGoogleLinked && profile.email.isNotBlank()) {
                profile.email
            } else {
                return@withContext // Cannot sync without a stable ID
            }

            val data = mapOf(
                "id" to profile.id,
                "name" to profile.name,
                "stage" to profile.stage,
                "weeksOrMonths" to profile.weeksOrMonths,
                "concern" to profile.concern,
                "isPremium" to profile.isPremium,
                "billingPlan" to profile.billingPlan,
                "isOnboarded" to profile.isOnboarded,
                "email" to profile.email,
                "age" to profile.age,
                "location" to profile.location,
                "testScore" to profile.testScore,
                "trackedRecommendations" to profile.trackedRecommendations,
                "lastUpdated" to System.currentTimeMillis()
            )

            // Use a timeout to avoid hanging if network is unreachable
            withTimeoutOrNull(8000) {
                db.collection("users").document(docId)
                    .set(data, SetOptions.merge())
                    .await()
                Log.d(TAG, "Profile synced successfully")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing profile: ${e.message}")
        }
    }

    suspend fun syncMoodToCheckInCloud(userId: String, mood: MoodCheckIn) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            val data = mapOf(
                "moodScore" to mood.moodScore,
                "moodNotes" to mood.moodNotes,
                "timestamp" to mood.timestamp,
                "advice" to mood.advice,
                "syncedAt" to System.currentTimeMillis()
            )

            withTimeoutOrNull(5000) {
                db.collection("users").document(userId)
                    .collection("mood_check_ins").document("mood_${mood.id}")
                    .set(data)
                    .await()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing mood: ${e.message}")
        }
    }

    suspend fun syncDocumentToCloud(userId: String, document: MedicalDocument) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            val data = mapOf(
                "name" to document.name,
                "timestamp" to document.timestamp,
                "type" to document.type,
                "documentText" to document.documentText,
                "syncedAt" to System.currentTimeMillis()
            )

            withTimeoutOrNull(10000) {
                db.collection("users").document(userId)
                    .collection("documents").document("doc_${document.id}")
                    .set(data)
                    .await()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing document: ${e.message}")
        }
    }

    suspend fun deleteDocumentFromCloud(userId: String, documentId: Int) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            db.collection("users").document(userId)
                .collection("documents").document("doc_$documentId")
                .delete()
                .await()
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting document from cloud: ${e.message}")
        }
    }
}
