//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.api

import androidx.lifecycle.LiveData
import com.innovativequest.sky_go_test_app.util.AppConstants
import com.innovativequest.sky_go_test_app.model.DataListItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface DataService {

    @GET(AppConstants.GET_LIST_ITEMS)
    fun getDataItems(): LiveData<ApiResponse<DataListItemResponse>>

    @GET(AppConstants.GET_LIST_ITEM_BY_ID)
    fun getDataItemById(
        @Path("id") id : String
    ): LiveData<ApiResponse<DataListItemResponse>>
}
