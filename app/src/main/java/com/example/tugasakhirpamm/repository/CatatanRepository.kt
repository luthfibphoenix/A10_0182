package com.example.tugasakhirpamm.repository

import com.example.tugasakhirpamm.model.AllCatatanResponse
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.service.CatatanService

interface CatatanRepository {
    suspend fun getCatatan(): AllCatatanResponse
    suspend fun insertCatatan(catatan: Catatan)
    suspend fun updateCatatan(idPanen: String, catatan: Catatan)
    suspend fun deleteCatatan(idPanen: String)
    suspend fun getCatatanById(idPanen: String): Catatan
}

class NetworkCatatanRepository(
    private val catatanApiService: CatatanService
) : CatatanRepository {

    override suspend fun getCatatan(): AllCatatanResponse {
        println(catatanApiService.getCatatan())
        return catatanApiService.getCatatan()
    }

    override suspend fun getCatatanById(idPanen: String): Catatan {
        return catatanApiService.getCatatanById(idPanen).data
    }

    override suspend fun insertCatatan(catatan: Catatan) {
        catatanApiService.insertCatatan(catatan)
    }

    override suspend fun updateCatatan(idPanen: String, catatan: Catatan) {
        catatanApiService.updateCatatan(idPanen, catatan)
    }

    override suspend fun deleteCatatan(idPanen: String) {
        try {
            val response = catatanApiService.deleteCatatan(idPanen)
            if (!response.isSuccessful) {
                throw Exception("Failed to delete catatan. HTTP Status code: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
