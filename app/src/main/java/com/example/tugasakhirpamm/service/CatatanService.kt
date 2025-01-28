package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.AllCatatanResponse
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.model.CatatanDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface CatatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("/api/catatan")
    suspend fun getCatatan(): AllCatatanResponse

    @GET("/api/catatan/{id_panen}")
    suspend fun getCatatanById(@Path("id_panen") idPanen: String): CatatanDetailResponse

    @POST("/api/catatan/store")
    suspend fun insertCatatan(@Body catatan: Catatan): Response<Catatan>

    @PUT("/api/catatan/{id_panen}")
    suspend fun updateCatatan(@Path("id_panen") idPanen: String, @Body catatan: Catatan): Response<Void>

    @DELETE("/api/catatan/{id_panen}")
    suspend fun deleteCatatan(@Path("id_panen") idPanen: String): Response<Void>
}
