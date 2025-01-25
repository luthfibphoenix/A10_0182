package com.example.tugasakhirpamm.ui.viewmodel.Pekerja

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.repository.PekerjaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePekerjaUiState{
    data class Success(val Pekerja: List<Pekerja>) : HomePekerjaUiState()
    object Error : HomePekerjaUiState()
    object Loading : HomePekerjaUiState()
}

class HomePekerjaViewModel(private val pekerja: PekerjaRepository): ViewModel(){
    var pekerjaUiState: HomePekerjaUiState by mutableStateOf(HomePekerjaUiState.Loading)
        private set

    init {
        getpekerja()
    }

    fun getpekerja(){
        viewModelScope.launch {
            pekerjaUiState = HomePekerjaUiState.Loading
            pekerjaUiState = try {
                HomePekerjaUiState.Success(pekerja.getPekerja().data)
            } catch (e: IOException){
                HomePekerjaUiState.Error
            } catch (e: HttpException){
                HomePekerjaUiState.Error
            }
        }
    }

    fun deletePekerja(id_pekerja: String){
        viewModelScope.launch {
            try {
                pekerja.deletePekerja(id_pekerja)
            } catch (e: IOException) {
                HomePekerjaUiState.Error
            }catch (e : HttpException){
                HomePekerjaUiState.Error
            }
        }
    }
}