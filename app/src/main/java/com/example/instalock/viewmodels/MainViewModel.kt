package com.example.instalock.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.instalock.data.Repository

abstract class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = Repository()

    protected var _succes: MutableLiveData<Boolean> = MutableLiveData()
    val succes: LiveData<Boolean>
        get() = _succes

    protected var _failed: MutableLiveData<String> = MutableLiveData()
    val failed: LiveData<String>
        get() = _failed
}