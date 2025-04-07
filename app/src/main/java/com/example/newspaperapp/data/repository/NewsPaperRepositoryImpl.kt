package com.example.newspaperapp.data.repository

import android.content.ContentValues
import android.util.Log
import com.example.newspaperapp.repository.INoteRepository
import com.example.newspaperapp.repository.IRssRepository
import com.example.newspaperapp.data.database.ArticleDatabase
import com.example.newspaperapp.data.database.NoteDatabase

import com.example.newspaperapp.data_model.Article
import com.example.newspaperapp.data_model.Note
import com.example.newspaperapp.data_model.Rss
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class NewsPaperRepositoryImpl(
    private val articleDatabase: ArticleDatabase,
    private val noteDatabase: NoteDatabase
) : IRssRepository, INoteRepository {

    companion object {


        private const val TABLE_NAME_ARTICLE = "articles"
        private const val COLUMN_ID_ARTICLE = "id"
        private const val COLUMN_TITLE_ARTICLE = "title"
        private const val COLUMN_LINK_ARTICLE = "link"
        private const val COLUMN_DESCRIPTION_ARTICLE = "description"
        private const val COLUMN_PUBDATE_ARTICLE = "pubDate"
        private const val COLUMN_CREATOR_ARTICLE = "creator"
        private const val COLUMN_CATEGORY_ARTICLE = "category"


    }
    override suspend fun loadRssFeedData(url: String): List<Rss>
    {
        return withContext(Dispatchers.IO){
            try{
                val doc = Jsoup.connect(url).get()
                val items = doc.select("item")
                items.map { element ->
                    Rss(
                        title = element.select("title").text(),
                        link = element.select("link").text(),
                        description = element.select("description").text(),
                        pubDate = element.select("pubDate").text(),
                        creator = element.getElementsByTag("dc:creator").text(), // Cách sửa lỗi chính xác
                        category = element.select("category").text()

                    )

                }

            }catch (e: Exception){
                Log.d("fetchRssFeed", e.message.toString())
                emptyList<Rss>()

            }
        }
    }
    override suspend fun getContentFromHTML(link: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val document: Document = Jsoup.connect(link).get()
                val paragraphs = document.select("p")  // Lấy nội dung từ các thẻ <p>
                val content = StringBuilder()

                for (paragraph in paragraphs) {
                    content.append(paragraph.text()).append("\n\n")
                }

                content.toString().trim() // Loại bỏ khoảng trắng dư thừa ở đầu và cuối
            } catch (e: IOException) {
                e.printStackTrace()
                "Lỗi kết nối: Không thể truy cập trang web."
            } catch (e: Exception) {
                e.printStackTrace()
                "Đã xảy ra lỗi trong quá trình xử lý."
            }
        }
    }


    override suspend fun insertArticle(rssItem: Rss): Long {

        val db = articleDatabase.writableDatabase

        // Kiểm tra bài báo đã tồn tại chưa
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME_ARTICLE WHERE $COLUMN_TITLE_ARTICLE = ? AND $COLUMN_PUBDATE_ARTICLE = ?",
            arrayOf(rssItem.title, rssItem.pubDate)
        )

        return if (cursor.count > 0) {
            // Nếu tồn tại, không thêm nữa
            cursor.close()
            -1L // Trả về -1 để báo hiệu bài báo đã tồn tại
        } else {
            // Nếu chưa tồn tại, thêm mới
            cursor.close()
            val values = ContentValues().apply {
                put(COLUMN_TITLE_ARTICLE, rssItem.title)
                put(COLUMN_LINK_ARTICLE, rssItem.link)
                put(COLUMN_DESCRIPTION_ARTICLE, rssItem.description)
                put(COLUMN_PUBDATE_ARTICLE, rssItem.pubDate)
                put(COLUMN_CREATOR_ARTICLE, rssItem.creator)
                put(COLUMN_CATEGORY_ARTICLE, rssItem.category)
            }
            db.insert(TABLE_NAME_ARTICLE, null, values)
        }
    }

    override suspend fun getAllArticles(): List<Article> {
        val articles = mutableListOf<Article>()
        val db = articleDatabase.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_ARTICLE", null)

        cursor.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID_ARTICLE))
                    val title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE_ARTICLE))
                    val link = it.getString(it.getColumnIndexOrThrow(COLUMN_LINK_ARTICLE))
                    val description = it.getString(it.getColumnIndexOrThrow(
                        COLUMN_DESCRIPTION_ARTICLE
                    ))
                    val pubDate = it.getString(it.getColumnIndexOrThrow(COLUMN_PUBDATE_ARTICLE))
                    val creator = it.getString(it.getColumnIndexOrThrow(COLUMN_CREATOR_ARTICLE))
                    val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY_ARTICLE))

                    val article = Article(id, title, link, description, pubDate, creator, category)
                    articles.add(article)
                } while (it.moveToNext())
            }
        }
        return articles
    }

    override suspend fun deleteArticle(id: Int): Boolean {
        val db =articleDatabase.writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME_ARTICLE, "$COLUMN_ID_ARTICLE = ?", arrayOf(id.toString()))
        return rowsDeleted > 0
    }


    override suspend fun insertNote(note: Note): Long {
      return noteDatabase.insertNote(note)
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteDatabase.getAllNotes()
    }

    override suspend fun updateNote(note: Note): Int {
        return noteDatabase.updateNote(note)
    }

    override suspend fun deleteNote(id: Int): Int {
        return noteDatabase.deleteNote(id)
    }
}