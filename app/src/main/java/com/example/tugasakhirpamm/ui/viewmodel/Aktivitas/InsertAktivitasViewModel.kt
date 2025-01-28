package com.example.tugasakhirpamm.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

// ViewModel untuk Insert Aktivitas
class InsertAktViewModel(
    private val aktivitasRepository: AktivitasRepository,
    private val tanamanRepository: TanamanRepository,  // Inject Tanaman Repository
    private val pekerjaRepository: PekerjaRepository   // Inject Pekerja Repository
) : ViewModel() {

    var uiState by mutableStateOf(AktivitasUiState())
        private set

    init {
        fetchTanamanList()
        fetchPekerjaList()
    }

    private fun fetchTanamanList() {
        viewModelScope.launch {
            try {
                val tanamanList = tanamanRepository.getTanaman()
                uiState = uiState.copy(tanamanList = tanamanList)
            } catch (e: Exception) {
                uiState = uiState.copy(snackBarMessage = "Gagal memuat daftar tanaman")
            }
        }
    }

    private fun fetchPekerjaList() {
        viewModelScope.launch {
            try {
                val pekerjaList = pekerjaRepository.getPekerja()
                uiState = uiState.copy(pekerjaList = pekerjaList)
            } catch (e: Exception) {
                uiState = uiState.copy(snackBarMessage = "Gagal memuat daftar pekerja")
            }
        }
    }

    fun updateAktivitas(aktivitasEvent: AktivitasEvent) {
        uiState = uiState.copy(aktivitasEvent = aktivitasEvent)
    }

    private fun validateFields(): Boolean {
        val event = uiState.aktivitasEvent
        val errorState = AktivitasFormErrorState(
            id_aktivitas = if (event.id_aktivitas.isNotBlank()) null else "Id Aktivitas tidak boleh kosong",
            id_tanaman = if (event.id_tanaman.isNotBlank()) null else "Id Tanaman tidak boleh kosong",
            id_pekerja = if (event.id_pekerja.isNotBlank()) null else "Id Pekerja tidak boleh kosong",
            tanggal_aktivitas = if (event.tanggal_aktivitas.isNotBlank()) null else "Tanggal Aktivitas tidak boleh kosong",
            deskripsi_aktivitas = if (event.deskripsi_aktivitas.isNotBlank()) null else "Deskripsi Aktivitas tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertAktivitas() {
        val currentEvent = uiState.aktivitasEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    aktivitasRepository.insertAktivitas(currentEvent.toAktivitasEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        aktivitasEvent = AktivitasEvent(),
                        isEntryValid = AktivitasFormErrorState()
                    )
                } catch (e: IOException) {
                    uiState = uiState.copy(snackBarMessage = "Gagal menyimpan data. Periksa koneksi internet.")
                } catch (e: Exception) {
                    uiState = uiState.copy(snackBarMessage = "Terjadi kesalahan: ${e.message}")
                }
            }
        } else {
            uiState = uiState.copy(snackBarMessage = "Data tidak valid. Periksa kembali input Anda.")
        }
    }

    fun resetSnackbarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// Data Classes

data class AktivitasUiState(
    val aktivitasEvent: AktivitasEvent = AktivitasEvent(),
    val tanamanList: List<Tanaman> = emptyList(),
    val pekerjaList: List<Pekerja> = emptyList(),
    val snackBarMessage: String? = null,
    val isEntryValid: AktivitasFormErrorState = AktivitasFormErrorState()
)


data class AktivitasFormErrorState(
    val id_aktivitas: String? = null,
    val id_tanaman: String? = null,
    val id_pekerja: String? = null,
    val tanggal_aktivitas: String? = null,
    val deskripsi_aktivitas: String? = null
) {
    fun isValid(): Boolean {
        return id_aktivitas == null && id_tanaman == null && id_pekerja == null && tanggal_aktivitas == null && deskripsi_aktivitas == null
    }
}

data class AktivitasEvent(
    val id_aktivitas: String = "",
    val id_tanaman: String = "",
    val id_pekerja: String = "",
    val tanggal_aktivitas: String = "",
    val deskripsi_aktivitas: String = ""
)

fun AktivitasEvent.toAktivitasEntity(): Aktivitas = Aktivitas(
    id_aktivitas = id_aktivitas,
    id_tanaman = id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas = tanggal_aktivitas,
    deskripsi_aktivitas = deskripsi_aktivitas
)