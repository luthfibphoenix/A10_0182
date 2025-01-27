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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiInsertAktivitas
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertUiEventAktivitas
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertUiStateAkt
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAktivitasScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Fetch Tanaman dan Pekerja berdasarkan ID
    val selectedTanamanId = "some-tanaman-id" // Ganti dengan ID Tanaman yang diinginkan
    val selectedPekerjaId = "some-pekerja-id"  // Ganti dengan ID Pekerja yang diinginkan

    // Call fetchTanamanAndPekerja saat screen pertama kali dibuka
    LaunchedEffect(true) {
        viewModel.fetchTanamanAndPekerja(
            idTanaman = selectedTanamanId,
            idPekerja = selectedPekerjaId,
            onSuccess = { tanaman, pekerja ->
                // On success, viewModel's UI state will be updated
            },
            onError = { error ->
                // Handle error
            }
        )
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertAktivitas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onAktivitasValueChange = viewModel::updateAktivitasState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAktivitas(
                        onSuccess = navigateBack,
                        onError = { /* Handle error (e.g., show a snackbar or toast) */ }
                    )
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}



@Composable
fun EntryBody(
    insertUiState: InsertUiStateAkt,
    onAktivitasValueChange: (InsertUiEventAktivitas) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEventAkt, // Correct reference
            onValueChange = onAktivitasValueChange,
            tanamanList = insertUiState.availableTanaman,  // Tanaman list from ViewModel
            pekerjaList = insertUiState.availablePekerja,  // Pekerja list from ViewModel
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEventAktivitas,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEventAktivitas) -> Unit = {},
    enabled: Boolean = true,
    tanamanList: List<Tanaman> = emptyList(), // List of Tanaman from ViewModel
    pekerjaList: List<Pekerja> = emptyList()  // List of Pekerja from ViewModel
) {
    var expandedTanaman by remember { mutableStateOf(false) }
    var expandedPekerja by remember { mutableStateOf(false) }
    var selectedTanaman by remember { mutableStateOf(insertUiEvent.id_tanaman) }
    var selectedPekerja by remember { mutableStateOf(insertUiEvent.id_pekerja) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ID Aktivitas TextField
        OutlinedTextField(
            value = insertUiEvent.id_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(id_aktivitas = it)) },
            label = { Text("ID Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // ID Tanaman Dropdown
        OutlinedTextField(
            value = insertUiEvent.id_tanaman,
            onValueChange = { onValueChange(insertUiEvent.copy(id_tanaman = it)) },
            label = { Text("ID Tanaman") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.id_pekerja,
            onValueChange = { onValueChange(insertUiEvent.copy(id_pekerja = it)) },
            label = { Text("ID Pekerja") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Tanggal Aktivitas TextField
        OutlinedTextField(
            value = insertUiEvent.tanggal_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_aktivitas = it)) },
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Deskripsi Aktivitas TextField
        OutlinedTextField(
            value = insertUiEvent.deskripsi_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_aktivitas = it)) },
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Informasi untuk Pengisian Data
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}