package com.example.ktool.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TestViewModel: ViewModel() {

    fun test() {
        viewModelScope.launch {

        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }
}