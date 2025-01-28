package com.example.tugasakhirpamm.ui.view.Catatan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.viewmodel.catatan.CatatanEvent
import com.example.tugasakhirpamm.ui.viewmodel.catatan.CatatanFormErrorState
import com.example.tugasakhirpamm.ui.viewmodel.catatan.CatatanUiState
import com.example.tugasakhirpamm.ui.viewmodel.catatan.InsertCatatanViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertCatatanScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertCatatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope() // Perbaikan: inisialisasi coroutineScope

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            CostumeTopAppBar(
                title = "Insert Catatan",
                canNavigateBack = true
            )

            InsertBodyCatatan(
                uiState = uiState,
                tanamanList = uiState.tanamanList,
                onValueChange = { updateEvent ->
                    viewModel.updateCatatanState(updateEvent)
                },
                onClick = {
                    viewModel.insertCatatan()
                    onNavigate() // Navigasi setelah data disimpan
                }
            )
        }
    }
}


@Composable
fun InsertBodyCatatan(
    modifier: Modifier = Modifier,
    onValueChange: (CatatanEvent) -> Unit,
    uiState: CatatanUiState,
    onClick: () -> Unit,
    tanamanList: List<Tanaman>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormCatatan(
            catatanEvent = uiState.catatanEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            tanamanList = tanamanList
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormCatatan(
    catatanEvent: CatatanEvent = CatatanEvent(),
    onValueChange: (CatatanEvent) -> Unit,
    errorState: CatatanFormErrorState = CatatanFormErrorState(),
    tanamanList: List<Tanaman>,
    modifier: Modifier = Modifier
) {
    var chosenDropdown by remember { mutableStateOf(catatanEvent.id_tanaman) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = catatanEvent.id_panen,
            onValueChange = {
                onValueChange(catatanEvent.copy(id_panen = it))
            },
            label = { Text(text = "Id Panen") },
            isError = errorState.id_panen != null,
            placeholder = { Text("Masukkan ID Panen") }
        )
        Text(text = errorState.id_panen ?: "", color = Color.Red)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = chosenDropdown,
                onValueChange = {},
                label = { Text("Pilih Tanaman") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                readOnly = true,
                isError = errorState.id_tanaman != null
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tanamanList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            chosenDropdown = it.id_tanaman
                            expanded = false
                            onValueChange(catatanEvent.copy(id_tanaman = it.id_tanaman))
                        },
                        text = { Text(text = it.nama_tanaman) } // Asumsikan nama_tanaman adalah nama untuk ditampilkan
                    )
                }
            }
        }
        Text(text = errorState.id_tanaman ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = catatanEvent.jumlah_panen,
            onValueChange = {
                onValueChange(catatanEvent.copy(jumlah_panen = it))
            },
            label = { Text("Jumlah Panen") },
            isError = errorState.jumlah_panen != null,
            placeholder = { Text("Masukkan Jumlah Panen") }
        )
        Text(text = errorState.jumlah_panen ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = catatanEvent.tanggal_panen,
            onValueChange = {
                onValueChange(catatanEvent.copy(tanggal_panen = it))
            },
            label = { Text("Tanggal Panen") },
            isError = errorState.tanggal_panen != null,
            placeholder = { Text("Masukkan Tanggal Panen") }
        )
        Text(text = errorState.tanggal_panen ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = catatanEvent.keterangan,
            onValueChange = {
                onValueChange(catatanEvent.copy(keterangan = it))
            },
            label = { Text("Keterangan Panen") },
            isError = errorState.keterangan != null,
            placeholder = { Text("Masukkan Keterangan Panen") }
        )
        Text(text = errorState.keterangan ?: "", color = Color.Red)
    }
}
