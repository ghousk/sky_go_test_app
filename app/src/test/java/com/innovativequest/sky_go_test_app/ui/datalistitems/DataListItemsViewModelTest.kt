//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.ui.datalistitems

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.innovativequest.sky_go_test_app.repository.DataListItemsRepository
import com.innovativequest.sky_go_test_app.util.mock
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

@RunWith(JUnit4::class)
class DataListItemsViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val dataListItemsRepository = mock(DataListItemsRepository::class.java)
    private val dataListItemViewModel = DataListItemsViewModel(dataListItemsRepository)

    @Test
    fun loadRepositories() {
        dataListItemViewModel.dataListItems.observeForever(mock())
        verifyNoMoreInteractions(dataListItemsRepository)
        dataListItemViewModel.setId("123456")
        verify(dataListItemsRepository).loadDataListItemResponses()
        reset(dataListItemsRepository)
        verifyNoMoreInteractions(dataListItemsRepository)
    }

}