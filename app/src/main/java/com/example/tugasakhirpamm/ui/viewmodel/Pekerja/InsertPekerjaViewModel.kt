package com.example.tugasakhirpamm.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertPekerjaViewModel(private val pekerjaRepository: PekerjaRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiStatePkr())
        private set

    // Update state based on UI event
    fun updatePekerjaState(event: InsertUiEventPekerja) {
        uiState = uiState.copy(insertUiEventPkr = event)
    }

    // Insert new pekerja into the repository
    fun insertPekerja(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                // Validate the input data before trying to insert
                val newPekerja = uiState.insertUiEventPkr.toPekerja()

                if (newPekerja.id_pekerja.isBlank() || newPekerja.nama_pekerja.isBlank() || newPekerja.jabatan.isBlank()) {
                    // Update UI state to show validation error
                    uiState = uiState.copy(insertUiEventPkr = uiState.insertUiEventPkr.copy(idPekerja = "", namaPekerja = "", jabatan = ""))
                    onError(IllegalArgumentException("Semua field harus diisi"))
                    return@launch
                }

                // Proceed with the insertion
                pekerjaRepository.insertPekerja(newPekerja)
                onSuccess()

            } catch (e: IOException) {
                e.printStackTrace()
                // Handle network error
                uiState = uiState.copy(insertUiEventPkr = uiState.insertUiEventPkr.copy(idPekerja = "", namaPekerja = "", jabatan = ""))
                onError(e)
            } catch (e: HttpException) {
                e.printStackTrace()
                // Handle HTTP error
                uiState = uiState.copy(insertUiEventPkr = uiState.insertUiEventPkr.copy(idPekerja = "", namaPekerja = "", jabatan = ""))
                onError(e)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle other generic errors
                uiState = uiState.copy(insertUiEventPkr = uiState.insertUiEventPkr.copy(idPekerja = "", namaPekerja = "", jabatan = ""))
                onError(e)
            }
        }
    }
}

// Conversion methods to convert UI event to Pekerja and vice versa
fun InsertUiEventPekerja.toPekerja(): Pekerja = Pekerja(
    id_pekerja = idPekerja,
    nama_pekerja = namaPekerja,
    jabatan = jabatan,
    kontak = kontak
)

fun Pekerja.toUiStatePekerja(): InsertUiStatePkr = InsertUiStatePkr(
    insertUiEventPkr = this.toInsertUiEventPkr()
)

fun Pekerja.toInsertUiEventPkr(): InsertUiEventPekerja = InsertUiEventPekerja(
    idPekerja = id_pekerja,
    namaPekerja = nama_pekerja,
    jabatan = jabatan,
    kontak = kontak
)

data class InsertUiEventPekerja(
    val idPekerja: String = "",
    val namaPekerja: String = "",
    val jabatan: String = "",
    val kontak: String = ""
)

data class InsertUiStatePkr(
    val insertUiEventPkr: InsertUiEventPekerja = InsertUiEventPekerja()
)
