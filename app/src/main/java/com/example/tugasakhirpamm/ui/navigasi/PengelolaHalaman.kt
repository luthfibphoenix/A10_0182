package com.example.tugasakhirpamm.ui.navigasi

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhirpamm.ui.view.Aktivitas.*
import com.example.tugasakhirpamm.ui.view.Catatan.*
import com.example.tugasakhirpamm.ui.view.HomeView
import com.example.tugasakhirpamm.ui.view.MainMenu
import com.example.tugasakhirpamm.ui.view.Pekerja.*
import com.example.tugasakhirpamm.ui.view.Tanaman.*

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MainMenu.route,
        modifier = modifier
    ) {
        // Main Menu
        composable(MainMenu.route) {
            HomeView(
                onTanamanButton = {
                    navController.navigate(DestinasiHomeTanaman.route)
                },
                onPekerjaButton = {
                    navController.navigate(DestinasiHomePekerja.route)
                },
                onAktivitasButton = {
                    navController.navigate(DestinasiHomeAktivitas.route)
                },
                onCatatanButton = {
                    navController.navigate(DestinasiHomeCatatan.route)
                }
            )
        }

        // Home Pekerja
        composable(DestinasiHomePekerja.route) {
            HomeViewPekerja(
                navigateToItemEntryPekerja = { navController.navigate(DestinasiInsertPekerja.route) },
                onBackClick = { navController.popBackStack() },
                onDetailPekerjaClick = { idPekerja ->
                    if (idPekerja.isNotBlank()) {
                        navController.navigate("${DestinasiDetailPekerja.route}/$idPekerja")
                    } else {
                        Log.e("Navigasi", "ID Pekerja tidak valid")
                    }
                }
            )
        }

        // Insert Pekerja
        composable(DestinasiInsertPekerja.route) {
            InsertPekerjaScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Pekerja
        composable(
            route = DestinasiDetailPekerja.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPekerja.PEKERJA) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idPekerja = backStackEntry.arguments?.getString(DestinasiDetailPekerja.PEKERJA)
            idPekerja?.let {
                DetailViewPekerja(
                    navigateBack = { navController.popBackStack() },
                    navigateToEdit = { navController.navigate("${DestinasiPekerjaUpdate.route}/$idPekerja") }
                )
            } ?: run {
                Log.e("Navigasi", "ID Pekerja null")
                navController.popBackStack()
            }
        }

        // Update Pekerja
        composable(
            route = DestinasiPekerjaUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiPekerjaUpdate.PEKERJA) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idPekerja = backStackEntry.arguments?.getString(DestinasiPekerjaUpdate.PEKERJA)
            idPekerja?.let {
                UpdatePekerjaScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: navController.popBackStack()
        }

        // Home Tanaman
        composable(DestinasiHomeTanaman.route) {
            HomeViewTanaman(
                navigateToItemEntry = { navController.navigate(DestinasiInsertTanaman.route) },
                onBackClick = { navController.popBackStack() },
                onDetailTanamanClick = { idTanaman ->
                    if (idTanaman.isNotBlank()) {
                        navController.navigate("${DestinasiDetailTanaman.route}/$idTanaman")
                    } else {
                        Log.e("Navigasi", "ID Tanaman tidak valid")
                    }
                }
            )
        }

        // Insert Tanaman
        composable(DestinasiInsertTanaman.route) {
            InsertTanamanScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Tanaman
        composable(
            route = DestinasiDetailTanaman.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailTanaman.TANAMAN) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString(DestinasiDetailTanaman.TANAMAN)
            idTanaman?.let {
                DetailViewTanaman(
                    navigateBack = { navController.popBackStack() },
                    onEditButton = { navController.navigate("${DestinasiTanamanUpdate.route}/$idTanaman") },
                    onAddPanenButton = { navController.navigate(DestinasiInsertCatatan.route)}
                )
            } ?: run {
                Log.e("Navigasi", "ID Tanaman null")
                navController.popBackStack()
            }
        }

        // Update Tanaman
        composable(
            route = DestinasiTanamanUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiTanamanUpdate.TANAMAN) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idTanaman = backStackEntry.arguments?.getString(DestinasiTanamanUpdate.TANAMAN)
            idTanaman?.let {
                UpdateTanamanView(
                    navigateBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: navController.popBackStack()
        }

        // Home Aktivitas
        composable(DestinasiHomeAktivitas.route) {
            HomeViewAktivitas(
                navigateToItemEntryAktivitas = { navController.navigate(DestinasiInsertAktivitas.route) },
                onBackClick = { navController.popBackStack() },
                onDetailAktivitasClick = { idAktivitas ->
                    if (idAktivitas.isNotBlank()) {
                        navController.navigate("${DestinasiDetailAktivitas.route}/$idAktivitas")
                    } else {
                        Log.e("Navigasi", "ID Aktivitas tidak valid")
                    }
                }
            )
        }

        // Insert Aktivitas
        composable(DestinasiInsertAktivitas.route) {
            InsertAktivitasScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Detail Aktivitas
        composable(
            route = DestinasiDetailAktivitas.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailAktivitas.AKTIVITAS) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idAktivitas = backStackEntry.arguments?.getString(DestinasiDetailAktivitas.AKTIVITAS)
            idAktivitas?.let {
                DetailViewAktivitas(
                    navigateBack = { navController.popBackStack() },
                    navigateToEdit = { navController.navigate("${DestinasiAktivitasUpdate.route}/$idAktivitas") }
                )
            } ?: run {
                Log.e("Navigasi", "ID Aktivitas null")
                navController.popBackStack()
            }
        }

        // Update Aktivitas
        composable(
            route = DestinasiAktivitasUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiAktivitasUpdate.AKTIVITAS) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idAktivitas = backStackEntry.arguments?.getString(DestinasiAktivitasUpdate.AKTIVITAS)
            idAktivitas?.let {
                UpdateAktivitasScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: navController.popBackStack()
        }

        // Home Catatan
        composable(DestinasiHomeCatatan.route) {
            HomeViewCatatan(
                navigateToItemEntryCatatan = { navController.navigate(DestinasiInsertCatatan.route) },
                onBackClick = { navController.popBackStack() },
                onDetailCatatanClick = { idCatatan ->
                    if (idCatatan.isNotBlank()) {
                        navController.navigate("${DestinasiDetailCatatan.route}/$idCatatan")
                    } else {
                        Log.e("Navigasi", "ID Catatan tidak valid")
                    }
                }
            )
        }

        // Insert Catatan
        composable(DestinasiInsertCatatan.route) {
            InsertCatatanScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }

        // Detail Catatan
        composable(
            route = DestinasiDetailCatatan.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailCatatan.CATATAN) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idCatatan = backStackEntry.arguments?.getString(DestinasiDetailCatatan.CATATAN)
            idCatatan?.let {
                DetailViewCatatan(
                    navigateBack = { navController.popBackStack() },
                    navigateToEdit = { navController.navigate("${DestinasiCatatanUpdate.route}/$idCatatan") }
                )
            } ?: run {
                Log.e("Navigasi", "ID Catatan null")
                navController.popBackStack()
            }
        }

        // Update Catatan
        composable(
            route = DestinasiCatatanUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiCatatanUpdate.CATATAN) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idCatatan = backStackEntry.arguments?.getString(DestinasiCatatanUpdate.CATATAN)
            idCatatan?.let {
                UpdateCatatanScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } ?: navController.popBackStack()
        }
    }
}
