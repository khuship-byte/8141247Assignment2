package com.example.assignment2

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.example.assignment2.util.AnimalImageMapper
import com.example.assignment2.util.AnimalInfoProvider
import com.google.android.material.button.MaterialButton

class DetailsActivity : AppCompatActivity() {

    private var isExtraContentVisible = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // -------------------------
        // FIND VIEWS
        // -------------------------

        val ivAnimal =
            findViewById<ImageView>(R.id.ivDetailAnimal)

        val tvSpecies =
            findViewById<TextView>(R.id.tvDetailSpecies)

        val tvScientificName =
            findViewById<TextView>(R.id.tvDetailScientificName)

        val tvStatus =
            findViewById<TextView>(R.id.tvDetailStatus)

        val tvHabitat =
            findViewById<TextView>(R.id.tvDetailHabitat)

        val tvDiet =
            findViewById<TextView>(R.id.tvDetailDiet)

        val tvLifespan =
            findViewById<TextView>(R.id.tvDetailLifespan)

        val tvConservation =
            findViewById<TextView>(R.id.tvDetailConservation)

        val tvDescription =
            findViewById<TextView>(R.id.tvDetailDescription)

        val tvDidYouKnow =
            findViewById<TextView>(R.id.tvDidYouKnow)

        val tvMoreAbout =
            findViewById<TextView>(R.id.tvMoreAbout)

        val btnBack =
            findViewById<View>(R.id.btnBack)

        val btnDiscoverMore =
            findViewById<MaterialButton>(R.id.btnDiscoverMore)

        val layoutExtraContent =
            findViewById<LinearLayout>(R.id.layoutExtraContent)

        val navHome =
            findViewById<LinearLayout>(R.id.navHome)

        val navFavorite =
            findViewById<LinearLayout>(R.id.navFavorite)

        val ivFavoriteIcon =
            findViewById<ImageView>(R.id.ivFavoriteIcon)

        val tvFavoriteLabel =
            findViewById<TextView>(R.id.tvFavoriteLabel)


        // -------------------------
        // RECEIVE ANIMAL DATA
        // -------------------------

        val species =
            intent.getStringExtra(EXTRA_SPECIES).orEmpty()

        val scientificName =
            intent.getStringExtra(EXTRA_SCIENTIFIC_NAME).orEmpty()

        val habitat =
            intent.getStringExtra(EXTRA_HABITAT).orEmpty()

        val diet =
            intent.getStringExtra(EXTRA_DIET).orEmpty()

        val status =
            intent.getStringExtra(EXTRA_STATUS).orEmpty()

        val lifespan =
            intent.getIntExtra(EXTRA_LIFESPAN, 0)

        val description =
            intent.getStringExtra(EXTRA_DESCRIPTION).orEmpty()


        // -------------------------
        // DISPLAY API DATA
        // -------------------------

        tvSpecies.text =
            species

        tvScientificName.text =
            scientificName

        tvStatus.text =
            status.uppercase()

        tvHabitat.text =
            habitat

        tvDiet.text =
            diet

        tvLifespan.text =
            "$lifespan years"

        tvConservation.text =
            status

        tvDescription.text =
            description


        // -------------------------
        // DISPLAY LOCAL IMAGE
        // -------------------------

        ivAnimal.setImageResource(
            AnimalImageMapper.getImageResource(
                species
            )
        )


        // -------------------------
        // DISPLAY EXTRA INFO
        // -------------------------

        val extraInfo =
            AnimalInfoProvider.getExtraInfo(
                species
            )

        tvDidYouKnow.text =
            extraInfo.didYouKnow

        tvMoreAbout.text =
            extraInfo.moreAbout


        // -------------------------
        // BACK BUTTON
        // -------------------------

        btnBack.setOnClickListener {

            finish()
        }


        // -------------------------
        // DISCOVER MORE
        // -------------------------

        btnDiscoverMore.setOnClickListener {

            isExtraContentVisible =
                !isExtraContentVisible

            if (isExtraContentVisible) {

                layoutExtraContent.visibility =
                    View.VISIBLE

                btnDiscoverMore.text =
                    "SHOW LESS  ↑"

            } else {

                layoutExtraContent.visibility =
                    View.GONE

                btnDiscoverMore.text =
                    "DISCOVER MORE  ↓"
            }
        }


        // -------------------------
        // FAVORITES
        // -------------------------

        sharedPreferences =
            getSharedPreferences(
                FAVORITES_PREFERENCES,
                MODE_PRIVATE
            )

        val favoriteKey =
            "favorite_$species"

        var isFavorite =
            sharedPreferences.getBoolean(
                favoriteKey,
                false
            )

        updateFavoriteDisplay(
            isFavorite,
            ivFavoriteIcon,
            tvFavoriteLabel
        )


        // -------------------------
        // FAVORITE BUTTON
        // -------------------------

        navFavorite.setOnClickListener {

            isFavorite =
                !isFavorite

            sharedPreferences
                .edit()
                .putBoolean(
                    favoriteKey,
                    isFavorite
                )
                .apply()

            updateFavoriteDisplay(
                isFavorite,
                ivFavoriteIcon,
                tvFavoriteLabel
            )
        }


        // -------------------------
        // HOME BUTTON
        // -------------------------

        navHome.setOnClickListener {

            val homeIntent =
                Intent(
                    this,
                    DashboardActivity::class.java
                )

            homeIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP

            startActivity(
                homeIntent
            )

            finish()
        }

        // Profile remains display-only.
    }


    // -------------------------
    // UPDATE FAVORITE DISPLAY
    // -------------------------

    private fun updateFavoriteDisplay(
        isFavorite: Boolean,
        icon: ImageView,
        label: TextView
    ) {

        if (isFavorite) {

            icon.setImageResource(
                R.drawable.ic_favorite
            )

            ImageViewCompat.setImageTintList(
                icon,
                ColorStateList.valueOf(
                    Color.parseColor(
                        "#FF4B55"
                    )
                )
            )

            label.text =
                "Favorited"

            label.setTextColor(
                Color.parseColor(
                    "#FF4B55"
                )
            )

        } else {

            icon.setImageResource(
                R.drawable.ic_favorite
            )

            ImageViewCompat.setImageTintList(
                icon,
                ColorStateList.valueOf(
                    Color.parseColor(
                        "#C8D5CF"
                    )
                )
            )

            label.text =
                "Favorite"

            label.setTextColor(
                Color.parseColor(
                    "#C8D5CF"
                )
            )
        }
    }


    companion object {

        const val EXTRA_SPECIES =
            "SPECIES"

        const val EXTRA_SCIENTIFIC_NAME =
            "SCIENTIFIC_NAME"

        const val EXTRA_HABITAT =
            "HABITAT"

        const val EXTRA_DIET =
            "DIET"

        const val EXTRA_STATUS =
            "STATUS"

        const val EXTRA_LIFESPAN =
            "LIFESPAN"

        const val EXTRA_DESCRIPTION =
            "DESCRIPTION"

        private const val FAVORITES_PREFERENCES =
            "animal_favorites"
    }
}