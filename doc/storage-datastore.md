# Storage DataStore Module

The `Storage` is an abstraction to support any storage implementation. The `datastore` module provides an implementation based on the [Android JetPack DataStore](https://developer.android.com/topic/libraries/architecture/datastore).

This module is placed inside the `datastore` artifact and can simple be used like following:

```kotlin
object SettingsModel : SettingsModel(
    DataStoreStorage(
        name = "demo_settings", // file name of preference file
        cache = SettingSetup.ENABLE_CACHING // false by default, only relevant for blocking reads
    )
) {
   ...
}
```

It supports following data types:

* `String`
* `Bool`
* `Int`
* `Long`
* `Float`
* `Double`
* Sets:
  * `Set<String>`
  * `Set<Int>`
  * `Set<Long>`
  * `Set<Float>`
  * `Set<Double>`
