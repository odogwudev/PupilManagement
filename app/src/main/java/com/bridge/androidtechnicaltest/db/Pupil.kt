package com.bridge.androidtechnicaltest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bridge.androidtechnicaltest.data.local.Pupil
import com.google.gson.annotations.SerializedName


data class PupilList(
    @SerializedName("items")
    val items: List<Pupil> = emptyList(),

    @SerializedName("pageNumber")
    val pageNumber: Int = 1,

    @SerializedName("itemCount")
    val itemCount: Int = 0,

    @SerializedName("totalPages")
    val totalPages: Int = 1
)