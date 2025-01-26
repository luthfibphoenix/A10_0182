package com.example.tugasakhirpamm.ui.view.Catatan

import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.DetailCatUiState
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.DetailCatatanViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
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
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi

object DestinasiDetailCatatan : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Catatan"
    const val CATATAN = "idCatatan"
    val routeWithArg = "$route/{$CATATAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewCatatan(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailCatatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Detail Catatan",
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
                    contentDescription = "Edit Catatan"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailCatatan(
            detailCatatanUiState = viewModel.catatanDetailState,
            modifier = Modifier.padding(innerPadding),
            retryAction = {
                viewModel.getCatatanById() // Fix the call here
            },
            onDeleteClick = {
                viewModel.deleteCatatan()
                navigateBack()
            }
        )
    }
}


@Composable
fun BodyDetailCatatan(
    retryAction: () -> Unit,
    detailCatatanUiState: DetailCatUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailCatatanUiState) {
        is DetailCatUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailCatUiState.Error -> {
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
        is DetailCatUiState.Success -> {
            // Check if pekerja is null and handle accordingly
            detailCatatanUiState.catatan?.let { catatan ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    ItemDetailCatatan(
                        catatan = catatan, // Pass the actual pekerja object
                        onDeleteClick = onDeleteClick,
                        modifier = modifier
                    )
                }
            } ?: run {
                // Handle the case when pekerja is null
                Text("Pekerja data is not available.")
            }
        }
    }
}


@Composable
fun ItemDetailCatatan(
    modifier: Modifier = Modifier,
    catatan: Catatan,
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
            ComponentDetailPekerja(judul = "Id Pekerja", isinya = catatan.id_panen)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Nama Pekerja", isinya = catatan.id_tanaman)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Jabatan", isinya = catatan.tanggal_panen)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Tanggal Panen", isinya = catatan.jumlah_panen)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Jumlah Panen", isinya = catatan.keterangan)
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
