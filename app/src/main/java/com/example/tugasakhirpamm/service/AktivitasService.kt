package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.model.AktivitasDetailResponse
import com.example.tugasakhirpamm.model.AllAktivitasResponse
import com.example.tugasakhirpamm.model.PekerjaDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AktivitasService{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET("/api/aktivitas_pertanian")
    suspend fun getAktivitas(): AllAktivitasResponse

    @GET("/api/aktivitas_pertanian/{id}")
    suspend fun getAktivitasById(@Path("id") idAktivitas: String): AktivitasDetailResponse

    @POST("/api/aktivitas_pertanian/store")
    suspend fun insertAktivitas(aktivitas: Aktivitas)

    @PUT("/api/aktivitas_pertanian/{id}")
    suspend fun updateAktivitas(@Query("id")idAktivitas: String,@Body aktivitas: Aktivitas)

    @DELETE("/api/aktivitas_pertanian/{id}")
    suspend fun deleteAktivitas(@Query("id")idAktivitas: String): retrofit2.Response<Void>
}