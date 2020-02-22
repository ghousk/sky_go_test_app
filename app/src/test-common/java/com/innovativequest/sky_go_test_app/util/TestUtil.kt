//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.util

import com.innovativequest.sky_go_test_app.model.DataListItem
import com.innovativequest.sky_go_test_app.model.DataListItemResponse


object TestUtil {

    fun createRepos(count: Int, aYear: String?, aGenre: String?, aId: Int?,
                    aTitle: String?, aPoster: String?): DataListItemResponse {
        val dataListItemArray = ArrayList<DataListItem>()

        for (i in 0..count) {
            dataListItemArray.add( createRepo(
                aYear = aYear,
                aGenre = aGenre,
                aId = aId,
                aTitle = aTitle + i,
                aPoster = aPoster
            ) )
        }

        return DataListItemResponse(dataListItemArray)

    }

    private fun createRepo(aYear: String?, aGenre: String?, aId: Int?, aTitle: String?, aPoster: String?) = DataListItem(
        year = aYear,
        genre = aGenre,
        id = aId,
        title = aTitle,
        poster = aPoster
    )
}