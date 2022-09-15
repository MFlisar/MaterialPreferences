# Material Preferences  [![Release](https://jitpack.io/v/MFlisar/MaterialPreferences.svg)](https://jitpack.io/#MFlisar/MaterialPreferences) ![License](https://img.shields.io/github/license/MFlisar/MaterialPreferences)

This library is based on `Flows` and `Coroutines` and works with the provided *DataStore Storage* or even with a custom storage implementation.
It supports `LiveData` by default as `Flows` can easily be converted to `LiveData`. Preferences are elegantly declared via `delegates`.
Additionally the *preference-screen* module provides a DSL to easily set up `RecyclerView` based preference screens.
It also supports custom extensions for custom preference screens.

Following are the key features:

* define preferences elegantly via delegates
* flow and coroutine based
* allows to observe single / some / all preferences
* provides suspending update functions
* provides a DSL for a `RecyclerView` based setting screen

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
    // core module
    implementation "com.github.MFlisar.MaterialPreferences:core:<LATEST-VERSION>"
    // data store module
    implementation "com.github.MFlisar.MaterialPreferences:datastore:<LATEST-VERSION>" 
    // screen modules
    implementation "com.github.MFlisar.MaterialPreferences:screen:<LATEST-VERSION>"
    implementation "com.github.MFlisar.MaterialPreferences:screen-bool:<LATEST-VERSION>"
    implementation "com.github.MFlisar.MaterialPreferences:screen-input:<LATEST-VERSION>"
    implementation "com.github.MFlisar.MaterialPreferences:screen-choice:<LATEST-VERSION>"
    implementation "com.github.MFlisar.MaterialPreferences:screen-color:<LATEST-VERSION>"
}
```

# Example

With this library you can declare preferences via kotlin `delegates`,and observe and update them via kotlin `Flows`. This works with any storage implementation, an implementation for JetPack DataStore is provided already.

##### 1/4 Define preferences:

```kotlin
object UserSettingsModel : SettingsModel(DataStoreStorage(name = "user")) {

  // Basic
  val name by stringPref("User")
  val alive by boolPref(true)
  val hairColor by intPref(Color.parseColor("#A52A2A"))
  val age by intPref(40)
  val income by floatPref(50000f)
  val dayOfBirth by longPref(0L)
  val doubleTest by doublePref(0.0)
  
  // Sets
  val childrenAges by intSetPref(setOf(20, 18, 16))
  val childrenIncomes by floatSetPref(setOf(30000f, 10000f, 0f))
  val childrenDaysOfBirth by longSetPref(setOf(0L, 0L, 0L))
  
  // Enum
  val car by enumPref(Car.Tesla)
  
  // custom class - provide a custom converter (String <=> Class)
  val testClass by anyPref(TestClass.CONVERTER, TestClass())
  
  // NULLABLE variants
  val nullableString by nullableStringPref()
  val nullableInt by nullableIntPref()
  val nullableFloat by nullableFloatPref()
  val nullableDouble by nullableDoublePref()
  val nullableLong by nullableLongPref()
  val nullableBool by nullableBoolPref()
}
```

##### 2/4 Observe/Read preferences:

```kotlin
// 1) simply observe a setting
UserSettingsModel.name.observe(lifecycleScope) {
	L.d { "name = $it"}
}

// 2) direct read (not recommended if not necessary but may be useful in many cases => simply returns flow.first() in a blocking way)
val name = UserSettingsModel.name.value

// 3) observe a setting once
UserSettingsModel.name.observeOnce(lifecycleScope) {
	L.d { "name = $it"}
}

// 4) observe ALL settings
UserSettingsModel.changes.onEach {
	L.d { "[ALL SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to ${it.value}" }
}.launchIn(lifecycleScope)

// 5) observe SOME settings
UserSettingsModel.changes´
	.filter {
		it.setting == UserSettingsModel.name ||
		it.setting == UserSettingsModel.age
	}.onEach {
		// we know that either the name or the age changes
		L.d { "[SOME SETTINGS OBSERVER] Setting '${it.setting.key}' changed its value to ${it.value}" }
	}.launchIn(lifecycleScope)
	
// 6) read multiple settings in a suspending way
lifecycleScope.launch(Dispatchers.IO) {
  val name = UserSettingsModel.childName1.flow.first()
  val alive = DemoSettingsModel.alive.flow.first()
  val hairColor = DemoSettingsModel.hairColor.flow.first()
  withContext(Dispatchers.Main) {
	textView.text = "Informations: $name, $alive, $hairColor"
  }
}
```

##### 3/4 Lifedata:

```kotlin
val lifedata = UserSettingsModel.name.flow.asLiveData()
```

##### 4/4 Update preferences:

```kotlin
lifecycleScope.launch(Dispatchers.IO)  {
  UserSettingsModel.name.update("Some new name")
  UserSettingsModel.age.update(30)
}
```

# Screenshots

| | | | |
| :---: | :---: | :---: | :---: |
| ![Demo](screenshots/preference-screen-1.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-2.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-3.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-4.jpg?raw=true "Demo") |

# Modules

Readmes are split up for each module and can be found inside the `doc` folder, here are the links:

* [DataStore Storage](doc/storage-datastore.md)
* [Screen (all modules)](doc/screen.md)

# DEMO APP

Check the [demo app](demo/src/main/java/com/michaelflisar/materialpreferences/demo) for more informations.

# TODO

- [ ] Preference Types
	- [ ] SeekBar Preference
	- [ ] Collapse Preference - add sub items ONCE on click
	- [ ] Dropdown Preference
	- [ ] Spacer Preference
- [ ] Others
  - [ ] Global Setting - enable title numbering (Count numbers of categories yes/no)
