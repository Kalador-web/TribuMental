package com.example.services

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.security.MessageDigest

object FirebaseAuthService {
    private const val TAG = "FirebaseAuthService"
    private var isInitialized = false

    val auth: FirebaseAuth?
        get() {
            return try {
                if (!isInitialized) {
                    FirebaseApp.getInstance()
                    isInitialized = true
                }
                FirebaseAuth.getInstance()
            } catch (e: Exception) {
                Log.e(TAG, "Error obtaining FirebaseAuth instance: ${e.message}")
                null
            }
        }

    fun getCurrentUser(): FirebaseUser? {
        return auth?.currentUser
    }

    fun getUserId(): String {
        return getCurrentUser()?.uid ?: "anonymous_user"
    }

    fun isUserLoggedIn(): Boolean {
        return getCurrentUser() != null
    }

    fun signOut() {
        try {
            auth?.signOut()
        } catch (e: Exception) {
            Log.e(TAG, "Error signing out from FirebaseAuth: ${e.message}")
        }
    }

    /**
     * Generates a stable fallback password for Google simulated accounts
     * to register them securely and seamlessly in Firebase Auth.
     */
    private fun getStablePassword(email: String): String {
        return try {
            val bytes = MessageDigest.getInstance("MD5").digest((email + "tribu_mental_secret_salt_2026").toByteArray())
            val hex = bytes.joinToString("") { "%02x".format(it) }
            "GAuth_$hex!9"
        } catch (e: Exception) {
            "GoogleAuthPass123!"
        }
    }

    /**
     * Integrates real Google Sign-In with Firebase Auth.
     * Includes a timeout to prevent infinite loading.
     */
    suspend fun signInWithGoogle(
        idToken: String,
        onComplete: (success: Boolean, errorMsg: String?) -> Unit
    ) = withContext(Dispatchers.Main) {
        val firebaseAuth = auth ?: return@withContext onComplete(false, "Firebase no listo")
        
        try {
            val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
            
            // Coroutine-friendly sign-in with 15s timeout
            withContext(Dispatchers.IO) {
                withTimeoutOrNull(15000) {
                    firebaseAuth.signInWithCredential(credential).await()
                }
            } ?: throw Exception("Tiempo de espera agotado al conectar con Firebase")

            onComplete(true, null)
        } catch (e: Exception) {
            Log.e(TAG, "Error in signInWithGoogle: ${e.message}")
            onComplete(false, e.localizedMessage ?: "Error de conexión")
        }
    }

    /**
     * Integrates Google accounts with Firebase Auth securely (Legacy/Simulated).
     * When a simulated/real Google login occurs, we enroll or sign them in
     * using Firebase Authentication, creating a full real session.
     */
    suspend fun loginOrRegisterGoogleInFirebase(
        email: String,
        name: String,
        onComplete: (success: Boolean, errorMsg: String?) -> Unit
    ) = withContext(Dispatchers.Main) {
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            Log.w(TAG, "Firebase Auth not available. Proceeding offline.")
            onComplete(true, null) // Fallback for offline simulation
            return@withContext
        }

        val stablePassword = getStablePassword(email)

        // Try signing in
        firebaseAuth.signInWithEmailAndPassword(email, stablePassword)
            .addOnSuccessListener { result ->
                Log.d(TAG, "Successfully logged in Google account in Firebase Auth: $email")
                onComplete(true, null)
            }
            .addOnFailureListener { signInError ->
                // If user doesn't exist, register them
                Log.d(TAG, "Google user does not exist in Firebase Auth yet. Registering... ${signInError.message}")
                firebaseAuth.createUserWithEmailAndPassword(email, stablePassword)
                    .addOnSuccessListener { signUpResult ->
                        Log.d(TAG, "Successfully registered Google account in Firebase Auth: $email")
                        // Update display name
                        val profileUpdates = com.google.firebase.auth.userProfileChangeRequest {
                            displayName = name
                        }
                        signUpResult.user?.updateProfile(profileUpdates)
                        onComplete(true, null)
                    }
                    .addOnFailureListener { signUpError ->
                        Log.e(TAG, "Failed registering Google User in Firebase Auth: ${signUpError.message}")
                        onComplete(false, signUpError.localizedMessage)
                    }
            }
    }

    /**
     * Registers a new user with standard email & password using Firebase Authentication.
     */
    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        onComplete: (success: Boolean, errorMsg: String?) -> Unit
    ) = withContext(Dispatchers.Main) {
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            onComplete(false, "Firebase Auth no está inicializado en este momento.")
            return@withContext
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                Log.d(TAG, "Standard Registration Successful for: $email")
                val profileUpdates = com.google.firebase.auth.userProfileChangeRequest {
                    displayName = name
                }
                result.user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener {
                        onComplete(true, null)
                    } ?: onComplete(true, null)
            }
            .addOnFailureListener { error ->
                Log.e(TAG, "Standard Registration Failed: ${error.message}")
                onComplete(false, error.localizedMessage)
            }
    }

    /**
     * Signs in an existing user with email and password via Firebase Auth.
     */
    suspend fun signInUser(
        email: String,
        password: String,
        onComplete: (success: Boolean, errorMsg: String?) -> Unit
    ) = withContext(Dispatchers.Main) {
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            onComplete(false, "Firebase Auth no está inicializado.")
            return@withContext
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                Log.d(TAG, "Standard Login Successful: $email")
                onComplete(true, null)
            }
            .addOnFailureListener { error ->
                Log.e(TAG, "Standard Login Failed: ${error.message}")
                onComplete(false, error.localizedMessage)
            }
    }
}
