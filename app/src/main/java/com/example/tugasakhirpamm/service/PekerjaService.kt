package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.Pekerja
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PekerjaService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("/api/pekerja")
    suspend fun getPekerja(): List<Pekerja>

    @GET("/api/pekerja/{id}")
    suspend fun getPekerjaById(id: String): Pekerja

    @POST("/api/pekerja")
    suspend fun insertPekerja(pekerja: Pekerja)

    @PUT("/api/pekerja/{id}")
    suspend fun updatePekerja(@Query("id")id: String, @Body pekerja: Pekerja)

    @DELETE("/api/pekerja/{id}")
    suspend fun deletePekerja(@Query("id")id: String): retrofit2.Response<Void>
}