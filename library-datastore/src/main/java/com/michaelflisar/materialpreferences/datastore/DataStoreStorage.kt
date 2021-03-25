package com.michaelflisar.materialpreferences.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.michaelflisar.materialpreferences.core.SettingsChangeEvent
import com.michaelflisar.materialpreferences.core.classes.SetConverter
import com.michaelflisar.materialpreferences.core.initialisation.SettingSetup
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Suppress("UNCHECKED_CAST")
class DataStoreStorage(
        name: String = "settings"
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
    // String
    // --------------

    override fun <T : String?> getString(key: String, defaultValue: T) = ctx.dataStore.data.map { settings ->
        settings[stringPreferencesKey(key)] ?: defaultValue
    }.map { it as T }.distinctUntilChanged()

    override suspend fun <T : String?> setString(key: String, value: T) {
        ctx.dataStore.edit { settings ->
            value?.let { settings[stringPreferencesKey(key)] = it }
                    ?: settings.remove(stringPreferencesKey(key))
        }
    }

    // --------------
    // Integer
    // --------------

    override fun <T : Int?> getInt(key: String, defaultValue: T) = ctx.dataStore.data.map { settings ->
        settings[intPreferencesKey(key)] ?: defaultValue
    }.map { it as T }.distinctUntilChanged()

    override suspend fun <T : Int?> setInt(key: String, value: T) {
        ctx.dataStore.edit { settings ->
            value?.let { settings[intPreferencesKey(key)] = it }
                    ?: settings.remove(intPreferencesKey(key))
        }
    }

    // --------------
    // Boolean
    // --------------

    override fun <T : Boolean?> getBool(key: String, defaultValue: T) = ctx.dataStore.data.map { settings ->
        settings[booleanPreferencesKey(key)] ?: defaultValue
    }.map { it as T }.distinctUntilChanged()

    override suspend fun <T : Boolean?> setBool(key: String, value: T) {
        ctx.dataStore.edit { settings ->
            value?.let { settings[booleanPreferencesKey(key)] = it }
                    ?: settings.remove(booleanPreferencesKey(key))
        }
    }

    // --------------
    // Float
    // --------------

    override fun <T : Float?> getFloat(key: String, defaultValue: T) = ctx.dataStore.data.map { settings ->
        settings[floatPreferencesKey(key)] ?: defaultValue
    }.map { it as T }.distinctUntilChanged()

    override suspend fun <T : Float?> setFloat(key: String, value: T) {
        ctx.dataStore.edit { settings ->
            value?.let { settings[floatPreferencesKey(key)] = it }
                    ?: settings.remove(floatPreferencesKey(key))
        }
    }

    // --------------
    // Double
    // --------------

    override fun <T : Double?> getDouble(key: String, defaultValue: T) = ctx.dataStore.data.map { settings ->
        settings[doublePreferencesKey(key)] ?: defaultValue
    }.map { it as T }.distinctUntilChanged()

    override suspend fun <T : Double?> setDouble(key: String, value: T) {
        ctx.dataStore.edit { settings ->
            value?.let { settings[doublePreferencesKey(key)] = it }
                    ?: settings.remove(doublePreferencesKey(key))
        }
    }

    // --------------
    // Long
    // --------------

    override fun <T : Long?> getLong(key: String, defaultValue: T) = ctx.dataStore.data.map { settings ->
        settings[longPreferencesKey(key)] ?: defaultValue
    }.map { it as T }.distinctUntilChanged()

    override suspend fun <T : Long?> setLong(key: String, value: T) {
        ctx.dataStore.edit { settings ->
            value?.let { settings[longPreferencesKey(key)] = it }
                    ?: settings.remove(longPreferencesKey(key))
        }
    }

    // --------------
    // StringSet
    // --------------

    override fun getStringSet(key: String, defaultValue: Set<String>) = ctx.dataStore.data.map { settings ->
        settings[stringSetPreferencesKey(key)] ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setStringSet(key: String, value: Set<String>) {
        ctx.dataStore.edit { settings ->
            settings[stringSetPreferencesKey(key)] = value
        }
    }

    // --------------
    // IntSet
    // --------------

    override fun getIntSet(key: String, defaultValue: Set<Int>) = ctx.dataStore.data.map { settings ->
        settings[stringSetPreferencesKey(key)]?.let { SetConverter.convertStringToIntSet(it) }
                ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setIntSet(key: String, value: Set<Int>) {
        ctx.dataStore.edit { settings ->
            settings[stringSetPreferencesKey(key)] = SetConverter.convertIntSetToStringSet(value)
        }
    }

    // --------------
    // LongSet
    // --------------

    override fun getLongSet(key: String, defaultValue: Set<Long>) = ctx.dataStore.data.map { settings ->
        settings[stringSetPreferencesKey(key)]?.let { SetConverter.convertStringToLongSet(it) }
                ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setLongSet(key: String, value: Set<Long>) {
        ctx.dataStore.edit { settings ->
            settings[stringSetPreferencesKey(key)] = SetConverter.convertLongSetToStringSet(value)
        }
    }
}