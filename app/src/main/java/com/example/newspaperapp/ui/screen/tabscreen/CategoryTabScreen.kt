package com.example.newspaperapp.ui.screen.tabscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newspaperapp.ui.screen.categoryscreen.BusinessNewsScreen
import com.example.newspaperapp.ui.screen.categoryscreen.EducationNewsScreen
import com.example.newspaperapp.ui.screen.categoryscreen.EntertainmentNewsScreen
import com.example.newspaperapp.ui.screen.categoryscreen.LawNewsScreen
import com.example.newspaperapp.ui.screen.categoryscreen.SportNewScreen
import com.example.newspaperapp.ui.screen.categoryscreen.WorldNewsScreen
import com.example.newspaperapp.viewmodel.NewsPaperViewModel
import com.example.newspaperapp.viewmodel.Title

@Composable
fun HomeScreen(myViewModel: NewsPaperViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Log.d("State","HomeScreen")

        val listItemTab = myViewModel.listItemCategory
        val selected by myViewModel.tabIndexCategory.collectAsState()

        val pagerSate = rememberPagerState {
            listItemTab.size
        }

        LaunchedEffect(selected) {
            pagerSate.animateScrollToPage(selected)
        }
        LaunchedEffect(pagerSate.currentPage, pagerSate.isScrollInProgress) {
            if (!pagerSate.isScrollInProgress) {
                myViewModel.setTabIndexChildScreen(pagerSate.currentPage)
            }
        }
        ScrollableTabRow(
            selectedTabIndex = selected,
            edgePadding = 0.dp,
            modifier = Modifier.padding(2.dp)

        ) {
            listItemTab.forEachIndexed { index, item ->
                Tab(
                    selected = index == selected,
                    onClick = {
                        myViewModel.setTabIndexChildScreen(index)

                    },
                    icon = {
                        Icon(
                            painter = painterResource(
                                if (index == selected) {
                                    item.selected
                                } else item.unSelected
                            ),
                            contentDescription = ""
                        )
                    },
                    text = { Text(text = item.title) }
                )

            }

        }
        HorizontalPager(state = pagerSate, modifier = Modifier
            .fillMaxSize()
            .weight(1f)) {

            when (listItemTab[it].title) {
                Title.WORLD -> {
                    WorldNewsScreen(myViewModel, navController)
                }

                Title.SPORT -> {
                    SportNewScreen(myViewModel, navController)
                }

                Title.EDUCATION -> {
                    EducationNewsScreen(myViewModel, navController)
                }

                Title.BUSINESS -> {
                    BusinessNewsScreen(myViewModel, navController)
                }

                Title.ENTERTAINMENT -> {
                    EntertainmentNewsScreen(myViewModel, navController)
                }

                else -> {
                    LawNewsScreen(myViewModel, navController)
                }
            }

        }

    }


}