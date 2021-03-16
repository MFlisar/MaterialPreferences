# Abount

This library contains following (if possible independent) sub modules

* core
  * this module contains a kotlin delegate based preference manager that can work with any storage type
  * it provides convenient methods to declare, read and observe settings inside a storage (e.g. a datastore)
* datastore
  * this module contains a storage implementation based on [Android JetPack DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
* 

# Example - Core

TODO

```kotlin
```

# Example - PreferenceScreen

TODO

```kotlin
```

# Credits

Special thanks goes to following 2 libraries that do something very similar but do not work with the `DataStore` (yet).

* [KotPref](https://github.com/chibatching/Kotpref) - this library gave me the idea of the kotlin delegate based preference setup which is really useful
* [ModernAndroidPreferences](https://github.com/Maxr1998/ModernAndroidPreferences) - this library gave me the idea for the preference screens and I also copied a basic layout from there
