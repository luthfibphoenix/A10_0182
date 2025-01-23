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

@Serializable
data class pekerja(
    @SerialName("id_pekerja")
    val id_pekerja : String,

    @SerialName("nama_pekerja")
    val nama_pekerja : String,

    val jabatan : String
)

@Serializable
data class aktivitas(
    @SerialName("id_aktivitas")
    val id_aktivitas : String,

    @SerialName("id_tanaman")
    val id_tanaman : String,

    @SerialName("id_pekerja")
    val id_pekerja : String,

    @SerialName("tanggal_aktivitas")
    val tanggal_aktivitas : String,

    @SerialName("deskripsi_aktivitas")
    val deskripsi_aktivitas : String,
)

@Serializable
data class catatan(
    @SerialName("id_panen")
    val id_panen : String,

    @SerialName("id_tanaman")
    val id_tanaman : String,

    @SerialName("tanggal_panen")
    val tanggal_panen : String,

    @SerialName("jumlah_panen")
    val jumlah_panen : String,

    val keterangan : String,
)

