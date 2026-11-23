package com.example.practicalmidexam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ArtworkDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "extra_artwork_title";
    public static final String EXTRA_ARTIST = "extra_artwork_artist";
    public static final String EXTRA_YEAR = "extra_artwork_year";
    public static final String EXTRA_IMAGE_RES_ID = "extra_artwork_image_res_id";
    public static final String EXTRA_WEBSITE_URL = "extra_artwork_website_url";
    public static final String EXTRA_DESCRIPTION = "extra_artwork_description";

    private String websiteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_artwork_details);

        View detailsRoot = findViewById(R.id.details_root);
        if (detailsRoot != null) {
            ViewCompat.setOnApplyWindowInsetsListener(detailsRoot, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        ImageView ivArtwork = findViewById(R.id.iv_details_artwork);
        TextView tvTitle = findViewById(R.id.tv_details_title);
        TextView tvArtist = findViewById(R.id.tv_details_artist);
        TextView tvYear = findViewById(R.id.tv_details_year);
        TextView tvDescription = findViewById(R.id.tv_details_description);
        Button btnVisitWebsite = findViewById(R.id.btn_visit_website);
        Button btnReturn = findViewById(R.id.btn_return);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(EXTRA_TITLE);
            String artist = intent.getStringExtra(EXTRA_ARTIST);
            String year = intent.getStringExtra(EXTRA_YEAR);
            int imageResId = intent.getIntExtra(EXTRA_IMAGE_RES_ID, 0);
            websiteUrl = intent.getStringExtra(EXTRA_WEBSITE_URL);
            String description = intent.getStringExtra(EXTRA_DESCRIPTION);

            if (title != null) {
                tvTitle.setText(title);
            }
            if (artist != null) {
                tvArtist.setText(String.format(Locale.US, "Artist: %s", artist));
            }
            if (year != null) {
                tvYear.setText(String.format(Locale.US, "Creation Year: %s", year));
            }
            if (imageResId != 0) {
                ivArtwork.setImageResource(imageResId);
            }
            if (description != null) {
                tvDescription.setText(description);
            }
        }

        btnVisitWebsite.setOnClickListener(v -> {
            if (websiteUrl != null && !websiteUrl.isEmpty()) {
                try {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
                    startActivity(webIntent);
                } catch (Exception e) {
                    Toast.makeText(ArtworkDetailsActivity.this, "Unable to open web browser.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ArtworkDetailsActivity.this, "Website URL not available.", Toast.LENGTH_SHORT).show();
            }
        });

        // Return button: Finish current activity and return to MainActivity (Requirement 4)
        btnReturn.setOnClickListener(v -> finish());
    }
}
