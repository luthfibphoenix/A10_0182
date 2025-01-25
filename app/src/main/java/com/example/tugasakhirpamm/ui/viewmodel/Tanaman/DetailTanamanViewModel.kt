package com.example.tugasakhirpamm.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.TanamanRepository
import com.example.tugasakhirpamm.ui.view.Tanaman.DestinasiDetailTanaman
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailTnmUiState {
    data class Success(val tanaman: Tanaman) : DetailTnmUiState()
    object Error : DetailTnmUiState()
    object Loading : DetailTnmUiState()
}

class DetailTanamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val tanaman: TanamanRepository
) : ViewModel() {

    var tanamanDetailState: DetailTnmUiState by mutableStateOf(DetailTnmUiState.Loading)
        private set

    private val _idTanaman: String = checkNotNull(savedStateHandle[DestinasiDetailTanaman.TANAMAN])

    init {
        getTanamanById()
    }

    fun getTanamanById() {
        viewModelScope.launch {
            tanamanDetailState = DetailTnmUiState.Loading
            tanamanDetailState = try {
                val tanaman = tanaman.getTanamanById(_idTanaman)
                DetailTnmUiState.Success(tanaman)
            } catch (e: IOException) {
                DetailTnmUiState.Error
            } catch (e: HttpException) {
                DetailTnmUiState.Error
            } catch (e: Exception) {
                // Log or track unexpected errors
                DetailTnmUiState.Error
            }
        }
    }

    fun deleteTanaman() {
        viewModelScope.launch {
            try {
                tanaman.deleteTanaman(_idTanaman)
            } catch (e: IOException) {
                // Handle error specific to delete operation if needed
                tanamanDetailState = DetailTnmUiState.Error
            } catch (e: HttpException) {
                // Handle error specific to delete operation if needed
                tanamanDetailState = DetailTnmUiState.Error
            } catch (e: Exception) {
                // Log unexpected errors
                tanamanDetailState = DetailTnmUiState.Error
            }
        }
    }
}
