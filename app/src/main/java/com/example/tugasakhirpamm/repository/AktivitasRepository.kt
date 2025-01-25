package com.example.tugasakhirpamm.repository

import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.AllAktivitasResponse
import com.example.tugasakhirpamm.service.AktivitasService

interface AktivitasRepository {
    suspend fun getAktivitas(): AllAktivitasResponse
    suspend fun insertAktivitas(aktivitas: Aktivitas)
    suspend fun updateAktivitas(idAktivitas: String, aktivitas: Aktivitas) // String
    suspend fun deleteAktivitas(idAktivitas: String) // String
    suspend fun getAktivitasById(idAktivitas: String): Aktivitas // String
}

class NetworkAktivitasRepository(
    private val aktivitasApiService: AktivitasService
) : AktivitasRepository{

    override suspend fun getAktivitas(): AllAktivitasResponse {
        println(aktivitasApiService.getAktivitas())
        return aktivitasApiService.getAktivitas()
    }

    override suspend fun getAktivitasById(idAktivitas: String): Aktivitas {
        return aktivitasApiService.getAktivitasById(idAktivitas).data
    }

    override suspend fun insertAktivitas(aktivitas: Aktivitas) {
        aktivitasApiService.insertAktivitas(aktivitas)
    }

    override suspend fun updateAktivitas(idAktivitas: String, aktivitas: Aktivitas) {
        aktivitasApiService.updateAktivitas(idAktivitas, aktivitas)
    }

    override suspend fun deleteAktivitas(idAktivitas: String) {
        try{
            val response = aktivitasApiService.deleteAktivitas(idAktivitas)
            if (!response.isSuccessful){
                throw Exception("Failed to delete aktivitas. HTTP Status code: " + "${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }
}