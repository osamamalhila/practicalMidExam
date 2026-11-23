package com.example.practicalmidexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "current_artwork_index";

    private TextView tvPosition;
    private ImageView ivArtwork;
    private TextView tvTitle;
    private TextView tvArtistYear;
    private Button btnPrevious;
    private Button btnNext;

    private List<Artwork> artworkList;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        // Apply window insets so the layout is displayed above system bars (Status bar & Navigation bar)
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Initialize UI components
        tvPosition = findViewById(R.id.tv_artwork_position);
        ivArtwork = findViewById(R.id.iv_artwork);
        View cardArtwork = findViewById(R.id.card_artwork);
        tvTitle = findViewById(R.id.tv_artwork_title);
        tvArtistYear = findViewById(R.id.tv_artist_year);
        btnPrevious = findViewById(R.id.btn_previous);
        btnNext = findViewById(R.id.btn_next);

        // Populate Artwork Collection (Requirement 2: Minimum 6 artworks)
        initArtworkList();

        // Restore state after rotation (Requirement 3: Activity Lifecycle & State Management)
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
        }

        // Display current artwork
        updateUI();

        // Next button click listener (Requirement 2)
        btnNext.setOnClickListener(v -> {
            if (currentIndex < artworkList.size() - 1) {
                currentIndex++;
                updateUI();
            }
        });

        // Previous button click listener (Requirement 2)
        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateUI();
            }
        });

        // Artwork click listener -> Explicit Intent to ArtworkDetailsActivity (Requirement 4)
        View.OnClickListener openDetailsListener = v -> openArtworkDetails();
        ivArtwork.setOnClickListener(openDetailsListener);
        if (cardArtwork != null) {
            cardArtwork.setOnClickListener(openDetailsListener);
        }
    }

    private void initArtworkList() {
        artworkList = new ArrayList<>();
        artworkList.add(new Artwork(
                "Deep Field Stellar Deaths",
                "Smacs Carin",
                "2021",
                R.drawable.artwork_stellar_deaths,
                "https://www.nasa.gov/mission_pages/webb/main/index.html",
                "A high-resolution deep field view showing stellar death nebulae and thousands of distant galaxies in infrared space clarity."
        ));
        artworkList.add(new Artwork(
                "Carina Nebula Cosmic Cliffs",
                "James Webb",
                "2022",
                R.drawable.artwork_carina_nebula,
                "https://www.nasa.gov/image-feature/goddard/2022/nasa-s-webb-reveals-cosmic-cliffs-glittering-landscape-of-star-birth",
                "A dramatic curtain of gas and dust revealing emerging stellar nurseries and newborn stars within NGC 3324."
        ));
        artworkList.add(new Artwork(
                "Pillars of Creation",
                "Hubble Space",
                "1995",
                R.drawable.artwork_pillars_creation,
                "https://hubblesite.org/contents/media/images/2015/01/3471-Image.html",
                "Iconic snapshot of vast elephant trunks of interstellar gas and dust in the Eagle Nebula located 6,500 light-years away."
        ));
        artworkList.add(new Artwork(
                "Southern Ring Nebula",
                "Webb Telescope",
                "2022",
                R.drawable.artwork_southern_ring,
                "https://www.nasa.gov/image-feature/goddard/2022/nasa-s-webb-captures-dying-star-s-final-performance-in-southern-ring-nebula",
                "An expanding shell of gas surrounding a dying star, captured in unprecedented detail across mid-infrared wavelengths."
        ));
        artworkList.add(new Artwork(
                "Stephan's Quintet",
                "Compact Group",
                "2022",
                R.drawable.artwork_stephans_quintet,
                "https://www.nasa.gov/image-feature/goddard/2022/nasa-s-webb-reveals-stephan-s-quintet-a-visual-grouping-of-five-galaxies",
                "A visual grouping of five galaxies exhibiting galactic collisions, shockwaves, and massive star-forming tidal tails."
        ));
        artworkList.add(new Artwork(
                "Hubble Ngc Flat Final",
                "Sonay Billan",
                "2019",
                R.drawable.artwork_hubble_ngc,
                "https://hubblesite.org/contents/media/images/2019/33/4523-Image.html",
                "A sprawling wide-field composite capturing millions of radiant stars and pink ionized gas clouds across NGC star fields."
        ));
    }

    /**
     * Updates the UI elements based on the current artwork index.
     * Handles enabling/disabling of navigation buttons (Requirement 2).
     */
    private void updateUI() {
        if (artworkList == null || artworkList.isEmpty()) {
            return;
        }

        Artwork currentArtwork = artworkList.get(currentIndex);

        // Update position counter (e.g. 1 / 6)
        tvPosition.setText(String.format(Locale.US, "%d / %d", currentIndex + 1, artworkList.size()));

        // Update artwork image
        ivArtwork.setImageResource(currentArtwork.getImageResId());

        // Update artwork title
        tvTitle.setText(currentArtwork.getTitle());

        // Update artist name and creation year: "Smacs Carin  (2021)"
        tvArtistYear.setText(String.format(Locale.US, "%s  (%s)", currentArtwork.getArtist(), currentArtwork.getYear()));

        // Enable/Disable Previous button (disabled on first artwork)
        btnPrevious.setEnabled(currentIndex > 0);

        // Enable/Disable Next button (disabled on last artwork)
        btnNext.setEnabled(currentIndex < artworkList.size() - 1);
    }

    /**
     * Requirement 3: Save the current artwork index during configuration changes (e.g. screen rotation).
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    /**
     * Requirement 4: Explicit Intent to open ArtworkDetailsActivity with artwork details.
     */
    private void openArtworkDetails() {
        Artwork currentArtwork = artworkList.get(currentIndex);
        Intent intent = new Intent(MainActivity.this, ArtworkDetailsActivity.class);
        intent.putExtra(ArtworkDetailsActivity.EXTRA_TITLE, currentArtwork.getTitle());
        intent.putExtra(ArtworkDetailsActivity.EXTRA_ARTIST, currentArtwork.getArtist());
        intent.putExtra(ArtworkDetailsActivity.EXTRA_YEAR, currentArtwork.getYear());
        intent.putExtra(ArtworkDetailsActivity.EXTRA_IMAGE_RES_ID, currentArtwork.getImageResId());
        intent.putExtra(ArtworkDetailsActivity.EXTRA_WEBSITE_URL, currentArtwork.getWebsiteUrl());
        intent.putExtra(ArtworkDetailsActivity.EXTRA_DESCRIPTION, currentArtwork.getDescription());
        startActivity(intent);
    }
}
