package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import java.util.List;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Trailer;

public interface OnGetTrailersCallback {
    void onSuccess(List<Trailer> trailers);

    void onError();
}
