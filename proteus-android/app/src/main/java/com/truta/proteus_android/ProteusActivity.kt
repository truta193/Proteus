package com.truta.proteus_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.model.ScheduleDao
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.model.TaskModel
import com.truta.proteus_android.service.MappingService
import com.truta.proteus_android.service.StorageService
import com.truta.proteus_android.ui.screen.sign_in.SignInScreen
import com.truta.proteus_android.ui.theme.ProteusAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime

@AndroidEntryPoint
class ProteusActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Firebase.firestore.useEmulator("192.168.0.122", 8080)
        //Firebase.auth.useEmulator("192.168.0.122", 9099)

        val db = Firebase.firestore

//        val t1 = TaskModel(
//            "1",
//            "Task 1",
//            color = Color.Red,
//            "T1",
//            LocalDateTime.parse("2022-01-01T00:00:00"),
//            LocalDateTime.parse("2022-01-01T01:00:00"),
//            1,
//            "Location 1",
//            1
//        )
//        val t2 = TaskModel(
//            "2",
//            "Task 2",
//            color = Color.Blue,
//            "T2",
//            LocalDateTime.parse("2022-01-01T01:00:00"),
//            LocalDateTime.parse("2022-01-01T02:00:00"),
//            1,
//            "Location 2",
//            1
//        )
//        val s = ScheduleModel("1", "Schedule 1", listOf(t1, t2), "testUser", true)
//        val mappingService = MappingService()
//        db.collection("schedules").add(mappingService.scheduleModelToDao(s))

        //val mappingService = MappingService()
        //val storageService = StorageService(mappingService)

//        lifecycleScope.launch {
//            val ns = storageService.getSchedule("BSuKGq3BElpoUUGIDwEn")
//            Log.d("ProteusActivity", ns.toString())
//        }

        setContent {
            ProteusAndroidTheme {
                ProteusApp()
            }
        }
    }
}