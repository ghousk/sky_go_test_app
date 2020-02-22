//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.repository

import androidx.lifecycle.LiveData
import com.innovativequest.sky_go_test_app.AppExecutors
import com.innovativequest.sky_go_test_app.api.ApiResponse
import com.innovativequest.sky_go_test_app.api.DataService
import com.innovativequest.sky_go_test_app.db.DataListItemResponseDao
import com.innovativequest.sky_go_test_app.db.AppDb
import com.innovativequest.sky_go_test_app.testing.OpenForTesting
import com.innovativequest.sky_go_test_app.model.*
import com.innovativequest.sky_go_test_app.util.AppConstants
import com.innovativequest.sky_go_test_app.util.AppConstants.REFRESH_TIME_OUT_IN_MILLIS
import com.innovativequest.sky_go_test_app.util.PreferencesManager
import com.innovativequest.sky_go_test_app.util.Utils
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
    private val preferencesManager: PreferencesManager,
    private val dataListItemDao: DataListItemResponseDao,
    private val dataService: DataService
) {

    fun loadDataListItemResponses(): LiveData<Resource<DataListItemResponse>> {
        return object : NetworkBoundResource<DataListItemResponse, DataListItemResponse>(appExecutors) {
            override fun saveCallResult(item: DataListItemResponse) {
                dataListItemDao.insertDataListItemResponses(item)
            }

            override fun shouldFetch(data: DataListItemResponse?): Boolean {
                return data?.items == null || data.items.isEmpty() ||
                        isOutdated(preferencesManager.getLong(AppConstants.GET_LIST_ITEMS))
            }

            override fun loadFromDb() = dataListItemDao.loadAllDataListItemResponses()

            override fun createCall() : LiveData<ApiResponse<DataListItemResponse>> {
                preferencesManager.setLong(AppConstants.GET_LIST_ITEMS, Utils.getCurrentTime())
                return dataService.getDataItems()
            }

            override fun onFetchFailed() {
            }
        }.asLiveData()
    }

    fun loadDataListItemResponseById(id: String): LiveData<Resource<DataListItemResponse>> {
        return object : NetworkBoundResource<DataListItemResponse, DataListItemResponse>(appExecutors) {
            override fun saveCallResult(item: DataListItemResponse) {
                dataListItemDao.insertDataListItemResponses(item)
            }

            override fun shouldFetch(data: DataListItemResponse?): Boolean {
                return data?.items == null ||data.items.isEmpty() ||
                        isOutdated(preferencesManager.getLong(AppConstants.GET_LIST_ITEM_BY_ID + id))
            }

            override fun loadFromDb() = dataListItemDao.loadAllDataListItemResponses()

            override fun createCall() : LiveData<ApiResponse<DataListItemResponse>> {
                preferencesManager.setLong(AppConstants.GET_LIST_ITEM_BY_ID + id, Utils.getCurrentTime())
                return dataService.getDataItemById(id)
            }

            override fun onFetchFailed() {
            }
        }.asLiveData()
    }

    /**
     * Check if a time stamp from prefs is outdated
     */
    private fun isOutdated(lastUpdateTime: Long): Boolean = Utils.getCurrentTime() > lastUpdateTime + REFRESH_TIME_OUT_IN_MILLIS
}
