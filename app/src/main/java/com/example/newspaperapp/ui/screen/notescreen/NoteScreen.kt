package com.example.newspaperapp.ui.screen.notescreen

import android.content.Context
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newspaperapp.data_model.Note
import com.example.newspaperapp.viewmodel.NewsPaperViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(myViewModel: NewsPaperViewModel, navController: NavController) {
    val listNote by myViewModel.listNote.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        myViewModel.getAllNote()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CenterAlignedTopAppBar(
            title = { Text("Ghi Chú Của Bạn") },
            navigationIcon = {},
            actions = {
                IconButton(onClick = {
                    navController.navigate("insert_note")

                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }
        )
        if (listNote.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Chưa có ghi chú nào!")
            }
        } else {
            LazyColumn {
                items(listNote) { noteItem ->
                    NoteItemView(noteItem, viewModel = myViewModel, context)
                }
            }
        }


    }


}

@Composable
fun NoteItemView(note: Note, viewModel: NewsPaperViewModel, context: Context) {
    // State to toggle title and content visibility
    var isContentExpanded by remember { mutableStateOf(false) }
    var isTitleExpanded by remember { mutableStateOf(false) }

// Card UI
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title + Delete Button Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title Text
                Text(
                    text = if (isTitleExpanded || note.title.length <= 30) {
                        note.title
                    } else {
                        "${note.title.take(30)}..."
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                // Xem thêm / Đóng cho Title nếu dài
                if (note.title.length > 30) {
                    Text(
                        text = if (isTitleExpanded) "Đóng" else "Xem thêm",
                        color = if (isTitleExpanded) Color.Red else Color.Blue,
                        modifier = Modifier
                            .clickable { isTitleExpanded = !isTitleExpanded }
                            .padding(end = 8.dp)
                    )
                }

                // Delete Icon
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Xóa ghi chú",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            viewModel.deleteNote(note.id)
                        }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Content Text
            Text(
                text = if (isContentExpanded || note.content.length <= 100) {
                    note.content
                } else {
                    "${note.content.take(100)}..."
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Show Xem thêm / Đóng cho content
            if (note.content.length > 100) {
                Text(
                    text = if (isContentExpanded) "Đóng" else "Xem thêm",
                    color = if (isContentExpanded) Color.Red else Color.Blue,
                    modifier = Modifier
                        .clickable { isContentExpanded = !isContentExpanded }
                        .padding(bottom = 8.dp)
                )
            }

            // Time and Date Row
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = note.time,
                    modifier = Modifier.padding(end = 3.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = note.date,
                    modifier = Modifier.padding(start = 3.dp)
                )
            }
        }
    }


}
