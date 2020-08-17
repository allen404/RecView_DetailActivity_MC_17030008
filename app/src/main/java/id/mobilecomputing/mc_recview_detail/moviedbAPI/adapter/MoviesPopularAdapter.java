package id.mobilecomputing.mc_recview_detail.moviedbAPI.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.mobilecomputing.mc_recview_detail.R;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.activity.MovieDetailActivity;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;

public class MoviesPopularAdapter extends RecyclerView.Adapter<MoviesPopularAdapter.MoviesPopularViewHolder> {
    private List<Result> results;
    private int columnItem;
    private Context context;

    public MoviesPopularAdapter(List<Result> results, int columnItem, Context context) {
        this.results = results;
        this.columnItem = columnItem;
        this.context = context;
    }


    @NonNull
    @Override
    public MoviesPopularAdapter.MoviesPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(columnItem, parent, false);
        return new MoviesPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesPopularAdapter.MoviesPopularViewHolder holder, final int position) {
        String sRating = "Rating: " + String.valueOf(results.get(position).getVoteAverage());

        Glide.with(context).load("https://image.tmdb.org/t/p/w185/" + results
                .get(position)
                .getPosterPath())
                .into(holder.ivPoster);
        holder.tvTitle.setText(results.get(position).getTitle());
        holder.tvReleaseDate.setText(results.get(position).getReleaseDate());
        holder.tvRating.setText(sRating);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetail = new Intent(v.getContext(), MovieDetailActivity.class);

                toDetail.putExtra("judul", results.get(position).getOriginalTitle());
                toDetail.putExtra("backdrop", results.get(position).getBackdropPath());
                toDetail.putExtra("overview", results.get(position).getOverview());
                toDetail.putExtra("release", results.get(position).getReleaseDate());
                toDetail.putExtra("poster",results.get(position).getPosterPath());
                toDetail.putExtra("rating",String.valueOf(results.get(position).getVoteAverage()/2));
                v.getContext().startActivity(toDetail);
            }
        });


    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class MoviesPopularViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSummary, tvReleaseDate, tvRating;
        ImageView ivPoster;
        CardView container;


        public MoviesPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivMoviePoster);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_movie_release_date);
            tvRating = itemView.findViewById(R.id.tv_movie_rating);
            container = itemView.findViewById(R.id.cv_container);
        }
    }
}
