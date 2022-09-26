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
    implementation "com.github.MFlisar.MaterialPreferences:screen-slider:<LATEST-VERSION>"
    implementation "com.github.MFlisar.MaterialPreferences:screen-image:<LATEST-VERSION>"
}
```

# Example

With this library you can declare preferences via kotlin `delegates`,and observe and update them via kotlin `Flows`. This works with any storage implementation, an implementation for JetPack DataStore is provided already.

##### 1/4 Define preferences:

```kotlin
object UserSettingsModel : SettingsModel(DataStoreStorage(name = "user")) {

    // main data types
    val someString by stringPref("value")
    val someBool by boolPref(false)
    val someInt by intPref(123)
    val someLong by intPref(123L)
    val someFloat by intPref(123f)
    val someDouble by intPref(123.0)
    
    // enum
    val someEnum by enumPref(Enum.Value1)
    
    // custom
    val someCustomClass by anyPref(TestClass.CONVERTER, TestClass())
    
    // sets
    val someStringSet by stringSetPref(setOf("a"))
    val someIntSet by intSetPref(setOf(1))
    val someLongSet by longSetPref(setOf(1L))
    val someFloatSet by floatSetPref(setOf(1f))
    val someDoubleSet by doubleSetPref(setOf(1.0))
    
    // NULLABLE vs NON NULLABLE
    val nonNullableString by stringPref()
    val nullableString by nullableStringPref()
    val nonNullableInt by intPref()
    val nullableInt by nullableIntPref()
    val nonNullableFloat by floatPref()
    val nullableFloat by nullableFloatPref()
    val nonNullableDouble by doublePref()
    val nullableDouble by nullableDoublePref()
    val nonNullableLong by longPref()
    val nullableLong by nullableLongPref()
    val nonNullableBool by boolPref()
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
UserSettingsModel.changes
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
| ![Demo](screenshots/preference-screen-5.jpg?raw=true "Demo") | ![Demo](screenshots/preference-screen-6.jpg?raw=true "Demo") |  |  |

# Modules

Readmes are split up for each module and can be found inside the `doc` folder, here are the links:

* [DataStore Storage](doc/storage-datastore.md)
* [Screen (all modules)](doc/screen.md)

# DEMO APP

Check the [demo app](demo/src/main/java/com/michaelflisar/materialpreferences/demo) for more informations.

# TODO

- [ ] Preference Types
    - [ ] Collapse Preference - add sub items ONCE on click
    - [ ] Dropdown Preference - can be achieved with visibility dependencies actually...
    - [ ] Spacer Preference
- [ ] Others
  - [ ] Global Setting - enable title numbering (Count numbers of categories yes/no)
