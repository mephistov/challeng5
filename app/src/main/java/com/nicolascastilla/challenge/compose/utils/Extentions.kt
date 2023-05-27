package com.nicolascastilla.challenge.compose.utils


    fun Float.playerFormat():String{
        val intvalue = (this.toInt()/1000)+1
        if(intvalue < 10)
            return "0:0${intvalue}"
        else
            return "0:${intvalue}"
    }
