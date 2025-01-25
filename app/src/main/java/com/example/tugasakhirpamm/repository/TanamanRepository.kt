package com.example.tugasakhirpamm.repository

import com.example.tugasakhirpamm.model.AllTanamanResponse
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.service.TanamanService

interface TanamanRepository {
    suspend fun getTanaman(): AllTanamanResponse
    suspend fun insertTanaman(tanaman: Tanaman)
    suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman) // String
    suspend fun deleteTanaman(idTanaman: String) // String
    suspend fun getTanamanById(idTanaman: String): Tanaman // String
}

class NetworkTanamanRepository(
    private val tanamanApiService: TanamanService
) : TanamanRepository {

    override suspend fun getTanaman(): AllTanamanResponse {
        println(tanamanApiService.getTanaman())
        return tanamanApiService.getTanaman()
    }

    override suspend fun getTanamanById(idTanaman: String): Tanaman {
        return tanamanApiService.getTanamanById(idTanaman).data
    }

    override suspend fun insertTanaman(tanaman: Tanaman) {
        tanamanApiService.insertTanaman(tanaman)
    }

    override suspend fun updateTanaman(idTanaman: String, tanaman: Tanaman) {
        tanamanApiService.updateTanaman(idTanaman, tanaman)
    }

    override suspend fun deleteTanaman(idTanaman: String) {
        try {
            val response = tanamanApiService.deleteTanaman(idTanaman)
            if (!response.isSuccessful) {
                throw Exception("Failed to delete tanaman. HTTP Status code: ${response.code()}")
            } else {
                println("Delete successful: ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

}
