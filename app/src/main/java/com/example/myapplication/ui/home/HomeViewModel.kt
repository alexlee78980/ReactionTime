package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random

class HomeViewModel : ViewModel() {
    private var time: Long = 0;
    private val _imageX = MutableLiveData<Float>().apply {
        0F
    }
    val imageX: LiveData<Float> = _imageX

    private val _imageY = MutableLiveData<Float>().apply {
        0F
    }
    val imageY: LiveData<Float> = _imageY

    private val _tryOne = MutableLiveData<Int>()
    val tryOne: LiveData<Int> = _tryOne

    private val _tryTwo = MutableLiveData<Int>()
    val tryTwo: LiveData<Int> = _tryTwo

    private val _tryThree = MutableLiveData<Int>()
    val tryThree: LiveData<Int> = _tryThree



    var avg: Int = 0;


    private val _imageSrc = MutableLiveData<Int>().apply {
        R.drawable.ic_baseline_bolt_24
    }
    val imageSrc: LiveData<Int> = _imageSrc
    private val _imageVsb = MutableLiveData<Boolean>().apply {
        true
    }
    val imageVsb: LiveData<Boolean> = _imageVsb

    private val _start = MutableLiveData<Boolean>()
    val start: LiveData<Boolean> = _start

    private val _popup = MutableLiveData<Boolean>()
    val popup: LiveData<Boolean> = _popup


    fun startGame() {
           if(start.value == null) {
            println("ran")
            _start.value = true
            _imageX.value = Random.nextFloat() * 1000
            _imageY.value = Random.nextFloat() * 1000
            Timer().schedule(2000) {
                _imageVsb.postValue(true)
                _imageSrc.postValue(R.drawable.ic_baseline_bolt_24)
                time = System.currentTimeMillis()
            }

    }}
    fun changeLocation(): Long{
        println("ran")
        val timeNow = System.currentTimeMillis()
        _imageVsb.postValue(false)
        _imageX.value = Random.nextFloat()* 1000
        _imageY.value = Random.nextFloat()* 1000
        Timer().schedule(2000) {
            _imageVsb.postValue(true)
            _imageSrc.postValue(R.drawable.ic_baseline_bolt_24)
            time = System.currentTimeMillis()
        }
        if(tryOne.value == null)
            _tryOne.value = (timeNow-time).toInt()
        else if(tryTwo.value == null)
            _tryTwo.value = (timeNow-time).toInt()
        else if(tryThree.value == null) {
            avg = ((timeNow - time).toInt() + tryTwo.value!! + tryOne.value!!)/3
            _tryThree.value = (timeNow - time).toInt()
        }
        return timeNow-time
    }

    fun restart() {
        _tryOne.value = null
        _tryTwo.value = null
        _tryThree.value = null
    }
}