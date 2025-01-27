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
import com.example.tugasakhirpamm.ui.navigasi.DestinasiDetailCatatan
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailCatatanUiState {
    data class Success(val catatan: Catatan?) : DetailCatatanUiState()
    object Error : DetailCatatanUiState()
    object Loading : DetailCatatanUiState()
}

class DetailCatatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val catatan: CatatanRepository
) : ViewModel() {

    var catatanDetailState: DetailCatatanUiState by mutableStateOf(DetailCatatanUiState.Loading)
        private set

    private val _idCatatan: String = checkNotNull(savedStateHandle[DestinasiDetailCatatan.CATATAN])

    init {
        getCatatanById()
    }

    fun getCatatanById() {
        viewModelScope.launch {
            catatanDetailState = DetailCatatanUiState.Loading
            catatanDetailState = try {
                val catatan = catatan.getCatatanById(_idCatatan)
                DetailCatatanUiState.Success(catatan)
            } catch (e: IOException) {
                DetailCatatanUiState.Error
            } catch (e: retrofit2.HttpException) {
                DetailCatatanUiState.Error
            }
        }
    }

    fun deleteCatatan() {
        viewModelScope.launch {
            try {
                catatan.deleteCatatan(_idCatatan)
            } catch (e: IOException) {
                DetailCatatanUiState.Error
            } catch (e: retrofit2.HttpException) {
                DetailCatatanUiState.Error
            } catch (e: Exception) {
                DetailCatatanUiState.Error
            }
        }
    }
}