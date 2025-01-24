package com.example.tugasakhirpamm.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val tanamanRepository: TanamanRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateTanamanState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(insertUiEvent = insertUiEvent)
    }

    fun insertTanaman() {
        viewModelScope.launch {
            try {
                tanamanRepository.insertTanaman(uiState.insertUiEvent.toTanaman())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun InsertUiEvent.toTanaman(): Tanaman = Tanaman(
    id_tanaman = this.idTanaman,
    nama_tanaman = this.namaTanaman,
    periode_tanaman = this.periodeTanaman,
    deskripsi_tanaman = this.deskripsiTanaman
)

fun Tanaman.toUiStateTanaman(): InsertUiState = InsertUiState(
    insertUiEvent = this.toInsertUiEvent()
)

data class InsertUiEvent(
    val idTanaman: String = "",
    val namaTanaman: String = "",
    val periodeTanaman: String = "",
    val deskripsiTanaman: String = ""
)

fun Tanaman.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idTanaman = id_tanaman,
    namaTanaman = nama_tanaman,
    periodeTanaman = periode_tanaman,
    deskripsiTanaman = deskripsi_tanaman
)

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)
