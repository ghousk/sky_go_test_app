package com.innovativequest.sky_go_test_app.model

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
	indices = [Index("items")],
	primaryKeys = ["items"]
)
data class DataListItemResponse(
	@field:SerializedName("data")
	val items: List<DataListItem>
)