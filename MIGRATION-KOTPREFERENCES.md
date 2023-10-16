# Migration

No class names or functions did change, only some package names did change. Following are the necessary migration steps:

### Step 1: update gradle files

```
// OLD
implementation "com.github.MFlisar.MaterialPreferences:core:${versions.kotPreferences}"
implementation "com.github.MFlisar.MaterialPreferences:datastore:${versions.kotPreferences}"
implementation "com.github.MFlisar.MaterialPreferences:encryption-aes:${versions.kotPreferences}"

// NEW
implementation "com.github.MFlisar.KotPreferences:core:${versions.kotPreferences}"
implementation "com.github.MFlisar.KotPreferences:datastore:${versions.kotPreferences}"
implementation "com.github.MFlisar.KotPreferences:encryption-aes:${versions.kotPreferences}"
```

### Step2: update imports

* Core
  * OLD: `import com.michaelflisar.materialpreferences.core.*`
  * NEW: `import com.michaelflisar.kotpreferences.core.*`
* DataStore
  * OLD: `import com.michaelflisar.materialpreferences.datastore.*`
  * NEW: `import com.michaelflisar.kotpreferences.datastore.*`
* Encryption
  * OLD: `import com.michaelflisar.materialpreferences.encryption.*`
  * NEW: `import com.michaelflisar.kotpreferences.encryption.*`
