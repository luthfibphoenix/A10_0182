package com.example.tugasakhirpamm.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.ui.view.Pekerja.DestinasiDetailPekerja
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPkrUiState {
    data class Success(val pekerja: Pekerja? = null) : DetailPkrUiState()  // Optional pekerja
    data class Error(val message: String? = null) : DetailPkrUiState()
    object Loading : DetailPkrUiState()
}

class DetailPekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pekerjaRepository: PekerjaRepository
) : ViewModel() {

    var pekerjaDetailState: DetailPkrUiState by mutableStateOf(DetailPkrUiState.Loading)
        private set

    private val _idPekerja: String? = savedStateHandle[DestinasiDetailPekerja.PEKERJA]

    // Property for idPekerja with null safety
    val idPekerja: String
        get() = _idPekerja ?: throw IllegalStateException("ID Pekerja tidak ditemukan")

    // Ubah parameter ke dalam fungsi untuk menggunakan _idPekerja
    fun getPekerjaById() {
        _idPekerja?.let { id ->
            viewModelScope.launch {
                pekerjaDetailState = DetailPkrUiState.Loading
                try {
                    val fetchedPekerja = pekerjaRepository.getPekerjaById(id)
                    pekerjaDetailState = DetailPkrUiState.Success(fetchedPekerja)
                } catch (e: IOException) {
                    pekerjaDetailState = DetailPkrUiState.Error("Tidak ada koneksi internet")
                } catch (e: HttpException) {
                    pekerjaDetailState = DetailPkrUiState.Error("Gagal memuat data pekerja")
                }
            }
        } ?: run {
            pekerjaDetailState = DetailPkrUiState.Error("ID Pekerja tidak ditemukan")
        }
    }

    // Function to delete Pekerja
    fun deletePekerja() {
        _idPekerja?.let {
            viewModelScope.launch {
                try {
                    pekerjaRepository.deletePekerja(it)
                    pekerjaDetailState = DetailPkrUiState.Success()  // Optionally reset state after deletion
                } catch (e: IOException) {
                    pekerjaDetailState = DetailPkrUiState.Error("Tidak ada koneksi internet")
                } catch (e: HttpException) {
                    pekerjaDetailState = DetailPkrUiState.Error("Gagal menghapus pekerja")
                }
            }
        } ?: run {
            pekerjaDetailState = DetailPkrUiState.Error("ID Pekerja tidak ditemukan")
        }
    }
}
