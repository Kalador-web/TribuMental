# ProGuard rules for TribuMental Production

# Keep our Data Models so Firebase and Room can map them correctly
-keep class com.example.data.model.** { *; }

# Keep Room generated code
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.**

# Keep Moshi / Retrofit
-keep class com.squareup.moshi.** { *; }
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature, InnerClasses, EnclosingMethod

# Keep Firebase
-keep class com.google.firebase.** { *; }
