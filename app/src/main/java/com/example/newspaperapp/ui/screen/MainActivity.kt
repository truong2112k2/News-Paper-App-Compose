package com.example.newspaperapp.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newspaperapp.data.repository.NewsPaperRepositoryImpl
import com.example.newspaperapp.data.database.ArticleDatabase
import com.example.newspaperapp.data.database.NoteDatabase
import com.example.newspaperapp.ui.screen.newspaperscreen.DetailNewsScreen
import com.example.newspaperapp.ui.screen.newspaperscreen.SaveNewsDetailScreen
import com.example.newspaperapp.ui.screen.notescreen.AddNoteScreen
import com.example.newspaperapp.ui.screen.tabscreen.FirstScreen
import com.example.newspaperapp.ui.theme.NewsPaperAppTheme2
import com.example.newspaperapp.viewmodel.NewsPaperViewModel
import com.example.newspaperapp.viewmodel.NewsPaperModelFactory

class MainActivity : ComponentActivity() {
    lateinit var myViewModel: NewsPaperViewModel
    lateinit var articleDatabase: ArticleDatabase
    lateinit var noteDatabase: NoteDatabase
    lateinit var repository: NewsPaperRepositoryImpl

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsPaperAppTheme2 {

                articleDatabase = ArticleDatabase(this)
                noteDatabase = NoteDatabase(this)
                repository = NewsPaperRepositoryImpl(
                    articleDatabase = articleDatabase,
                    noteDatabase = noteDatabase
                )
                myViewModel = viewModel(factory = NewsPaperModelFactory(repository))
                val context = LocalContext.current


                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "first") {
                    composable("first") {
                        FirstScreen(myViewModel, navController)
                    }
                    composable("detail_news") {
                        DetailNewsScreen(myViewModel, navController)
                    }
                    composable("detail_save") {
                        SaveNewsDetailScreen(myViewModel, navController)
                    }
                    composable("insert_note") {
                        AddNoteScreen(myViewModel, navController, context)
                    }
                }


            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {


}


