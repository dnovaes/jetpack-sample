package com.arctouch.io.outdoorsychallenge.data.source.remote

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.datasource.VehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.network.OutdoorsyApi
import com.arctouch.io.outdoorsychallenge.domain.model.factory.VehicleFactory
import com.arctouch.io.outdoorsychallenge.features.main.MainViewModel.Companion.RV_LIST_PAGE_LIMIT
import com.arctouch.io.outdoorsychallenge.unitmocks.mockVehicles
import com.arctouch.io.outdoorsychallenge.unitmocks.mockVehiclesResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class VehicleDataSourceTest {

    @Mock
    private lateinit var api: OutdoorsyApi

    private val mockedVehicleResponse = mockVehiclesResponse(RV_LIST_PAGE_LIMIT)
    private val mockedEmptyVehicleResponse = mockVehiclesResponse(0)
    private val mockedVehicles = mockVehicles(RV_LIST_PAGE_LIMIT)

    private lateinit var dataSource: IVehicleRemoteDataSource
    private val factory = VehicleFactory()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        dataSource = VehicleRemoteDataSource(api, factory)
    }

    @Test
    fun `test fetchVehicles when api-fetchVehiclesAsync returns empty list`() = runBlocking<Unit> {
        whenever(api.fetchVehiclesAsync(any(), any(), any())).thenReturn(
            mockedEmptyVehicleResponse
        )

        val result = dataSource.fetchVehicles("trailer", RV_LIST_PAGE_LIMIT, 0)

        assertEquals(0, result?.size)
        verify(api, times(1)).fetchVehiclesAsync(any(), any(), any())
    }

    @Test
    fun `test fetchVehicles when api-fetchVehiclesAsync returns data`() = runBlocking<Unit> {
        whenever(api.fetchVehiclesAsync(any(), any(), any())).thenReturn(
            mockedVehicleResponse
        )

        val result = dataSource.fetchVehicles("trailer", RV_LIST_PAGE_LIMIT, 0)

        assertEquals(mockedVehicles, result)
        verify(api, times(1)).fetchVehiclesAsync(any(), any(), any())
    }
}
