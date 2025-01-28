package com.example.tugasakhirpamm.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpamm.PertanianApplications
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.DetailAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.HomeAktivitasViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.InsertAktViewModel
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
        // HomeTanamanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            HomeTanamanViewModel(aplikasiPertanian.container.tanamanRepository)
        }

        // DetailTanamanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            DetailTanamanViewModel(createSavedStateHandle(), aplikasiPertanian.container.tanamanRepository)
        }

        // InsertTanamanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            InsertTanamanViewModel(aplikasiPertanian.container.tanamanRepository)
        }

        // UpdateTanamanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            UpdateTanamanViewModel(createSavedStateHandle(), aplikasiPertanian.container.tanamanRepository)
        }

        // HomePekerjaViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            HomePekerjaViewModel(aplikasiPertanian.container.pekerjaRepository)
        }

        // InsertPekerjaViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            InsertPekerjaViewModel(aplikasiPertanian.container.pekerjaRepository)
        }

        // DetailPekerjaViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            DetailPekerjaViewModel(createSavedStateHandle(), aplikasiPertanian.container.pekerjaRepository)
        }

        // UpdatePekerjaViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            UpdatePekerjaViewModel(createSavedStateHandle(), aplikasiPertanian.container.pekerjaRepository)
        }

        // HomeAktivitasViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            HomeAktivitasViewModel(aplikasiPertanian.container.aktivitasRepository)
        }

        // InsertAktivitasViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            InsertAktViewModel(
                aplikasiPertanian.container.aktivitasRepository,
                aplikasiPertanian.container.tanamanRepository,
                aplikasiPertanian.container.pekerjaRepository
            )
        }

        // DetailAktivitasViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            DetailAktivitasViewModel(
                createSavedStateHandle(),
                aplikasiPertanian.container.aktivitasRepository,
                aplikasiPertanian.container.pekerjaRepository,
                aplikasiPertanian.container.tanamanRepository
            )
        }

        // UpdateAktivitasViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            UpdateAktivitasViewModel(
                createSavedStateHandle(),
                aplikasiPertanian.container.aktivitasRepository,
                aplikasiPertanian.container.tanamanRepository,
                aplikasiPertanian.container.pekerjaRepository
            )
        }

        // HomeCatatanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            HomeCatatanViewModel(aplikasiPertanian.container.catatanRepository)
        }

        // InsertCatatanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            InsertCatatanViewModel(
                aplikasiPertanian.container.catatanRepository,
                aplikasiPertanian.container.tanamanRepository
            )
        }

        // DetailCatatanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            DetailCatatanViewModel(
                createSavedStateHandle(),
                aplikasiPertanian.container.catatanRepository
            )
        }

        // UpdateCatatanViewModel
        initializer {
            val aplikasiPertanian = getPertanianApplication()
            UpdateCatatanViewModel(
                createSavedStateHandle(),
                aplikasiPertanian.container.catatanRepository,
                aplikasiPertanian.container.tanamanRepository
            )
        }
    }
}

// Helper function for accessing PertanianApplications instance
fun CreationExtras.getPertanianApplication(): PertanianApplications =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplications
