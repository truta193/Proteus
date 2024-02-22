package com.truta.proteus_android.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import com.truta.proteus_android.domain.service.IAuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authenticationService: IAuthenticationService
) : ViewModel() {

}