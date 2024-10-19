package co.touchlab.dogify

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service interface for fetching dog breeds and their images.
 */
interface DogService {

    /**
     * Fetches the list of dog breeds.
     */
    @GET("breeds/list")
    suspend fun getBreeds(): NameResult

    /**
     * Fetches a random image for the given dog breed.
     */
    @GET("breed/{breedName}/images/random")
    suspend fun getBreedImage(@Path("breedName") breedName: String): ImageResult

    companion object {
        /**
         * Creates an instance of DogService using Retrofit.
         */
        fun create(): DogService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(DogService::class.java)
        }
    }
}

