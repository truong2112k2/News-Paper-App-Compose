package com.example.newspaperapp.ui.screen.categoryscreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.newspaperapp.data_model.Rss
import com.example.newspaperapp.ui.component.RssArticleItem
import com.example.newspaperapp.viewmodel.NewsPaperViewModel
import com.example.newspaperapp.viewmodel.Title
@Composable
fun BusinessNewsScreen(myViewModel: NewsPaperViewModel, navController: NavController) {

    LaunchedEffect(Unit) {
        myViewModel.fetchNewsPaper(Title.BUSINESS)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val listItem: List<Rss> by myViewModel.listRssItemBusiness.collectAsState()

        if (listItem.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            LazyColumn {
                val rssItemList = listItem
                items(rssItemList) {
                    RssArticleItem(it, navController, myViewModel)
                }
            }
        }


    }
}

@Composable
fun EducationNewsScreen(myViewModel: NewsPaperViewModel, navController: NavController) {
    LaunchedEffect(Unit) {

        myViewModel.fetchNewsPaper(Title.EDUCATION)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val listItem: List<Rss> by myViewModel.listRssItemEnducation.collectAsState()

        if (listItem.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            LazyColumn {
                val rssItemList = listItem
                items(rssItemList) {
                    RssArticleItem(it, navController, myViewModel)

                }
            }
        }
    }

}
@Composable
fun EntertainmentNewsScreen(myViewModel: NewsPaperViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        myViewModel.fetchNewsPaper(Title.ENTERTAINMENT)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val listItem: List<Rss> by myViewModel.listRssItemENTERTAINMENT.collectAsState()

        if (listItem.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            LazyColumn {
                val rssItemList = listItem
                items(rssItemList) {
                    RssArticleItem(it, navController, myViewModel)

                }
            }
        }

    }

}
@Composable
fun LawNewsScreen(myViewModel: NewsPaperViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        myViewModel.fetchNewsPaper(Title.LAW)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val listItem: List<Rss> by myViewModel.listRssItemLAW.collectAsState()

        if (listItem.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            LazyColumn {
                val rssItemList = listItem
                items(rssItemList) {
                    RssArticleItem(it, navController, myViewModel)

                }
            }
        }
    }

}
@Composable
fun SportNewScreen(myViewModel: NewsPaperViewModel, navController: NavController) {

    LaunchedEffect(Unit) {
        myViewModel.fetchNewsPaper(Title.SPORT)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val listItem: List<Rss> by myViewModel.listItemSport.collectAsState()

        if (listItem.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            LazyColumn {
                val rssItemList = listItem
                items(rssItemList) {
                    RssArticleItem(it, navController, myViewModel)

                }
            }
        }
    }

}

@Composable
fun WorldNewsScreen(myViewModel: NewsPaperViewModel, navController: NavController) {

    LaunchedEffect(Unit) {
        myViewModel.fetchNewsPaper(Title.WORLD)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val listItem: List<Rss> by myViewModel.listItemWorld.collectAsState()

        if (listItem.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            LazyColumn {
                val rssItemList = listItem
                items(rssItemList) {
                    RssArticleItem(it, navController, myViewModel)
                }
            }
        }
    }

}







