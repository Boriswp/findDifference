package com.immo.findTheDifferences.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Streaming
import java.util.*


interface ApiInterface {
    @Streaming
    @GET(EndPoints.TXT_DATA)
    suspend fun downloadTxtFile(): Response<ResponseBody>
}
