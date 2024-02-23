package com.truta.proteus_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.ui.screen.sign_up.SignUpScreen
import com.truta.proteus_android.ui.theme.ProteusAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProteusActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.firestore.useEmulator("192.168.0.122", 8080)
        //Firebase.auth.useEmulator("192.168.0.122", 9099)

        setContent {
            ProteusAndroidTheme {
                SignUpScreen()
            }
        }
    }
}