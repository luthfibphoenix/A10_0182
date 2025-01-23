package com.example.tugasakhirpamm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tanaman(
    @SerialName("id_tanaman")
    val id_tanaman : String,

    @SerialName("nama_tanaman")
    val nama_tanaman : String,

    @SerialName("periode_tanaman")
    val periode_tanaman : String,

    @SerialName("deskripsi_tanaman")
    val deskripsi_tanaman : String,
)
