package com.example.tugasakhirpamm.dependenciesinjection

interface AppContainer{
    val tanamanRepository: TanamanRepository
    val pekerjasaRepository: PekerjasaRepository
    val catatanRepository: CatatanRepository
    val aktivitasRepository: AktivitasRepository
}

class PertanianContainer : AppContainer{
    private val baseUrl
}