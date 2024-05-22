package com.example.marketapp.presentation.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketapp.domian.model.State
import com.example.marketapp.domian.repo.StockRepository
import com.example.marketapp.presentation.companyListings.CompanyListingsEvent
import com.example.marketapp.presentation.companyListings.CompanyListingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repo : StockRepository

) :ViewModel(){
    var state by mutableStateOf(CompanyListingsState())

    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }
    fun onEvent(event : CompanyListingsEvent){
        when(event){
            is CompanyListingsEvent.Refresh ->{
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChange ->{
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings(fetchFromRemote = false)
                }

            }
        }
    }

    fun getCompanyListings(
        query:String = state.searchQuery.lowercase(),
        fetchFromRemote :Boolean = false
    ){
        viewModelScope.launch {
            repo.getCompanyListings(query).collect{result ->
                when(result){
                    is State.Success ->{
                        result.data.let {
                            state = state.copy(companies = it!!)
                        }
                    }
                    is State.Error ->{

                    }
                    is State.Loading ->{
                        state = state.copy(isLoading = true)
                    }
                }
            }
        }
    }
}