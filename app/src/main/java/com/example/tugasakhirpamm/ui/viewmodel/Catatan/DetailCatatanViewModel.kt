package com.example.tugasakhirpamm.ui.viewmodel.Catatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.repository.CatatanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailCatUiState {
    data class Success(val catatan: Catatan?) : DetailCatUiState()  // Success state with nullable catatan
    data class Error(val message: String? = null) : DetailCatUiState()
    object Loading : DetailCatUiState()
}

class DetailCatatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val catatanRepository: CatatanRepository
) : ViewModel() {

    var catatanDetailState: DetailCatUiState by mutableStateOf(DetailCatUiState.Loading)
        private set

    private val _idPenen: String? = savedStateHandle["catatanId"]  // Replace this with your actual key

    // Property for idCatatan with null safety
    val idCatatan: String
        get() = _idPenen ?: throw IllegalStateException("ID Catatan tidak ditemukan")

    // Fetch Catatan by ID
    fun getCatatanById() {
        _idPenen?.let { id ->
            viewModelScope.launch {
                catatanDetailState = DetailCatUiState.Loading
                try {
                    val fetchedCatatan = catatanRepository.getCatatanById(id)
                    catatanDetailState = DetailCatUiState.Success(fetchedCatatan)
                } catch (e: IOException) {
                    catatanDetailState = DetailCatUiState.Error("Tidak ada koneksi internet")
                } catch (e: HttpException) {
                    catatanDetailState = DetailCatUiState.Error("Gagal memuat data catatan")
                }
            }
        } ?: run {
            catatanDetailState = DetailCatUiState.Error("ID Catatan tidak ditemukan")
        }
    }

    // Function to delete Catatan
    fun deleteCatatan() {
        _idPenen?.let {
            viewModelScope.launch {
                try {
                    // Hapus catatan dari repository
                    catatanRepository.deleteCatatan(it)
                    // Reset state setelah penghapusan berhasil
                    catatanDetailState = DetailCatUiState.Success(null)  // Optionally set to null or Loading after deletion
                } catch (e: IOException) {
                    catatanDetailState = DetailCatUiState.Error("Tidak ada koneksi internet")
                } catch (e: HttpException) {
                    catatanDetailState = DetailCatUiState.Error("Gagal menghapus catatan")
                }
            }
        } ?: run {
            catatanDetailState = DetailCatUiState.Error("ID Catatan tidak ditemukan")
        }
    }
}
