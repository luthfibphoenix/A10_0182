package com.example.tugasakhirpamm.repository

import com.example.tugasakhirpamm.model.AllPekerjaResponse
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.service.PekerjaService

interface PekerjaRepository {
    suspend fun getPekerja(): AllPekerjaResponse
    suspend fun insertPekerja(pekerja: Pekerja)
    suspend fun updatePekerja(idPekerja: String, pekerja: Pekerja) // String
    suspend fun deletePekerja(idPekerja: String) // String
    suspend fun getPekerjaById(idPekerja: String): Pekerja // String
}

class NetworkPekerjaRepository(
    private val pekerjaApiService: PekerjaService
) : PekerjaRepository {

    override suspend fun getPekerja(): AllPekerjaResponse {
        println(pekerjaApiService.getPekerja())
        return pekerjaApiService.getPekerja()
    }

    override suspend fun getPekerjaById(idPekerja: String): Pekerja {
        return pekerjaApiService.getPekerjaById(idPekerja).data
    }

    override suspend fun insertPekerja(pekerja: Pekerja) {
        pekerjaApiService.insertPekerja(pekerja)
    }

    override suspend fun updatePekerja(idPekerja: String, pekerja: Pekerja) {
        pekerjaApiService.updatePekerja(idPekerja, pekerja)
    }

    override suspend fun deletePekerja(idPekerja: String) {
        try{
            val response = pekerjaApiService.deletePekerja(idPekerja)
            if (!response.isSuccessful){
                throw Exception("Failed to delete mahasiswa. HTTP Status code: " + "${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }
}
