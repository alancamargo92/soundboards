# Model classes
-keep class com.ukdev.carcadasalborghetti.model.** { *; }
-keep class com.ukdev.carcadasalborghetti.paid.model.** { *; }
-keep class com.ukdev.carcadasalborghetti.paid.api.** { *; }

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keep class org.koin.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}