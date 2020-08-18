package id.mobilecomputing.mc_recview_detail.moviedbAPI.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import id.mobilecomputing.mc_recview_detail.R;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.adapter.MoviesPopularAdapter;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Genre;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.MoviesPopularResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.APIClient;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.APIInterface;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetGenresCallback;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnGetMoviesCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MoviesPopularAdapter adapter;
    private final static String API_KEY ="8333f84b3a68a2aef2d8badd07e79d30";
    private APIClient apiClient;
    private List<Genre> movieGenres;
    private boolean isFetchingMovies;
    private int currentPage = 1;
    private String sortBy = APIClient.POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.rc_movie_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiClient = APIClient.getInstance();

        setupOnScrollListener();

        getGenres();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_movies,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort:
                showSortMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortMenu(){
        PopupMenu sortMenu = new PopupMenu(this, findViewById(R.id.sort));
        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                currentPage = 1;

                switch (item.getItemId()){
                    case R.id.popular:
                        sortBy = APIClient.POPULAR;
                        getMovies(currentPage);
                        return true;
                    case R.id.top_rated:
                        sortBy = APIClient.TOP_RATED;
                        getMovies(currentPage);
                        return true;
                    case R.id.upcoming:
                        sortBy = APIClient.UPCOMING;
                        getMovies(currentPage);
                        return true;
                    default:
                        return false;
                }
            }
        });
        sortMenu.inflate(R.menu.menu_movies_sort);
        sortMenu.show();
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2){
                    if(!isFetchingMovies){
                        getMovies(currentPage + 1);
                    }
                }
            }
        });
    }

    private void getGenres() {
        apiClient.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                movieGenres = genres;
                getMovies(currentPage);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }



    private void getMovies(int page) {
        isFetchingMovies = true;
        apiClient.getMovies(page, sortBy, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Result> movies) {
                Log.d("APIClient", "Current Page = " + page);
                if (adapter == null){
                    adapter = new MoviesPopularAdapter(movies, movieGenres);
                    recyclerView.setAdapter(adapter);
                }else{
                    if(page == 1){
                        adapter.clearMovies();
                    }
                    adapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
    }
}