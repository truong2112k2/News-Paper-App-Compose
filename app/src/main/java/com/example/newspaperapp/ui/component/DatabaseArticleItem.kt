package com.example.newspaperapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newspaperapp.R
import com.example.newspaperapp.data_model.Article
import com.example.newspaperapp.viewmodel.NewsPaperViewModel

@Composable
fun DatabaseArticleItem(
    item: Article,
    navController: NavController,
    myViewModel: NewsPaperViewModel

){
    val changeScreen = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp), // Bo góc
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp), // Độ cao của shadow
        onClick = {changeScreen.value = true}

    ) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = myViewModel.getImageFromDescription(item.description),
                    placeholder = painterResource(id = R.drawable.ic_img_place), // Ảnh tạm
                    error = painterResource(id = R.drawable.ic_img_place) // Ảnh lỗi nếu URL sai
                ),
                contentDescription = "Ảnh từ URL",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = item.title,
                maxLines = 1, // Giới hạn số dòng hiển thị
                overflow = TextOverflow.Ellipsis, // Thêm "..." khi quá dài
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            if(changeScreen.value){
                LaunchedEffect(Unit) {
                    navController.navigate("detail_save")
                    myViewModel.setItemArticleSelected(item)

                }
                myViewModel.getContentFromLink(item.link)

            }

        }


    }



}