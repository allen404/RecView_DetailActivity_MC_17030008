package id.mobilecomputing.mc_recview_detail.moviedbAPI.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.mobilecomputing.mc_recview_detail.R;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView backdrop,poster;
    TextView title,releaseDate,overview,trailers;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        backdrop = findViewById(R.id.iv_movie_backdrop);
        poster = findViewById(R.id.iv_movie_poster);
        title = findViewById(R.id.tv_movie_title);
        releaseDate = findViewById(R.id.tv_movie_release_date);
        overview = findViewById(R.id.tv_movie_overview);
        rating = findViewById(R.id.rtBar_movie_rating);
        trailers = findViewById(R.id.tv_trailers_label);

        Intent fromMain = getIntent();

        title.setText(fromMain.getStringExtra("judul"));
        overview.setText(fromMain.getStringExtra("overview"));
        releaseDate.setText(fromMain.getStringExtra("release"));
        rating.setRating(Float.parseFloat(fromMain.getStringExtra("rating")));

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/w185" + fromMain.getStringExtra("poster"))
                .into(poster);

        Glide.with(getApplicationContext())
                .load("https://image.tmdb.org/t/p/original" + fromMain.getStringExtra("backdrop"))
                .into(backdrop);

    }


}