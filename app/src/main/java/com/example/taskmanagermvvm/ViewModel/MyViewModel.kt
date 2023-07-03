package com.example.taskmanagermvvm.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.taskmanagermvvm.DataModel.DataModel
import com.example.taskmanagermvvm.Repo.Repo
import com.example.taskmanagermvvm.Repo.RepoInterface

class MyViewModel : ViewModel() {
    private val repo: RepoInterface = Repo()
    fun setDataToMutableLiveData(data: DataModel): LiveData<ArrayList<DataModel>> {
        return repo.sendDataToViewModel(data)
    }
}