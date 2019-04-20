package com.patronusstudio.kaydirkazan.Activity

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.patronusstudio.kaydirkazan.R
import kotlinx.android.synthetic.main.activity_uygulama_hakkinda.*

class UygulamaHakkinda : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uygulama_hakkinda)

        val animation_drawable: AnimationDrawable = scroolview.background as AnimationDrawable
        animation_drawable.setEnterFadeDuration(1000)
        animation_drawable.setExitFadeDuration(2000)
        animation_drawable.start()
    }
}
