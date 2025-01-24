package com.example.tugasakhirpamm.ui.view.Tanaman

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.DetailTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.DetailUiState

object DestinasiDetailTanaman : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Tanaman"
    const val TANAMAN = "idTanaman"
    val routeWithArg = "$route/{$TANAMAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewTanaman(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
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
                    contentDescription = "Edit Tanaman"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailTanaman(
            detailUiState = viewModel.tanamanDetailState,
            modifier = Modifier.padding(innerPadding),
            retryAction = { viewModel.getTanamanById() },
            onDeleteClick = {
                viewModel.deleteTanaman()
                navigateBack()
            }
        )
    }
}

@Composable
fun BodyDetailTanaman(
    retryAction: () -> Unit,
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is DetailUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DetailUiState.Error -> {
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


        is DetailUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailTanaman(
                    tanaman = detailUiState.tanaman,
                    onDeleteClick = onDeleteClick,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailTanaman(
    modifier: Modifier = Modifier,
    tanaman: Tanaman,
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
            ComponentDetailTanaman(judul = "Id Tanaman", isinya = tanaman.id_tanaman)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTanaman(judul = "Nama Tanaman", isinya = tanaman.nama_tanaman)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTanaman(judul = "Periode Tanaman", isinya = tanaman.periode_tanaman)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTanaman(judul = "Deskripsi Tanaman", isinya = tanaman.deskripsi_tanaman)
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
fun ComponentDetailTanaman(
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
