package com.example.newspaperapp.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.newspaperapp.R
import kotlin.random.Random

class NotificationWorker(
    context: Context, // context
    workerParams: WorkerParameters // chua cac thong so dau vao (input data) truyen vao worker
) : Worker(context, workerParams) {

    override fun doWork(): Result { // ham duoc goi khi WorkManager thuc hien cong viec
        val title = inputData.getString("title") ?: "Ghi chú"
        val content = inputData.getString("content") ?: "Ban co ghi chu can xem"
        // lay gia tri tu input data

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        /*
        Lay Notification Manager - he thong quan ly thong bao trong Android --> de hien thi 1 thong bao (Notification)
        applicationContext.getSystemService(...) // la context toan cuc cua ung dung khong phuc thuoc vao 1 Activity cu the

        Context.NOTIFICATION_SERVICE
        Đây là hằng số tĩnh (static constant) được định nghĩa trong Context.

        Nó đại diện cho chuỗi "notification" (tên của dịch vụ thông báo).

        Dùng nó để chỉ rõ là bạn muốn lấy Notification Service.
        as NotificationManager :  Mặc định getSystemService() trả về Any?, nên cần ép kiểu (cast) nó về NotificationManager.
        NotificationManager là lớp quản lý tất cả các thông báo (gửi, hủy, tạo channel...).
         */
        val channelId = "note_channel"  // khai bao id cua channel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // kiem tra phien ban he dieu hanh xem co lon hon 8.0 khong
            /*
            Build.VERSION.SDK_INT: là số nguyên biểu thị phiên bản Android đang chạy.
            Build.VERSION_CODES.O: là hằng số tương ứng với Android 8.0 (Oreo), tức là API 26.
             */
            val channel = NotificationChannel(
                channelId, // id cua kenh
                "Ghi chu", // ten kenh
                NotificationManager.IMPORTANCE_HIGH // do quan trong ,ức quan trọng cao, thông báo sẽ hiển thị ngay lập tức, kèm âm thanh, đèn nháy (nếu có).

            )
            notificationManager.createNotificationChannel(channel)
            /*
             notificationManager.createNotificationChannel(channel)
             Gửi yêu cầu tạo kênh thông báo đến hệ thống.
             Nếu kênh đã tồn tại rồi (ID trùng), thì hệ thống sẽ không tạo mới, nhưng cũng không lỗi.
             */
        }

        /*
        Tu Android 8 tro len bat buoc phai co NotificationChannel khi muon hien thi thong bao
         */

        val notification = NotificationCompat.Builder(applicationContext, channelId) // tao doi tuong notification
            .setContentTitle(title) // xet title
            .setContentText(content) // xet content
            .setSmallIcon(R.drawable.ic_launcher_foreground) // xet icon cho thong bao
            .setAutoCancel(true) // tu dong dong thong bao khi nguoi dung an vao
            .build() // hien thi noi dung

        notificationManager.notify(Random.nextInt(), notification)

        return Result.success()
    }
}