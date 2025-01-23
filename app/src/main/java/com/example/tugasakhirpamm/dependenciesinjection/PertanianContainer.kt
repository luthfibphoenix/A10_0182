package com.example.tugasakhirpamm.dependenciesinjection

import com.example.tugasakhirpamm.repository.AktivitasRepository
import com.example.tugasakhirpamm.service.AktivitasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
//    val tanamanRepository: TanamanRepository
//    val pekerjasaRepository: PekerjasaRepository
//    val catatanRepository: CatatanRepository
    val aktivitasRepository: AktivitasRepository
}

class PertanianContainer : AppContainer{
    private val baseUrl = "http://10.0.2.2:83/api/kangpijat/"
    private val json = Json{ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
    private val aktivitasService:AktivitasService by lazy {
        retrofit.create(AktivitasService::class.java)
    }
}