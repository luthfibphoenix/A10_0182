package com.example.tugasakhirpamm.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.TanamanRepository
import com.example.tugasakhirpamm.ui.view.Tanaman.DestinasiTanamanUpdate
import kotlinx.coroutines.launch

class UpdateTanamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val tanamanRepository: TanamanRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    private val _idTanaman: String = checkNotNull(savedStateHandle[DestinasiTanamanUpdate.TANAMAN])

    init {
        viewModelScope.launch {
            val tanaman = tanamanRepository.getTanamanById(_idTanaman)
            uiState = tanaman.toUiStateTnmUpdate()
        }
    }

    fun updateTanaman() {
        viewModelScope.launch {
            try {
                val tanaman = uiState.insertUiEvent.toTanaman()
                tanamanRepository.updateTanaman(_idTanaman, tanaman)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(
            insertUiEvent = insertUiEvent
        )
    }
}

fun Tanaman.toUiStateTnmUpdate() = InsertUiState(
    insertUiEvent = this.toInsertUiEventTnm()
)