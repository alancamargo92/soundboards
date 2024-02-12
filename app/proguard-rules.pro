# Model classes
-keep class com.ukdev.carcadasalborghetti.framework.remote.api.** { *; }
-keep class com.ukdev.carcadasalborghetti.domain.model.** { *; }

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keep class org.koin.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}