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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.view.Pekerja.DestinasiInsertPekerja
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertUiEventAktivitas
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertUiStateAkt
import kotlinx.coroutines.launch

object DestinasiInsertAktivitas : DestinasiNavigasi {
    override val route = "insert_aktivitas"
    override val titleRes = "Insert Aktivitas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAktivitasScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPekerja.titleRes,
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
    insertUiState: InsertUiStateAkt, // From Pekerja ViewModel
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
    tanamanList: List<String> = emptyList(), // List of id_tanaman
    pekerjaList: List<String> = emptyList() // List of id_pekerja
) {
    var expandedTanaman by remember { mutableStateOf(false) }
    var expandedPekerja by remember { mutableStateOf(false) }
    var selectedTanaman by remember { mutableStateOf(insertUiEvent.id_tanaman) }
    var selectedPekerja by remember { mutableStateOf(insertUiEvent.id_pekerja) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.id_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(id_aktivitas = it)) },
            label = { Text("ID Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // ID Tanaman Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedTanaman,
            onExpandedChange = { expandedTanaman = !expandedTanaman }
        ) {
            OutlinedTextField(
                value = selectedTanaman,
                onValueChange = { selectedTanaman = it },
                label = { Text("ID Tanaman") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                },
                readOnly = true
            )

            ExposedDropdownMenu(
                expanded = expandedTanaman,
                onDismissRequest = { expandedTanaman = false }
            ) {
                tanamanList.forEach { tanaman ->
                    DropdownMenuItem(
                        text = { Text(tanaman) },
                        onClick = {
                            selectedTanaman = tanaman
                            onValueChange(insertUiEvent.copy(id_tanaman = tanaman))
                            expandedTanaman = false
                        }
                    )
                }
            }
        }

        // ID Pekerja Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedPekerja,
            onExpandedChange = { expandedPekerja = !expandedPekerja }
        ) {
            OutlinedTextField(
                value = selectedPekerja,
                onValueChange = { selectedPekerja = it },
                label = { Text("ID Pekerja") },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                },
                readOnly = true
            )

            ExposedDropdownMenu(
                expanded = expandedPekerja,
                onDismissRequest = { expandedPekerja = false }
            ) {
                pekerjaList.forEach { pekerja ->
                    DropdownMenuItem(
                        text = { Text(pekerja) },
                        onClick = {
                            selectedPekerja = pekerja
                            onValueChange(insertUiEvent.copy(id_pekerja = pekerja))
                            expandedPekerja = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = insertUiEvent.tanggal_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggal_aktivitas = it)) },
            label = { Text("Tanggal Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.deskripsi_aktivitas,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_aktivitas = it)) },
            label = { Text("Deskripsi Aktivitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

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


