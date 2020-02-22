//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.innovativequest.sky_go_test_app.util.LiveDataTestUtil.getValue
import com.innovativequest.sky_go_test_app.util.TestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataListItemResponseDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndRead() {
        val repo = TestUtil.createRepos(2, "2017", "Action", 11241,
            "Jumanji: welcome to the jungle",
            "https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg")
        db.dataListItemResponseDao().insert(repo)
        val loaded = getValue(db.dataListItemResponseDao().load())
        assertThat(loaded, notNullValue())
        assertThat(loaded.items[0].title, `is`("Jumanji: welcome to the jungle0"))
    }
}
