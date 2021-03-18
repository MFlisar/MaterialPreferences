### About

![Latest release](https://img.shields.io/github/v/release/MFlisar/MaterialPreferences)
![Build status](https://img.shields.io/github/workflow/status/MFlisar/MaterialPreferences/Build%20library%20and%20test%20app)
![License](https://img.shields.io/github/license/MFlisar/MaterialPreferences)

### Structure

This library is based on `Flows` and `Coroutines` and works with the provided *DataStore Storage* or even with a custom storage implementation.
It supports `LiveData` by default as `Flows` can easily be converted to `LiveData`.
Additionally the *preference-screen* module provides a DSL to easily set up `RecyclerView` based preference screens.

### Introduction Core

With this library you can observe a storage (JetPack DataStore based implemention is provided, but can be replaced by any custom storage) via kotlin `Flows`.
This implicitly means it supports `LiveData` as well.
Settings (Preferences) are simply declared by delegates.

Here's a simply example of a preference definitions:

```kotlin
object UserSettingsModel : SettingsModel(DataStoreStorage(name = "user")) {

  // Basic
  val name by stringPref("User")
  val alive by boolPref(true)
  val hairColor by intPref(Color.parseColor("#A52A2A"))
  val age by intPref(40)
  val income by floatPref(50000f)
  val dayOfBirth by longPref(0L)
  
  // Sets
  val childrenAges by intSetPref(setOf(20, 18, 16))
  val childrenIncomes by floatSetPref(setOf(30000f, 10000f, 0f))
  val childrenDaysOfBirth by longSetPref(setOf(0L, 0L, 0L))
  
  // Enum
  val car by enumPref(Car.Tesla)
  
  // custom class - provide a custom converter (String <=> Class)
  val testClass by anyPref(TestClass.CONVERTER, TestClass())
}
```

And here's a simply example of the usage of this definition:

```kotlin

// 1) simply observe a setting
UserSettingsModel.name.observe(lifecycleScope) {
	L.d { "name = $it"}
}

// 2) direct read (ot recommended if not necessary)
val name = UserSettingsModel.name.value

// 3) observe a setting once
UserSettingsModel.name.observeOnce(lifecycleScope) {
	L.d { "name = $it"}
}

// 4) lifedata
val lifedata = UserSettingsModel.name.flow.asLiveData()

// 5) access flow directly and do whatever you want with it
val flow = UserSettingsModel.name.flow

```

### Introduction Storage

The `Storage` is an abstraction to support any storage implementation. The `datastore` module provides an implementation based on the [Android JetPack DataStore](https://developer.android.com/topic/libraries/architecture/datastore).

More about it here: [DataStore Module](README-DATASTORE.md)

### Introduction Preference Screen

The *preference-screen&ast;* modules allow you to create preference screens like following easily via DSL.

More about it here: [Preference Screen Modules](README-PREFERENCE-SCREEN.md)


### Gradle (via [JitPack.io](https://jitpack.io/))

1. add jitpack to your project's `build.gradle`:
```groovy
repositories {
    maven { url "https://jitpack.io" }
}
```
2. add the compile statement to your module's `build.gradle`:
```groovy
dependencies {

	// core module
	implementation 'com.github.MFlisar.MaterialPreferences:materialpreferences-core:<LATEST-VERSION>'

	// data store module
	implementation 'com.github.MFlisar.MaterialPreferences:materialpreferences-datastore:<LATEST-VERSION>'
    
    // preference screen modules
	implementation 'com.github.MFlisar.MaterialPreferences:materialpreferences-preference-screen:<LATEST-VERSION>'
	implementation 'com.github.MFlisar.MaterialPreferences:materialpreferences-preference-screen-input:<LATEST-VERSION>'
	implementation 'com.github.MFlisar.MaterialPreferences:materialpreferences-preference-screen-choice:<LATEST-VERSION>'
	implementation 'com.github.MFlisar.MaterialPreferences:materialpreferences-preference-screen-color:<LATEST-VERSION>'
}
```
