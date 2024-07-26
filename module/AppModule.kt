package com.ozpehlivantugrul.passmanapp.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ozpehlivantugrul.passmanapp.room.UserDao
import com.ozpehlivantugrul.passmanapp.room.UserDatabase
import com.ozpehlivantugrul.passmanapp.room.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("appSharedPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "pass_man_user_database_v3"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: UserDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao = userDao)
    }

}