package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import id.mobilecomputing.mc_recview_detail.BuildConfig;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.GenresResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.MoviesPopularResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.TrailerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = "8333f84b3a68a2aef2d8badd07e79d30";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String UPCOMING = "upcoming";

    private static APIClient apiClient;

    private APIInterface api;

    private APIClient (APIInterface api){
        this.api = api;
    }

    public static APIClient getInstance(){
        if (apiClient == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiClient = new APIClient(retrofit.create(APIInterface.class));
        }
        return apiClient;
    }


    public void getMovies(int page,String sortBy, final OnGetMoviesCallback callback){
        Callback<MoviesPopularResponse> call = new Callback<MoviesPopularResponse>(){

            @Override
            public void onResponse(Call<MoviesPopularResponse> call, Response<MoviesPopularResponse> response) {
                if(response.isSuccessful()){
                    MoviesPopularResponse moviesPopularResponse = response.body();
                    if(moviesPopularResponse != null && moviesPopularResponse.getResults() != null){
                        callback.onSuccess(moviesPopularResponse.getPage(), moviesPopularResponse.getResults());
                    }else{
                        callback.onError();
                    }
                }else{
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<MoviesPopularResponse> call, Throwable t) {
                callback.onError();
            }
        };
        switch (sortBy){
            case TOP_RATED:
                api.getTopRatedMovies(API_KEY,LANGUAGE,page)
                        .enqueue(call);
                break;
            case UPCOMING:
                api.getUpcomingMovies(API_KEY,LANGUAGE,page)
                        .enqueue(call);
                break;
            case POPULAR:
            default:
                api.getPopularMovies(API_KEY,LANGUAGE,page)
                        .enqueue(call);
                break;
        }
    }

    public void getGenres(final OnGetGenresCallback callback) {
        api.getGenres(API_KEY, LANGUAGE)
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                        if (response.isSuccessful()) {
                            GenresResponse genresResponse = response.body();
                            if (genresResponse != null && genresResponse.getGenres() != null) {
                                callback.onSuccess(genresResponse.getGenres());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenresResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTrailers(int movieId, final OnGetTrailersCallback callback){
        api.getTrailers(movieId, API_KEY, LANGUAGE)
                .enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                        if(response.isSuccessful()){
                            TrailerResponse trailerResponse = response.body();
                            if(trailerResponse != null && trailerResponse.getTrailers() != null){
                                callback.onSuccess(trailerResponse.getTrailers());
                            }else{
                                callback.onError();
                            }
                        }else{
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrailerResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getMovie(int movieId, final OnGetMovieCallback callback) {
        api.getMovie(movieId, API_KEY, LANGUAGE)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Result result = response.body();
                            if (response != null) {
                                callback.onSuccess(result);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}
