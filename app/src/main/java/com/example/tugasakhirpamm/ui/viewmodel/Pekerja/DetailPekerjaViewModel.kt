package com.example.tugasakhirpamm.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.ui.navigasi.DestinasiDetailPekerja
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPekerjaUiState {
    data class Success(val pekerja: Pekerja) : DetailPekerjaUiState()
    object Error : DetailPekerjaUiState()
    object Loading : DetailPekerjaUiState()
}

class DetailPekerjaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pekerja: PekerjaRepository
) : ViewModel() {

    var pekerjaDetailState: DetailPekerjaUiState by mutableStateOf(DetailPekerjaUiState.Loading)
        private set

    private val _idPekerja: String = checkNotNull(savedStateHandle[DestinasiDetailPekerja.PEKERJA])

    init {
        getPekerjaById()
    }

    fun getPekerjaById() {
        viewModelScope.launch {
            pekerjaDetailState = DetailPekerjaUiState.Loading
            pekerjaDetailState = try {
                val pekerja = pekerja.getPekerjaById(_idPekerja)
                DetailPekerjaUiState.Success(pekerja)
            } catch (e: IOException) {
                DetailPekerjaUiState.Error
            } catch (e: HttpException) {
                DetailPekerjaUiState.Error
            }
        }
    }

    fun deletePekerja(_idPekerja: String) {
        viewModelScope.launch {
            try {
                pekerja.deletePekerja(_idPekerja)
            } catch (e: IOException) {
                DetailPekerjaUiState.Error
            } catch (e: HttpException) {
                DetailPekerjaUiState.Error
            } catch (e: Exception) {
                DetailPekerjaUiState.Error
            }
        }
    }
}
