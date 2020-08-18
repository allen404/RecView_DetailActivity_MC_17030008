package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.MoviesPopularResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.TrailerResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("movie/popular")
    Call<MoviesPopularResponse> getPopularMovies(
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );
}
