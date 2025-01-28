package com.example.tugasakhirpamm.ui.view.Aktivitas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiInsertAktivitas
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.view.Catatan.FormCatatan
import com.example.tugasakhirpamm.ui.view.Catatan.InsertBodyCatatan
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.AktivitasEvent
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.AktivitasFormErrorState
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.AktivitasUiState
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertAktViewModel
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAktivitasScreen(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAktViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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
                title = "Insert Aktivitas",
                canNavigateBack = true,
            )

            InsertBodyAktivitas(
                uiState = uiState,
                tanamanList = uiState.tanamanList,
                pekerjaList = uiState.pekerjaList,
                onValueChange = { updateEvent ->
                    viewModel.updateAktivitas(updateEvent)
                },
                onClick = {
                    viewModel.insertAktivitas()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyAktivitas(
    modifier: Modifier = Modifier,
    uiState: AktivitasUiState,
    tanamanList: List<Tanaman>,
    pekerjaList: List<Pekerja>,
    onValueChange: (AktivitasEvent) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormAktivitas(
            aktivitasEvent = uiState.aktivitasEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            tanamanList = tanamanList,
            pekerjaList = pekerjaList
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
fun FormAktivitas(
    aktivitasEvent: AktivitasEvent,
    onValueChange: (AktivitasEvent) -> Unit,
    errorState: AktivitasFormErrorState,
    tanamanList: List<Tanaman>,
    pekerjaList: List<Pekerja>,
    modifier: Modifier = Modifier
) {
    var expandedTanaman by remember { mutableStateOf(false) }
    var expandedPekerja by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = aktivitasEvent.id_aktivitas,
            onValueChange = {
                onValueChange(aktivitasEvent.copy(id_aktivitas = it))
            },
            label = { Text("Id Aktivitas") },
            isError = errorState.id_aktivitas != null,
            placeholder = { Text("Masukkan ID Aktivitas") }
        )
        Text(text = errorState.id_aktivitas ?: "", color = Color.Red)

        ExposedDropdownMenuBox(
            expanded = expandedTanaman,
            onExpandedChange = { expandedTanaman = !expandedTanaman }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = tanamanList.find { it.id_tanaman == aktivitasEvent.id_tanaman }?.nama_tanaman
                    ?: "",
                onValueChange = {},
                label = { Text("Pilih Tanaman") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTanaman)
                },
                readOnly = true,
                isError = errorState.id_tanaman != null
            )

            ExposedDropdownMenu(
                expanded = expandedTanaman,
                onDismissRequest = { expandedTanaman = false }
            ) {
                tanamanList.forEach { tanaman ->
                    DropdownMenuItem(
                        onClick = {
                            expandedTanaman = false
                            onValueChange(aktivitasEvent.copy(id_tanaman = tanaman.id_tanaman))
                        },
                        text = { Text(tanaman.nama_tanaman) }
                    )
                }
            }
        }
        Text(text = errorState.id_tanaman ?: "", color = Color.Red)

        ExposedDropdownMenuBox(
            expanded = expandedPekerja,
            onExpandedChange = { expandedPekerja = !expandedPekerja }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = pekerjaList.find { it.id_pekerja == aktivitasEvent.id_pekerja }?.nama_pekerja
                    ?: "",
                onValueChange = {},
                label = { Text("Pilih Pekerja") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPekerja)
                },
                readOnly = true,
                isError = errorState.id_pekerja != null
            )

            ExposedDropdownMenu(
                expanded = expandedPekerja,
                onDismissRequest = { expandedPekerja = false }
            ) {
                pekerjaList.forEach { pekerja ->
                    DropdownMenuItem(
                        onClick = {
                            expandedPekerja = false
                            onValueChange(aktivitasEvent.copy(id_pekerja = pekerja.id_pekerja))
                        },
                        text = { Text(pekerja.nama_pekerja) }
                    )
                }
            }
        }
        Text(text = errorState.id_pekerja ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = aktivitasEvent.tanggal_aktivitas,
            onValueChange = {
                onValueChange(aktivitasEvent.copy(tanggal_aktivitas = it))
            },
            label = { Text("Tanggal Aktivitas") },
            isError = errorState.tanggal_aktivitas != null,
            placeholder = { Text("Masukkan Tanggal Aktivitas") }
        )
        Text(text = errorState.tanggal_aktivitas ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = aktivitasEvent.deskripsi_aktivitas,
            onValueChange = {
                onValueChange(aktivitasEvent.copy(deskripsi_aktivitas = it))
            },
            label = { Text("Deskripsi Aktivitas") },
            isError = errorState.deskripsi_aktivitas != null,
            placeholder = { Text("Masukkan Deskripsi") }
        )
        Text(text = errorState.deskripsi_aktivitas ?: "", color = Color.Red)
    }
}
