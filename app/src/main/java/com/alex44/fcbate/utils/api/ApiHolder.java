package com.alex44.fcbate.utils.api;

import com.alex44.fcbate.App;
import com.alex44.fcbate.utils.api.support.ApiSupportUtil;
import com.alex44.fcbate.utils.ui.SystemInfo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.alex44.fcbate.utils.model.ApiStrings.RETROFIT_BASE_URL;

public class ApiHolder {

    public static volatile ApiHolder instance;

    private SystemInfo systemInfo;

    @Getter
    private Retrofit retrofit;

    public static ApiHolder getInstance() {
        ApiHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (ApiHolder.class) {
                localInstance = instance;       //double check
                if (localInstance == null) {
                    instance = localInstance = new ApiHolder();
                }
            }
        }
        return localInstance;
    }

    private ApiHolder() {}

    public void init() {

        systemInfo = new SystemInfo();

        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        final OkHttpClient client = buildClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(RETROFIT_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    private OkHttpClient buildClient() {
        //interceptors
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        final ChuckInterceptor chuckInterceptor = new ChuckInterceptor(App.getInstance().getApplicationContext());

        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(chuckInterceptor);

        return ApiSupportUtil.enableTls12OnPreLollipop(builder, systemInfo).build();
    }

}
