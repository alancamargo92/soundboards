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
    compileSdk = Config.Build.TARGET_SDK

    defaultConfig {
        applicationId = "com.ukdev.carcadasalborghetti"
        minSdk = Config.Build.MIN_SDK
        targetSdk = Config.Build.TARGET_SDK
        multiDexEnabled = true
    }

    signingConfigs {
        create(Config.Free.FLAVOUR_NAME) {
            keyAlias = properties[Config.SigningConfigProperties.KEY_ALIAS] as String
            keyPassword = properties[Config.SigningConfigProperties.KEY_PASSWORD] as String
            storeFile = file(path = properties[Config.SigningConfigProperties.STORE_FILE_FREE] as String)
            storePassword = properties[Config.SigningConfigProperties.STORE_PASSWORD] as String
        }

        create(Config.Paid.FLAVOUR_NAME) {
            keyAlias = properties[Config.SigningConfigProperties.KEY_ALIAS] as String
            keyPassword = properties[Config.SigningConfigProperties.KEY_PASSWORD] as String
            storeFile = file(path = properties[Config.SigningConfigProperties.STORE_FILE_PAID] as String)
            storePassword = properties[Config.SigningConfigProperties.STORE_PASSWORD] as String
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

    flavorDimensions += Config.Build.FLAVOUR_DIMENSION

    productFlavors {
        create(Config.Free.FLAVOUR_NAME) {
            versionCode = Config.Free.VERSION_CODE
            versionName = Config.Build.VERSION_NAME
            dimension = Config.Build.FLAVOUR_DIMENSION
            signingConfig = signingConfigs.getByName(Config.Free.FLAVOUR_NAME)
        }

        create(Config.Paid.FLAVOUR_NAME) {
            versionCode = Config.Paid.VERSION_CODE
            versionName = Config.Build.VERSION_NAME
            dimension = Config.Build.FLAVOUR_DIMENSION
            applicationIdSuffix = ".paid"
            signingConfig = signingConfigs.getByName(Config.Paid.FLAVOUR_NAME)
        }
    }

    compileOptions {
        sourceCompatibility = Config.Build.javaVersion
        targetCompatibility = Config.Build.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.Build.javaVersionString
    }

    buildFeatures {
        viewBinding = true
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
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

    testImplementation(libs.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.android)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
}
