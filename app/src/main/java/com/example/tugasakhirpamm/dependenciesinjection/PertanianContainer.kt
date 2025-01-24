package com.example.tugasakhirpamm.dependenciesinjection

import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.repository.CatatanRepository
import com.example.tugasakhirpamm.repository.NetworkAktivitasRepository
import com.example.tugasakhirpamm.repository.NetworkCatatanRepository
import com.example.tugasakhirpamm.repository.NetworkPekerjaRepository
import com.example.tugasakhirpamm.repository.NetworkTanamanRepository
import com.example.tugasakhirpamm.repository.PekerjaRepository
import com.example.tugasakhirpamm.repository.TanamanRepository
import com.example.tugasakhirpamm.service.AktivitasService
import com.example.tugasakhirpamm.service.CatatanService
import com.example.tugasakhirpamm.service.PekerjaService
import com.example.tugasakhirpamm.service.TanamanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val tanamanRepository: TanamanRepository
    val pekerjaRepository: PekerjaRepository
    val catatanRepository: CatatanRepository
    val aktivitasRepository: AktivitasRepository
}

class PertanianContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/pertanian/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val aktivitasService: AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    override val aktivitasRepository: AktivitasRepository by lazy {
        NetworkAktivitasRepository(aktivitasService)
    }

    private val pekerjaService: PekerjaService by lazy {
        retrofit.create(PekerjaService::class.java)
    }
    override val pekerjaRepository: PekerjaRepository by lazy {
        NetworkPekerjaRepository(pekerjaService)
    }

    private val tanamanService: TanamanService by lazy {
        retrofit.create(TanamanService::class.java)
    }
    override val tanamanRepository: TanamanRepository by lazy {
        NetworkTanamanRepository(tanamanService)
    }

    private val catatanService: CatatanService by lazy {
        retrofit.create(CatatanService::class.java)
    }
    override val catatanRepository: CatatanRepository by lazy {
        NetworkCatatanRepository(catatanService)
    }

}
