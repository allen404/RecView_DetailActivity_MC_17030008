package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.GenresResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.MoviesPopularResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.TrailerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/popular")
    Call<MoviesPopularResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("movie/upcoming")
    Call<MoviesPopularResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/top_rated")
    Call<MoviesPopularResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );





}
