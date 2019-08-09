package com.alex44.fcbate.di.modules;

import com.alex44.fcbate.App;
import com.alex44.fcbate.common.model.api.support.ApiSupportUtil;
import com.alex44.fcbate.home.model.api.IHomeSource;
import com.alex44.fcbate.common.model.ISystemInfo;
import com.alex44.fcbate.newsdetail.model.api.INewsDetailSource;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.readystatesoftware.chuck.ChuckInterceptor;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.alex44.fcbate.common.ApiStrings.RETROFIT_BASE_URL;

@Module(includes = {SystemModule.class, AppModule.class})
public class ApiModule {

    @Named("baseUrl")
    @Provides
    public String baseUrlGithub() {
        return RETROFIT_BASE_URL;
    }

    @Singleton
    @Provides
    public Gson gson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    public OkHttpClient okHttpClient(ISystemInfo systemInfo, ChuckInterceptor chuckInterceptor, HttpLoggingInterceptor httpLoggingInterceptor) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(chuckInterceptor);
        return ApiSupportUtil.enableTls12OnPreLollipop(builder, systemInfo).build();
    }

    @Provides
    public ChuckInterceptor chuckInterceptor(App app) {
        return new ChuckInterceptor(app.getApplicationContext());
    }

    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    public Retrofit retrofit(@Named("baseUrl") String baseUrl, Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    @Provides
    public IHomeSource homeSource(Retrofit retrofit) {
        return retrofit.create(IHomeSource.class);
    }

    @Provides
    public INewsDetailSource newsDetailSource(Retrofit retrofit) {
        return retrofit.create(INewsDetailSource.class);
    }

}
