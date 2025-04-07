package com.example.newspaperapp.data_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rss(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val creator: String,
    val category: String,
): Parcelable
