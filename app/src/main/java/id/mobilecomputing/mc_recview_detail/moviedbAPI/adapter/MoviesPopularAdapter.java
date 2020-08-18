package id.mobilecomputing.mc_recview_detail.moviedbAPI.adapter;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import id.mobilecomputing.mc_recview_detail.R;

import id.mobilecomputing.mc_recview_detail.moviedbAPI.activity.MovieDetailActivity;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Genre;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.model.Result;
import id.mobilecomputing.mc_recview_detail.moviedbAPI.network.OnMoviesClickCallback;

public class MoviesPopularAdapter extends RecyclerView.Adapter<MoviesPopularAdapter.MoviesPopularViewHolder> {
    private List<Result> result;
    private List<Genre> allGenres;

    private int columnItem;
    private Context context;

    private OnMoviesClickCallback callback;

    public MoviesPopularAdapter(List<Result> result, List<Genre> allGenres, OnMoviesClickCallback callback) {
        this.result = result;
        this.allGenres = allGenres;
        this.callback = callback;
    }


    @NonNull
    @Override
    public MoviesPopularAdapter.MoviesPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movie_list,parent,false);
        return new MoviesPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesPopularAdapter.MoviesPopularViewHolder holder, final int position) {

        holder.bind(result.get(position));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetail = new Intent(v.getContext(), MovieDetailActivity.class);

                toDetail.putExtra("judul", result.get(position).getOriginalTitle());
                toDetail.putExtra("backdrop", result.get(position).getBackdropPath());
                toDetail.putExtra("overview", result.get(position).getOverview());
                toDetail.putExtra("release", result.get(position).getReleaseDate());
                toDetail.putExtra("poster",result.get(position).getPosterPath());
                toDetail.putExtra("rating",String.valueOf(result.get(position).getVoteAverage()/2));
                v.getContext().startActivity(toDetail);
            }
        });


    }

    @Override
    public int getItemCount() {
        return result.size();
    }

     class MoviesPopularViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvGenre, tvReleaseDate, tvRating;
        ImageView ivPoster;
        CardView container;
        Result result;


        public MoviesPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivMoviePoster);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_movie_release_date);
            tvRating = itemView.findViewById(R.id.tv_movie_rating);
            tvGenre = itemView.findViewById(R.id.tv_movie_genre);
            container = itemView.findViewById(R.id.cv_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(result);
                }
            });
        }

        public void bind(Result result) {
            this.result = result;
            String sRating = "Rating: " + String.valueOf(result.getVoteAverage());

            Glide.with(itemView).load("https://image.tmdb.org/t/p/w185/" + result
                    .getPosterPath())
                    .into(ivPoster);
            tvTitle.setText(result.getTitle());
            tvReleaseDate.setText(result.getReleaseDate());
            tvRating.setText(sRating);
            tvGenre.setText(getGenres(result.getGenreIds()));
        }

        private String getGenres(List<Integer> genreIds) {
            List<String> movieGenres = new ArrayList<>();
            for (Integer genreId : genreIds) {
                for (Genre genre : allGenres) {
                    if (genre.getId() == genreId) {
                        movieGenres.add(genre.getName());
                        break;
                    }
                }
            }
            return TextUtils.join(", ", movieGenres);
        }
    }

    public void appendMovies(List<Result> moviesToAppend){
        result.addAll(moviesToAppend);
        notifyDataSetChanged();
    }

    public void clearMovies(){
        result.clear();
        notifyDataSetChanged();
    }
}
