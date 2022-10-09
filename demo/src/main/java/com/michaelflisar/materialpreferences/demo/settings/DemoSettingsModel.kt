package com.michaelflisar.materialpreferences.demo.settings

import android.graphics.Color
import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.core.classes.SettingsGroup
import com.michaelflisar.materialpreferences.core.initialisation.SettingSetup
import com.michaelflisar.materialpreferences.core.interfaces.StorageSetting
import com.michaelflisar.materialpreferences.datastore.DataStoreStorage
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

object DemoSettingsModel : SettingsModel(
    DataStoreStorage(
        name = "demo_settings",
        cache = SettingSetup.ENABLE_CACHING // false by default, only relevant for blocking reads
    )
) {

    val darkTheme by boolPref(false)
    val color1 by intPref(Color.parseColor("#A52A2A"))
    val color2 by intPref(Color.GREEN)
    val color3 by intPref(Color.parseColor("#88000000"))
    val proFeature1 by boolPref(false)
    val proFeature2 by boolPref(false)
    val proFeature3 by boolPref(true)
    val text1 by stringPref("Input 1")
    val text2 by stringPref("1234")
    val number1 by intPref(1234)
    val number2 by intPref(500)
    val numberLong by longPref(100L)
    val numberFloat by floatPref(200.5f)
    val numberDouble by doublePref(300.5)

    val numberSeekbarInt by intPref(50)
    val numberSeekbarLong by longPref(50L)
    val numberSeekbarFloat by floatPref(5f)
    val numberSeekbarDouble by doublePref(5.0)

    val enableChild by boolPref(false)
    val childName1 by stringPref("Michael")
    val childName2 by stringPref("Thomas")
    val childName3 by stringPref("Christian")

    val showChild by boolPref(true)
    val showChildName1 by stringPref("Michael")
    val showChildName2 by stringPref("Thomas")
    val showChildName3 by stringPref("Christian")

    val image by stringPref("")
    val image2 by intPref(-1)

    val choiceSingle by intPref(0)
    val choiceSingle2 by intPref(0)
    val choiceSingle3 by intPref(0)
    val choiceMulti by intSetPref()

    val parentOfCustomDependency by stringPref("Data")

    val enableFeature1 by boolPref(false)
    val enableFeature2 by boolPref(false)
    val enableFeature3 by boolPref(false)
    val enableFeature4 by boolPref(false)
    val enableFeature5 by boolPref(false)
    val enableFeature6 by boolPref(false)

    // simple types
    val test by stringPref("initial value")
    val testInt by intPref()
    val testBool by boolPref()
    val testFloat by floatPref()
    val testLong by longPref()

    // enum
    val testEnum by enumPref(TestEnum.Blue)

    // custom class
    val testClass by anyPref(TestClass.CONVERTER, TestClass())

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

    // ----------------
    // SPECIAL EXAMPLES - SettingsGroup
    // ----------------

    // Combine multiple settings in a single data class for convenience
    // => SettingsGroup offers its own flow/value property
    private val t1 by stringPref("t1")
    private val t2 by intPref()
    private val t3 by boolPref()
    private val t4 by floatPref()
    private val t5 by longPref()
    val tGroup = SettingsGroup(t1, t2, t3, t4, t5) {
        TestData.create(it)
    }

    data class TestData(
        val test: String,
        val testInt: Int,
        val testBool: Boolean,
        val testFloat: Float,
        val testLong: Long
    ) {
        companion object {
            fun create(data: List<Any?>) = TestData(
                data[0] as String,
                data[1] as Int,
                data[2] as Boolean,
                data[3] as Float,
                data[4] as Long,
            )
        }
    }


}