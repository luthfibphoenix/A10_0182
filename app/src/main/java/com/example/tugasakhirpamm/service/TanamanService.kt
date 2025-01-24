package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.Tanaman
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TanamanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("/api/tanaman")
    suspend fun getTanaman(): List<Tanaman>

    @GET("/api/tanaman/{id}")
    suspend fun getTanamanById(idTanaman: String): Tanaman

    @POST("/api/tanaman")
    suspend fun insertTanaman(tanaman: Tanaman)

    @PUT("/api/tanaman/{id}")
    suspend fun updateTanaman(@Query("id")idTanaman: String, @Body tanaman: Tanaman)

    @DELETE("/api/tanaman/{id}")
    suspend fun deleteTanaman(@Query("id")idTanaman: String): retrofit2.Response<Void>
}