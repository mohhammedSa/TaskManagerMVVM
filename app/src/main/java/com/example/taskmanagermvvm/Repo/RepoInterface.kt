package com.example.taskmanagermvvm.Repo

import androidx.lifecycle.LiveData
import com.example.taskmanagermvvm.DataModel.DataModel

interface RepoInterface {
    fun sendDataToViewModel(data: DataModel): LiveData<ArrayList<DataModel>>
}