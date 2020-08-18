package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;


import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;

public interface OnGetMovieCallback {
    void onSuccess(Result result);
    void onError();
}
