package com.example.tugasakhirpamm.ui.view.Pekerja

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
import com.example.tugasakhirpamm.model.Pekerja
import com.example.tugasakhirpamm.model.Tanaman
import com.example.tugasakhirpamm.ui.PenyediaViewModel
import com.example.tugasakhirpamm.ui.costumwidget.CostumeTopAppBar
import com.example.tugasakhirpamm.ui.navigasi.DestinasiHomePekerja
import com.example.tugasakhirpamm.ui.navigasi.DestinasiNavigasi
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.HomePekerjaUiState
import com.example.tugasakhirpamm.ui.viewmodel.Pekerja.HomePekerjaViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.HomeTanamanViewModel
import com.example.tugasakhirpamm.ui.viewmodel.Tanaman.HomeUiState




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewPekerja(
    navigateToItemEntryPekerja: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onDetailPekerjaClick: (String) -> Unit = {},
    viewModel: HomePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getpekerja()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryPekerja,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pekerja")
            }
        },
    ) { innerPadding ->
        HomePekerjaStatus(
            homePekerjaUiState = viewModel.pekerjaUiState,
            retryAction = { viewModel.getpekerja() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailPekerjaClick,
            onDeleteClick = { pekerja ->
                viewModel.deletePekerja(pekerja.id_pekerja)
                viewModel.getpekerja()
            }
        )
    }
}


@Composable
fun HomePekerjaStatus(
    homePekerjaUiState: HomePekerjaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homePekerjaUiState) {
        is HomePekerjaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePekerjaUiState.Success -> {
            if (homePekerjaUiState.Pekerja.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data Pekerja")
                }
            } else {
                PekerjaLayout(
                    pekerja = homePekerjaUiState.Pekerja,  // Here you pass the list of pekerja
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_pekerja) },
                    onDeleteClick = { pekerja -> onDeleteClick(pekerja) }
                )
            }
        }
        is HomePekerjaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}



@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading2),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction:()->Unit, modifier: Modifier = Modifier){
    Column(
        modifier=modifier,
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
fun PekerjaLayout(
    pekerja: List<Pekerja>,
    modifier: Modifier = Modifier,
    onDetailClick:(Pekerja)->Unit,
    onDeleteClick: (Pekerja) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pekerja){ kontak ->
            PekerjaCard(
                pekerja = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(kontak)},
                onDeleteClick ={
                    onDeleteClick(kontak)
                }
            )

        }
    }
}

@Composable
fun PekerjaCard(
    pekerja: Pekerja,
    modifier: Modifier = Modifier,
    onDeleteClick:(Pekerja)->Unit={}
){
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
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = pekerja.id_pekerja,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = pekerja.nama_pekerja,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Text(
                    text = pekerja.jabatan,
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(onClick = { onDeleteClick(pekerja) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}