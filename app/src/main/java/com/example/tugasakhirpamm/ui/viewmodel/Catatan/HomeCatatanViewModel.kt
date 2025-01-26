package com.example.tugasakhirpamm.ui.viewmodel.Catatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhirpamm.model.Catatan
import com.example.tugasakhirpamm.repository.CatatanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeCatatanUiState{
    data class Success(val Catatan: List<Catatan>) : HomeCatatanUiState()
    object Error : HomeCatatanUiState()
    object Loading : HomeCatatanUiState()
}

class HomeCatatanViewModel(private val catatan: CatatanRepository): ViewModel(){
    var catatanUiState: HomeCatatanUiState by mutableStateOf(HomeCatatanUiState.Loading)
        private set

    init {
        getCatatan()
    }

    fun getCatatan(){
        viewModelScope.launch {
            catatanUiState = HomeCatatanUiState.Loading
            catatanUiState = try {
                HomeCatatanUiState.Success(catatan.getCatatan().data)
            } catch (e: IOException){
                HomeCatatanUiState.Error
            } catch (e: HttpException){
                HomeCatatanUiState.Error
            }
        }
    }

    fun deleteCatatan(id_Catatans: String){
        viewModelScope.launch {
            try {
                catatan.deleteCatatan(id_Catatans)
            } catch (e: IOException) {
                HomeCatatanUiState.Error
            }catch (e : HttpException){
                HomeCatatanUiState.Error
            }
        }
    }
}