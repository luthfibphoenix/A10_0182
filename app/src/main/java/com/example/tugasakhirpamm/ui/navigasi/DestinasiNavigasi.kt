package com.example.tugasakhirpamm.ui.navigasi

interface DestinasiNavigasi {
    val route : String
    val titleRes : String
}

//AKTIVITAS
object DestinasiDetailAktivitas : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Aktivitas"
    const val AKTIVITAS = "idAktivitas"
    val routeWithArg = "$route/{$AKTIVITAS}"
}

object DestinasiHomeAktivitas: DestinasiNavigasi {
    override val route ="home_aktivitas"
    override val titleRes = "Home Aktivitas"
}

object DestinasiInsertAktivitas : DestinasiNavigasi {
    override val route = "insert_aktivitas"
    override val titleRes = "Insert Aktivitas"
}

object DestinasiAktivitasUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Aktivitas" // Updated title to reflect 'Pekerja'
    const val AKTIVITAS = "idAktivitas"
    val routeWithArg = "$route/{$AKTIVITAS}"
}

//CATATAN
object DestinasiDetailCatatan : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Catatan"
    const val CATATAN = "idCatatan"
    val routeWithArg = "$route/{$CATATAN}"
}

object DestinasiHomeCatatan : DestinasiNavigasi {
    override val route = "home_catatan"
    override val titleRes = "Home Catatan"
}

object DestinasiInsertCatatan : DestinasiNavigasi {
    override val route = "insert_catatan"
    override val titleRes = "Insert Catatan"
}

object DestinasiCatatanUpdate : DestinasiNavigasi {
    override val route = "update_catatan"
    override val titleRes = "Update Catatan"
    const val CATATAN = "catatanId"  // Ganti dari 'id Panen' menjadi 'catatanId'
    val routeWithArg = "$route/{$CATATAN}"
}

//PEKERJA
object DestinasiDetailPekerja : DestinasiNavigasi {
    override val route = "item_detail"
    override val titleRes = "Detail Pekerja"
    const val PEKERJA = "idPekerja"
    val routeWithArg = "$route/{$PEKERJA}"
}

object DestinasiHomePekerja: DestinasiNavigasi {
    override val route ="home_pekerja"
    override val titleRes = "Home Pekerja"
}

object DestinasiInsertPekerja : DestinasiNavigasi {
    override val route = "insert_pekerja"
    override val titleRes = "Insert Pekerja"
}

object DestinasiPekerjaUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Pekerja" // Updated title to reflect 'Pekerja'
    const val PEKERJA = "id Pekerja"
    val routeWithArg = "$route/{$PEKERJA}"
}

//TANAMAN
object DestinasiDetailTanaman : DestinasiNavigasi {
    override val route = "detail_tanaman"
    override val titleRes = "Detail Tanaman"
    const val TANAMAN = "idTanaman"
    val routeWithArg = "$route/{$TANAMAN}"
}

object DestinasiHomeTanaman: DestinasiNavigasi {
    override val route ="home_tanaman"
    override val titleRes = "Home Tanaman"
}


object DestinasiInsertTanaman: DestinasiNavigasi {
    override val route = "insert_tanaman"
    override val titleRes = "Insert Tamaman"

}

object DestinasiTanamanUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Tanaman"
    const val TANAMAN = "idTanaman"
    val routeWithArg = "$route/{$TANAMAN}"
}