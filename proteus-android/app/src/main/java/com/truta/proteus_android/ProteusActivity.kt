package com.truta.proteus_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.ui.theme.ProteusAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProteusActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
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
//        val s = ScheduleModel("", "Schedule 2", emptyList(), "NLXU29pHHoNpJ0cmzxKHl9Ct0jI3", true)
//        val mappingService = MappingService()
//        db.collection("schedules").add(mappingService.scheduleModelToDao(s))
//        val authenticationService = AuthenticationService()
//        val mappingService = MappingService()
//        val scheduleRepository = ScheduleRepository(authenticationService, mappingService)
//        scheduleRepository.addSchedule(s)
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