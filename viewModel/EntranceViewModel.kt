package com.ozpehlivantugrul.passmanapp.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozpehlivantugrul.passmanapp.model.UserModel
import com.ozpehlivantugrul.passmanapp.room.UserDao
import com.ozpehlivantugrul.passmanapp.room.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EntranceViewModel @Inject constructor(
    private val repository: UserRepository,
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    fun insertUser(userModel: UserModel) {
        viewModelScope.launch {
            repository.insertUser(user = userModel)
            saveLoginState(isLoading = true)
        }
    }

    fun saveLoginState(isLoading: Boolean) {
        sharedPreferences.edit().putBoolean("isLoading", isLoading).apply()
    }

    fun getLoginState(): Boolean {
        return sharedPreferences.getBoolean("isLoading", false)
    }

}