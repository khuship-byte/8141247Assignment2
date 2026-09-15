package com.example.assignment2

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.data.model.Animal
import com.example.assignment2.ui.dashboard.AnimalAdapter
import com.example.assignment2.ui.dashboard.DashboardState
import com.example.assignment2.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels()

    private lateinit var animalAdapter: AnimalAdapter

    private var allAnimals: List<Animal> = emptyList()

    private var showingFavorites = false

    private lateinit var recyclerViewAnimals: RecyclerView
    private lateinit var tvError: TextView

    private lateinit var navHome: LinearLayout
    private lateinit var navFavorites: LinearLayout

    private lateinit var ivHomeIcon: ImageView
    private lateinit var tvHomeLabel: TextView

    private lateinit var ivFavoriteIcon: ImageView
    private lateinit var tvFavoriteLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        // -------------------------
        // FIND VIEWS
        // -------------------------

        recyclerViewAnimals =
            findViewById(R.id.recyclerViewAnimals)

        val progressBar =
            findViewById<View>(R.id.progressBarDashboard)

        tvError =
            findViewById(R.id.tvDashboardError)

        navHome =
            findViewById(R.id.navHome)

        navFavorites =
            findViewById(R.id.navFavorites)

        ivHomeIcon =
            findViewById(R.id.ivNavHome)

        tvHomeLabel =
            findViewById(R.id.tvNavHomeLabel)

        ivFavoriteIcon =
            findViewById(R.id.ivNavFavorite)

        tvFavoriteLabel =
            findViewById(R.id.tvNavFavoriteLabel)


        // -------------------------
        // ADAPTER
        // -------------------------

        animalAdapter =
            AnimalAdapter { animal ->

                val detailsIntent =
                    Intent(
                        this,
                        DetailsActivity::class.java
                    )

                detailsIntent.putExtra(
                    DetailsActivity.EXTRA_SPECIES,
                    animal.species
                )

                detailsIntent.putExtra(
                    DetailsActivity.EXTRA_SCIENTIFIC_NAME,
                    animal.scientificName
                )

                detailsIntent.putExtra(
                    DetailsActivity.EXTRA_HABITAT,
                    animal.habitat
                )

                detailsIntent.putExtra(
                    DetailsActivity.EXTRA_DIET,
                    animal.diet
                )

                detailsIntent.putExtra(
                    DetailsActivity.EXTRA_STATUS,
                    animal.conservationStatus
                )

                detailsIntent.putExtra(
                    DetailsActivity.EXTRA_LIFESPAN,
                    animal.averageLifespan
                )

                detailsIntent.putExtra(
                    DetailsActivity.EXTRA_DESCRIPTION,
                    animal.description
                )

                startActivity(detailsIntent)
            }


        // -------------------------
        // RECYCLER VIEW
        // -------------------------

        recyclerViewAnimals.layoutManager =
            GridLayoutManager(
                this,
                2
            )

        recyclerViewAnimals.adapter =
            animalAdapter


        // -------------------------
        // KEYPASS
        // -------------------------

        val keypass =
            intent.getStringExtra(
                KEYPASS_EXTRA
            ).orEmpty()

        viewModel.loadDashboard(
            keypass
        )


        // -------------------------
        // OBSERVE DASHBOARD
        // -------------------------

        lifecycleScope.launch {

            repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.dashboardState.collect { state ->

                    when (state) {

                        is DashboardState.Idle -> {

                            progressBar.visibility =
                                View.GONE

                            tvError.visibility =
                                View.GONE
                        }


                        is DashboardState.Loading -> {

                            progressBar.visibility =
                                View.VISIBLE

                            tvError.visibility =
                                View.GONE

                            recyclerViewAnimals.visibility =
                                View.GONE
                        }


                        is DashboardState.Success -> {

                            progressBar.visibility =
                                View.GONE

                            allAnimals =
                                state.animals

                            if (showingFavorites) {

                                showFavoriteAnimals()

                            } else {

                                showAllAnimals()
                            }
                        }


                        is DashboardState.Error -> {

                            progressBar.visibility =
                                View.GONE

                            recyclerViewAnimals.visibility =
                                View.GONE

                            tvError.text =
                                state.message

                            tvError.visibility =
                                View.VISIBLE
                        }
                    }
                }
            }
        }


        // -------------------------
        // HOME
        // -------------------------

        navHome.setOnClickListener {

            showingFavorites =
                false

            showAllAnimals()

            updateNavigationColors()

            if (allAnimals.isNotEmpty()) {

                recyclerViewAnimals.scrollToPosition(
                    0
                )
            }
        }


        // -------------------------
        // FAVORITES
        // -------------------------

        navFavorites.setOnClickListener {

            showingFavorites =
                true

            showFavoriteAnimals()

            updateNavigationColors()
        }


        // Home selected initially

        updateNavigationColors()
    }


    // -------------------------
    // SHOW ALL ANIMALS
    // -------------------------

    private fun showAllAnimals() {

        tvError.visibility =
            View.GONE

        recyclerViewAnimals.visibility =
            View.VISIBLE

        animalAdapter.submitList(
            allAnimals
        )
    }


    // -------------------------
    // SHOW FAVORITES
    // -------------------------

    private fun showFavoriteAnimals() {

        val sharedPreferences =
            getSharedPreferences(
                FAVORITES_PREFERENCES,
                MODE_PRIVATE
            )

        val favoriteAnimals =
            allAnimals.filter { animal ->

                sharedPreferences.getBoolean(
                    "favorite_${animal.species}",
                    false
                )
            }


        if (favoriteAnimals.isEmpty()) {

            recyclerViewAnimals.visibility =
                View.GONE

            tvError.text =
                "No favorite animals yet.\nExplore the wild and save the ones you love."

            tvError.visibility =
                View.VISIBLE

        } else {

            tvError.visibility =
                View.GONE

            recyclerViewAnimals.visibility =
                View.VISIBLE

            animalAdapter.submitList(
                favoriteAnimals
            )

            recyclerViewAnimals.scrollToPosition(
                0
            )
        }
    }


    // -------------------------
    // NAVIGATION COLORS
    // -------------------------

    private fun updateNavigationColors() {

        val activeColor =
            Color.parseColor(
                "#A7F34B"
            )

        val inactiveColor =
            Color.parseColor(
                "#C8D5CF"
            )


        if (showingFavorites) {

            // HOME INACTIVE

            ImageViewCompat.setImageTintList(
                ivHomeIcon,
                ColorStateList.valueOf(
                    inactiveColor
                )
            )

            tvHomeLabel.setTextColor(
                inactiveColor
            )


            // FAVORITES ACTIVE

            ImageViewCompat.setImageTintList(
                ivFavoriteIcon,
                ColorStateList.valueOf(
                    activeColor
                )
            )

            tvFavoriteLabel.setTextColor(
                activeColor
            )

        } else {

            // HOME ACTIVE

            ImageViewCompat.setImageTintList(
                ivHomeIcon,
                ColorStateList.valueOf(
                    activeColor
                )
            )

            tvHomeLabel.setTextColor(
                activeColor
            )


            // FAVORITES INACTIVE

            ImageViewCompat.setImageTintList(
                ivFavoriteIcon,
                ColorStateList.valueOf(
                    inactiveColor
                )
            )

            tvFavoriteLabel.setTextColor(
                inactiveColor
            )
        }
    }


    // -------------------------
    // RETURN FROM DETAILS
    // -------------------------

    override fun onResume() {
        super.onResume()

        if (::animalAdapter.isInitialized) {

            animalAdapter.refreshFavorites()

            if (showingFavorites) {

                showFavoriteAnimals()
            }
        }
    }


    companion object {

        const val KEYPASS_EXTRA =
            "KEYPASS"

        private const val FAVORITES_PREFERENCES =
            "animal_favorites"
    }
}