package com.example.newspaperapp.repository

import com.example.newspaperapp.data_model.Article
import com.example.newspaperapp.data_model.Rss

interface IRssRepository {
    suspend fun loadRssFeedData(url: String): List<Rss>
    suspend fun getContentFromHTML(link: String): String
    suspend fun insertArticle(rssItem: Rss): Long
    suspend fun getAllArticles(): List<Article>
    suspend fun deleteArticle(id: Int): Boolean
}