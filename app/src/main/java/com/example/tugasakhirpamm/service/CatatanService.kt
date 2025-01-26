package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.AllCatatanResponse
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.model.CatatanDetailResponse
import com.example.tugasakhirpamm.model.PekerjaDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CatatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("/api/catatan")
    suspend fun getCatatan(): AllCatatanResponse

    @GET("/api/catatan/{id_panen}")
    suspend fun getCatatanById(@Path("id_panen") idPanen: String): CatatanDetailResponse

    @POST("/api/catatan")
    suspend fun insertCatatan(@Body catatan: Catatan)

    @PUT("/api/catatan/{id_panen}")
    suspend fun updateCatatan(@Path("id_panen") idPanen: String, @Body catatan: Catatan)

    @DELETE("/api/catatan/{id_panen}")
    suspend fun deleteCatatan(@Path("id_panen") idPanen: String): retrofit2.Response<Void>
}
