package com.ozpehlivantugrul.passmanapp.room

import androidx.lifecycle.LiveData
import com.ozpehlivantugrul.passmanapp.model.CredentialModel
import com.ozpehlivantugrul.passmanapp.model.UserModel
import java.util.UUID
import javax.inject.Inject


class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun insertUser(user: UserModel): Long {
        return userDao.insertUser(user)
    }

    suspend fun insertCredential(credential: CredentialModel): Long {
        return userDao.insertCredential(credential)
    }

    suspend fun updateUser(user: UserModel) {
        userDao.updateUser(user)
    }

    suspend fun updateCredential(credential: CredentialModel) {
        userDao.updateCredential(credential)
    }

    suspend fun getUser(): UserModel? {
        return userDao.getUser()
    }

    suspend fun getCredentialById(credentialId: UUID): CredentialModel? {
        return userDao.getCredentialById(credentialId)
    }

    suspend fun getCredentialsByUserId(userId: Int): List<CredentialModel> {
        return userDao.getCredentialsByUserId(userId)
    }

    suspend fun getCredentialByUrl(url: String): CredentialModel? {
        return userDao.getCredentialsByURL(url)
    }

    suspend fun deleteUser(user: UserModel) {
        userDao.deleteUser(user)
    }

    suspend fun deleteCredential(credential: CredentialModel) {
        userDao.deleteCredential(credential)
    }
}
