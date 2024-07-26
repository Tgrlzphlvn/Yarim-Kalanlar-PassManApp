package com.ozpehlivantugrul.passmanapp.view.main

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ozpehlivantugrul.passmanapp.R
import com.ozpehlivantugrul.passmanapp.components.LongText
import com.ozpehlivantugrul.passmanapp.components.textFields.PassmanTextFiled
import com.ozpehlivantugrul.passmanapp.model.CredentialModel
import com.ozpehlivantugrul.passmanapp.model.CryptModel
import com.ozpehlivantugrul.passmanapp.ui.theme.egg
import com.ozpehlivantugrul.passmanapp.ui.theme.night
import com.ozpehlivantugrul.passmanapp.ui.theme.pen
import com.ozpehlivantugrul.passmanapp.ui.theme.salt
import com.ozpehlivantugrul.passmanapp.utils.RouteNames
import com.ozpehlivantugrul.passmanapp.viewModel.HomeViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val context = LocalContext.current

    val credentialModel by viewModel.credentials.observeAsState()


    var showModalBottomSheet by remember {
        mutableStateOf(false)
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var url by remember {
        mutableStateOf("")
    }

    var tag by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.saveKey(context = context)
    }


    Scaffold(
        containerColor = salt,
        topBar = {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(pen)
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Text(
                    text = "Passman",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = night,
                    modifier = Modifier.padding(20.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxHeight(0.7f)
                ) {
                    IconButton(onClick = {
                        navController.navigate(RouteNames.generatePassword)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.outline_key_24), contentDescription = "key")
                    }
                    IconButton(onClick = {
                        navController.navigate(RouteNames.settings)
                    }) {
                        Icon(painter = painterResource(id = R.drawable.outline_settings_24), contentDescription = "key")
                    }
                }
            }
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    modifier = Modifier.padding(4.dp),
                    containerColor = egg,
                    onClick = {
                        showModalBottomSheet = true
                        viewModel.getCredentials()
                    }
                ) {
                    Icon(painter = painterResource(id = R.drawable.outline_add_24), contentDescription = "Add")
                }
            }
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
        ) {
            if (!credentialModel.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .background(
                            pen,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    LazyColumn {
                        items(credentialModel!!) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 4.dp, end = 4.dp, top = 4.dp)
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = salt
                                    ),
                                    modifier = Modifier
                                        .weight(5f)
                                        .height(70.dp)
                                        .padding(2.dp)
                                        .background(Color.Transparent)

                                ) {
                                    Text(
                                        text = it.tag,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                                    )
                                    Text(
                                        text = viewModel.decryptItem(iv = it.username.iv, encryptedData = it.username.hash, context = context),
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                                    )
                                }


                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = salt
                                    ),
                                    modifier = Modifier
                                        .height(70.dp)
                                        .weight(1f)
                                        .padding(4.dp)
                                        .clickable {
                                            navController.navigate(RouteNames.detailWithArgs(it.id.toString()))
                                        }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillParentMaxSize()
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.outline_edit_note_24),
                                            contentDescription = "edit",
                                            modifier = Modifier.size(34.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            } else { Text(text = "Burası boş.")
            }
            if (showModalBottomSheet)
                ModalBottomSheet(
                    onDismissRequest = { showModalBottomSheet = false }
                ) {
                    Column {
                        LongText(text = "Email adresinizi giriniz.")
                        PassmanTextFiled(value = email, placeholder = "Email") {
                            email = it
                        }
                        LongText(text = "Şifrenizi giriniz")
                        PassmanTextFiled(value = password, placeholder = "Şifre") {
                            password = it
                        }
                        LongText(text = "Giriş bilgilerinizi kullanacağınız url")
                        PassmanTextFiled(value = url, placeholder = "Url adresi") {
                            url = it
                        }
                        LongText(text = "Bilgilerinizin hangi isimle görüneceğini belirleyin.")
                        PassmanTextFiled(value = tag, placeholder = "Başlık") {
                            tag = it
                        }

                        Button(
                            modifier = Modifier
                                .padding(30.dp)
                                .fillMaxWidth(),
                            onClick = {
                                if (email != "" && password != "" && url != "" && tag != ""){
                                    val (userIv, userCrypt) = viewModel.encryptItem(email, context = context)
                                    val (passIv, passCrypt) = viewModel.encryptItem(password, context = context)

                                    if (viewModel.user.value != null) {
                                        viewModel.addNewCredentialModel(
                                            CredentialModel(
                                                userId = viewModel.user.value!!.uid,
                                                username = CryptModel(iv = userIv, hash = userCrypt),
                                                password = CryptModel(iv = passIv, hash = passCrypt),
                                                url = url,
                                                tag = tag
                                            )
                                        )
                                    }
                                    email = ""
                                    password = ""
                                    url = ""
                                    tag = ""
                                    showModalBottomSheet = false
                                } else {
                                    Toast.makeText(context, "Hiçbir bölüm boş bırakılamaz!", Toast.LENGTH_LONG).show()
                                }
                            }
                        ) {
                            Text(text = "Kaydet")
                        }
                    }
                }
        }
    }
}
