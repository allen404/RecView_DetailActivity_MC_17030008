package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import java.util.List;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Genre;

public interface OnGetGenresCallback {
    void onSuccess(List<Genre> genres);
    void onError();
}
