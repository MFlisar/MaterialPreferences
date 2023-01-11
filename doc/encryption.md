# Encryption Module

Currently there only exists the AES encryption module. It simple implements a predefined interface that encrypts and decrypts all data before it get's persisted by a storage implementation.

This module is placed inside the `encrpytion-aes` artifact and can simple be used like following:

### Step 1/2: define the encryption

```kotlin
private const val ALGORITHM = StorageEncryptionAES.DEFAULT_ALGORITHM
private const val KEY_ALGORITHM = StorageEncryptionAES.DEFAULT_KEY_ALGORITHM
// also check out StorageEncryptionAES::generateKey and StorageEncryptionAES::generateIv if you need some helper functions
private val KEY = StorageEncryptionAES.getKeyFromPassword(KEY_ALGORITHM, "my key", "my salt")
private val BYTE_ARRAY = listOf(0x16, 0x09, 0xc0, 0x4d, 0x4a, 0x09, 0xd2, 0x46, 0x71, 0xcc, 0x32, 0xb7, 0xd2, 0x91, 0x8a, 0x9c)
	.map { it.toByte() }
	.toByteArray()
private val IV = StorageEncryptionAES.getIv(BYTE_ARRAY) // byte array must be 16 bytes!
val ENCRYPTION = StorageEncryptionAES(ALGORITHM, KEY, IV)
```

### Step 2/2: attach the encryption to your storage instance

```kotlin
object MyEncryptedSettingsModel : SettingsModel(
    DataStoreStorage(
        name = "encrypted",
        cache = true,
        encryption = ENCRYPTION
    )
) {
	// ...
}
```