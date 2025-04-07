package com.example.newspaperapp.ui.screen.notescreen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newspaperapp.viewmodel.NewsPaperViewModel
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun AddNoteScreen(myViewModel: NewsPaperViewModel, navController: NavController, context: Context) {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
            }

            if (selectedCalendar.timeInMillis < calendar.timeInMillis) {
                // Hiển thị thông báo nếu thời gian đã qua
                Toast.makeText(context, "Không thể chọn giờ trong quá khứ!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                myViewModel.noteTime.value =
                    String.format("%02d:%02d", selectedHour, selectedMinute)
            }
        }, currentHour, currentMinute, true
    )

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, selectedDay)
            }

            if (selectedCalendar.timeInMillis < calendar.timeInMillis) {
                // Hiển thị thông báo nếu ngày đã qua
                Toast.makeText(context, "Không thể chọn ngày trong quá khứ!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                myViewModel.noteDate.value =
                    String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            }
        }, currentYear, currentMonth, currentDay
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Thêm Ghi Chú", style = MaterialTheme.typography.headlineLarge)
            OutlinedTextField(
                value = myViewModel.noteTitle.value,
                onValueChange = { myViewModel.noteTitle.value = it },
                label = { Text("Nhập tiêu đề") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = myViewModel.noteContent.value,
                onValueChange = { myViewModel.noteContent.value = it },
                label = { Text("Nhập nội dung") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Chọn ngày")
                }
                Text(
                    myViewModel.noteDate.value,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,


                    )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { timePickerDialog.show() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Chọn giờ")
                }
                Text(
                    myViewModel.noteTime.value,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }



            Button(onClick = {
                var errorMessage = ""
                when {
                    myViewModel.noteTitle.value.isEmpty() -> errorMessage = "Vui lòng nhập tiêu đề!"
                    myViewModel.noteContent.value.isEmpty() -> errorMessage =
                        "Vui lòng nhập nội dung!"

                    myViewModel.noteTime.value == "00:00" -> errorMessage = "Vui lòng chọn giờ!"
                    myViewModel.noteDate.value == "00/00/00" -> errorMessage = "Vui lòng chọn ngày!"
                }
                if (errorMessage.isNotEmpty()) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                } else {
                    myViewModel.addNote(context)


                }
            }) {
                Text("Lưu Ghi Chú")
            }
        }
    }


    val resultAdd by myViewModel.resultAddNote.collectAsState()

    LaunchedEffect(resultAdd) {
        when (resultAdd) {
            true -> {
                Toast.makeText(context, "Ghi chú đã được thêm", Toast.LENGTH_SHORT).show()
                myViewModel.resetResultAdd()
                navController.popBackStack()
                myViewModel.resetFieldAddNote()
            }

            false -> {
                Toast.makeText(context, "Thêm ghi chú thất bại", Toast.LENGTH_SHORT).show()
                myViewModel.resetResultAdd()
                myViewModel.resetFieldAddNote()

            }

            else -> {}
        }

    }


}


