package com.lakshita.mycodetest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lakshita.mycodetest.R

class MainActivity : AppCompatActivity() {

    val appComponent by lazy {
        (application as AlbumListApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}