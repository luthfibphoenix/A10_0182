package com.example.tugasakhirpamm.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.ui.view.Aktivitas.DestinasiDetailAktivitas
import com.example.tugasakhirpamm.ui.view.Pekerja.DestinasiDetailPekerja
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailAktUiState {
    data class Success(val aktivitas: Aktivitas) : DetailAktUiState()
    object Error : DetailAktUiState()
    object Loading : DetailAktUiState()
}

class DetailAktivitasViewModel(
    savedStateHandle: SavedStateHandle,
    private val aktivitas: AktivitasRepository
) : ViewModel() {
    var aktivitasDetailState: DetailAktUiState by mutableStateOf(DetailAktUiState.Loading)
        private set

    private val _idAktivitas: String = checkNotNull(savedStateHandle[DestinasiDetailAktivitas.AKTIVITAS])

    init {
        getAktivitasById()
    }

    fun getAktivitasById() {
        viewModelScope.launch {
            aktivitasDetailState = DetailAktUiState.Loading
            aktivitasDetailState = try {
                val fetchedAktivitas= aktivitas.getAktivitasById(_idAktivitas)
                DetailAktUiState.Success(fetchedAktivitas)
            } catch (e: IOException) {
                DetailAktUiState.Error
            } catch (e: HttpException) {
                DetailAktUiState.Error
            }
        }
    }

    fun deleteAktivitas() {
        viewModelScope.launch {
            try {
                aktivitas.deleteAktivitas(_idAktivitas)
            } catch (e: IOException) {
                // Handle error
            } catch (e: HttpException) {
                // Handle error
            }
        }
    }
}