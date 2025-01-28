package com.example.tugasakhirpamm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.example.tugasakhirpamm.ui.navigasi.PengelolaHalaman

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PertanianApp(
    modifier: Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Green, Color.Yellow)
                        )
                    )
            ) {
                LargeTopAppBar(
                    title = { Text("Pertanian App") },
                    scrollBehavior = scrollBehavior,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Green, Color.Yellow)
                    )
                )
        ) {
            PengelolaHalaman()
            Text(
                text = "Selamat Datang di Pertanian App!",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,  // Or use MaterialTheme.colorScheme.onBackground
                modifier = Modifier.padding(16.dp)
            )

        }
    }
}
