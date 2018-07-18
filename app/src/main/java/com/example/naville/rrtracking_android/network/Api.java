package com.example.naville.rrtracking_android.network;

import com.example.naville.rrtracking_android.model.InstrumentResponse;
import com.example.naville.rrtracking_android.model.Login;
import com.example.naville.rrtracking_android.model.RecoverPasswordResponse;
import com.example.naville.rrtracking_android.model.User;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("rrtracking/controller_webservice/login_usuario")
    Call<Login> login();

    @GET("rrtracking/controller_webservice/esqueci_senha")
    Call<RecoverPasswordResponse> recoverPassWord(@Query("email_usuario") String email);

    @GET("rrtracking/controller_webservice/listar_instrumentos")
    Call<InstrumentResponse> gettingAllInstruments();

}

