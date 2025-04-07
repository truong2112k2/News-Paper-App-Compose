package com.example.newspaperapp.ui.screen.newspaperscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newspaperapp.ui.component.DatabaseArticleItem
import com.example.newspaperapp.viewmodel.NewsPaperViewModel

@Composable
fun FavoriteScreen(myViewModel: NewsPaperViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        val listArticle by myViewModel.listArticle.collectAsState()
        LaunchedEffect(Unit) {
            myViewModel.getListArticle()
        }
        Text(
            "Tin Đã Lưu",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        if (listArticle.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Bạn chưa lưu tin nào!")
            }
        } else {
            LazyColumn {
                items(listArticle) {
                    DatabaseArticleItem(it, navController, myViewModel)
                }
            }
        }

    }
}