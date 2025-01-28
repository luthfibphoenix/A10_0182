package com.example.tugasakhirpamm.ui.view.Aktivitas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.DetailAktivitasUiState
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.DetailAktivitasViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewAktivitas(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Detail Aktivitas",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigatBack = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEdit,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Aktivitas"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailAktivitas(
            detailAktivitasUiState = viewModel.aktivitasDetailState,
            modifier = Modifier.padding(innerPadding),
            retryAction = { viewModel.getAktivitasById() },
            onDeleteClick = {
                viewModel.deleteAktivitas()
                navigateBack()
            }
        )
    }
}

@Composable
fun BodyDetailAktivitas(
    retryAction: () -> Unit,
    detailAktivitasUiState: DetailAktivitasUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailAktivitasUiState) {
        is DetailAktivitasUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailAktivitasUiState.Error -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "An error occurred. Please try again.",
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = retryAction) {
                        Text("Retry")
                    }
                }
            }
        }
        is DetailAktivitasUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailAktivitas(
                    aktivitas = detailAktivitasUiState.aktivitas,
                    onDeleteClick = onDeleteClick,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailAktivitas(
    modifier: Modifier = Modifier,
    aktivitas: Aktivitas,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            ComponentDetailPekerja(judul = "Id Aktivitas", isinya = aktivitas.id_aktivitas)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Id Tanaman", isinya = aktivitas.id_tanaman)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Id Pekerja", isinya = aktivitas.id_pekerja)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Tanggal", isinya = aktivitas.tanggal_aktivitas)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Deskripsi", isinya = aktivitas.deskripsi_aktivitas)
            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    deleteConfirmationRequired = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }

            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDeleteClick()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun ComponentDetailPekerja(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}
