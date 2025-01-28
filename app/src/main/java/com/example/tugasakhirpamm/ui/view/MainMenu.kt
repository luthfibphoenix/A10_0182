package com.example.tugasakhirpamm.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi

object MainMenu: DestinasiNavigasi {
    override val route = "main_menu"
    override val titleRes = "Main Menu"
}

@Composable
fun HomeView(
    onTanamanButton: () -> Unit,
    onPekerjaButton: () -> Unit,
    onAktivitasButton: () -> Unit,
    onCatatanButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Green, Color.DarkGray)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title Text
        Text(
            text = "Aplikasi Pertanian",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(Modifier.height(40.dp))

        // Buttons Layout
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tanaman Button
            Button(
                onClick = { onTanamanButton() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Tanaman", tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Tanaman",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pekerja Button
            Button(
                onClick = { onPekerjaButton() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Icon(Icons.Default.Person, contentDescription = "Pekerja", tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Pekerja",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Aktivitas Button
            Button(
                onClick = { onAktivitasButton() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = "Aktivitas", tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Aktivitas",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Catatan Button
            Button(
                onClick = { onCatatanButton() },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Catatan", tint = Color.White)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Catatan",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
