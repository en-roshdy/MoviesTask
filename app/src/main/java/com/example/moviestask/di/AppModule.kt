import com.example.moviesapptask.Constants.Companion.BASE_URL
import com.example.moviesapptask.data.MoviesApiService
import com.example.moviestask.data.data_source.MoviesApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(): MoviesApiService {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .addInterceptor(RetrofitObject.HeaderInterceptor())
            .readTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
//                .followRedirects(false)
//                .followSslRedirects(false)
            .build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofitObject = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient).build()


        return retrofitObject.create(MoviesApiService::class.java)
    }



}