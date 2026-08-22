# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Preserve line numbers for debugging
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Room Database R8 Rules
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Entity models & DAO interfaces
-keep class com.sanctus.christian.prayer.data.local.entity.** { *; }
-keep class com.sanctus.christian.prayer.data.local.dao.** { *; }

# Firebase Messaging R8 Rules
-keep class com.google.firebase.messaging.** { *; }
-dontwarn com.google.firebase.messaging.**

# Google Play Services & AdMob
-keep class com.google.android.gms.ads.** { *; }
-dontwarn com.google.android.gms.ads.**

# ViewBinding
-keep class com.sanctus.christian.prayer.databinding.** { *; }

# AndroidX Navigation
-keep class androidx.navigation.** { *; }
