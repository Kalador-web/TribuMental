package com.example.data.repository

import com.example.data.dao.UserProfileDao
import com.example.data.dao.MoodCheckInDao
import com.example.data.dao.AppointmentDao
import com.example.data.dao.MedicalDocumentDao
import com.example.data.dao.SupportContactDao
import com.example.data.dao.WhatsAppLogDao
import com.example.data.model.UserProfile
import com.example.data.model.MoodCheckIn
import com.example.data.model.Appointment
import com.example.data.model.MedicalDocument
import com.example.data.model.SupportContact
import com.example.data.model.WhatsAppMessageLog
import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val profileDao: UserProfileDao,
    private val moodDao: MoodCheckInDao,
    private val appointmentDao: AppointmentDao,
    private val documentDao: MedicalDocumentDao,
    private val contactDao: SupportContactDao,
    private val whatsappDao: WhatsAppLogDao
) {
    val profile: Flow<UserProfile?> = profileDao.getProfileFlow()
    val allMoods: Flow<List<MoodCheckIn>> = moodDao.getAllMoods()
    val allAppointments: Flow<List<Appointment>> = appointmentDao.getAllAppointments()
    val allDocuments: Flow<List<MedicalDocument>> = documentDao.getAllDocuments()
    val allContacts: Flow<List<SupportContact>> = contactDao.getAllContacts()
    val allWhatsAppLogs: Flow<List<WhatsAppMessageLog>> = whatsappDao.getAllLogs()

    suspend fun getProfileSync(): UserProfile? = profileDao.getProfile()

    suspend fun saveProfile(profile: UserProfile) {
        profileDao.insertOrUpdate(profile)
        try {
            com.example.services.FirebaseSyncService.syncProfileToCloud(profile)
        } catch (e: Exception) {
            android.util.Log.e("AppRepository", "Failed cloud sync for profile: ${e.message}")
        }
    }

    suspend fun insertMood(mood: MoodCheckIn) {
        moodDao.insert(mood)
        try {
            val currentProfile = getProfileSync()
            val docId = if (currentProfile != null && currentProfile.isGoogleLinked && currentProfile.email.isNotBlank()) {
                currentProfile.email
            } else {
                "profile_1"
            }
            com.example.services.FirebaseSyncService.syncMoodToCheckInCloud(docId, mood)
        } catch (e: Exception) {
            android.util.Log.e("AppRepository", "Failed cloud sync for mood: ${e.message}")
        }
    }

    suspend fun clearMoods() {
        moodDao.clearMoods()
    }

    suspend fun insertAppointment(appointment: Appointment) {
        appointmentDao.insert(appointment)
    }

    suspend fun updateAppointmentStatus(id: Int, status: String) {
        appointmentDao.updateStatus(id, status)
    }

    suspend fun deleteAppointment(id: Int) {
        appointmentDao.delete(id)
    }

    suspend fun insertDocument(document: MedicalDocument) {
        documentDao.insert(document)
        try {
            val currentProfile = getProfileSync()
            val docId = if (currentProfile != null && currentProfile.isGoogleLinked && currentProfile.email.isNotBlank()) {
                currentProfile.email
            } else {
                "profile_1"
            }
            com.example.services.FirebaseSyncService.syncDocumentToCloud(docId, document)
        } catch (e: Exception) {
            android.util.Log.e("AppRepository", "Failed cloud sync for document: ${e.message}")
        }
    }

    suspend fun deleteDocument(id: Int) {
        documentDao.delete(id)
        try {
            val currentProfile = getProfileSync()
            val docId = if (currentProfile != null && currentProfile.isGoogleLinked && currentProfile.email.isNotBlank()) {
                currentProfile.email
            } else {
                "profile_1"
            }
            com.example.services.FirebaseSyncService.deleteDocumentFromCloud(docId, id)
        } catch (e: Exception) {
            android.util.Log.e("AppRepository", "Failed cloud sync for document deletion: ${e.message}")
        }
    }

    fun getDocumentsByAppointment(appointmentId: Int): Flow<List<MedicalDocument>> {
        return documentDao.getDocumentsByAppointment(appointmentId)
    }

    suspend fun insertContact(contact: SupportContact) {
        contactDao.insert(contact)
    }

    suspend fun deleteContact(id: Int) {
        contactDao.delete(id)
    }

    suspend fun insertWhatsAppLog(log: WhatsAppMessageLog) {
        whatsappDao.insert(log)
    }

    suspend fun clearWhatsAppLogs() {
        whatsappDao.clearLogs()
    }
}
