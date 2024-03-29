package com.truta.proteus_android.service

import com.truta.proteus_android.model.UserModel
import kotlinx.coroutines.flow.Flow

interface IAuthenticationService {
    val currentUser: Flow<UserModel?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun signIn(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun deleteAccount()

}