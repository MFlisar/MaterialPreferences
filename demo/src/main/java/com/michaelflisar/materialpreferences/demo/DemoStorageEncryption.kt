package com.michaelflisar.materialpreferences.demo

import android.util.Base64
import com.michaelflisar.materialpreferences.core.interfaces.StorageEncryption
import org.json.JSONArray

/*
 * just for demo purposes - this type of pseudo encryption is not recommended!
 */
object DemoStorageEncryption : StorageEncryption {

    override fun decryptString(data: String) = decrypt(data)
    override fun encryptString(value: String) = encrypt(value)

    override fun decryptBool(data: String) = decrypt(data).toBoolean()
    override fun encryptBool(value: Boolean) = encrypt(value.toString())

    override fun decryptInt(data: String) = decrypt(data).toInt()
    override fun encryptInt(value: Int) = encrypt(value.toString())

    override fun decryptLong(data: String) = decrypt(data).toLong()
    override fun encryptLong(value: Long) = encrypt(value.toString())

    override fun decryptFloat(data: String) = decrypt(data).toFloat()
    override fun encryptFloat(value: Float) = encrypt(value.toString())

    override fun decryptDouble(data: String) = decrypt(data).toDouble()
    override fun encryptDouble(value: Double) = encrypt(value.toString())

    override fun decryptStringSet(data: String): Set<String> {
        val jsonArray = JSONArray(decrypt(data))
        val set = HashSet<String>()
        for (i in 0 until jsonArray.length())
            set.add(jsonArray.getString(i))
        return set
    }

    override fun encryptStringSet(value: Set<String>): String {
        val jsonArray = JSONArray(value)
        return encrypt(jsonArray.toString())
    }

    override fun decryptIntSet(data: String) = decryptStringSet(data).map { it.toInt() }.toSet()
    override fun encryptIntSet(value: Set<Int>) = encryptStringSet(value.map { it.toString() }.toSet())

    override fun decryptLongSet(data: String) = decryptStringSet(data).map { it.toLong() }.toSet()
    override fun encryptLongSet(value: Set<Long>) = encryptStringSet(value.map { it.toString() }.toSet())

    override fun decryptFloatSet(data: String) = decryptStringSet(data).map { it.toFloat() }.toSet()
    override fun encryptFloatSet(value: Set<Float>) = encryptStringSet(value.map { it.toString() }.toSet())

    override fun decryptDoubleSet(data: String) = decryptStringSet(data).map { it.toDouble() }.toSet()
    override fun encryptDoubleSet(value: Set<Double>) = encryptStringSet(value.map { it.toString() }.toSet())

    // --------------------------
    //  "algorithm" functions - simple base64 encoding/decoding
    // --------------------------

    private fun decrypt(data: String): String {
        return Base64.decode(data, Base64.DEFAULT).toString(Charsets.UTF_8)
    }

    private fun encrypt(data: String): String {
        return Base64.encodeToString(data.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
    }

}