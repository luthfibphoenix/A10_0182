package com.example.tugasakhirpamm.ui.viewmodel.Catatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.repository.CatatanRepository
import com.example.tugasakhirpamm.ui.view.Catatan.DestinasiCatatanUpdate
import kotlinx.coroutines.launch

class UpdateCatatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val catatanRepository: CatatanRepository
) : ViewModel() {

    var updateUiStateCatatan by mutableStateOf(InsertUiStateCat())
        private set

    private val _idCatatan: String = checkNotNull(savedStateHandle[DestinasiCatatanUpdate.CATATAN])

    init {
        viewModelScope.launch {
            // Fetch the catatan by ID from repository and update the state
            val catatan = catatanRepository.getCatatanById(_idCatatan)
            updateUiStateCatatan = catatan.toUiStateCatUpdate()
        }
    }

    fun updateCatatan(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                // Validate the input data before trying to update
                val updatedCatatan = updateUiStateCatatan.insertUiEventCat.toCatatan()

                if (updatedCatatan.id_panen.isBlank() || updatedCatatan.id_tanaman.isBlank() ||
                    updatedCatatan.tanggal_panen.isBlank() || updatedCatatan.jumlah_panen.isBlank() ||
                    updatedCatatan.keterangan.isBlank()) {

                    updateUiStateCatatan = updateUiStateCatatan.copy(
                        errorMessage = "Semua field harus diisi"
                    )
                    onError(IllegalArgumentException("Semua field harus diisi"))
                    return@launch
                }

                // Proceed with the update
                catatanRepository.updateCatatan(_idCatatan, updatedCatatan)
                onSuccess()

            } catch (e: Exception) {
                e.printStackTrace()
                updateUiStateCatatan = updateUiStateCatatan.copy(
                    errorMessage = "Terjadi kesalahan saat mengupdate catatan"
                )
                onError(e)
            }
        }
    }

    fun updateState(updatedCatatan: InsertUiEventCatatan) {
        updateUiStateCatatan = InsertUiStateCat(
            insertUiEventCat = updatedCatatan
        )
    }
}

// Extension function to map Catatan to InsertUiStateCat
fun Catatan.toUiStateCatUpdate(): InsertUiStateCat {
    return InsertUiStateCat(
        insertUiEventCat = this.toInsertUiEventCatatan()
    )
}


// Extension function to map Catatan to InsertUiEventCatatan
fun Catatan.toInsertUiEventCatatan() = InsertUiEventCatatan(
    idPanen = this.id_panen,
    idTanaman = this.id_tanaman,
    jumlahPanen = this.jumlah_panen,
    tanggalPanen = this.tanggal_panen,
    keterangan = this.keterangan
)
