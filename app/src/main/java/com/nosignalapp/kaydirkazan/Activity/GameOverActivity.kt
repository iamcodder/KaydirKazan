package com.nosignalapp.kaydirkazan.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nosignalapp.kaydirkazan.R

class GameOverActivity : AppCompatActivity() {

    var gelenBundle:Bundle? = null
    var dogruSayisi:Int=0
    var rekor:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        gelenBundle=intent.extras

        if(gelenBundle!=null){

            dogruSayisi = gelenBundle!!.getInt("dogruSayisi")
            rekor = gelenBundle!!.getInt("rekor")
            kontrolEt(dogruSayisi,rekor)
        }
    }

    fun kontrolEt(dogruSayisi:Int,rekor:Int){

        if(dogruSayisi>rekor){

        }

        else{

        }

    }
}
