package com.serviceexpress.serviceexpressapp

import com.serviceexpress.serviceexpressapp.model.ExampleData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit

/**
 * Interface used to access the Service Express API
 * @author: Josh Eldridge
 */
interface SEService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<ExampleData>>

    // Similar to static vars and functions in Java
    companion object {
        val INSTANCE = Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .build()
                .create(SEService::class.java)!!
    }
}
