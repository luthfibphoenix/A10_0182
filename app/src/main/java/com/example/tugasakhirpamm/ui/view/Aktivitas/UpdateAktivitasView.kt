package com.example.tugasakhirpamm.ui.view.Aktivitas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.UpdateAktivitasViewModel
import kotlinx.coroutines.launch

object DestinasiAktivitasUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Aktivitas" // Updated title to reflect 'Pekerja'
    const val AKTIVITAS = "id Aktivitas"
    val routeWithArg = "$route/{$AKTIVITAS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAktivitasScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onNavigate: () -> Unit
) {
    // Access the UI state from the ViewModel
    val insertUiState = viewModel.updatePkrUiState
    val coroutineScope = rememberCoroutineScope()

    // Scaffold for UI layout
    Scaffold(
        modifier = modifier,
        topBar = {
            // Custom top bar for navigation
            CostumeTopAppBar(
                title = DestinasiAktivitasUpdate.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        // Column that holds the form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(15.dp)
        ) {
            // Body that displays the form and save button
                EntryBody(
                insertUiState = insertUiState,
                onAktivitasValueChange = { updateEvent ->
                    // Update state in ViewModel
                    viewModel.updateState(updateEvent)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        // Update pekerja and navigate back
                        viewModel.updateAktivitas()
                        onNavigate()
                    }
                }
            )
        }
    }
}