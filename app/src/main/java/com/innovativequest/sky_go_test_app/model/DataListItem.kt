package com.innovativequest.sky_go_test_app.model

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
	indices = [
		Index("id"),
		Index("title")],
	primaryKeys = ["id"]
)
data class DataListItem(

	@field:SerializedName("year")
	val year: String? = null,

	@field:SerializedName("genre")
	val genre: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("poster")
	val poster: String? = null
)