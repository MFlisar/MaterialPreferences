plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {

    namespace = "com.michaelflisar.materialpreferences.demo"

    compileSdk = app.versions.compileSdk.get().toInt()

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdk = app.versions.minSdk.get().toInt()
        targetSdk = app.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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

    implementation(libs.kotlinx.serialization.json)

    // ------------------------
    // AndroidX
    // ------------------------

    implementation(deps.material)
    implementation(androidx.recyclerview)
    implementation(androidx.core)
    implementation(androidx.lifecycle)

    // ------------------------
    // Library
    // ------------------------

    implementation(deps.kotpreferences.core)
    implementation(deps.kotpreferences.datastore)
    implementation(deps.kotpreferences.encryption.aes)

    // ------------------------
    // Libraries
    // ------------------------

    val live = false
    val materialPreferences = "1.3"

    // release test
    if (live) {
        implementation("com.github.MFlisar.MaterialPreferences:screen-core:$materialPreferences")
        implementation("com.github.MFlisar.MaterialPreferences:screen-bool:$materialPreferences")
        implementation("com.github.MFlisar.MaterialPreferences:screen-input:$materialPreferences")
        implementation("com.github.MFlisar.MaterialPreferences:screen-choice:$materialPreferences")
        implementation("com.github.MFlisar.MaterialPreferences:screen-color:$materialPreferences")
        implementation("com.github.MFlisar.MaterialPreferences:screen-slider:$materialPreferences")
        implementation("com.github.MFlisar.MaterialPreferences:screen-image:$materialPreferences")
    } else {
        implementation(project(":MaterialPreferences:Screen:Core"))
        implementation(project(":MaterialPreferences:Screen:Bool"))
        implementation(project(":MaterialPreferences:Screen:Input"))
        implementation(project(":MaterialPreferences:Screen:Choice"))
        implementation(project(":MaterialPreferences:Screen:Color"))
        implementation(project(":MaterialPreferences:Screen:Slider"))
        implementation(project(":MaterialPreferences:Screen:Image"))
    }


    // ------------------------
    // Dialogs (those are used inside the screen modules as well already!)
    // ------------------------

    implementation(deps.materialdialogs.core)
    implementation(deps.materialdialogs.dialogs.list)

    // ------------------------
    // Others
    // ------------------------

    implementation(deps.lumberjack)
}