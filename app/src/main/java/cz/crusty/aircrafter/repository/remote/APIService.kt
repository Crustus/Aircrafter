package cz.crusty.aircrafter.repository.remote

import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("states/all")
    suspend fun getPlanes(
        @Query("lamin") lamin: Float,
        @Query("lomin") lomin: Float,
        @Query("lamax") lamax: Float,
        @Query("lomax") lomax: Float,
    ): Response<StatesResponse>

}