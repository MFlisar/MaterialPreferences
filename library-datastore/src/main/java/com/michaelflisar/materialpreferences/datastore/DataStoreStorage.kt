package com.michaelflisar.materialpreferences.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.michaelflisar.materialpreferences.core.classes.SetConverter
import com.michaelflisar.materialpreferences.core.initialisation.SettingSetup
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DataStoreStorage(
        name: String = "settings"
) : Storage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name)

    private val ctx: Context by lazy { SettingSetup.context }

    override suspend fun clear() {
        ctx.dataStore.edit { it.clear() }
    }

    // --------------
    // String
    // --------------

    override fun getString(key: String, defaultValue: String) = ctx.dataStore.data.map { settings ->
        settings[stringPreferencesKey(key)] ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setString(key: String, value: String) {
        ctx.dataStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }

    // --------------
    // Integer
    // --------------

    override fun getInt(key: String, defaultValue: Int) = ctx.dataStore.data.map { settings ->
        settings[intPreferencesKey(key)] ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setInt(key: String, value: Int) {
        ctx.dataStore.edit { settings ->
            settings[intPreferencesKey(key)] = value
        }
    }

    // --------------
    // Boolean
    // --------------

    override fun getBool(key: String, defaultValue: Boolean) = ctx.dataStore.data.map { settings ->
        settings[booleanPreferencesKey(key)] ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setBool(key: String, value: Boolean) {
        ctx.dataStore.edit { settings ->
            settings[booleanPreferencesKey(key)] = value
        }
    }

    // --------------
    // Float
    // --------------

    override fun getFloat(key: String, defaultValue: Float) = ctx.dataStore.data.map { settings ->
        settings[floatPreferencesKey(key)] ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setFloat(key: String, value: Float) {
        ctx.dataStore.edit { settings ->
            settings[floatPreferencesKey(key)] = value
        }
    }

    // --------------
    // Long
    // --------------

    override fun getLong(key: String, defaultValue: Long) = ctx.dataStore.data.map { settings ->
        settings[longPreferencesKey(key)] ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setLong(key: String, value: Long) {
        ctx.dataStore.edit { settings ->
            settings[longPreferencesKey(key)] = value
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
        settings[stringSetPreferencesKey(key)]?.let { SetConverter.convertStringToIntSet(it) } ?: defaultValue
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
        settings[stringSetPreferencesKey(key)]?.let { SetConverter.convertStringToLongSet(it) } ?: defaultValue
    }.distinctUntilChanged()

    override suspend fun setLongSet(key: String, value: Set<Long>) {
        ctx.dataStore.edit { settings ->
            settings[stringSetPreferencesKey(key)] = SetConverter.convertLongSetToStringSet(value)
        }
    }
}