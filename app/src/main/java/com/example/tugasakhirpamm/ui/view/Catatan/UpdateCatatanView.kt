package com.example.tugasakhirpamm.ui.view.Catatan

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
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.UpdateAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.UpdateCatatanViewModel
import kotlinx.coroutines.launch

object DestinasiCatatanUpdate : DestinasiNavigasi {
    override val route = "update_catatan"
    override val titleRes = "Update Catatan"
    const val CATATAN = "catatanId"  // Ganti dari 'id Panen' menjadi 'catatanId'
    val routeWithArg = "$route/{$CATATAN}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCatatanScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateCatatanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onNavigate: () -> Unit
) {
    // Access the UI state from the ViewModel
    val insertUiState = viewModel.updateUiStateCatatan
    val coroutineScope = rememberCoroutineScope()

    // Scaffold for UI layout
    Scaffold(
        modifier = modifier,
        topBar = {
            // Custom top bar for navigation
            CostumeTopAppBar(
                title = DestinasiCatatanUpdate.titleRes,
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
            com.example.tugasakhirpamm.ui.view.Catatan.EntryBody(
                insertUiState = insertUiState,
                onCatatanValueChange = { updatedCatatan ->
                    viewModel.updateState(updatedCatatan)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateCatatan()
                        onNavigate()
                    }
                }
            )
        }
    }
}
