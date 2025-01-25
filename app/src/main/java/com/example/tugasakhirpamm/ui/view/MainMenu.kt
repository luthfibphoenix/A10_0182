package com.example.tugasakhirpamm.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

object MainMenu: DestinasiNavigasi {
    override val route ="main_menu"
    override val titleRes = "Main Menu"
}

@Composable
fun HomeView(
    onTanamanButton: () -> Unit,
    onPekerjaButton: () -> Unit,
    onAktivitasButton: () -> Unit,
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
            text = "Aplikasi Pertanian",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(Modifier.height(32.dp))

        // Buttons Layout
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onTanamanButton() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)
            ) {
                Text(
                    text = "Tanaman",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onPekerjaButton() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)
            ) {
                Text(
                    text = "Pekerja",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onAktivitasButton() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)
            ) {
                Text(
                    text = "Aktivitas",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}
