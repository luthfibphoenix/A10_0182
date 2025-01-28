package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.AktivitasDetailResponse
import com.example.tugasakhirpamm.model.AllAktivitasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AktivitasService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("/api/aktivitas")
    suspend fun getAktivitas(): AllAktivitasResponse

    @GET("/api/aktivitas/{id_aktivitas}")
    suspend fun getAktivitasById(@Path("id_aktivitas") idAktivitas: String): AktivitasDetailResponse

    @POST("/api/aktivitas/store")
    suspend fun insertAktivitas(@Body aktivitas: Aktivitas): Response<Aktivitas>

    @PUT("/api/aktivitas/{id_aktivitas}")
    suspend fun updateAktivitas(@Path("id_aktivitas") idAktivitas: String, @Body aktivitas: Aktivitas)

    @DELETE("/api/aktivitas/{id_aktivitas}")
    suspend fun deleteAktivitas(@Path("id_aktivitas") idAktivitas: String): Response<Void>
}

