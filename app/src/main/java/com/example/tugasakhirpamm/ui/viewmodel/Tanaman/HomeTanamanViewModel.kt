package com.example.tugasakhirpamm.ui.viewmodel.Tanaman

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState{
    data class Success(val Tanaman: List<Tanaman>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeTanamanViewModel(private val tanaman: TanamanRepository): ViewModel(){

    var tanamanUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getTanaman()
    }

    fun getTanaman(){
        viewModelScope.launch {
            tanamanUiState = HomeUiState.Loading
            tanamanUiState = try {
                HomeUiState.Success(tanaman.getTanaman().data)
            } catch (e: IOException){
                HomeUiState.Error
            } catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteTanaman(id_tanaman: String){
        viewModelScope.launch {
            try {
                tanaman.deleteTanaman(id_tanaman)
            } catch (e: IOException) {
                HomeUiState.Error
            }catch (e : HttpException){
                HomeUiState.Error
            }
        }
    }
}