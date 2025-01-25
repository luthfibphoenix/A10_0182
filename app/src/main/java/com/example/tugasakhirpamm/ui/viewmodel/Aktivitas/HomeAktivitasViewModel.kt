package com.example.tugasakhirpamm.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.repository.AktivitasRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeAktivitasUiState{
    data class Success(val Aktivitas: List<Aktivitas>) : HomeAktivitasUiState()
    object Error : HomeAktivitasUiState()
    object Loading : HomeAktivitasUiState()
}

class HomeAktivitasViewModel(private val aktivitas: AktivitasRepository): ViewModel(){
    var aktivitasUiState: HomeAktivitasUiState by mutableStateOf(HomeAktivitasUiState.Loading)
        private set

    init {
        getAktivitas()
    }

    fun getAktivitas(){
        viewModelScope.launch {
            aktivitasUiState = HomeAktivitasUiState.Loading
            aktivitasUiState = try {
                HomeAktivitasUiState.Success(aktivitas.getAktivitas().data)
            } catch (e: IOException){
                HomeAktivitasUiState.Error
            } catch (e: HttpException){
                HomeAktivitasUiState.Error
            }
        }
    }

    fun deleteAktivitas(id_aktivitas: String){
        viewModelScope.launch {
            try {
                aktivitas.deleteAktivitas(id_aktivitas)
            } catch (e: IOException) {
                HomeAktivitasUiState.Error
            }catch (e : HttpException){
                HomeAktivitasUiState.Error
            }
        }
    }
}