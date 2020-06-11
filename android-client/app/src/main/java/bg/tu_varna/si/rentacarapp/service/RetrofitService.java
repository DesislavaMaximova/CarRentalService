package bg.tu_varna.si.rentacarapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import bg.tu_varna.si.rentacarapp.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request newRequest = request.newBuilder()
                            .header("Authorization", "Bearer " + JwtHandler.getJwt()).build();
                    return chain.proceed(newRequest);
                }
            }).build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BACKEND_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                            .create()
            ))
            .client(okHttpClient)
            .build();

    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
