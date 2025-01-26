package com.example.tugasakhirpamm.ui.view.Catatan

import com.example.tugasakhirpamm.model.Aktivitas
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.HomeAktivitasUiState
import com.example.tugasakhirpamm.ui.viewmodel.Aktivitas.HomeAktivitasViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhirpamm.R
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.HomeCatatanUiState
import com.example.tugasakhirpamm.ui.viewmodel.Catatan.HomeCatatanViewModel

object DestinasiHomeCatatan : DestinasiNavigasi {
    override val route = "home_catatan"
    override val titleRes = "Home Catatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewCatatan(
    navigateToItemEntryCatatan: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onDetailCatatanClick: (String) -> Unit = {},
    viewModel: HomeCatatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Home Catatan",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getCatatan()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryCatatan,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Catatan")
            }
        },
    ) { innerPadding ->
        HomeCatatanStatus(
            homeCatatanUiState = viewModel.catatanUiState,
            retryAction = { viewModel.getCatatan() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailCatatanClick,
            onDeleteClick = { catatan ->
                viewModel.deleteCatatan(catatan.id_panen)
                viewModel.getCatatan()
            }
        )
    }
}

@Composable
fun HomeCatatanStatus(
    homeCatatanUiState: HomeCatatanUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Catatan) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeCatatanUiState) {
        is HomeCatatanUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeCatatanUiState.Success ->
            if (homeCatatanUiState.Catatan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Catatan")
                }
            } else {
                CatatanLayout(
                    catatan = homeCatatanUiState.Catatan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = onDeleteClick
                )
            }
        is HomeCatatanUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading2),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading2), contentDescription = ""
        )

        Text(text = stringResource(R.string.loading), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun CatatanLayout(
    catatan: List<Catatan>,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Catatan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(catatan) { catatanItem ->
            CatatansCard(
                catatan = catatanItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(catatanItem.id_panen) },
                onDeleteClick = { onDeleteClick(catatanItem) }
            )
        }
    }
}

@Composable
fun CatatansCard(
    catatan: Catatan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Catatan) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = catatan.id_panen,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = catatan.tanggal_panen,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = catatan.jumlah_panen,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(catatan) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
