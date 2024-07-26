package com.ozpehlivantugrul.passmanapp.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ozpehlivantugrul.passmanapp.R
import com.ozpehlivantugrul.passmanapp.ui.theme.night
import com.ozpehlivantugrul.passmanapp.ui.theme.pen
import com.ozpehlivantugrul.passmanapp.ui.theme.salt
import com.ozpehlivantugrul.passmanapp.utils.RouteNames
import kotlin.random.Random


@Composable
fun GeneratePasswordView(navController: NavController) {
    var passwordLength by remember { mutableStateOf(8) }
    var generatedPassword by remember { mutableStateOf("pass") }
    var checkedNumbers by remember { mutableStateOf(true) }
    var checkedSymbols by remember { mutableStateOf(false) }
    var checkedUpperCase by remember { mutableStateOf(true) }
    var checkedLowerCase by remember { mutableStateOf(true) }


    val clipboardManager = LocalClipboardManager.current

    Scaffold(
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

                IconButton(
                    modifier = Modifier.padding(bottom = 15.dp),
                    onClick = {
                        navController.navigate(RouteNames.home) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.outline_chevron_backward_24
                        ),
                        contentDescription = "backward"
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                    .background(pen, shape = RoundedCornerShape(20.dp))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                    ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 6.dp, end = 6.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(salt, shape = RoundedCornerShape(20.dp))
                                .height(100.dp)
                                .weight(6f)
                        ) {
                            Text(text = generatedPassword)
                        }

                        Spacer(modifier = Modifier.size(6.dp))

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(salt, shape = RoundedCornerShape(20.dp))
                                .height(100.dp)
                                .weight(1f)
                        ) {
                            Column {
                                IconButton(onClick = {
                                    generatedPassword = generatePassword(
                                        passwordLength,
                                        symbols = checkedSymbols,
                                        numbers = checkedNumbers,
                                        upperCase = checkedUpperCase,
                                        lowerCase = checkedLowerCase
                                    )
                                }) {
                                    Icon(painter = painterResource(id = R.drawable.outline_restart_alt_24), contentDescription = "")
                                }
                                IconButton(onClick = {
                                    clipboardManager.setText(AnnotatedString(generatedPassword))
                                }) {
                                    Icon(painter = painterResource(id = R.drawable.outline_content_copy_24), contentDescription = "")
                                }
                            }
                        }
                    }

                    Column(
                    ) {
                        Text(
                            text = "Parola uzunluğunu ${passwordLength} karakter.",
                            fontSize = 12.sp
                        )

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(salt, shape = RoundedCornerShape(20.dp))
                                .height(50.dp)
                                .fillMaxWidth(0.97f)
                        ) {
                            Slider(
                                value = passwordLength.toFloat(),
                                onValueChange = {
                                    generatedPassword = generatePassword(
                                        passwordLength,
                                        symbols = checkedSymbols,
                                        numbers = checkedNumbers,
                                        upperCase = checkedUpperCase,
                                        lowerCase = checkedLowerCase
                                    )
                                    passwordLength = it.toInt()
                                },
                                valueRange = 4f..30f,
                                steps = 21
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(6.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(salt, shape = RoundedCornerShape(20.dp))
                            .height(50.dp)
                            .fillMaxWidth(0.97f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = "Sayılar")
                            Switch(
                                checked = checkedNumbers,
                                onCheckedChange = { checkedNumbers = it }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(6.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(salt, shape = RoundedCornerShape(20.dp))
                            .height(50.dp)
                            .fillMaxWidth(0.97f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = "Küçük Harf")
                            Switch(
                                checked = checkedLowerCase,
                                onCheckedChange = { checkedLowerCase = it }
                            )
                        }
                    }


                    Spacer(modifier = Modifier.padding(6.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(salt, shape = RoundedCornerShape(20.dp))
                            .height(50.dp)
                            .fillMaxWidth(0.97f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = "Büyük Harf")
                            Switch(
                                checked = checkedUpperCase,
                                onCheckedChange = { checkedUpperCase = it }
                            )
                        }
                    }


                    Spacer(modifier = Modifier.padding(6.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(salt, shape = RoundedCornerShape(20.dp))
                            .height(50.dp)
                            .fillMaxWidth(0.97f)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Text(text = "Semboller")
                            Switch(
                                checked = checkedSymbols,
                                onCheckedChange = { checkedSymbols = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun generatePassword(
    length: Int,
    symbols: Boolean,
    numbers: Boolean,
    upperCase: Boolean,
    lowerCase: Boolean
): String {
    val symbols = if (symbols) "!@#\$%^&*()_+-=[]{}|;:',.<>?/~`" else ""
    val numbers = if (numbers)"0123456789" else ""
    val upperCase = if (upperCase) "ABCDEFGHIJKLMNOPQRSTUVWXYZ" else ""
    val lowerCase = if (lowerCase) "abcdefghijklmnopqrstuvwxyz" else ""

    val charPool = "${upperCase}${lowerCase}${numbers}${symbols}"
    return (1..length)
        .map { Random.nextInt(0, charPool.length) }
        .map(charPool::get)
        .joinToString("")
}
