package com.example.tugasakhirpamm.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertPekerjaViewModel(private val pekerjaRepository: PekerjaRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiStatePkr())
        private set

    fun updatePekerjaState(event: InsertUiEventPekerja) {
        uiState = uiState.copy(insertUiEventPkr = event)
    }

    fun insertPekerja(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val newPekerja = uiState.insertUiEventPkr.toPekerja()
                pekerjaRepository.insertPekerja(newPekerja)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
            }
        }
    }
}

fun InsertUiEventPekerja.toPekerja(): Pekerja = Pekerja(
    id_pekerja = idPekerja,
    nama_pekerja = namaPekerja,
    jabatan = jabatan
)

fun Pekerja.toUiStatePekerja(): InsertUiStatePkr = InsertUiStatePkr(
    insertUiEventPkr = this.toInsertUiEventPkr()
)

data class InsertUiEventPekerja(
    val idPekerja: String = "",
    val namaPekerja: String = "",
    val jabatan: String = ""
)

fun Pekerja.toInsertUiEventPkr(): InsertUiEventPekerja = InsertUiEventPekerja(
    idPekerja = id_pekerja,
    namaPekerja = nama_pekerja,
    jabatan = jabatan
)

data class InsertUiStatePkr(
    val insertUiEventPkr: InsertUiEventPekerja = InsertUiEventPekerja()
)