package com.example.tugasakhirpamm.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.InsertUiStatePkr
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.toInsertUiEventPkr
import kotlinx.coroutines.launch

// ViewModel untuk Insert Aktivitas
class InsertAktivitasViewModel(private val aktivitasRepository: AktivitasRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiStateAkt())
        private set

    // Update state when an event occurs
    fun updateAktivitasState(event: InsertUiEventAktivitas) {
        uiState = uiState.copy(insertUiEventAkt = event)
    }

    // Insert aktivitas data into repository
    fun insertAktivitas(onSuccess: () -> Unit = {}, onError: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            try {
                val newAktivitas = uiState.insertUiEventAkt.toAktivitas()
                aktivitasRepository.insertAktivitas(newAktivitas)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
            }
        }
    }
}

// Extension function to map InsertUiEventAktivitas to Aktivitas
fun InsertUiEventAktivitas.toAktivitas(): Aktivitas = Aktivitas(
    id_aktivitas = this.id_aktivitas,
    id_tanaman = this.id_tanaman,
    id_pekerja = this.id_pekerja,
    tanggal_aktivitas = this.tanggal_aktivitas,
    deskripsi_aktivitas = this.deskripsi_aktivitas
)

// Extension function to convert Aktivitas to InsertUiStateAkt
fun Aktivitas.toUiStateAktivitas(): InsertUiStateAkt = InsertUiStateAkt(
    insertUiEventAkt = this.toInsertUiEventAkt()
)

// Data class for the UI event
data class InsertUiEventAktivitas(
    val id_aktivitas: String = "",
    val id_tanaman: String = "",
    val id_pekerja: String = "",
    val tanggal_aktivitas: String = "",
    val deskripsi_aktivitas: String = ""
)

// Extension function to map Aktivitas to InsertUiEventAktivitas
fun Aktivitas.toInsertUiEventAkt(): InsertUiEventAktivitas = InsertUiEventAktivitas(
    id_aktivitas = this.id_aktivitas,
    id_tanaman = this.id_tanaman,
    id_pekerja = this.id_pekerja,
    tanggal_aktivitas = this.tanggal_aktivitas,
    deskripsi_aktivitas = this.deskripsi_aktivitas
)

// State to represent UI data
data class InsertUiStateAkt(
    val insertUiEventAkt: InsertUiEventAktivitas = InsertUiEventAktivitas()
)