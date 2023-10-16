dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
    versionCatalogs {

        val kotlin = "1.9.10"
        val ksp = "1.9.10-1.0.13"
        val coroutines = "1.7.3"
        val json = "1.0.1"
        val gradle = "8.1.2"
        val maven = "2.0"

        // TOML Files
        create("androidx") {
            from(files("gradle/androidx.versions.toml"))
        }
        create("deps") {
            from(files("gradle/dependencies.versions.toml"))
        }

        // Rest
        create("tools") {
            version("kotlin", kotlin)
            version("gradle", gradle)
            version("maven", maven)
            version("ksp", ksp)
        }
        create("app") {
            version("compileSdk", "34")
            version("minSdk", "21")
            version("targetSdk", "34")
        }
        create("libs") {
            // Kotlin
            library("kotlin", "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin")
            library("kotlin.coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")
            library("kotlin.reflect", "org.jetbrains.kotlin:kotlin-reflect:$kotlin")
            // KotlinX
            library("kotlinx.serialization.json", "org.jetbrains.kotlinx:kotlinx-serialization-json:$json")
        }
    }
}

// --------------
// App
// --------------

include(":MaterialPreferences:Screen:Core")
project(":MaterialPreferences:Screen:Core").projectDir = file("library-preference-screen")
include(":MaterialPreferences:Screen:Bool")
project(":MaterialPreferences:Screen:Bool").projectDir = file("library-preference-screen-bool")
include(":MaterialPreferences:Screen:Input")
project(":MaterialPreferences:Screen:Input").projectDir = file("library-preference-screen-input")
include(":MaterialPreferences:Screen:Choice")
project(":MaterialPreferences:Screen:Choice").projectDir = file("library-preference-screen-choice")
include(":MaterialPreferences:Screen:Color")
project(":MaterialPreferences:Screen:Color").projectDir = file("library-preference-screen-color")
include(":MaterialPreferences:Screen:Slider")
project(":MaterialPreferences:Screen:Slider").projectDir = file("library-preference-screen-slider")
include(":MaterialPreferences:Screen:Image")
project(":MaterialPreferences:Screen:Image").projectDir = file("library-preference-screen-image")

include(":demo")
project(":demo").projectDir = file("demo")
