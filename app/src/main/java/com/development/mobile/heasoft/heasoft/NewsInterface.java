package com.development.mobile.heasoft.heasoft;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface NewsInterface {
    @GET("/anton/getNews.php")
    Call<Example> getNews();
}
