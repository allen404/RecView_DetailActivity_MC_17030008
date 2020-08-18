package id.mobilecomputing.mc_recview_detail.moviedbAPI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import id.mobilecomputing.mc_recview_detail.R;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Genre;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Review;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Trailer;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.APIClient;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetGenresCallback;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetMovieCallback;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetReviewsCallback;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetTrailersCallback;


public class MovieDetailActivity extends AppCompatActivity {
    ImageView backdrop,poster;
    TextView title,releaseDate,overview,tv_trailers, overviewLabel, movieGenre,reviewsLabel;
    RatingBar rating;
    LinearLayout movieTrailers, movieReviews;


    public static String MOVIE_ID = "movie_id";
    private static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w780";
    private static String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%s";
    private static String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg";
    private APIClient apiClient;

    Integer movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);




        Intent fromMain = getIntent();
        movieId = fromMain.getIntExtra("movie_id",0);

        apiClient = APIClient.getInstance();

        initUI();
        getMovieDetails();

    }

    private void initUI(){
        backdrop = findViewById(R.id.iv_movie_backdrop);
        title = findViewById(R.id.tv_movie_title);
        releaseDate = findViewById(R.id.tv_movie_release_date);
        overview = findViewById(R.id.tv_movie_overview);
        rating = findViewById(R.id.rtBar_movie_rating);
        tv_trailers = findViewById(R.id.tv_trailer_label);
        movieGenre = findViewById(R.id.tv_movie_genre);
        movieTrailers = findViewById(R.id.ll_movie_trailers);
        reviewsLabel = findViewById(R.id.tv_movie_review_label);
        movieReviews = findViewById(R.id.ll_movie_reviews);


    }

    private void getMovieDetails() {
        apiClient.getMovie(movieId, new OnGetMovieCallback() {
            @Override
            public void onSuccess(Result result) {
                title.setText(result.getTitle());
                releaseDate.setText(result.getReleaseDate());
                getGenres(result);
                overview.setText(result.getOverview());
                rating.setRating(result.getVoteAverage()/2);
                getTrailers(result);
                getReviews(result);
                if (!isFinishing()) {
                    Glide.with(MovieDetailActivity.this)
                            .load("https://image.tmdb.org/t/p/w1280" + result.getBackdropPath())
                            .into(backdrop);
                }
            }

            @Override
            public void onError() {
                Toast.makeText(MovieDetailActivity.this, "testoooo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReviews(Result result){
        apiClient.getReviews(result.getId(), new OnGetReviewsCallback() {
            @Override
            public void onSuccess(List<Review> reviews) {
                reviewsLabel.setVisibility(View.VISIBLE);
                movieReviews.removeAllViews();
                for (Review review : reviews){
                    View parent = getLayoutInflater().inflate(R.layout.review,movieReviews, false);
                    TextView author = parent.findViewById(R.id.tv_review_author);
                    TextView content = parent.findViewById(R.id.tv_review_content);
                    author.setText(review.getAuthor());
                    content.setText(review.getContent());
                    movieReviews.addView(parent);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void getTrailers(Result result) {
        apiClient.getTrailers(result.getId(), new OnGetTrailersCallback() {
            @Override
            public void onSuccess(List<Trailer> trailers) {
                tv_trailers.setVisibility(View.VISIBLE);
                movieTrailers.removeAllViews();
                for (final Trailer trailer : trailers) {
                    View parent = getLayoutInflater().inflate(R.layout.detail_trailer, movieTrailers, false);

                    ImageView thumbnail = parent.findViewById(R.id.iv_trailer_thumbnail);
                    thumbnail.requestLayout();
                    thumbnail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showTrailer(String.format(YOUTUBE_VIDEO_URL, trailer.getKey()));
                        }
                    });
                    Glide.with(MovieDetailActivity.this)
                            .load(String.format(YOUTUBE_THUMBNAIL_URL, trailer.getKey()))
                            .into(thumbnail);
                    movieTrailers.addView(parent);
                }
            }

            @Override
            public void onError() {
                // Do nothing
                tv_trailers.setVisibility(View.GONE);
            }
        });
    }

    private void showTrailer(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void setupToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
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