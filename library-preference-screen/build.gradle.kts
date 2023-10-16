plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {

    namespace = "com.michaelflisar.materialpreferences.preferencescreen"

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
    implementation(deps.materialdialogs.ext.fragment.bottomsheet)
    implementation(deps.materialdialogs.ext.fragment.dialog)

    // ------------------------
    // Library
    // ------------------------

    implementation(deps.kotpreferences.core)
    api(deps.androidtext)
}

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = "screen"
                from(components["release"])
            }
        }
    }
}