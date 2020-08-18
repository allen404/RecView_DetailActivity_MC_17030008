package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import android.graphics.Movie;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.MoviesPopularResponse;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;

public interface OnGetMovieCallback {
    void onSuccess(Result result);
    void onError();
}
