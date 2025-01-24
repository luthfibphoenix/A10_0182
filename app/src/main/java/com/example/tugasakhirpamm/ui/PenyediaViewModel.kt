package com.example.tugasakhirpamm.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpamm.PertanianApp
import com.example.tugasakhirpamm.PertanianApplications
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.DetailPekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.HomePekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.InsertPekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.UpdatePekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.DetailTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.HomeTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.InsertTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.UpdateTanamanViewModel


object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeTanamanViewModel(AplikasiPertanian().container.tanamanRepository) }
        initializer { DetailTanamanViewModel(createSavedStateHandle(), AplikasiPertanian().container.tanamanRepository) }
        initializer { InsertTanamanViewModel(AplikasiPertanian().container.tanamanRepository) }
        initializer { UpdateTanamanViewModel(createSavedStateHandle(), AplikasiPertanian().container.tanamanRepository) }
        initializer { HomePekerjaViewModel(AplikasiPertanian().container.pekerjaRepository) }
        initializer { InsertPekerjaViewModel(AplikasiPertanian().container.pekerjaRepository) }
        initializer { DetailPekerjaViewModel(createSavedStateHandle(), AplikasiPertanian().container.pekerjaRepository) }
        initializer { UpdatePekerjaViewModel(createSavedStateHandle(), AplikasiPertanian().container.pekerjaRepository) }
    }
}

fun CreationExtras.AplikasiPertanian(): PertanianApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApplications)
