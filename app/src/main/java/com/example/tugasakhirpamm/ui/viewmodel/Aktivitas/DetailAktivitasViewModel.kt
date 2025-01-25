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

sealed class DetailUiStateAkt {
    data class Success(val aktivitas: Aktivitas) : DetailUiStateAkt()
    object Error : DetailUiStateAkt()
    object Loading : DetailUiStateAkt()
}

class DetailAktivitasViewModel(
    savedStateHandle: SavedStateHandle,
    private val aktivitas: AktivitasRepository
) : ViewModel() {
    var aktivitasDetailState: DetailUiStateAkt by mutableStateOf(DetailUiStateAkt.Loading)
        private set

    private val _idAktivitas: String = checkNotNull(savedStateHandle[DestinasiDetailAktivitas.AKTIVITAS])

    init {
        getAktivitasById()
    }

    fun getAktivitasById() {
        viewModelScope.launch {
            aktivitasDetailState = DetailUiStateAkt.Loading
            aktivitasDetailState = try {
                val fetchedAktivitas= aktivitas.getAktivitasById(_idAktivitas)
                DetailUiStateAkt.Success(fetchedAktivitas)
            } catch (e: IOException) {
                DetailUiStateAkt.Error
            } catch (e: HttpException) {
                DetailUiStateAkt.Error
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