import org.gradle.api.JavaVersion

object Config {

    object Build {

        const val MIN_SDK = 23
        const val TARGET_SDK = 34
        const val FLAVOUR_DIMENSION = "version"
        const val VERSION_NAME = "2023.3.0"

        val javaVersion = JavaVersion.VERSION_17
        val javaVersionString = javaVersion.majorVersion
    }

    object SigningConfigProperties {

        const val KEY_ALIAS = "carcadas_key_alias"
        const val KEY_PASSWORD = "carcadas_key_password"
        const val STORE_PASSWORD = "carcadas_store_password"
        const val STORE_FILE_FREE = "carcadas_free_store_file"
        const val STORE_FILE_PAID = "carcadas_paid_store_file"
    }

    object Free {

        const val FLAVOUR_NAME = "free"
        const val VERSION_CODE = 58
    }

    object Paid {

        const val FLAVOUR_NAME = "paid"
        const val VERSION_CODE = 59
    }
}
