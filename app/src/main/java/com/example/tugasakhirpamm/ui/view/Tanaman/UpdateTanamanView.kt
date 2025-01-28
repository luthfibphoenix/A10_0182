package com.example.tugasakhirpamm.ui.view.Tanaman

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
import com.example.tugasakhirpamm.ui.navigasi.DestinasiTanamanUpdate
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.UpdateTanamanViewModel
import kotlinx.coroutines.launch




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTanamanView(
    modifier: Modifier = Modifier,
    viewModel: UpdateTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateBack: () -> Unit,
    onNavigate: () -> Unit
){
    val insertUiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold (
        modifier = Modifier,
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTanamanUpdate.titleRes,
                canNavigateBack = true,
                navigatBack = navigateBack
            )
        }
    ){
        padding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(15.dp)
        ){
            EntryBody(
                insertUiState = insertUiState,
                onTanamanValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.updateTanaman()
                        onNavigate()
                    }
                }
            )
        }
    }
}