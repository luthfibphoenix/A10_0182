package com.example.tugasakhirpamm.ui.view.Pekerja

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
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.DetailPekerjaUiState
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.DetailPekerjaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewPekerja(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Detail Pekerja",
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
                    contentDescription = "Edit Pekerja"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailPekerja(
            detailPekerjaUiState = viewModel.pekerjaDetailState,
            modifier = Modifier.padding(innerPadding),
            retryAction = {
                viewModel.getPekerjaById() // Fix the call here
            },
            onDeleteClick = {
                viewModel.deletePekerja(viewModel.pekerjaDetailState.let { state ->
                    if (state is DetailPekerjaUiState.Success)  state.pekerja.id_pekerja else ""
                })
                navigateBack()
            }
        )
    }
}


@Composable
fun BodyDetailPekerja(
    retryAction: () -> Unit,
    detailPekerjaUiState: DetailPekerjaUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailPekerjaUiState) {
        is DetailPekerjaUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailPekerjaUiState.Error -> {
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
        is DetailPekerjaUiState.Success -> {
            detailPekerjaUiState.pekerja?.let { pekerja ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    ItemDetailPekerja(
                        pekerja = pekerja,
                        onDeleteClick = onDeleteClick,
                        modifier = modifier
                    )
                }
            }
        }
    }
}


@Composable
fun ItemDetailPekerja(
    modifier: Modifier = Modifier,
    pekerja: Pekerja,
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
            ComponentDetailPekerja(judul = "Id Pekerja", isinya = pekerja.id_pekerja)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Nama Pekerja", isinya = pekerja.nama_pekerja)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPekerja(judul = "Jabatan", isinya = pekerja.jabatan)
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
