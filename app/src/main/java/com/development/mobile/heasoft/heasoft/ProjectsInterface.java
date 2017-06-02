package com.development.mobile.heasoft.heasoft;

import retrofit2.Call;
import retrofit2.http.GET;

interface ProjectsInterface {
    @GET("/heasoft/getProjects.php")
    Call<Projects> getProjects();

}
