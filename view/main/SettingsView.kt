package com.ozpehlivantugrul.passmanapp.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ozpehlivantugrul.passmanapp.R
import com.ozpehlivantugrul.passmanapp.ui.theme.night
import com.ozpehlivantugrul.passmanapp.ui.theme.pen
import com.ozpehlivantugrul.passmanapp.utils.RouteNames


@Composable
fun SettingsView(navController: NavController) {

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
            modifier = Modifier.padding(it)
        ) {

        }
    }
}