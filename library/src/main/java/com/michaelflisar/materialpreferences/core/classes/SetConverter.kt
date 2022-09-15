package com.michaelflisar.materialpreferences.core.classes

object SetConverter {

    fun convertIntSetToStringSet(data: Set<Int>) = data.map { it.toString() }.toSet()
    fun convertStringToIntSet(data: Set<String>) = data.map { it.toIntOrNull() ?: 0 }.toSet()

    fun convertLongSetToStringSet(data: Set<Long>) = data.map { it.toString() }.toSet()
    fun convertStringToLongSet(data: Set<String>) = data.map { it.toLongOrNull() ?: 0L }.toSet()

    fun convertFloatSetToStringSet(data: Set<Float>) = data.map { it.toString() }.toSet()
    fun convertStringToFloatSet(data: Set<String>) = data.map { it.toFloatOrNull() ?: 0f }.toSet()

    fun convertDoubleSetToStringSet(data: Set<Double>) = data.map { it.toString() }.toSet()
    fun convertStringToDoubleSet(data: Set<String>) = data.map { it.toDoubleOrNull() ?: 0.0 }.toSet()
}