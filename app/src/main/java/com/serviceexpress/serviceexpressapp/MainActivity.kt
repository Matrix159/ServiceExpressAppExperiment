package com.serviceexpress.serviceexpressapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Access the companion object as it's statically bound to the interface
        SEService.INSTANCE.listRepos("TestString")

        // Show an example using RxJava to show how it's easier with callbacks that way
    }
}
