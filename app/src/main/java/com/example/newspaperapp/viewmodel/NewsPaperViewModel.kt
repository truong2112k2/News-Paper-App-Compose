package com.example.newspaperapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newspaperapp.R
import com.example.newspaperapp.data.repository.NewsPaperRepositoryImpl
import com.example.newspaperapp.data_model.Article
import com.example.newspaperapp.data_model.Note
import com.example.newspaperapp.data_model.Rss
import com.example.newspaperapp.data_model.Tab
import com.example.newspaperapp.workmanager.NotificationWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class NewsPaperViewModel(private val repository: NewsPaperRepositoryImpl) : ViewModel() {


    private val _listRssItemWorld = MutableStateFlow<List<Rss>>(emptyList())
    val listItemWorld: StateFlow<List<Rss>> = _listRssItemWorld

    private val _listRssItemSport = MutableStateFlow<List<Rss>>(emptyList())
    val listItemSport: StateFlow<List<Rss>> = _listRssItemSport

    private val _listRssItemEndu = MutableStateFlow<List<Rss>>(emptyList())
    val listRssItemEnducation: StateFlow<List<Rss>> = _listRssItemEndu

    private val _listRssItemBusiness = MutableStateFlow<List<Rss>>(emptyList())
    val listRssItemBusiness: StateFlow<List<Rss>> = _listRssItemBusiness

    private val _listRssItemENTERTAINMENT = MutableStateFlow<List<Rss>>(emptyList())
    val listRssItemENTERTAINMENT: StateFlow<List<Rss>> = _listRssItemENTERTAINMENT

    private val _listRssItemLAW = MutableStateFlow<List<Rss>>(emptyList())
    val listRssItemLAW: StateFlow<List<Rss>> = _listRssItemLAW

    private val sampleItem = Rss("null", "null", "null", "null", "null", "null")
    private val _itemRss = MutableStateFlow<Rss>(sampleItem)
    val itemRss: StateFlow<Rss> = _itemRss

    private val sampleItem2 = Article(0, "null", "null", "null", "null", "null", "null")
    private val _itemArticle = MutableStateFlow<Article>(sampleItem2)
    val itemArticle: StateFlow<Article> = _itemArticle

    private val _content = MutableStateFlow<String>("")
    val content: StateFlow<String> = _content

    private val _tabIndexCategory = MutableStateFlow<Int>(0)
    val tabIndexCategory: StateFlow<Int> = _tabIndexCategory

    private val _tabIndexTab = MutableStateFlow<Int>(0)
    val tabIndexTab: StateFlow<Int> = _tabIndexTab


    val listItemTab = listOf(
        Tab(
            title = Title.HOME,
            selected = R.drawable.ic_home_fill,
            unSelected = R.drawable.ic_home_outline
        ),
        Tab(
            title = Title.SAVE,
            selected = R.drawable.ic_love_fill,
            unSelected = R.drawable.ic_love_outline
        ),
        Tab(
            title = Title.NOTE,
            selected = R.drawable.icon_save_fill,
            unSelected = R.drawable.ic_save_outline
        ),
    )

    val listItemCategory = listOf(
        Tab(
            title = Title.WORLD,
            selected = R.drawable.ic_world_fill,
            unSelected = R.drawable.ic_world_outline
        ),
        Tab(
            title = Title.SPORT,
            selected = R.drawable.ic_volleyball_fill,
            unSelected = R.drawable.ic_volleyball_outline
        ),
        Tab(
            title = Title.EDUCATION,
            selected = R.drawable.ic_education_fill,
            unSelected = R.drawable.ic_education_outline
        ),
        Tab(
            title = Title.BUSINESS,
            selected = R.drawable.ic_business_fill,
            unSelected = R.drawable.ic_business_outline
        ),
        Tab(
            title = Title.ENTERTAINMENT,
            selected = R.drawable.ic_game_fill,
            unSelected = R.drawable.ic_game_outline
        ),
        Tab(
            title = Title.LAW,
            selected = R.drawable.ic_law_fill,
            unSelected = R.drawable.ic_law_outline
        ),
    )

    fun fetchNewsPaper(title: String) {
        viewModelScope.launch {
            when (title) {

                Title.WORLD -> {
                    _listRssItemWorld.value = repository.loadRssFeedData(Url.WORLD)
                }

                Title.SPORT -> {
                    _listRssItemSport.value = repository.loadRssFeedData(Url.SPORT)
                }

                Title.EDUCATION -> {
                    _listRssItemEndu.value = repository.loadRssFeedData(Url.ENDUCATION)
                }

                Title.BUSINESS -> {
                    _listRssItemBusiness.value = repository.loadRssFeedData(Url.BUSINESS)
                }

                Title.LAW -> {
                    _listRssItemLAW.value = repository.loadRssFeedData(Url.LAW)
                }

                Title.ENTERTAINMENT -> {
                    _listRssItemENTERTAINMENT.value = repository.loadRssFeedData(Url.ENTERTAINMENT)
                }
            }
        }
    }

    fun getContentFromLink(link: String) {
        viewModelScope.launch {
            val string = repository.getContentFromHTML(link)
            _content.value = string
        }
    }

    fun getImageFromDescription(description: String): String? {
        val regex = "<img src='(.*?)'".toRegex()
        return regex.find(description)?.groupValues?.get(1)
    }

    fun setItemRssSelected(item: Rss) {
        _itemRss.value = item
    }

    fun setItemArticleSelected(item: Article) {
        _itemArticle.value = item
    }

    fun setTabIndexChildScreen(index: Int) {
        _tabIndexCategory.value = index
    }

    fun setTabIndexHomeScreen(index: Int) {
        _tabIndexTab.value = index
    }

    fun convertDateFormat(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: return "Invalid Date")
        } catch (e: Exception) {
            "Invalid Date"
        }
    }


    private val _insert = MutableStateFlow<Long>(0)
    val insert: StateFlow<Long> = _insert

    private val _listArticle = MutableStateFlow<List<Article>>(emptyList())
    val listArticle: StateFlow<List<Article>> = _listArticle


    fun insertArticle(rssItem: Rss): Long {
        viewModelScope.launch {
            _insert.value = repository.insertArticle(rssItem)

        }
        return insert.value
    }

    fun getListArticle() {
        viewModelScope.launch {
            _listArticle.value = repository.getAllArticles()
        }
    }

    private val _resultDelete = MutableSharedFlow<Boolean>()
    val resultDelete: SharedFlow<Boolean> = _resultDelete

    fun deleteArticle(idArticle: Int) {
        viewModelScope.launch {
            val isDelete = repository.deleteArticle(idArticle)
            _resultDelete.emit(isDelete)
        }

    }


    var noteTitle = mutableStateOf("")
    var noteContent = mutableStateOf("")
    var noteTime = mutableStateOf("00:00")
    var noteDate = mutableStateOf("00/00/00")

    private val _resultAddNote = MutableStateFlow<Boolean?>(null)
    val resultAddNote: StateFlow<Boolean?> get() = _resultAddNote


    // note database funtion

    fun addNote(context: Context) {
        // Reset thông báo lỗi mỗi khi hàm được gọi
        viewModelScope.launch(Dispatchers.IO) {
            try {

                // Nếu không có lỗi, lưu ghi chú
                val item = Note(
                    title = noteTitle.value,
                    content = noteContent.value,
                    time = noteTime.value,
                    date = noteDate.value
                )
                val addNote = insertNote(item)
                if (addNote == (-1L).toInt()) {
                    // Thêm không thành công
                    _resultAddNote.value = false
                    Log.d("Insert", "Không thể thêm ghi chú.")
                } else {
                    // Thêm thành công
                    _resultAddNote.value = true
                    scheduleNoteReminder(context = context, item)

                }


            } catch (e: Exception) {

                Log.d("ERROR", "ERROR INSERT NOTE ${e.toString()}")
            }
        }
    }


    private val _listNote = MutableStateFlow<List<Note>>(emptyList())
    val listNote: StateFlow<List<Note>> = _listNote

    fun getAllNote() {
        viewModelScope.launch {
            _listNote.value = repository.getAllNotes()
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {

            val deleteNote = deleteNoteDatabase(id)

            if (deleteNote == 0) {

                // Thêm không thành công

                Log.d("Delete", "Không thể xóa ghi chú.")
            } else {

                Log.d("Delete", "Xóa thành công")
                getAllNote()

            }
        }
    }


    private suspend fun insertNote(item: Note): Int {
        return repository.insertNote(item).toInt()
    }

    private suspend fun deleteNoteDatabase(id: Int): Int {
        return repository.deleteNote(id)
    }

    fun resetResultAdd() {
        _resultAddNote.value = null
    }

    fun resetFieldAddNote() {
        noteTitle.value = ""
        noteContent.value = ""
        noteTime.value = "00:00"
        noteDate.value = "00/00/00"
    }


    private fun scheduleNoteReminder(context: Context, noteItem: Note) {
        Log.d("scheduleNoteReminder", "Add time thanh cong")
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val triggerTime = sdf.parse("${noteItem.date} ${noteItem.time}")?.time ?: return

        val delay = triggerTime - System.currentTimeMillis()
        if (delay > 0) {
            val data = Data.Builder()
                .putString("title", noteItem.title)
                .putString("content", noteItem.content)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(data)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}