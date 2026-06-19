package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.UserProfile
import com.example.data.model.MoodCheckIn
import com.example.data.model.Appointment
import com.example.data.model.MedicalDocument
import com.example.data.model.SupportContact
import com.example.data.model.WhatsAppMessageLog
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getProfileFlow(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getProfile(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfile)

    @Update
    suspend fun update(profile: UserProfile)
}

@Dao
interface MoodCheckInDao {
    @Query("SELECT * FROM mood_check_in ORDER BY timestamp DESC")
    fun getAllMoods(): Flow<List<MoodCheckIn>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mood: MoodCheckIn)

    @Query("DELETE FROM mood_check_in")
    suspend fun clearMoods()
}

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointment ORDER BY dateTime ASC")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointment WHERE id = :id LIMIT 1")
    suspend fun getAppointmentById(id: Int): Appointment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment)

    @Query("UPDATE appointment SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)

    @Query("DELETE FROM appointment WHERE id = :id")
    suspend fun delete(id: Int)
}

@Dao
interface MedicalDocumentDao {
    @Query("SELECT * FROM medical_document ORDER BY timestamp DESC")
    fun getAllDocuments(): Flow<List<MedicalDocument>>

    @Query("SELECT * FROM medical_document WHERE appointmentId = :appointmentId")
    fun getDocumentsByAppointment(appointmentId: Int): Flow<List<MedicalDocument>>

    @Query("SELECT * FROM medical_document WHERE id = :id LIMIT 1")
    suspend fun getDocumentById(id: Int): MedicalDocument?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(document: MedicalDocument)

    @Query("DELETE FROM medical_document WHERE id = :id")
    suspend fun delete(id: Int)
}

@Dao
interface SupportContactDao {
    @Query("SELECT * FROM support_contact ORDER BY name ASC")
    fun getAllContacts(): Flow<List<SupportContact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: SupportContact)

    @Query("DELETE FROM support_contact WHERE id = :id")
    suspend fun delete(id: Int)
}

@Dao
interface WhatsAppLogDao {
    @Query("SELECT * FROM whatsapp_log ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<WhatsAppMessageLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: WhatsAppMessageLog)

    @Query("DELETE FROM whatsapp_log")
    suspend fun clearLogs()
}
