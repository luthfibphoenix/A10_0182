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

sealed class DetailUiState {
    data class Success(val pekerja: Pekerja) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailPekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pekerja: PekerjaRepository
) : ViewModel() {
    var pekerjaDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _idPekerja: String = checkNotNull(savedStateHandle[DestinasiDetailPekerja.PEKERJA])

    init {
        getPekerjaById()
    }

    fun getPekerjaById() {
        viewModelScope.launch {
            pekerjaDetailState = DetailUiState.Loading
            pekerjaDetailState = try {
                val fetchedPekerja = pekerja.getPekerjaById(_idPekerja)
                DetailUiState.Success(fetchedPekerja)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    fun deletePekerja() {
        viewModelScope.launch {
            try {
                pekerja.deletePekerja(_idPekerja)
            } catch (e: IOException) {
                // Handle error
            } catch (e: HttpException) {
                // Handle error
            }
        }
    }
}
