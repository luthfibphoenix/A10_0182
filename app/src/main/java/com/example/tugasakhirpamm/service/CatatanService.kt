package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Catatan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CatatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("/api/catatan_panen")
    suspend fun getCatatan(): List<Catatan>

    @GET("/api/catatan_panen/{id}")
    suspend fun getCatatanById(idPanen: String): Catatan

    @POST("/api/catatan_panen")
    suspend fun insertCatatan(catatan: Catatan)

    @PUT("/api/catatan_panen/{id}")
    suspend fun updateCatatan(@Query("id")idPanen: String, @Body catatan: Catatan)

    @DELETE("/api/catatan_panen/{id}")
    suspend fun deleteCatatan(@Query("id")idPanen: String): retrofit2.Response<Void>
}