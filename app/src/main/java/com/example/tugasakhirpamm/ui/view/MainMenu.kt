package com.example.tugasakhirpamm.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi

@Composable
fun HomeView(
    onTanamanButton: () -> Unit,
    onPekerjaButton: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Green, Color.Yellow)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Text
        Text(
            text = "Universitas Muhammadiyah Yogyakarta",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp ,
            color = Color.White
        )
        Spacer(Modifier.padding(15.dp))

        // Circular Image

        Spacer(Modifier.padding(15.dp))

        // Button for Dosen
        Button(
            onClick = { onTanamanButton() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp)
        ) {
            Text(text = "Tanaman")
        }

        // Button for Mata Kuliah
        Button(
            onClick = { onPekerjaButton() },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp)
        ) {
            Text(text = "Pekerja")
        }
    }
}