package com.example.tugasakhirpamm.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.ui.view.Aktivitas.DestinasiAktivitasUpdate
import kotlinx.coroutines.launch

class UpdateAktivitasViewModel(
    savedStateHandle: SavedStateHandle,
    private val aktivitasRepository: AktivitasRepository
) : ViewModel() {
    var updatePkrUiState by mutableStateOf(InsertUiStateAkt())
        private set

    private val _idAktivitas: String = checkNotNull(savedStateHandle[DestinasiAktivitasUpdate.AKTIVITAS])

    init {
        // Memuat data Aktivitas untuk diupdate berdasarkan ID
        viewModelScope.launch {
            try {
                val aktivitas = aktivitasRepository.getAktivitasById(_idAktivitas)
                updatePkrUiState = aktivitas.toUiStateAktUpdate()
            } catch (e: Exception) {
                e.printStackTrace() // Tangani error jika terjadi
            }
        }
    }

    // Fungsi untuk update aktivitas
    fun updateAktivitas() {
        viewModelScope.launch {
            try {
                val aktivitas = updatePkrUiState.insertUiEventAkt.toAktivitas()
                aktivitasRepository.updateAktivitas(_idAktivitas, aktivitas)
            } catch (e: Exception) {
                e.printStackTrace() // Tangani error jika terjadi
            }
        }
    }

    // Update state ketika terjadi perubahan pada UI event
    fun updateState(insertUiEventAkt: InsertUiEventAktivitas) {
        updatePkrUiState = InsertUiStateAkt(
            insertUiEventAkt = insertUiEventAkt
        )
    }
}

// Extension function to convert Aktivitas to InsertUiStateAkt for updating
fun Aktivitas.toUiStateAktUpdate() = InsertUiStateAkt(
    insertUiEventAkt = this.toInsertUiEventAkt()
)