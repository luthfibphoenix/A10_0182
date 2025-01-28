package com.example.tugasakhirpamm.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import com.example.tugasakhirpamm.ui.navigasi.DestinasiAktivitasUpdate
import kotlinx.coroutines.launch

class UpdateAktivitasViewModel(
    savedStateHandle: SavedStateHandle,
    private val aktivitas: AktivitasRepository,
    private val tanamanRepository: TanamanRepository,
    private val pekerjaRepository: PekerjaRepository
): ViewModel() {

    var tanamanList by mutableStateOf<List<Tanaman>>(emptyList())
        private set

    var pekerjaList by mutableStateOf<List<Pekerja>>(emptyList())
        private set

    var updateUiStateAktivitas by mutableStateOf(AktivitasUiState())
        private set

    private val _idAktivitas: String = checkNotNull(savedStateHandle[DestinasiAktivitasUpdate.AKTIVITAS])

    init {
        viewModelScope.launch {
            // Fetch aktivitas data by ID and update the UI state
            val aktivitasData = aktivitas.getAktivitasById(_idAktivitas)
            if (aktivitasData != null) {
                updateUiStateAktivitas = aktivitasData.toUiStateAktivitas() // Pastikan fungsi ini mengembalikan AktivitasUiState
            } else {
                updateUiStateAktivitas = updateUiStateAktivitas.copy(
                    snackBarMessage = "Aktivitas tidak ditemukan"
                )
            }
        }


        viewModelScope.launch {
            // Fetch tanaman list and update the UI state
            val tanamanListFromRepo = tanamanRepository.getTanaman() // Get full list, not just first item
            updateUiStateAktivitas = updateUiStateAktivitas.copy(tanamanList = tanamanListFromRepo)
            val pekerjaListFromRepo = pekerjaRepository.getPekerja()
            updateUiStateAktivitas = updateUiStateAktivitas.copy(pekerjaList = pekerjaListFromRepo)
        }
    }

    fun updateState(AktivitasEvent: AktivitasEvent) {
        updateUiStateAktivitas = updateUiStateAktivitas.copy(aktivitasEvent = AktivitasEvent())
    }

    fun validateFields(): Boolean {
        val event = updateUiStateAktivitas.aktivitasEvent
        val errorState = AktivitasFormErrorState(
            id_aktivitas = if (event.id_aktivitas.isNotBlank()) null else "Id Panen tidak boleh kosong",
            id_tanaman = if (event.id_tanaman.isNotBlank()) null else "Id Tanaman tidak boleh kosong",
            id_pekerja = if (event.id_pekerja.isNotBlank()) null else "Tanggal Panen tidak boleh kosong",
            tanggal_aktivitas = if (event.tanggal_aktivitas.isNotBlank()) null else "Jumlah Panen tidak boleh kosong",
            deskripsi_aktivitas = if (event.deskripsi_aktivitas.isNotBlank()) null else "Keterangan tidak boleh kosong"
        )
        updateUiStateAktivitas = updateUiStateAktivitas.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateAktivitas() {
        val currentEvent = updateUiStateAktivitas.aktivitasEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    // Ensure you're passing the correct ID and Catatan object to the update function
                    aktivitas.updateAktivitas(_idAktivitas, currentEvent.toAktivitasEntity()) // Pass ID and Catatan object
                    updateUiStateAktivitas = updateUiStateAktivitas.copy(
                        snackBarMessage = "Data berhasil diperbarui",
                        aktivitasEvent = AktivitasEvent(),
                        isEntryValid = AktivitasFormErrorState()
                    )
                } catch (e: Exception) {
                    updateUiStateAktivitas = updateUiStateAktivitas.copy(
                        snackBarMessage = "Gagal memperbarui data. Periksa koneksi internet."
                    )
                }
            }
        } else {
            updateUiStateAktivitas = updateUiStateAktivitas.copy(
                snackBarMessage = "Periksa kembali data yang dimasukkan"
            )
        }
    }

    fun resetSnackbarMessage() {
        updateUiStateAktivitas = updateUiStateAktivitas.copy(snackBarMessage = null)
    }
}

fun Aktivitas.toUiStateAktivitas(): AktivitasUiState = AktivitasUiState(
    aktivitasEvent = this.toDetailAktivitas() // Pastikan toDetailAktivitas() mengembalikan data yang sesuai
)
