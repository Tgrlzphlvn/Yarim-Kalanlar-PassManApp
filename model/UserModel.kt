package com.ozpehlivantugrul.passmanapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.UUID

@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val password: String,
    val keyword: String,
)

@Entity(
    tableName = "credential_table",
    foreignKeys = [ForeignKey(
        entity = UserModel::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["userId"])]
)
data class CredentialModel(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val userId: Int,
    val username: CryptModel,
    val password: CryptModel,
    val url: String,
    val tag: String,
)

data class CryptModel(
    val iv: String,
    val hash: String
)

class Converters {

    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun toUUID(uuid: String): UUID {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromCryptModel(cryptModel: CryptModel): String {
        val gson = Gson()
        return gson.toJson(cryptModel)
    }

    @TypeConverter
    fun toCryptModel(cryptModelString: String): CryptModel {
        val gson = Gson()
        return gson.fromJson(cryptModelString, CryptModel::class.java)
    }
}
