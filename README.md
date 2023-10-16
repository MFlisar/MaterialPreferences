# Material Preferences  [![Release](https://jitpack.io/v/MFlisar/MaterialPreferences.svg)](https://jitpack.io/#MFlisar/MaterialPreferences) ![License](https://img.shields.io/github/license/MFlisar/MaterialPreferences)

This library is dependent on [KotPreferences](https://github.com/MFlisar/KotPreferences), a library that is based on `Flows` and `Coroutines` and works with a provided *DataStore Storage* or even with a custom storage implementation.
It supports `LiveData` by default as `Flows` can easily be converted to `LiveData`. Preferences are elegantly declared via `delegates`.

This is an UI addition to [KotPreferences](https://github.com/MFlisar/KotPreferences) and provides a DSL to easily set up `RecyclerView` based preference screens.
It also supports custom extensions for custom preference screens.

Following are the key features:

* define preferences elegantly via delegates (KotPreferences)
* flow and coroutine based (KotPreferences)
* allows to observe single / some / all preferences (KotPreferences)
* provides suspending update functions (KotPreferences)
* provides a DSL for a `RecyclerView` based setting screen

# COMPOSE VERSION

You can find a compose version of this library under https://github.com/MFlisar/ComposePreferences

# IMPORTANT INFORMATION (2023-10-16)

I splitted up the library into `KotPreferences` and this library. Check out the migration guide [here](MIGRATION-KOTPREFERENCES.md)

This split up was made because I created a new preferences library for compose which uses the same core modules!

# Gradle (via [JitPack.io](https://jitpack.io/))

1. add jitpack to your project's `build.gradle`:

```
repositories {
    maven { url "https://jitpack.io" }
}
```

2. add the compile statement to your module's `build.gradle`:

```
dependencies {

    val kotPreferences = "<LATEST-VERSION>"
    val materialPreferences = "<LATEST-VERSION>"
    
    // --------------
    // KotPreferences
    // --------------
    
    // core module
    implementation("com.github.MFlisar.KotPreferences:core:$kotPreferences")
    
    // data store module
    implementation("com.github.MFlisar.KotPreferences:datastore:$kotPreferences")
    
    // encryption module
    implementation("com.github.MFlisar.KotPreferences:encryption-aes:$kotPreferences") 
    
    // -------------------
    // MaterialPreferences
    // -------------------
    
    // screen modules
    implementation("com.github.MFlisar.MaterialPreferences:screen:$materialPreferences")
    implementation("com.github.MFlisar.MaterialPreferences:screen-bool:$materialPreferences")
    implementation("com.github.MFlisar.MaterialPreferences:screen-input:$materialPreferences")
    implementation("com.github.MFlisar.MaterialPreferences:screen-choice:$materialPreferences")
    implementation("com.github.MFlisar.MaterialPreferences:screen-color:$materialPreferences")
    implementation("com.github.MFlisar.MaterialPreferences:screen-slider:$materialPreferences")
    implementation("com.github.MFlisar.MaterialPreferences:screen-image:$materialPreferences")
}
```

The latest **KotPreferences** release can be found [here](https://github.com/MFlisar/KotPreferences/releases/latest)
The latest **MaterialPreferences** release can be found [here](https://github.com/MFlisar/MaterialPreferences/releases/latest)

# Screenshots

| | | | |
| :---: | :---: | :---: | :---: |
| ![Demo](screenshots/preference-screen-1.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-2.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-3.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-4.jpg?raw=true "Demo") |
| ![Demo](screenshots/preference-screen-5.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-6.jpg?raw=true "Demo") |  |  |

# Example

With this library you can declare preferences via kotlin `delegates` and observe and update them via kotlin `Flows`. This works with any storage implementation, an implementation for JetPack DataStore is provided already.

##### Documentation

For the documentation of how preferences work and can be used, please check out the documentation of `KotPreferences` [here](https://github.com/MFlisar/KotPreferences)

# DEMO APP

Check the [demo app](demo/src/main/java/com/michaelflisar/materialpreferences/demo) for more informations.

This modules are placed inside the `screen-*` artifacts.

# Usage with build in settings activity (PREFFERED)

This is an activity with a toolbar and a back button and can be shown as following:

```kotlin
fun showDefaultSettingsActivity(activity: AppCompatActivity) {
    SettingsActivity.start(activity, ScreenCreator)
}

@Parcelize
object ScreenCreator : SettingsActivity.IScreenCreator {
    override fun createScreen(activity: AppCompatActivity, savedInstanceState: Bundle?, updateTitle: (title: String) -> Unit): PreferenceScreen {
        return screen {
             // ... set up your preference screen here
        }
    }
}
```

This uses a "trick" to provide a small and efficient parcelable setup via an `object` to avoid any problems (either memory nor speed wise) with the parcel size limit and still provides a convenient and simple way to use this library without having to write your own settings activity.

# Usage with a custom activity (ALTERNATIVE)

Alternatively you can simple extend `BaseSettingsActivity` and implement the single abstract `createScreen` function there s shown inside the [CustomSettingsActivity](../demo/src/main/java/com/michaelflisar/materialpreferences/demo/activities/CustomSettingsActivity.kt) and with this method you can of course also embed the settings screen inside any bigger layout.

Generally the manual approach works as simple as following:

* create the screen
* bind it to a `RecyclerView`
* forward the back press event to the screen so that it can handle its internal backstack

Here's an example:

https://github.com/MFlisar/MaterialPreferences/blob/bd25854fff6e23a826998013fdf5356fdc9b3bb0/demo/src/main/java/com/michaelflisar/materialpreferences/demo/activities/CustomSettingsActivity.kt#L15-L105

# Example - Screen

Here's an example:

```kotlin
val screen = screen {
  state = savedInstanceState
  category {
    title = "Test App Style".asText()
  }
  input(UserSettingsModel.name) {
    title = "Name".asText()
  }
  switch(UserSettingsModel.alive) {
    title = "Alive".asText()
  }
  subScreen {
    title = "More".asText()
    color(UserSettingsModel.hairColor) {
      title = "Hair Color".asText()
    }
    input(UserSettingsModel.age) {
      title = "Age".asText()
    }
  }
}
```

Check out the [demo app](../demo/src/main/java/com/michaelflisar/materialpreferences/demo) code for more details and especially the screen definitions in the demo here:

https://github.com/MFlisar/MaterialPreferences/blob/dad0db4565464905da10d4dd17f0ba6a3d92ee0e/demo/src/main/java/com/michaelflisar/materialpreferences/demo/DemoSettings.kt#L83-L555

# Supported Settings

Following settings are supported already:

* Category
* Sub Screens (supports nesting)
* Checkbox
* Switch
* Input (text, number, password, ...)
* Buttons
* Color (w/o alpha)
* Slider (Seekbar)

And following features are supported:

* callback to check if a value is allowed to be changed (e.g. to only allow a change in the pro version)
* dependency on other preference (even with custom dependency evaluator)
* badges to display a badge next to a settings title
* restores list state automatically even in nested preferences

# Default Settings

Some values can be defined globally and will be used by all preferences - those default values are stored in the `PreferenceScreenConfig` object. You can change dafault values like following:

```kotlin
PreferenceScreenConfig.apply {
    bottomSheet = true  // default: false
    maxLinesTitle = 1   // default: 1
    maxLinesSummary = 3 // default: 3
}
```

# Credits

Special thanks goes to [ModernAndroidPreferences](https://github.com/Maxr1998/ModernAndroidPreferences) because I copied a few things from there, namely following:
* the root layout xml
* the `RecyclerView` animations
* the badge idea and the badge drawable
* the basic idea for the DSL
