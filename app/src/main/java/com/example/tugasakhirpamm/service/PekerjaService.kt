package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.AllPekerjaResponse
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.PekerjaDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PekerjaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("/api/pekerja")
    suspend fun getPekerja(): AllPekerjaResponse

    @GET("/api/pekerja/{id_pekerja}")
    suspend fun getPekerjaById(@Path("id_pekerja") idPekerja: String): PekerjaDetailResponse

    @POST("/api/pekerja/store")
    suspend fun insertPekerja(@Body pekerja: Pekerja)

    @PUT("/api/pekerja/{id_pekerja}")
    suspend fun updatePekerja(@Path("id_pekerja") idPekerja: String, @Body pekerja: Pekerja)

    @DELETE("/api/pekerja/{id_pekerja}")
    suspend fun deletePekerja(@Path("id_pekerja") idPekerja: String): retrofit2.Response<Void>
}

