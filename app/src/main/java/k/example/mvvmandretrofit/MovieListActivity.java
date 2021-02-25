package k.example.mvvmandretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import k.example.mvvmandretrofit.models.MovieModel;
import k.example.mvvmandretrofit.utils.MovieApi;
import k.example.mvvmandretrofit.request.Services;
import k.example.mvvmandretrofit.response.MovieSearchResponse;
import k.example.mvvmandretrofit.viewModels.MovieListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static k.example.mvvmandretrofit.utils.Credentials.API_KEY;

public class MovieListActivity extends AppCompatActivity {

    MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        movieListViewModel=new ViewModelProvider(this).get(MovieListViewModel.class);
        ObserveAnyChanges();

        Button button=findViewById(R.id.button);
        button.setOnClickListener(v -> {
            searchMovieApi("fast",1);
        });

    }

    private void ObserveAnyChanges(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels!=null){
                    for (MovieModel m :movieModels){
                        Log.v("TAG", "onChanged: "+m.getTitle());
                    }
                }

            }
        });
    }

    private void searchMovieApi(String query,int pageNumber){
        movieListViewModel.searchMovieApi(query,pageNumber);
    }

    private void getData() {

        MovieApi movieApi= Services.getMovieApi();

        Call<MovieSearchResponse>responseCall=movieApi.searchMovie(API_KEY,"Action","1");

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code()==200){

                    Log.v("TAG","response "+response.body().getTotal_result());
                    List<MovieModel> movies=new ArrayList<>(response.body().getMovies());

                    for (MovieModel m:movies) {
                        Log.v("TAG","name "+m.getTitle());
                    }
                }
                else {
                    Log.v("TAG","error "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Log.v("TAG","Throwable "+t.getMessage());
            }
        });

    }

    private void getRetrofitResponseAccordingTOID() {

        MovieApi movieApi= Services.getMovieApi();

        Call<MovieModel>responseCall=movieApi.getMovie(400,API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code()==200){

                    Log.v("TAG","response "+response.body().getTitle());
                   // List<MovieModel> movies=new ArrayList<>(response.body().getMovies());


                }
                else {
                    Log.v("TAG","error "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.v("TAG","Throwable "+t.getMessage());
            }
        });

    }
}