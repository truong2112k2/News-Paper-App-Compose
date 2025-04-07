package com.example.newspaperapp.data_model

data class Article (
    val id : Int = 0,
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val creator: String,
    val category: String,
){

}