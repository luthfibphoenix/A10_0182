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

sealed class DetailUiState {
    data class Success(val tanaman: Tanaman) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailTanamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val tanaman: TanamanRepository
) :ViewModel(){
    var tanamanDetailState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    private val _idTanaman: String = checkNotNull(savedStateHandle[DestinasiDetailTanaman.TANAMAN])

    init{
        getTanamanById()
    }

    fun getTanamanById(){
        viewModelScope.launch {
            tanamanDetailState = DetailUiState.Loading
            tanamanDetailState = try {
                val tanaman = tanaman.getTanamanById(_idTanaman)
                DetailUiState.Success(tanaman)
            } catch (e: IOException){
                DetailUiState.Error
            } catch (e: HttpException){
                DetailUiState.Error
            }
        }
    }

    fun deleteTanaman(){
        viewModelScope.launch {
            try {
                tanaman.deleteTanaman(_idTanaman)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e : HttpException){
                HomeUiState.Error
            }
        }
    }
}