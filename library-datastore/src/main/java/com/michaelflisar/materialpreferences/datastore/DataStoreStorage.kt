package com.michaelflisar.materialpreferences.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.michaelflisar.materialpreferences.core.SettingsChangeEvent
import com.michaelflisar.materialpreferences.core.classes.SetConverter
import com.michaelflisar.materialpreferences.core.initialisation.SettingSetup
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageEncryption
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Suppress("UNCHECKED_CAST")
class DataStoreStorage(
    name: String = "settings",
    override val cache: Boolean = SettingSetup.ENABLE_CACHING,
    private val encryption: StorageEncryption? = null
) : Storage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name)
    private val ctx: Context by lazy { SettingSetup.context }
    private val mutableChangeFlow: MutableSharedFlow<SettingsChangeEvent<*>> = MutableSharedFlow()
    override val changeFlow: Flow<SettingsChangeEvent<*>> = mutableChangeFlow

    override suspend fun <T : Any?> onValueChanged(setting: StorageSetting<T>, value: T) {
        mutableChangeFlow.emit(SettingsChangeEvent(setting, value))
    }

    override suspend fun clear() {
        ctx.dataStore.edit { it.clear() }
    }

    // --------------
    // Helper functions
    // --------------

    private fun <T2 : Any, T : T2?> get(
        key: String,
        keyCreator: (String) -> Preferences.Key<T2>,
        defaultValue: T,
        decrypt: StorageEncryption.(String) -> T2
    ): Flow<T> {
        return ctx.dataStore.data.map { settings ->
            if (encryption != null) {
                settings[stringPreferencesKey(key)]?.let { encryption.decrypt(it) }
            } else {
                settings[keyCreator(key)]
            } ?: defaultValue
        }.map { it as T }.distinctUntilChanged()
    }

    private suspend fun <T2 : Any, T : T2?> set(
        key: String,
        keyCreator: (String) -> Preferences.Key<T2>,
        value: T,
        encrypt: StorageEncryption.(T2) -> String
    ) {
        ctx.dataStore.edit { settings ->
            value?.let {
                if (encryption != null) {
                    settings[stringPreferencesKey(key)] = encryption.encrypt(it)
                } else {
                    settings[keyCreator(key)] = it
                }
            }
                ?: settings.remove(booleanPreferencesKey(key))
        }
    }

    private fun <T : Any, T2: Any> get2(
        key: String,
        keyCreator: (String) -> Preferences.Key<T>,
        defaultValue: T2,
        decrypt: StorageEncryption.(String) -> T2,
        converter: (T) -> T2
    ): Flow<T2> {
        return ctx.dataStore.data.map { settings ->
            if (encryption != null) {
                settings[stringPreferencesKey(key)]
                    ?.let { encryption.decrypt(it) }
            } else {
                settings[keyCreator(key)]?.let { converter(it) }
            } ?: defaultValue
        }.distinctUntilChanged()
    }

    private suspend fun <T : Any, T2 : Any> set2(
        key: String,
        keyCreator: (String) -> Preferences.Key<T>,
        value: T2,
        encrypt: StorageEncryption.(T2) -> String,
        converter: (T2) -> T
    ) {
        ctx.dataStore.edit { settings ->
            if (encryption != null) {
                settings[stringPreferencesKey(key)] = encryption.encrypt(value)
            } else {
                settings[keyCreator(key)] = converter(value)
            }
        }
    }

    // --------------
    // String
    // --------------

    override fun <T : String?> getString(key: String, defaultValue: T) =
        get<String, T>(key, ::stringPreferencesKey, defaultValue, StorageEncryption::decryptString)

    override suspend fun <T : String?> setString(key: String, value: T) =
        set<String, T>(key, ::stringPreferencesKey, value, StorageEncryption::encryptString)

    // --------------
    // Boolean
    // --------------

    override fun <T : Boolean?> getBool(key: String, defaultValue: T) =
        get<Boolean, T>(key, ::booleanPreferencesKey, defaultValue, StorageEncryption::decryptBool)

    override suspend fun <T : Boolean?> setBool(key: String, value: T) =
        set<Boolean, T>(key, ::booleanPreferencesKey, value, StorageEncryption::encryptBool)

    // --------------
    // Integer
    // --------------

    override fun <T : Int?> getInt(key: String, defaultValue: T) =
        get<Int, T>(key, ::intPreferencesKey, defaultValue, StorageEncryption::decryptInt)

    override suspend fun <T : Int?> setInt(key: String, value: T) =
        set<Int, T>(key, ::intPreferencesKey, value, StorageEncryption::encryptInt)

    // --------------
    // Long
    // --------------

    override fun <T : Long?> getLong(key: String, defaultValue: T) =
        get<Long, T>(key, ::longPreferencesKey, defaultValue, StorageEncryption::decryptLong)

    override suspend fun <T : Long?> setLong(key: String, value: T) =
        set<Long, T>(key, ::longPreferencesKey, value, StorageEncryption::encryptLong)

    // --------------
    // Float
    // --------------

    override fun <T : Float?> getFloat(key: String, defaultValue: T) =
        get<Float, T>(key, ::floatPreferencesKey, defaultValue, StorageEncryption::decryptFloat)

    override suspend fun <T : Float?> setFloat(key: String, value: T) =
        set<Float, T>(key, ::floatPreferencesKey, value, StorageEncryption::encryptFloat)

    // --------------
    // Double
    // --------------

    override fun <T : Double?> getDouble(key: String, defaultValue: T) =
        get<Double, T>(key, ::doublePreferencesKey, defaultValue, StorageEncryption::decryptDouble)

    override suspend fun <T : Double?> setDouble(key: String, value: T) =
        set<Double, T>(key, ::doublePreferencesKey, value, StorageEncryption::encryptDouble)

    // --------------
    // StringSet
    // --------------

    override fun getStringSet(key: String, defaultValue: Set<String>) =
        get(key, ::stringSetPreferencesKey, defaultValue, StorageEncryption::decryptStringSet)

    override suspend fun setStringSet(key: String, value: Set<String>) =
        set(key, ::stringSetPreferencesKey, value, StorageEncryption::encryptStringSet)

    // --------------
    // IntSet
    // --------------

    override fun getIntSet(key: String, defaultValue: Set<Int>) =
        get2(key, ::stringSetPreferencesKey, defaultValue, StorageEncryption::decryptIntSet, SetConverter::convertStringToIntSet)

    override suspend fun setIntSet(key: String, value: Set<Int>) =
        set2(key, ::stringSetPreferencesKey, value, StorageEncryption::encryptIntSet, SetConverter::convertIntSetToStringSet)

    // --------------
    // LongSet
    // --------------

    override fun getLongSet(key: String, defaultValue: Set<Long>) =
        get2(key, ::stringSetPreferencesKey, defaultValue, StorageEncryption::decryptLongSet, SetConverter::convertStringToLongSet)

    override suspend fun setLongSet(key: String, value: Set<Long>) =
        set2(key, ::stringSetPreferencesKey, value, StorageEncryption::encryptLongSet, SetConverter::convertLongSetToStringSet)

    // --------------
    // FloatSet
    // --------------

    override fun getFloatSet(key: String, defaultValue: Set<Float>) =
        get2(key, ::stringSetPreferencesKey, defaultValue, StorageEncryption::decryptFloatSet, SetConverter::convertStringToFloatSet)

    override suspend fun setFloatSet(key: String, value: Set<Float>)  =
        set2(key, ::stringSetPreferencesKey, value, StorageEncryption::encryptFloatSet, SetConverter::convertFloatSetToStringSet)

    // --------------
    // DoubleSet
    // --------------

    override fun getDoubleSet(key: String, defaultValue: Set<Double>) =
        get2(key, ::stringSetPreferencesKey, defaultValue, StorageEncryption::decryptDoubleSet, SetConverter::convertStringToDoubleSet)

    override suspend fun setDoubleSet(key: String, value: Set<Double>)  =
        set2(key, ::stringSetPreferencesKey, value, StorageEncryption::encryptDoubleSet, SetConverter::convertDoubleSetToStringSet)
}