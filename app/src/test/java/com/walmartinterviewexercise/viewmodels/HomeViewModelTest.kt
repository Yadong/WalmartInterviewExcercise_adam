package com.walmartinterviewexercise.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.walmartinterviewexercise.api.APIService
import com.walmartinterviewexercise.model.Country
import com.walmartinterviewexercise.model.CountryManager
import com.walmartinterviewexercise.ui.UIState
import com.walmartinterviewexercise.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var countryManager: CountryManager
    private lateinit var viewModel: HomeViewModel

    @Mock
    private lateinit var apiService: APIService

    @Mock
    private lateinit var uiStateObserver: Observer<UIState<List<Country>>>

    @Before
    fun setUp() {
        countryManager = CountryManager((apiService))
        viewModel = HomeViewModel(countryManager)
    }

    @Test
    fun onApiSuccess_whenManager_getList_shouldReturnSuccess() {

        val result = listOf(Country("", "", "", ""))
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(result)
                .`when`(apiService)
                .getCountryList()

            viewModel.getCountryList()
            viewModel.getCountryListUIState().observeForever(uiStateObserver)
            verify(apiService).getCountryList()
            verify(uiStateObserver).onChanged(
                UIState.Success(result)
            )
        }
    }

    @Test
    fun onApiSuccess_whenManager_getEmptyList_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<Country>())
                .`when`(apiService)
                .getCountryList()

            viewModel.getCountryList()
            viewModel.getCountryListUIState().observeForever(uiStateObserver)
            verify(apiService).getCountryList()
            verify(uiStateObserver).onChanged(
                UIState.Success(emptyList())
            )
        }
    }

    @Test
    fun onApiError_whenManager_getCountryList_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "400 Message"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(apiService)
                .getCountryList()

            viewModel.getCountryList()
            viewModel.getCountryListUIState().observeForever(uiStateObserver)
            verify(apiService).getCountryList()
            verify(uiStateObserver).onChanged(UIState.Error(errorMessage))
        }
    }
}
