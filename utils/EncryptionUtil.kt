package com.ozpehlivantugrul.passmanapp.utils

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val KEY_SIZE = 256
    private const val IV_SIZE = 16

    fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(KEY_SIZE)
        return keyGenerator.generateKey()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encrypt(secretKey: SecretKey, data: String): Pair<String, String> {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedData = cipher.doFinal(data.toByteArray())
        val encodedData = Base64.getEncoder().encodeToString(encryptedData)
        val encodedIv = Base64.getEncoder().encodeToString(iv)
        return Pair(encodedIv, encodedData)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decrypt(secretKey: SecretKey, ivString: String, encryptedData: String): String {
        val iv = Base64.getDecoder().decode(ivString)
        val ivSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
        val decodedData = Base64.getDecoder().decode(encryptedData)
        val originalData = cipher.doFinal(decodedData)
        return String(originalData)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveSecretKeyToSharedPreferences(context: Context, secretKey: SecretKey) {
        val encodedKey = Base64.getEncoder().encodeToString(secretKey.encoded)
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("secret_key", encodedKey).apply()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSecretKeyFromSharedPreferences(context: Context): SecretKey? {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val encodedKey = sharedPreferences.getString("secret_key", null) ?: return null
        val decodedKey = Base64.getDecoder().decode(encodedKey)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }
}