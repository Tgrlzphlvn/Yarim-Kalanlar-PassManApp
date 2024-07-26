package com.ozpehlivantugrul.passmanapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ozpehlivantugrul.passmanapp.model.Converters
import com.ozpehlivantugrul.passmanapp.model.CredentialModel
import com.ozpehlivantugrul.passmanapp.model.UserModel


@Database(entities = [UserModel::class, CredentialModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}