package id.mobilecomputing.mc_recview_detail.moviedbAPI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.mobilecomputing.mc_recview_detail.R;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Genre;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.APIClient;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetGenresCallback;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetMovieCallback;


public class MovieDetailActivity extends AppCompatActivity {
    ImageView backdrop,poster;
    TextView title,releaseDate,overview,trailers, overviewLabel, movieGenre;
    RatingBar rating;
    LinearLayout movieTrailers, movieReviews;


    public static String MOVIE_ID = "movie_id";
    private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780";
    private static String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";


    private int movieId;
    private APIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);
        apiClient = APIClient.getInstance();

        setupToolbar();
        initUI();
        getMovieDetails();
    }

    private void initUI(){
        backdrop = findViewById(R.id.iv_movie_backdrop);
        poster = findViewById(R.id.iv_movie_poster);
        title = findViewById(R.id.tv_movie_title);
        releaseDate = findViewById(R.id.tv_movie_release_date);
        overview = findViewById(R.id.tv_movie_overview);
        rating = findViewById(R.id.rtBar_movie_rating);
        trailers = findViewById(R.id.tv_trailers_label);
        movieGenre = findViewById(R.id.tv_movie_genre);

    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void getMovieDetails() {
        apiClient.getMovie(movieId, new OnGetMovieCallback() {
            @Override
            public void onSuccess(Result result) {
                title.setText(result.getTitle());
            }

            @Override
            public void onError() {
                Toast.makeText(MovieDetailActivity.this, "testoooo", Toast.LENGTH_SHORT).show();
            }
        });
    }

   private void getGenres(final Result result){
        apiClient.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                if(result.getGenres() != null){
                    List<String> currentGenres = new ArrayList<>();
                    for (Genre genre : result.getGenres()){
                        currentGenres.add(genre.getName());
                    }
                    movieGenre.setText(TextUtils.join(", ", currentGenres));
                }
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(MovieDetailActivity.this,"Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}