package com.ozpehlivantugrul.passmanapp.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ozpehlivantugrul.passmanapp.R


@Composable
fun AppImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.passman_logo),
        contentDescription = "Passman",
        modifier = modifier
    )
}
