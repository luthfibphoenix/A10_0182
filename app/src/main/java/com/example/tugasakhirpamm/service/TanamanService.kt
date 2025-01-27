package com.example.tugasakhirpamm.service

import com.example.tugasakhirpamm.model.AllTanamanResponse
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.model.TanamanDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TanamanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("/api/tanaman")
    suspend fun getTanaman(): AllTanamanResponse

    @GET("/api/tanaman/{id_tanaman}")
    suspend fun getTanamanById(@Path("id_tanaman") idTanaman: String): TanamanDetailResponse

    @POST("/api/tanaman/store")
    suspend fun insertTanaman(@Body tanaman: Tanaman)

    @PUT("/api/tanaman/{id_tanaman}")
    suspend fun updateTanaman(@Path("id_tanaman") idTanaman: String, @Body tanaman: Tanaman)

    @DELETE("/api/tanaman/{id_tanaman}")
    suspend fun deleteTanaman(@Path("id_tanaman") idTanaman: String): retrofit2.Response<Void>
}

