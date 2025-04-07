package com.example.newspaperapp.ui.screen.newspaperscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newspaperapp.R
import com.example.newspaperapp.ui.component.CustomAlertDialog
import com.example.newspaperapp.viewmodel.NewsPaperViewModel


@Composable
fun SaveNewsDetailScreen(myViewModel: NewsPaperViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        val item by myViewModel.itemArticle.collectAsState()
        val content by myViewModel.content.collectAsState()
        var showDialog by remember { mutableStateOf<Boolean?>(null) } // null ban đầu, true nếu thành công, false nếu thất bại


        if (content.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()

            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Cho phép cuộn toàn bộ màn hình
                    .padding(16.dp)
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Image(
                    painter = rememberAsyncImagePainter(
                        model = myViewModel.getImageFromDescription(item.description),
                        placeholder = painterResource(id = R.drawable.ic_img_place), // Ảnh tạm
                        error = painterResource(id = R.drawable.ic_img_place)
                    ),
                    contentDescription = "Ảnh từ URL",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(224.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))


                item.let {
                    Text(
                        text = it.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 👤 Tác giả & Ngày đăng
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item.let {
                        Text(
                            text = it.creator,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start
                        )
                    }
                    item.let {
                        Text(
                            text = myViewModel.convertDateFormat(it.pubDate),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.End
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))


                // 📜 Nội dung bài báo
                Text(
                    text = content,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Button(
                        onClick = {
                            myViewModel.deleteArticle(item.id)

                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(8.dp), // Bo góc nút
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text(text = "Xóa bài", color = Color.White, fontSize = 18.sp)


                    }


                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(8.dp), // Bo góc nút
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text(text = "Đóng", color = Color.White, fontSize = 18.sp)
                    }


                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_dantri),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Text("Cơ quan của Bộ Nội vụ", fontSize = 14.sp, lineHeight = 20.sp)
                    Text("Tổng biên tập: Phạm Tuấn Anh", fontSize = 14.sp, lineHeight = 20.sp)
                    Text(
                        "Giấy phép hoạt động báo điện tử Dân trí số 411/GP - BTTTT Hà Nội, ngày 31-10-2023",
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        "Địa chỉ tòa soạn: Nhà 48, ngõ 2 Giảng Võ, Cát Linh, Đống Đa, Hà Nội",
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        "Điện thoại: 024-3736-6491. Hotline HN: 0973-567-567",
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        "Văn phòng đại diện miền Nam: 51 Võ Văn Tần, Phường Võ Thị Sáu, Quận 3, TPHCM",
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Text("Hotline TPHCM: 0974-567-567", fontSize = 14.sp, lineHeight = 20.sp)
                    Text("Email: info@dantri.com.vn", fontSize = 14.sp, lineHeight = 20.sp)
                }


            }
        }

        LaunchedEffect(Unit) {
            myViewModel.resultDelete.collect { isDeleted ->
                if (isDeleted) {
                    showDialog = isDeleted
                }

            }
        }
        showDialog?.let { isSuccess ->
            CustomAlertDialog(
                title = "Thông báo",
                text = if (isSuccess) "Xóa thành công" else "Không thể xóa",
                confirmText = "Ok",
                dismissText = "",
                onConfirm = {
                    showDialog = null
                    if (isSuccess) {
                        navController.popBackStack() // Quay lại màn hình trước
                    }
                },
                onDismiss = {
                    showDialog = null
                }
            )
        }

    }
}