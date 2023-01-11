package com.michaelflisar.materialpreferences.encryption.aes

import android.util.Base64
import com.michaelflisar.materialpreferences.core.interfaces.StorageEncryption
import java.io.*
import java.security.SecureRandom
import java.util.*
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class StorageEncryptionAES(
    private val algorithm: String,
    private val key: SecretKey,
    private val iv: IvParameterSpec
) : StorageEncryption {

    companion object {

        private const val ALGORITHM = "AES"
        const val DEFAULT_ALGORITHM = "AES/CBC/PKCS5Padding"
        const val DEFAULT_KEY_ALGORITHM = "PBKDF2WithHmacSHA256"

        fun generateIv(): IvParameterSpec {
            val iv = ByteArray(16)
            SecureRandom().nextBytes(iv)
            return IvParameterSpec(iv)
        }

        fun getIv(iv: ByteArray): IvParameterSpec {
            return IvParameterSpec(iv)
        }

        fun generateKey(keyLength: Int): SecretKey {
            val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
            keyGenerator.init(keyLength)
            return keyGenerator.generateKey()
        }

        fun getKeyFromPassword(
            algorithm: String = DEFAULT_KEY_ALGORITHM,
            password: String,
            salt: String,
            iterations: Int = 65536,
            keyLength: Int = 256
        ): SecretKey {
            val factory: SecretKeyFactory = SecretKeyFactory.getInstance(algorithm)
            val spec = PBEKeySpec(password.toCharArray(), salt.toByteArray(), iterations, keyLength)
            return SecretKeySpec(factory.generateSecret(spec).encoded, ALGORITHM)
        }
    }

    // -------------------------
    // Algorithm
    // -------------------------

    private val encryptCipher by lazy {
        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        cipher
    }

    private val decryptCipher by lazy {
        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        cipher
    }

    private fun encrypt(data: String): String {
        val cipherText = encryptCipher.doFinal(data.toByteArray())
        return Base64.encodeToString(cipherText, Base64.DEFAULT)
    }

    private fun decrypt(data: String): String {
        val plainText = decryptCipher.doFinal(
            Base64.decode(data, Base64.DEFAULT)
        )
        return String(plainText)
    }

    private fun <T : Serializable> encryptSealedObject(
        data: T
    ): String {
        val sealed = SealedObject(data, encryptCipher)
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(sealed)
        oos.close()
        bos.close()
        val bytes = bos.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun <T : Serializable> decryptSealedObject(
        data: String
    ): T {
        val bytes = Base64.decode(data, Base64.DEFAULT)
        val bis = ByteArrayInputStream(bytes)
        val ois = ObjectInputStream(bis)
        val sealed = ois.readObject() as SealedObject
        ois.close()
        bis.close()
        return sealed.getObject(decryptCipher) as T
    }

    // -------------------------
    // Interface implementations
    // -------------------------

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

    override fun decryptStringSet(data: String) = decryptSealedObject<TreeSet<String>>(data)
    override fun encryptStringSet(value: Set<String>) = encryptSealedObject(TreeSet(value))

    override fun decryptIntSet(data: String) = decryptSealedObject<TreeSet<Int>>(data)
    override fun encryptIntSet(value: Set<Int>) = encryptSealedObject(TreeSet(value))

    override fun decryptLongSet(data: String) = decryptSealedObject<TreeSet<Long>>(data)
    override fun encryptLongSet(value: Set<Long>) = encryptSealedObject(TreeSet(value))

    override fun decryptFloatSet(data: String) = decryptSealedObject<TreeSet<Float>>(data)
    override fun encryptFloatSet(value: Set<Float>) = encryptSealedObject(TreeSet(value))

    override fun decryptDoubleSet(data: String) = decryptSealedObject<TreeSet<Double>>(data)
    override fun encryptDoubleSet(value: Set<Double>) = encryptSealedObject(TreeSet(value))
}