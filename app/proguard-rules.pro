# Model classes
-keep class com.ukdev.carcadasalborghetti.api.** { *; }
-keep class com.ukdev.carcadasalborghetti.model.** { *; }

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keep class org.koin.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}