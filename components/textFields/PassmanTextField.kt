package com.ozpehlivantugrul.passmanapp.components.textFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PassmanTextFiled(value: String, placeholder: String, onChangeValue: (String) -> Unit) {

    OutlinedTextField(
        value = value,
        onValueChange = onChangeValue,
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(),
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp, top = 10.dp, bottom = 10.dp)
            .fillMaxWidth()
    )
}