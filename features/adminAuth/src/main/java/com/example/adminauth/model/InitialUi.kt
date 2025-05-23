package com.example.adminauth.model

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.adminauth.AdminAuthUiState

object InitialUi : AdminAuthUiState {
    @Composable
    override fun Show(onSignInClick: (String, String) -> Unit) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.padding(top = 48.dp))
            TextField(
                value = email,
                placeholder = { Text("Email") },
                onValueChange = { email = it })
            TextField(
                value = password,
                placeholder = { Text("Password") },
                onValueChange = { password = it })
            Button(onClick = {
                onSignInClick(email, password)
            }) {
                Text("Войти")
            }
        }
    }
}