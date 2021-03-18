package com.michaelflisar.materialpreferences.demo.settings

import android.graphics.Color
import com.michaelflisar.materialpreferences.core.SettingsModel
import com.michaelflisar.materialpreferences.datastore.DataStoreStorage

object DemoSettingsModel : SettingsModel(DataStoreStorage(name = "demo_settings")) {

    val darkTheme by boolPref(false)
    val color1 by intPref(Color.parseColor("#A52A2A"))
    val color2 by intPref(Color.GREEN)
    val color3 by intPref(Color.parseColor("#88000000"))
    val proFeature1 by boolPref(false)
    val proFeature2 by boolPref(false)
    val text1 by stringPref("Input 1")
    val text2 by stringPref("1234")
    val number1 by intPref(1234)
    val number2 by intPref(500)

    val enableChild by boolPref(false)
    val childName1 by stringPref("Michael")
    val childName2 by stringPref("Thomas")
    val childName3 by stringPref("Christian")

    val choiceSingle by intPref(0)
    val choiceMulti by intSetPref()

    val parentOfCustomDependency by stringPref("Data")

    val enableFeature1 by boolPref(false)
    val enableFeature2 by boolPref(false)
    val enableFeature3 by boolPref(false)
    val enableFeature4 by boolPref(false)

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
}