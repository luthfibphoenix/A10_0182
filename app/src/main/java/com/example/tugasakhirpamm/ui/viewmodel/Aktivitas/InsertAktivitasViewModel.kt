package com.example.tugasakhirpamm.ui.viewmodel.Aktivitas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import kotlinx.coroutines.launch

// ViewModel untuk Insert Aktivitas
class InsertAktivitasViewModel(
    private val aktivitasRepository: AktivitasRepository,
    private val tanamanRepository: TanamanRepository,  // Inject Tanaman Repository
    private val pekerjaRepository: PekerjaRepository   // Inject Pekerja Repository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiStateAkt())
        private set

    // Fetch Tanaman and Pekerja data from repositories based on IDs
    fun fetchTanamanAndPekerja(idTanaman: String, idPekerja: String, onSuccess: (Tanaman, Pekerja) -> Unit, onError: (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                // Mengambil data tanaman berdasarkan ID dari TanamanRepository
                val tanaman = tanamanRepository.getTanamanById(idTanaman)

                // Mengambil data pekerja berdasarkan ID dari PekerjaRepository
                val pekerja = pekerjaRepository.getPekerjaById(idPekerja)

                // Memanggil callback onSuccess dengan data yang sudah diambil
                onSuccess(tanaman, pekerja)

                // Update UI state dengan data tanaman dan pekerja
                uiState = uiState.copy(
                    availableTanaman = listOf(tanaman), // Update dengan hanya satu tanaman
                    availablePekerja = listOf(pekerja)   // Update dengan hanya satu pekerja
                )
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e)
            }
        }
    }

    // Update UI state when an event occurs
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

    // Update selected Tanaman
    fun updateSelectedTanaman(idTanaman: String) {
        uiState = uiState.copy(
            insertUiEventAkt = uiState.insertUiEventAkt.copy(id_tanaman = idTanaman)
        )
    }

    // Update selected Pekerja
    fun updateSelectedPekerja(idPekerja: String) {
        uiState = uiState.copy(
            insertUiEventAkt = uiState.insertUiEventAkt.copy(id_pekerja = idPekerja)
        )
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
    id_aktivitas = id_aktivitas,
    id_tanaman = id_tanaman,
    id_pekerja = id_pekerja,
    tanggal_aktivitas = tanggal_aktivitas,
    deskripsi_aktivitas = deskripsi_aktivitas
)

// State to represent UI data
data class InsertUiStateAkt(
    val insertUiEventAkt: InsertUiEventAktivitas = InsertUiEventAktivitas(),
    val availableTanaman: List<Tanaman> = emptyList(),
    val availablePekerja: List<Pekerja> = emptyList()
)

// Data class for validation errors in form
data class AktFormErrorState(
    val idAktivitas: String? = null,
    val idTanaman: String? = null,
    val idPekerja: String? = null,
    val tanggal_aktivitas: String? = null,
    val deskripsi_aktivitas: String? = null
) {
    fun isValid(): Boolean {
        return idAktivitas == null && idTanaman == null && idPekerja == null &&
                tanggal_aktivitas == null && deskripsi_aktivitas == null
    }
}

// State class for the whole UI
data class AktUiState(
    val insertUiEventAkt: InsertUiEventAktivitas = InsertUiEventAktivitas(),
    val isEntryValid: AktFormErrorState = AktFormErrorState(),
    val snackBarMessage: String? = null,
    val tanamanList: List<Tanaman> = emptyList(),
    val pekerjaList: List<Pekerja> = emptyList()
)