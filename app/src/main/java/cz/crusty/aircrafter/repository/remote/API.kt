package cz.crusty.aircrafter.repository.remote

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import cz.crusty.aircrafter.repository.remote.deserialize.StatesDeserializer
import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class API {

    val api by lazy {
        Timber.d("lazy init API")
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val gson = GsonBuilder().run {
            registerTypeAdapter(object : TypeToken<StatesResponse.States>() {}.type, StatesDeserializer())
            create()
        }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://opensky-network.org/api/")
            .client(client)
            .build()

        retrofit.create(APIService::class.java)
    }


    suspend fun getPlanesOverCZE() = api.getPlanes(48.55f, 12.9f, 51.06f, 18.87f)

}