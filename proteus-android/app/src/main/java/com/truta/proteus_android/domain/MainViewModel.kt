package com.truta.proteus_android.domain

import androidx.lifecycle.ViewModel
import com.truta.proteus_android.service.IAuthenticationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationService: IAuthenticationService
) : ViewModel() {

}