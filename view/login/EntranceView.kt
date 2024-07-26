package com.ozpehlivantugrul.passmanapp.view.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ozpehlivantugrul.passmanapp.components.LongText
import com.ozpehlivantugrul.passmanapp.components.WelcomeNextButton
import com.ozpehlivantugrul.passmanapp.components.textFields.PassmanTextFiled
import com.ozpehlivantugrul.passmanapp.model.UserModel
import com.ozpehlivantugrul.passmanapp.utils.RouteNames
import com.ozpehlivantugrul.passmanapp.viewModel.EntranceViewModel


@Composable
fun EntranceView(
    navController: NavController,
    viewModel: EntranceViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var password by remember {
        mutableStateOf(List(6){""})
    }

    var keyword by remember {
        mutableStateOf("")
    }

    var completePın by remember {
        mutableStateOf(false)
    }

    val focusRequesters = List(6) { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LongText(text = "Uygulamaya girerken kullanacağınız 6 haneli şifrenizi belirleyiniz.")
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            for(i in 0 until 6) {
                OutlinedTextField(
                    value = password[i],
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            password = password.toMutableList().apply { set(i, newValue) }
                            if (newValue.isNotEmpty() && i < 5) {
                                focusRequesters[i + 1].requestFocus()
                            } else if(i == 5) {
                                focusManager.clearFocus()
                                completePın = true
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp, 56.dp)
                        .background(Color.White)
                        .focusRequester(focusRequesters[i])
                    ,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    maxLines = 1
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        LongText(
            text = "Şifrenizi unuttuğunuzda yeniden şifre belirlemek için kullanacağınız" +
                " anahtar kelimeyi belirleyin ve bu anahtar kelimeyi lütfen bir yere not edin.",
            )
        Spacer(modifier = Modifier.height(50.dp))

        PassmanTextFiled(value = keyword, placeholder = "Anahtar kelime, en sevdiğin süper kahraman...") {
            keyword = it
        }

        Spacer(modifier = Modifier.height(100.dp))

        WelcomeNextButton(enabled = completePın) {
            if (password.size == 6 && keyword != "" && keyword != null) {
                viewModel.insertUser(
                    UserModel(
                        password = "${password[0]}${password[1]}${password[2]}${password[3]}${password[4]}${password[5]}",
                        keyword = keyword,
                    )
                )
            } else {
                Toast.makeText(context, "Anahtar kelime boş bırakılamaz!", Toast.LENGTH_LONG).show()
            }
            navController.navigate(RouteNames.mainNav) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }
}

