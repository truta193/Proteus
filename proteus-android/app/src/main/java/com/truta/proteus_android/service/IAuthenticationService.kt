package com.truta.proteus_android.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface IAuthenticationService {
    fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener)

    fun removeAuthStateListener(listener: FirebaseAuth.AuthStateListener)

    fun signUp(email: String, password: String, onComplete: (Boolean, String?) -> Unit)

    fun signIn(email: String, password: String, onComplete: (Boolean, String?) -> Unit)

    fun signOut()

    fun getCurrentUser(): FirebaseUser?

    fun isUserSignedIn(onComplete: (Boolean) -> Unit)
}