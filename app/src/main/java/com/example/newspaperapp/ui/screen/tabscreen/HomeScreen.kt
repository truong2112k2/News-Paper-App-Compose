package com.example.newspaperapp.ui.screen.tabscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newspaperapp.ui.screen.newspaperscreen.FavoriteScreen
import com.example.newspaperapp.ui.screen.notescreen.NoteScreen
import com.example.newspaperapp.viewmodel.NewsPaperViewModel
import com.example.newspaperapp.viewmodel.Title

@Composable
fun FirstScreen(myViewModel: NewsPaperViewModel, navController: NavController) {

       Log.d("State","FirstScreen")
    val listItemTab = myViewModel.listItemTab
    val selectedTab by myViewModel.tabIndexTab.collectAsState()

    val pagerState = rememberPagerState {
        listItemTab.size
    }
    LaunchedEffect(selectedTab) {
        pagerState.animateScrollToPage(selectedTab)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            myViewModel.setTabIndexHomeScreen(pagerState.currentPage)

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),

        ) {
        TabRow(
            selectedTabIndex = selectedTab, modifier = Modifier.padding(2.dp)

        ) {
            listItemTab.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTab,
                    onClick = {
                        myViewModel.setTabIndexHomeScreen(index)
                    },
                    icon =
                    {
                        Icon(
                            painter = painterResource(
                                if (index == selectedTab) {
                                    item.selected
                                } else item.unSelected
                            ),
                            contentDescription = ""
                        )
                    },
                    text = {
                        Text(text = item.title)
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.background),
        ) { index ->
            if (listItemTab[index].title == Title.HOME) {
                HomeScreen(myViewModel, navController)
            } else if (listItemTab[index].title == Title.SAVE) {
                FavoriteScreen(myViewModel, navController)
            } else {
                NoteScreen(myViewModel, navController)
            }
        }
    }
}