package com.michaelflisar.materialpreferences.core

import com.michaelflisar.materialpreferences.core.classes.EnumConverter
import com.michaelflisar.materialpreferences.core.initialisation.SettingSetup
import com.michaelflisar.materialpreferences.core.interfaces.Storage
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.core.settings.*
import kotlinx.coroutines.flow.Flow

abstract class SettingsModel(
    internal val storage: Storage
) {
    internal val internalProperties: MutableMap<String, StorageSetting<*>> = mutableMapOf()

    val changes: Flow<SettingsChangeEvent<*>> by lazy {
        storage.changeFlow
    }

    /**
     * Delegate string property
     * @param default default string value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun stringPref(
        default: String = "",
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<String> = StringSetting(this, default, key, cache)

    /**
     * Delegate string property
     * @param default default string value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableStringPref(
        default: String? = null,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<String?> = NullableStringSetting(this, default, key, cache)

    /**
     * Delegate string set property
     * @param default string integer set value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun stringSetPref(
        default: Set<String> = emptySet(),
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Set<String>> = StringSetSetting(this, default, key, cache)

    /**
     * Delegate bool property
     * @param default default bool value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun boolPref(
        default: Boolean = false,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Boolean> = BoolSetting(this, default, key, cache)

    /**
     * Delegate bool property
     * @param default default bool value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableBoolPref(
        default: Boolean? = null,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Boolean?> = NullableBoolSetting(this, default, key, cache)

    /**
     * Delegate integer property
     * @param default default integer value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun intPref(
        default: Int = 0,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Int> = IntSetting(this, default, key, cache)

    /**
     * Delegate integer property
     * @param default default integer value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableIntPref(
        default: Int? = null,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Int?> = NullableIntSetting(this, default, key, cache)

    /**
     * Delegate integer set property
     * @param default default integer set value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun intSetPref(
        default: Set<Int> = emptySet(),
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Set<Int>> = IntSetSetting(this, default, key, cache)

    /**
     * Delegate float property
     * @param default default float value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun floatPref(
        default: Float = 0f,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Float> = FloatSetting(this, default, key, cache)

    /**
     * Delegate float property
     * @param default default float value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableFloatPref(
        default: Float? = null,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Float?> = NullableFloatSetting(this, default, key, cache)

    /**
     * Delegate double property
     * @param default default double value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun doublePref(
        default: Double = 0.0,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Double> = DoubleSetting(this, default, key, cache)

    /**
     * Delegate double property
     * @param default default doubles value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableDoublePref(
        default: Double? = null,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Double?> = NullableDoubleSetting(this, default, key, cache)

    /**
     * Delegate long property
     * @param default default long value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun longPref(
        default: Long = 0L,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Long> = LongSetting(this, default, key, cache)

    /**
     * Delegate long property
     * @param default default long value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun nullableLongPref(
        default: Long? = null,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Long?> = NullableLongSetting(this, default, key, cache)

    /**
     * Delegate long property
     * @param default default long value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun longSetPref(
        default: Set<Long> = emptySet(),
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<Set<Long>> = LongSetSetting(this, default, key, cache)

    /**
     * Delegate enum property
     * @param default default enum value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected inline fun <reified T : Enum<*>> enumPref(
        default: T,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<T> = AnyIntSetting(this, default, key, EnumConverter(T::class.java), cache)

    /**
     * Delegate any property
     * @param default default any value
     * @param key custom storage key
     * @param cache enable caching for blocking reads (only used if storage has caching enabled as well)
     */
    protected fun <T : Any> anyPref(
        converter: SettingsConverter<T, String>,
        default: T,
        key: String? = null,
        cache: Boolean = SettingSetup.ENABLE_CACHING
    ): StorageSetting<T> = AnyStringSetting(this, default, key, converter, cache)
}