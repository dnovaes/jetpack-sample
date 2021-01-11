package com.arctouch.io.outdoorsychallenge.searchrv

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyApi
import com.arctouch.io.outdoorsychallenge.dispatchers.InstrumentedTestDispatcherMap
import com.arctouch.io.outdoorsychallenge.features.outdoorsy.OutdoorsyActivity
import com.arctouch.io.outdoorsychallenge.features.main.MainViewModel.Companion.RV_LIST_PAGE_LIMIT
import com.arctouch.io.outdoorsychallenge.instrumentedmocks.mockVehiclesResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchRvTest {

    @Mock
    private lateinit var api: OutdoorsyApi

    private val mockedVehicleResponse = mockVehiclesResponse(RV_LIST_PAGE_LIMIT)

    private val mockedSearchText = "trailer"

    @get:Rule
    val mainActivityRule = ActivityTestRule(OutdoorsyActivity::class.java, false, false)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        module(override = true) {
            single { api }

            single { InstrumentedTestDispatcherMap }
        }.also { loadKoinModules(it) }

        mainActivityRule.launchActivity(Intent())
    }

    @Test
    fun test_search_rv_screen_for_api_results() = searchRvState {
        whenever(api.fetchVehiclesAsync(any(), any(), any()))
            .thenReturn(mockedVehicleResponse)

        matchEmptyStateIsVisible()

        typeOnSearchRvField(mockedSearchText)

        clickOnSearchRvKeyboardActionButton()

        delay(DELAY_TO_LOAD_API_RESPONSE)

        matchEmptyStateIsGone()

        matchAmountOfListedItems(RV_LIST_PAGE_LIMIT * 2)
    }

    companion object {

        private const val DELAY_TO_LOAD_API_RESPONSE = 2000L
    }
}