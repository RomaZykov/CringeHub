package com.example.adminhome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

interface AdminHomeUiState {

    @Composable
    fun Show()

    object Initial : AdminHomeUiState {
        @Composable
        override fun Show() {
            Scaffold { innerPadding ->
                LazyVerticalGrid(contentPadding = innerPadding, columns = GridCells.Fixed(2)) {
                    val items = listOf("Создать \nгайд", "Создать \nпрактическое \nзадание")
                    itemsIndexed(items) { index, item ->
                        CardInGrid(index, item)
                    }
                }
            }
        }
    }
}

@Composable
private fun CardInGrid(index: Int, item: String) {
    Card(
        modifier = Modifier
            .padding(
                top = 16.dp,
                bottom = 8.dp,
                start = if (index % 2 == 0) 16.dp else 8.dp,
                end = if (index % 2 == 0) 8.dp else 16.dp
            )
            .heightIn(172.dp, 172.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp),
                text = item,
                fontSize = 16.sp
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                painter = painterResource(R.drawable.image_placeholder),
                contentDescription = null
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
internal fun AdminHomePreview() {
    AdminHomeUiState.Initial.Show()
}