package id.mobilecomputing.mc_recview_detail.moviedbAPI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import id.mobilecomputing.mc_recview_detail.R;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.adapter.MoviesPopularAdapter;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.MoviesPopularResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.APIClient;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MoviesPopularAdapter adapter;
    private final static String API_KEY ="8333f84b3a68a2aef2d8badd07e79d30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.rc_movie_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<MoviesPopularResponse> call = apiInterface.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MoviesPopularResponse>() {
            @Override
            public void onResponse(Call<MoviesPopularResponse> call, Response<MoviesPopularResponse> response) {
                List<Result> results = response.body().getResults();
                recyclerView.setAdapter(new MoviesPopularAdapter(results, R.layout.row_movie_list, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesPopularResponse> call, Throwable t) {
            }
        });
    }
}