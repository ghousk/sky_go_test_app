//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.innovativequest.sky_go_test_app.testing.OpenForTesting
import com.innovativequest.sky_go_test_app.model.*

/**
 * Interface for database access on Repo related operations.
 */
@Dao
@OpenForTesting
abstract class DataListItemResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg dataListItemResponse: DataListItemResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDataListItemResponses(dataListItemResponse: DataListItemResponse)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createDataListItemResponseIfNotExists(dataListItemResponse: DataListItemResponse): Long

    @Query("SELECT * FROM dataListItemResponse")
    abstract fun load(): LiveData<DataListItemResponse>

    @Query("SELECT * FROM dataListItemResponse")
    abstract fun loadAllDataListItemResponses(): LiveData<DataListItemResponse>

}
