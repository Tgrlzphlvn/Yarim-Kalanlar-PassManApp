package com.ozpehlivantugrul.passmanapp.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ozpehlivantugrul.passmanapp.components.AppImage
import com.ozpehlivantugrul.passmanapp.components.LongText
import com.ozpehlivantugrul.passmanapp.components.WelcomeNextButton
import com.ozpehlivantugrul.passmanapp.utils.RouteNames
import com.ozpehlivantugrul.passmanapp.viewModel.EntranceViewModel

@Composable
fun WelcomeView(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp, bottom = 100.dp)
    ) {
        AppImage(
            modifier = Modifier
                .size(400.dp)
        )
        LongText(text = "Hoşgeldiniz, Passman internet erişimi istemez, parolalarınız sadece telefonunuzda" +
                " şifrelenmiş bir şekilde saklar. Mahremiyetinize ve güvenliğinize önem verir.",)
        Spacer(modifier = Modifier.size(90.dp))
        WelcomeNextButton() {
            navController.navigate(RouteNames.entrance)
        }
    }
}

