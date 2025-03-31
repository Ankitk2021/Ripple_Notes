package com.ankitkumar.project.ripplenotes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val items = MutableLiveData<MutableList<NoteModal>>()
}
