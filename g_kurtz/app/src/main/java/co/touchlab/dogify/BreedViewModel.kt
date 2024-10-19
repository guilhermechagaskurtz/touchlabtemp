package co.touchlab.dogify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * ViewModel is responsible for fetching dog breed names and their respective images from the API.
 * Updates the UI incrementally as each breed is loaded.
 */
class BreedViewModel : ViewModel() {

    private val _breedList = MutableLiveData<List<Breed>>()
    val breedList: LiveData<List<Breed>> get() = _breedList

    private val currentBreeds = mutableListOf<Breed>()

    /**
     * Fetches the list of dog breeds and their images from the API, updating the LiveData incrementally.
     */
    suspend fun getBreeds() {
        withContext(Dispatchers.IO) {
            // Fetch list of breed names from the API
            val breedNames = DogService.create().getBreeds().message

            // For each breed, fetch the image URL and update the LiveData list incrementally
            breedNames.forEach { breedName ->
                val imageUrl = DogService.create().getBreedImage(breedName).message
                val breed = Breed(name = breedName, imageUrl = imageUrl)

                // Add the breed to the current list and post an update
                currentBreeds.add(breed)
                _breedList.postValue(currentBreeds.toList())
            }
        }
    }
}
