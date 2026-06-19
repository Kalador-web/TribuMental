package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.UserProfile
import com.example.data.model.MoodCheckIn
import com.example.data.model.Appointment
import com.example.data.model.MedicalDocument
import com.example.data.model.SupportContact
import com.example.data.model.WhatsAppMessageLog
import com.example.data.dao.UserProfileDao
import com.example.data.dao.MoodCheckInDao
import com.example.data.dao.AppointmentDao
import com.example.data.dao.MedicalDocumentDao
import com.example.data.dao.SupportContactDao
import com.example.data.dao.WhatsAppLogDao

@Database(
    entities = [
        UserProfile::class,
        MoodCheckIn::class,
        Appointment::class,
        MedicalDocument::class,
        SupportContact::class,
        WhatsAppMessageLog::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao
    abstract fun moodCheckInDao(): MoodCheckInDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun medicalDocumentDao(): MedicalDocumentDao
    abstract fun supportContactDao(): SupportContactDao
    abstract fun whatsAppLogDao(): WhatsAppLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tribumental_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
