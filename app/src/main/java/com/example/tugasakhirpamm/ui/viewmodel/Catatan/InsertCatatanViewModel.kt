package com.example.tugasakhirpamm.ui.viewmodel.Catatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.repository.CatatanRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertCatatanViewModel(private val catatanRepository: CatatanRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiStateCat())
        private set

    // Update state based on UI event
    fun updateCatatanState(event: InsertUiEventCatatan) {
        uiState = uiState.copy(insertUiEventCat = event)
    }

    // Insert new Catatan into the repository
    fun insertCatatan(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                // Validate the input data before trying to insert
                val newCatatan = uiState.insertUiEventCat.toCatatan()

                // Validate fields
                if (newCatatan.id_panen.isBlank() || newCatatan.id_tanaman.isBlank() ||
                    newCatatan.tanggal_panen.isBlank() || newCatatan.jumlah_panen.isBlank() ||
                    newCatatan.keterangan.isBlank()) {

                    uiState = uiState.copy(
                        errorMessage = "Semua field harus diisi"
                    )
                    onError(IllegalArgumentException("Semua field harus diisi"))
                    return@launch
                }

                // Proceed with the insertion
                catatanRepository.insertCatatan(newCatatan)
                onSuccess()

            } catch (e: IOException) {
                e.printStackTrace()
                uiState = uiState.copy(
                    errorMessage = "Tidak ada koneksi internet"
                )
                onError(e)
            } catch (e: HttpException) {
                e.printStackTrace()
                uiState = uiState.copy(
                    errorMessage = "Gagal memuat data dari server"
                )
                onError(e)
            } catch (e: Exception) {
                e.printStackTrace()
                uiState = uiState.copy(
                    errorMessage = "Terjadi kesalahan yang tidak terduga"
                )
                onError(e)
            }
        }
    }
}

// Conversion methods to convert UI event to Catatan and vice versa
fun InsertUiEventCatatan.toCatatan(): Catatan = Catatan(
    id_panen = idPanen,
    id_tanaman = idTanaman,
    tanggal_panen = tanggalPanen,
    jumlah_panen = jumlahPanen,
    keterangan = keterangan
)

fun Catatan.toUiStateCatatan(): InsertUiStateCat = InsertUiStateCat(
    insertUiEventCat = this.toInsertUiEventCat()
)

fun Catatan.toInsertUiEventCat(): InsertUiEventCatatan = InsertUiEventCatatan(
    idPanen = id_panen,
    idTanaman = id_tanaman,
    tanggalPanen = tanggal_panen,
    jumlahPanen = jumlah_panen,
    keterangan = keterangan
)

data class InsertUiEventCatatan(
    val idPanen: String = "",
    val idTanaman: String = "",
    val tanggalPanen: String = "",
    val jumlahPanen: String = "",
    val keterangan: String = ""
)

data class InsertUiStateCat(
    val insertUiEventCat: InsertUiEventCatatan = InsertUiEventCatatan(),
    val errorMessage: String? = null // Added errorMessage field
)
