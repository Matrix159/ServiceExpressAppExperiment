package com.serviceexpress.serviceexpressapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.serviceexpress.serviceexpressapp.R
import com.serviceexpress.serviceexpressapp.api.SEIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val seiService by lazy {
        SEIService.create()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchButton.setOnClickListener {
            beginSearch(wikiText.text.toString())
        }
        cameraButton.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }

    private fun beginSearch(srsearch: String) {
        disposable = seiService.hitCountCheck("query", "json", "search", srsearch)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> results.text = "Occurrences found: " +result.query.searchinfo.totalhits.toString()},
                                { error -> Toast.makeText(this, error.message, Toast.LENGTH_LONG) }
                        )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}
