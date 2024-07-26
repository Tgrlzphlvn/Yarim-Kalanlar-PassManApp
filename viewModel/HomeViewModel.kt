package com.ozpehlivantugrul.passmanapp.viewModel


import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ozpehlivantugrul.passmanapp.model.CredentialModel
import com.ozpehlivantugrul.passmanapp.model.UserModel
import com.ozpehlivantugrul.passmanapp.room.UserRepository
import com.ozpehlivantugrul.passmanapp.utils.EncryptionUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext
import javax.crypto.SecretKey
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {

    private val _credentials = MutableLiveData<List<CredentialModel>>()
    val credentials: LiveData<List<CredentialModel>> = _credentials

    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> = _user


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveKey(context: Context) {
        var sharedKey: SecretKey?

        viewModelScope.launch {
            sharedKey = EncryptionUtil.getSecretKeyFromSharedPreferences(context = context)

            if (sharedKey == null) {
                val key = EncryptionUtil.generateKey()
                EncryptionUtil.saveSecretKeyToSharedPreferences(context = context, secretKey = key)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getKey(context: Context): SecretKey? {
        return EncryptionUtil.getSecretKeyFromSharedPreferences(context = context)
    }


    init {
        getCredentials()
    }

    fun getCredentials() {
        viewModelScope.launch {
            _user.value = repository.getUser()
            _credentials.value = repository.getCredentialsByUserId(userId = user.value?.uid ?: 0)

        }
        println(_credentials.value)
        println(_user.value)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptItem(item: String, context: Context): Pair<String, String> {
        return EncryptionUtil.encrypt(secretKey = getKey(context = context)!!, item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decryptItem(iv: String, encryptedData: String, context: Context): String {
        return if (getKey(context = context) != null) EncryptionUtil.decrypt(secretKey = getKey(context = context)!!, ivString = iv, encryptedData = encryptedData) else ""
    }

    fun addNewCredentialModel(credentialModel: CredentialModel) {
        viewModelScope.launch {
            repository.insertCredential(credential = credentialModel)
            getCredentials()
        }
    }
}