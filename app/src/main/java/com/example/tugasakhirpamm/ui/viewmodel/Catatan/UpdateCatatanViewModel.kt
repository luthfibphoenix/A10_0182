package com.example.tugasakhirpamm.ui.viewmodel.Catatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.CatatanRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import com.example.tugasakhirpamm.ui.navigasi.DestinasiCatatanUpdate
import com.example.tugasakhirpamm.ui.viewmodel.catatan.CatatanEvent
import com.example.tugasakhirpamm.ui.viewmodel.catatan.CatatanFormErrorState
import com.example.tugasakhirpamm.ui.viewmodel.catatan.CatatanUiState
import com.example.tugasakhirpamm.ui.viewmodel.catatan.toCatatan
import kotlinx.coroutines.launch

class UpdateCatatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val catatanRepository: CatatanRepository,
    private val tanamanRepository: TanamanRepository
): ViewModel() {

    var tanamanList by mutableStateOf<List<Tanaman>>(emptyList())
        private set

    var updateUiState by mutableStateOf(CatatanUiState())
        private set

    private val _idCatatan: String = checkNotNull(savedStateHandle[DestinasiCatatanUpdate.CATATAN])

    init {
        viewModelScope.launch {
            // Fetch catatan data by ID and update the UI state
            val catatan = catatanRepository.getCatatanById(_idCatatan)
            if (catatan != null) {
                updateUiState = catatan.toUiStateCatatan() // Assuming toUiStateCatatan() expects a Catatan object
            } else {
                updateUiState = updateUiState.copy(
                    snackBarMessage = "Catatan tidak ditemukan"
                )
            }
        }

        viewModelScope.launch {
            // Fetch tanaman list and update the UI state
            val tanamanListFromRepo = tanamanRepository.getTanaman() // Get full list, not just first item
            updateUiState = updateUiState.copy(tanamanList = tanamanListFromRepo)
        }
    }

    fun updateState(CatatanEvent: CatatanEvent) {
        updateUiState = updateUiState.copy(catatanEvent = CatatanEvent)
    }

    fun validateFields(): Boolean {
        val event = updateUiState.catatanEvent
        val errorState = CatatanFormErrorState(
            id_panen = if (event.id_panen.isNotBlank()) null else "Id Panen tidak boleh kosong",
            id_tanaman = if (event.id_tanaman.isNotBlank()) null else "Id Tanaman tidak boleh kosong",
            tanggal_panen = if (event.tanggal_panen.isNotBlank()) null else "Tanggal Panen tidak boleh kosong",
            jumlah_panen = if (event.jumlah_panen.isNotBlank()) null else "Jumlah Panen tidak boleh kosong",
            keterangan = if (event.keterangan.isNotBlank()) null else "Keterangan tidak boleh kosong"
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateCatatan() {
        val currentEvent = updateUiState.catatanEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    // Ensure you're passing the correct ID and Catatan object to the update function
                    catatanRepository.updateCatatan(_idCatatan, currentEvent.toCatatan()) // Pass ID and Catatan object
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil diperbarui",
                        catatanEvent = CatatanEvent(),
                        isEntryValid = CatatanFormErrorState()
                    )
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Gagal memperbarui data. Periksa koneksi internet."
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Periksa kembali data yang dimasukkan"
            )
        }
    }

    fun resetSnackbarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun Catatan.toUiStateCatatan(): CatatanUiState = CatatanUiState(
    catatanEvent = this.toDetailUiEvent() // Ensure this method correctly maps the Catatan object
)
