package k.example.mvvmandretrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import k.example.mvvmandretrofit.R;
import k.example.mvvmandretrofit.models.MovieModel;

public class MovieRecycleView extends RecyclerView.Adapter<MovieRecycleView.MyViewHolder> {

    private List<MovieModel> mModels;
    Context context;
    public OnMovieListener onMovieListener;

    public MovieRecycleView(Context context, OnMovieListener onMovieListener) {
        this.context = context;
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public MovieRecycleView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        return new MyViewHolder(view,onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecycleView.MyViewHolder holder, int position) {
        holder.txt_name.setText(mModels.get(position).getTitle());
        holder.txt_catagory.setText(mModels.get(position).getRelease_date());
        holder.txt_duration.setText(mModels.get(position).getPopularity());
        holder.ratingBar.setRating(mModels.get(position).getVote_average()/2);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500"+mModels.get(position).getPoster_path())
                .into(holder.img_poster);
    }

    @Override
    public int getItemCount() {
        if (mModels!=null){
            return mModels.size();
        }
        return 0;

    }

    public void setmMovies(List<MovieModel> mModels){
        this.mModels=mModels;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView txt_name;
        TextView txt_catagory;
        TextView txt_duration;
        RatingBar ratingBar;

        OnMovieListener onMovieListener;

        public MyViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);
            this.onMovieListener=onMovieListener;
            img_poster=itemView.findViewById(R.id.img_poster);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_catagory=itemView.findViewById(R.id.txt_catagory);
            txt_duration=itemView.findViewById(R.id.txt_duration);
            ratingBar=itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(v -> {
                onMovieListener.onMovieClick(mModels.get(getAdapterPosition()));
            });
        }
    }

    public interface OnMovieListener{
        void onMovieClick(MovieModel movieModel);
    }
}
