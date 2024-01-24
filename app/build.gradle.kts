plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelise)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ukdev.carcadasalborghetti"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ukdev.carcadasalborghetti"
        minSdk = 23
        targetSdk = 34
        multiDexEnabled = true
    }

    signingConfigs {
        create("free") {
            keyAlias = properties["carcadas_key_alias"] as String
            keyPassword = properties["carcadas_key_password"] as String
            storeFile = file(path = properties["carcadas_free_store_file"] as String)
            storePassword = properties["carcadas_store_password"] as String
        }

        create("paid") {
            keyAlias = properties["carcadas_key_alias"] as String
            keyPassword = properties["carcadas_key_password"] as String
            storeFile = file(path = properties["carcadas_paid_store_file"] as String)
            storePassword = properties["carcadas_store_password"] as String
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("free") {
            versionCode = 58
            versionName = "2023.3.0"
            dimension = "version"
            signingConfig = signingConfigs.getByName("free")
        }

        create("paid") {
            versionCode = 59
            versionName = "2023.3.0"
            dimension = "version"
            applicationIdSuffix = ".paid"
            signingConfig = signingConfigs.getByName("paid")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))

    implementation(libs.android.activity)
    implementation(libs.android.appcompat)
    implementation(libs.android.core)
    implementation(libs.android.fragment)
    implementation(libs.android.swiperefreshlayout)
    implementation(libs.android.material)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.storage)
    implementation(libs.hilt.android)

    kapt(libs.hilt.compiler)
    ksp(libs.room.compiler)

    "freeImplementation"(libs.google.ads)

    "paidImplementation"(libs.room.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
}
