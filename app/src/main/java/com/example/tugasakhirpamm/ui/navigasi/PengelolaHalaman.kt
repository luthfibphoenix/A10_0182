package com.example.tugasakhirpamm.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhirpamm.ui.view.Pekerja.*

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePekerja.route,
        modifier = modifier
    ) {
        // Halaman Home
        composable(DestinasiHomePekerja.route) {
            HomeViewPekerja(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPekerja.route) },
                onDetailClick = { idPekerja ->
                    if (idPekerja.isNotBlank()) {
                        navController.navigate("${DestinasiDetailPekerja.route}/$idPekerja")
                    } else {
                        // Tampilkan pesan kesalahan atau feedback
                    }
                }
            )
        }

        // Halaman Insert Pekerja
        composable(route = DestinasiInsertPekerja.route) {
            InsertPekerjaScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        // Halaman Detail Pekerja
        composable(
            route = DestinasiDetailPekerja.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPekerja.PEKERJA) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idPekerja = backStackEntry.arguments?.getString(DestinasiDetailPekerja.PEKERJA)
            if (!idPekerja.isNullOrBlank()) {
                DetailViewPekerja(
                    navigateBack = { navController.popBackStack() },
                    navigateToEdit = { navController.navigate("${DestinasiPekerjaUpdate.route}/$idPekerja") }
                )
            } else {
                navController.popBackStack() // Argumen null atau kosong, kembali ke layar sebelumnya
            }
        }

        // Halaman Update Pekerja
        composable(
            route = DestinasiPekerjaUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiPekerjaUpdate.PEKERJA) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idPekerja = backStackEntry.arguments?.getString(DestinasiPekerjaUpdate.PEKERJA)
            if (!idPekerja.isNullOrBlank()) {
                UpdatePekerjaScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack() // Argumen null atau kosong, kembali ke layar sebelumnya
            }
        }
    }
}
