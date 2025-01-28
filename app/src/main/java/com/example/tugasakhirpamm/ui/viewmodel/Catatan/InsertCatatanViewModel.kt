package com.example.tugasakhirpamm.ui.viewmodel.catatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.CatatanRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertCatatanViewModel(
    private val catatanRepository: CatatanRepository,
    private val tanamanRepository: TanamanRepository
) : ViewModel() {

    var uiState by mutableStateOf(CatatanUiState())
        private set

    init {
        fetchTanamanList()
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

    fun updateCatatanState(catatanEvent: CatatanEvent) {
        uiState = uiState.copy(catatanEvent = catatanEvent)
    }

    private fun validateFields(): Boolean {
        val event = uiState.catatanEvent
        val errorState = CatatanFormErrorState(
            id_panen = if (event.id_panen.isNotBlank()) null else "Id Panen tidak boleh kosong",
            id_tanaman = if (event.id_tanaman.isNotBlank()) null else "Id Tanaman tidak boleh kosong",
            tanggal_panen = if (event.tanggal_panen.isNotBlank()) null else "Tanggal Panen tidak boleh kosong",
            jumlah_panen = if (event.jumlah_panen.isNotBlank()) null else "Jumlah Panen tidak boleh kosong",
            keterangan = if (event.keterangan.isNotBlank()) null else "Keterangan tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertCatatan() {
        val currentEvent = uiState.catatanEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    catatanRepository.insertCatatan(currentEvent.toCatatan())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        catatanEvent = CatatanEvent(),
                        isEntryValid = CatatanFormErrorState()
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

data class CatatanUiState(
    val catatanEvent: CatatanEvent = CatatanEvent(),
    val isEntryValid: CatatanFormErrorState = CatatanFormErrorState(),
    val snackBarMessage: String? = null,
    val tanamanList: List<Tanaman> = emptyList()
)

data class CatatanFormErrorState(
    val id_panen: String? = null,
    val id_tanaman: String? = null,
    val tanggal_panen: String? = null,
    val jumlah_panen: String? = null,
    val keterangan: String? = null
) {
    fun isValid(): Boolean {
        return id_panen == null && id_tanaman == null && tanggal_panen == null && jumlah_panen == null && keterangan == null
    }
}

data class CatatanEvent(
    val id_panen: String = "",
    val id_tanaman: String = "",
    val tanggal_panen: String = "",
    val jumlah_panen: String = "",
    val keterangan: String = ""
)

fun CatatanEvent.toCatatan(): Catatan = Catatan(
    id_panen = id_panen,
    id_tanaman = id_tanaman,
    tanggal_panen = tanggal_panen,
    jumlah_panen = jumlah_panen,
    keterangan = keterangan
)
