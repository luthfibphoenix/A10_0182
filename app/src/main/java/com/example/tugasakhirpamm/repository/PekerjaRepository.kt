package com.example.tugasakhirpamm.repository

import com.example.tugasakhirpamm.model.AllPekerjaResponse
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.service.PekerjaService

interface PekerjaRepository {
    suspend fun getPekerja(): List<Pekerja>
    suspend fun insertPekerja(pekerja: Pekerja)
    suspend fun updatePekerja(idPekerja: String, pekerja: Pekerja) // String
    suspend fun deletePekerja(idPekerja: String) // String
    suspend fun getPekerjaById(idPekerja: String): Pekerja // String
}

class NetworkPekerjaRepository(
    private val pekerjaApiService: PekerjaService
) : PekerjaRepository {

    override suspend fun getPekerja(): List<Pekerja> {
        val response = pekerjaApiService.getPekerja()
        if (response.status) {
            return response.data // Ambil hanya data
        } else {
            throw Exception(response.message) // Tangani error sesuai dengan respons API
        }
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
                throw Exception("Failed to delete Pekerja. HTTP Status code: " + "${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }
}
