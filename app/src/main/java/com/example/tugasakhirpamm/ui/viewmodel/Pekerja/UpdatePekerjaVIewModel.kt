package com.example.tugasakhirpamm.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.ui.view.Pekerja.DestinasiPekerjaUpdate
import kotlinx.coroutines.launch

class UpdatePekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pekerjaRepository: PekerjaRepository
) : ViewModel() {
    var UpdateUiState by mutableStateOf(InsertUiStatePkr())
        private set

    private val _idPekerja: String = checkNotNull(savedStateHandle[DestinasiPekerjaUpdate.PEKERJA])

    init {
        viewModelScope.launch {
            UpdateUiState = pekerjaRepository.getPekerjaById(_idPekerja).toUiStatePkrUpdate()
        }
    }

    fun updatePekerja() {
        viewModelScope.launch {
            try {
                pekerjaRepository.updatePekerja(_idPekerja, UpdateUiState.insertUiEventPkr.toPekerja())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updatePekerjaState(insertUiEventPkr: InsertUiEventPekerja) {
        UpdateUiState = InsertUiStatePkr(
            insertUiEventPkr = insertUiEventPkr
        )
    }
}

fun Pekerja.toUiStatePkrUpdate() = InsertUiStatePkr(
    insertUiEventPkr = this.toInsertUiEvent()
)
