package com.ozpehlivantugrul.passmanapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun LongText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
    )
}
