package com.ozpehlivantugrul.passmanapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ozpehlivantugrul.passmanapp.model.CredentialModel
import com.ozpehlivantugrul.passmanapp.model.UserModel
import java.util.UUID


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredential(credential: CredentialModel): Long

    @Update
    suspend fun updateUser(user: UserModel)

    @Update
    suspend fun updateCredential(credential: CredentialModel)

    @Transaction
    @Query("SELECT * FROM user_table")
    suspend fun getUser(): UserModel?

    @Transaction
    @Query("SELECT * FROM credential_table WHERE id = :credentialId")
    suspend fun getCredentialById(credentialId: UUID): CredentialModel?

    @Transaction
    @Query("SELECT * FROM credential_table WHERE userId = :userId")
    suspend fun getCredentialsByUserId(userId: Int): List<CredentialModel>

    @Transaction
    @Query("SELECT * FROM credential_table WHERE URL = :url")
    suspend fun getCredentialsByURL(url: String): CredentialModel?

    @Delete
    suspend fun deleteUser(user: UserModel)

    @Delete
    suspend fun deleteCredential(credential: CredentialModel)
}
