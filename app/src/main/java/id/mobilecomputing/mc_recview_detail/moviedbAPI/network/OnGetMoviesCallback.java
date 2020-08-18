package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import java.util.List;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;

public interface OnGetMoviesCallback {
    void onSuccess(int pages, List<Result> movies);

    void onError();
}

