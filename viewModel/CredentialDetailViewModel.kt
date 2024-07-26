package com.ozpehlivantugrul.passmanapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.ozpehlivantugrul.passmanapp.model.CredentialModel
import com.ozpehlivantugrul.passmanapp.model.CryptModel
import com.ozpehlivantugrul.passmanapp.model.UserModel
import com.ozpehlivantugrul.passmanapp.room.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class CredentialDetailViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    private val _credential = MutableLiveData<CredentialModel?>()
    val credential: LiveData<CredentialModel?> = _credential

    private val _user = MutableLiveData<UserModel?>()
    val user: LiveData<UserModel?> = _user


    fun getUser() {
        viewModelScope.launch {
            _user.value = repository.getUser()
        }
    }

    fun getFilteredCredential(id: String) {
        viewModelScope.launch {
            _credential.value = repository.getCredentialById(UUID.fromString(id))
        }
    }

    fun updateCredential(credentialModel: CredentialModel) {
        viewModelScope.launch {
            repository.updateCredential(credential = credentialModel)
        }
    }
}