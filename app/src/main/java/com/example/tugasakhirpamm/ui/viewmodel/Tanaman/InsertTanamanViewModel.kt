package com.example.tugasakhirpamm.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertTanamanViewModel(private val tanamanRepository: TanamanRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateTanamanState(insertUiEvent: InsertTnmUiEvent) {
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

fun InsertTnmUiEvent.toTanaman(): Tanaman = Tanaman(
    id_tanaman = idTanaman,
    nama_tanaman = namaTanaman,
    periode_tanaman = periodeTanaman,
    deskripsi_tanaman = deskripsiTanaman
)

fun Tanaman.toUiStateTanaman(): InsertUiState = InsertUiState(
    insertUiEvent = this.toInsertUiEventTnm()
)

data class InsertTnmUiEvent(
    val idTanaman: String = "",
    val namaTanaman: String = "",
    val periodeTanaman: String = "",
    val deskripsiTanaman: String = ""
)

fun Tanaman.toInsertUiEventTnm(): InsertTnmUiEvent = InsertTnmUiEvent(
    idTanaman = id_tanaman,
    namaTanaman = nama_tanaman,
    periodeTanaman = periode_tanaman,
    deskripsiTanaman = deskripsi_tanaman
)

data class InsertUiState(
    val insertUiEvent: InsertTnmUiEvent = InsertTnmUiEvent()
)
