package co.touchlab.dogify

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import co.touchlab.dogify.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

/**
 * MainActivity is the entry point of the app, where a list of dog breeds is fetched and displayed.
 * We use a ViewModel and Coroutines for async data fetching and updating UI incrementally.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val breedAdapter = BreedAdapter()
    private val viewModel: BreedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView with a grid layout and assign the adapter
        setupRecyclerView()

        // Show progress bar while loading data
        binding.progressBar.visibility = View.VISIBLE

        // Launch a coroutine to fetch the dog breeds instead of AsyncTask
        lifecycleScope.launch {
            viewModel.getBreeds()
        }

        // Observe breedlist and update the adapter when something changes
        viewModel.breedList.observe(this) { breeds ->
            breedAdapter.submitList(breeds)
            binding.progressBar.visibility = View.GONE
        }
    }

    /**
     * Configures the RecyclerView with a grid layout and sets the adapter.
     */
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = breedAdapter
        }
    }
}
