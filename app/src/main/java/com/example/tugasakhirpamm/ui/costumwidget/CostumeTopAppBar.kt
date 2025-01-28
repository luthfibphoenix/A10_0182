package com.example.tugasakhirpamm.ui.costumwidget

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumeTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigatBack: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        actions = {
            // Tombol Refresh dengan deskripsi untuk aksesibilitas
            IconButton(onClick = { onRefresh() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Segarkan"
                )
            }
        },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                // Tombol Back
                IconButton(onClick = navigatBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Kembali"
                    )
                }
            }
        }
    )
}
