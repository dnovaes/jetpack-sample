package com.arctouch.io.outdoorsychallenge.domain.repository

import com.arctouch.io.outdoorsychallenge.data.source.remote.outdoorsy.IVehicleRemoteDataSource
import com.arctouch.io.outdoorsychallenge.features.searchrv.SearchRvViewModel.Companion.RV_LIST_PAGE_LIMIT
import com.arctouch.io.outdoorsychallenge.unitmocks.mockVehicles
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

class VehicleRepositoryTest {

    @Mock
    private lateinit var dataSource: IVehicleRemoteDataSource

    private val mockedVehicles = mockVehicles(RV_LIST_PAGE_LIMIT)

    private lateinit var repository: IVehicleRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = VehicleRepository(dataSource)
    }

    @Test
    fun `test getVehiclesBy when dataSource-fetchVehicles returns null`() = runBlocking<Unit> {
        whenever(dataSource.fetchVehicles(any(), any(), any())).thenReturn(null)

        val result = repository.getVehiclesBy("trailer", RV_LIST_PAGE_LIMIT, 0)

        assertEquals(0, result.size)
        verify(dataSource, times(1)).fetchVehicles(any(), any(), any())
    }

    @Test
    fun `test getVehiclesBy when dataSource-fetchVehicles returns data`() = runBlocking<Unit> {
        whenever(dataSource.fetchVehicles(any(), any(), any())).thenReturn(mockedVehicles)

        val result = repository.getVehiclesBy("trailer", RV_LIST_PAGE_LIMIT, 0)

        assertEquals(mockedVehicles, result)
        verify(dataSource, times(1)).fetchVehicles(any(), any(), any())
    }
}
