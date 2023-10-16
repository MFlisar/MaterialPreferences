plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

android {

    namespace = "com.michaelflisar.materialpreferences.preferencescreen.choice"

    compileSdk = app.versions.compileSdk.get().toInt()

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            consumerProguardFiles("proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {

    // ------------------------
    // Kotlin
    // ------------------------

    implementation(libs.kotlin)

    // ------------------------
    // AndroidX / Google / Goolge
    // ------------------------

    implementation(deps.material)
    implementation(androidx.core)
    implementation(androidx.lifecycle)
    implementation(androidx.startup)

    // ------------------------
    // Dialogs
    // ------------------------

    implementation(deps.materialdialogs.core)
    implementation(deps.materialdialogs.dialogs.list)

    // ------------------------
    // Library
    // ------------------------

    implementation(deps.kotpreferences.core)
    implementation(project(":MaterialPreferences:Screen:Core"))
}

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = "screen-choice"
                from(components["release"])
            }
        }
    }
}