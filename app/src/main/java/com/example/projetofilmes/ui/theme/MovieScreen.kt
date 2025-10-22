package com.example.projetofilmes.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieScreen() {
    Text(text = "Conteúdo da tela de filmes")
}

@Preview(showBackground = true)
@Composable
fun MovieScreenPreview() {
    MovieScreen()
}
