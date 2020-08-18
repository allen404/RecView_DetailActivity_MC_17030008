package id.mobilecomputing.mc_recview_detail.moviedbAPI.network;

import java.util.List;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Review;

public interface OnGetReviewsCallback {
    void onSuccess(List<Review> reviews);

    void onError();
}
