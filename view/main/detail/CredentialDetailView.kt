package com.ozpehlivantugrul.passmanapp.view.main.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.ozpehlivantugrul.passmanapp.components.textFields.PassmanTextFiled
import com.ozpehlivantugrul.passmanapp.model.CredentialModel
import com.ozpehlivantugrul.passmanapp.model.CryptModel
import com.ozpehlivantugrul.passmanapp.ui.theme.ivory
import com.ozpehlivantugrul.passmanapp.ui.theme.night
import com.ozpehlivantugrul.passmanapp.ui.theme.pen
import com.ozpehlivantugrul.passmanapp.utils.RouteNames
import com.ozpehlivantugrul.passmanapp.viewModel.CredentialDetailViewModel
import com.ozpehlivantugrul.passmanapp.viewModel.HomeViewModel
import java.util.UUID


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CredentialDetailView(
    navController: NavController,
    credentialId: String,
    viewModel: CredentialDetailViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.getFilteredCredential(credentialId)
    }


    val credential by viewModel.credential.observeAsState()

    var email by remember {
        mutableStateOf(
             "null"
        )
    }

    var password by remember {
        mutableStateOf(
            "null"
        )
    }

    var url by remember {
        mutableStateOf( "null")
    }

    var tag by remember {
        mutableStateOf( "null")
    }

    if (credential != null) {
        email = homeViewModel.decryptItem(credential!!.username.iv, credential!!.username.hash, context)
        password = homeViewModel.decryptItem(credential!!.password.iv, credential!!.password.hash, context)
        url = credential!!.url
        tag =credential!!.tag
    }


    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .background(pen)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Text(
                    text = "Passman",
                    fontSize = 36.sp,
                    color = night,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(it)
        ) {
            Text(text = "Ad")
            PassmanTextFiled(
                value = tag,
                placeholder = ""
            ) {
                tag = it
            }
            Text(text = "Kullanıcı adı/mail adresi")
            PassmanTextFiled(
                value = email,
                placeholder = ""
            ) {
                email = it
            }
            Text(text = "Şifre")
            PassmanTextFiled(
                value = password,
                placeholder = ""
            ) {
                password = it
            }
            Text(text = "İnternet sitesi / Url")
            PassmanTextFiled(
                value = url,
                placeholder = ""
            ) {
                url = it
            }

            Button(onClick = {
                val (userIv,userHash) = homeViewModel.encryptItem(item = email, context = context)
                val (passIv, passHash) = homeViewModel.encryptItem(item = password, context = context)
                viewModel.updateCredential(
                    CredentialModel(
                        id = UUID.fromString(credentialId),
                        userId = credential?.userId ?: 1,
                        username = CryptModel(
                            iv = userIv,
                            hash = userHash
                        ),
                        password = CryptModel(
                            iv = passIv,
                            hash = passHash
                        ),
                        url = url,
                        tag = tag
                    )
                )

                navController.navigate(RouteNames.home) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
                viewModel.getUser()
            }) {
                Text(text = "Güncelle")
            }
        }
    }
}