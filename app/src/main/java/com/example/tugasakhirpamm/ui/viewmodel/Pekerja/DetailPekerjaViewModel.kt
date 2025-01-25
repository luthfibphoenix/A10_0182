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
    data class Success(val pekerja: Pekerja) : DetailPkrUiState()
    object Error : DetailPkrUiState()
    object Loading : DetailPkrUiState()
}

class DetailPekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pekerja: PekerjaRepository
) : ViewModel() {

    var pekerjaDetailState: DetailPkrUiState by mutableStateOf(DetailPkrUiState.Loading)
        private set

    private val _idPekerja: String? = savedStateHandle[DestinasiDetailPekerja.PEKERJA]

    val idPekerja: String
        get() = idPekerja



    // Ubah parameter ke dalam fungsi untuk menggunakan _idPekerja
    fun getPekerjaById(idPekerja: String) {
        viewModelScope.launch {
            pekerjaDetailState = DetailPkrUiState.Loading
            try {
                val fetchedPekerja = pekerja.getPekerjaById(idPekerja)
                pekerjaDetailState = DetailPkrUiState.Success(fetchedPekerja)
            } catch (e: IOException) {
                pekerjaDetailState = DetailPkrUiState.Error
            } catch (e: HttpException) {
                pekerjaDetailState = DetailPkrUiState.Error
            }
        }
    }

    fun deletePekerja() {
        _idPekerja?.let {
            viewModelScope.launch {
                try {
                    pekerja.deletePekerja(it)
                } catch (e: IOException) {
                    // Handle error
                } catch (e: HttpException) {
                    // Handle error
                }
            }
        }
    }
}
