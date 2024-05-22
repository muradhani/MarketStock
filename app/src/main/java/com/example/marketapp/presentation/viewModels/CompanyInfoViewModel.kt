package com.example.marketapp.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketapp.domian.model.CompanyInfo
import com.example.marketapp.domian.model.State
import com.example.marketapp.domian.repo.StockRepository
import com.example.marketapp.presentation.CompanyInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val repo : StockRepository,
    private val savedStateHandle: SavedStateHandle
):ViewModel(){
    var state by mutableStateOf(CompanyInfoState())
    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)
            val companyInfoResult = async { repo.getCompanyInfo(symbol)}
            val intradayInfoResult =async { repo.getIntradayInfo(symbol)}

            when(val result = companyInfoResult.await()){
                is State.Success ->{
                    state = state.copy(
                        company = result.data as CompanyInfo,
                        isLoading = false,
                    )
                }
                is State.Error ->{
                    state = state.copy(
                        isLoading = false,
                        error = result.message!!,
                        company = null
                    )
                }
                is State.Loading ->{

                }
            }
            when(val intradayInfoResult = intradayInfoResult.await()){
                is State.Success ->{
                    state = state.copy(
                        stockInfo = intradayInfoResult.data?: emptyList(),
                        isLoading = false,
                    )
                }
                is State.Error ->{
                    state = state.copy(
                        isLoading = false,
                        error = intradayInfoResult.message!!
                    )
                }
                is State.Loading ->{

                }
            }
        }
    }
}