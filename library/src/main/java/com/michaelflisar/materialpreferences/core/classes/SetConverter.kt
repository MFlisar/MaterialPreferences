package com.michaelflisar.materialpreferences.core.classes

object SetConverter {
    fun convertIntSetToStringSet(data: Set<Int>) = data.map { it.toString() }.toSet()
    fun convertStringToIntSet(data: Set<String>) = data.map { it.toIntOrNull() ?: 0 }.toSet()
    fun convertLongSetToStringSet(data: Set<Long>) = data.map { it.toString() }.toSet()
    fun convertStringToLongSet(data: Set<String>) = data.map { it.toLongOrNull() ?: 0L }.toSet()
}