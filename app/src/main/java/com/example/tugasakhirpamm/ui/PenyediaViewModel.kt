package com.example.tugasakhirpamm.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhirpamm.PertanianApp
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.DetailTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.HomeTanamanViewModel


object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeTanamanViewModel(AplikasiPertanian().container.tanamanRepository) }
        initializer { DetailTanamanViewModel(createSavedStateHandle(), AplikasiPertanian().container.tanamanRepository) }
    }
}

fun CreationExtras.AplikasiPertanian(): PertanianApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApp)
