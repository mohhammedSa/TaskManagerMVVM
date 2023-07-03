package com.example.taskmanagermvvm.Repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskmanagermvvm.DataModel.DataModel

class Repo : RepoInterface {
    private val mutableData = MutableLiveData<ArrayList<DataModel>>()
    private val list = ArrayList<DataModel>()
    override fun sendDataToViewModel(data: DataModel): LiveData<ArrayList<DataModel>> {
        list.add(data)
        mutableData.postValue(list)
        return mutableData
    }
}