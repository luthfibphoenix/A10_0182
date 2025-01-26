package com.example.tugasakhirpamm.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpamm.PertanianApp
import com.example.tugasakhirpamm.PertanianApplications
import com.example.tugasakhirpamm.ui.view.Aktivitas.DetailViewAktivitas
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.DetailAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.HomeAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.UpdateAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.DetailCatatanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.HomeCatatanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.InsertCatatanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.UpdateCatatanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.DetailPekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.HomePekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.InsertPekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.UpdatePekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.DetailTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.HomeTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.InsertTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.UpdateTanamanViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            HomeTanamanViewModel(aplikasiPertanian.container.tanamanRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            DetailTanamanViewModel(createSavedStateHandle(), aplikasiPertanian.container.tanamanRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            InsertTanamanViewModel(aplikasiPertanian.container.tanamanRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            UpdateTanamanViewModel(createSavedStateHandle(), aplikasiPertanian.container.tanamanRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            HomePekerjaViewModel(aplikasiPertanian.container.pekerjaRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            InsertPekerjaViewModel(aplikasiPertanian.container.pekerjaRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            DetailPekerjaViewModel(createSavedStateHandle(), aplikasiPertanian.container.pekerjaRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            UpdatePekerjaViewModel(createSavedStateHandle(), aplikasiPertanian.container.pekerjaRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            HomeAktivitasViewModel(aplikasiPertanian.container.aktivitasRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            InsertAktivitasViewModel(aplikasiPertanian.container.aktivitasRepository, aplikasiPertanian.container.tanamanRepository, aplikasiPertanian.container.pekerjaRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            DetailAktivitasViewModel(createSavedStateHandle(), aplikasiPertanian.container.aktivitasRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            UpdateAktivitasViewModel(createSavedStateHandle(), aplikasiPertanian.container.aktivitasRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            HomeCatatanViewModel(aplikasiPertanian.container.catatanRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            InsertCatatanViewModel(aplikasiPertanian.container.catatanRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            DetailCatatanViewModel(createSavedStateHandle(), aplikasiPertanian.container.catatanRepository)
        }
        initializer {
            val aplikasiPertanian = AplikasiPertanian() // Get application instance
            UpdateCatatanViewModel(createSavedStateHandle(), aplikasiPertanian.container.catatanRepository)
        }
    }
}



fun CreationExtras.AplikasiPertanian(): PertanianApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplications)
