//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.di

import com.innovativequest.sky_go_test_app.ui.repo.ItemDetailFragment
import com.innovativequest.sky_go_test_app.ui.datalistitems.DataListItemsFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): ItemDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeDataListItemsFragment(): DataListItemsFragment
}
