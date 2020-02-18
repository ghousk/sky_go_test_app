//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.repository

import androidx.lifecycle.LiveData
import com.innovativequest.sky_go_test_app.AppExecutors
import com.innovativequest.sky_go_test_app.api.DataService
import com.innovativequest.sky_go_test_app.db.DataListItemResponseDao
import com.innovativequest.sky_go_test_app.db.AppDb
import com.innovativequest.sky_go_test_app.testing.OpenForTesting
import com.innovativequest.sky_go_test_app.model.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Repo instances.
 *
 * unfortunate naming :/ .
 * Repo - value object name
 * Repository - type of this class.
 */
@Singleton
@OpenForTesting
class DataListItemsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: AppDb,
    private val dataListItemDao: DataListItemResponseDao,
    private val dataService: DataService
) {

    fun loadDataListItemResponses(): LiveData<Resource<DataListItemResponse>> {
        return object : NetworkBoundResource<DataListItemResponse, DataListItemResponse>(appExecutors) {
            override fun saveCallResult(item: DataListItemResponse) {
                dataListItemDao.insertDataListItemResponses(item)
            }

            override fun shouldFetch(data: DataListItemResponse?): Boolean {
                return data?.items == null ||data.items.isEmpty()
            }

            override fun loadFromDb() = dataListItemDao.loadAllDataListItemResponses()

            override fun createCall() = dataService.getDataItems(100, "desc", "reputation", "stackoverflow")

            override fun onFetchFailed() {
            }
        }.asLiveData()
    }

    fun loadDataListItemResponseById(userId: String): LiveData<Resource<DataListItemResponse>> {
        return object : NetworkBoundResource<DataListItemResponse, DataListItemResponse>(appExecutors) {
            override fun saveCallResult(item: DataListItemResponse) {
                dataListItemDao.insertDataListItemResponses(item)
            }

            override fun shouldFetch(data: DataListItemResponse?): Boolean {
                return data?.items == null ||data.items.isEmpty()
            }

            override fun loadFromDb() = dataListItemDao.loadAllDataListItemResponses()

            override fun createCall() = dataService.getDataItemById(100, "desc", "reputation", "stackoverflow", userId)

            override fun onFetchFailed() {
            }
        }.asLiveData()
    }
}
