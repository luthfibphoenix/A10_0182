package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.Aktivitas
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AktivitasService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("/api/aktivitas_pertanian")
    suspend fun getAktivitas(): List<Aktivitas>

    @GET("/api/aktivitas_pertanian/{id}")
    suspend fun getAktivitasById(id: String): Aktivitas

    @POST("/api/aktivitas_pertanian")
    suspend fun insertAktivitas(aktivitas: Aktivitas)

    @PUT("/api/aktivitas_pertanian/{id}")
    suspend fun updateMahasiswa(@Query("id")nim: String,@Body aktivitas: Aktivitas)

    @DELETE("/api/aktivitas_pertanian/{id}")
    suspend fun deleteMahasiswa(@Query("id")id: String): retrofit2.Response<Void>
}