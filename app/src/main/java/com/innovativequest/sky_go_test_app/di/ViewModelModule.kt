//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************

package com.innovativequest.sky_go_test_app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.innovativequest.sky_go_test_app.ui.repo.ItemDetailViewModel
import com.innovativequest.sky_go_test_app.ui.datalistitems.DataListItemsViewModel
import com.innovativequest.sky_go_test_app.viewmodel.AppViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DataListItemsViewModel::class)
    abstract fun bindDataListItemsViewModel(dataListItemsViewModel: DataListItemsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ItemDetailViewModel::class)
    abstract fun bindRepoViewModel(itemDetailViewModel: ItemDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
