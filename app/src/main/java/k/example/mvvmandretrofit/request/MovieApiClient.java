package k.example.mvvmandretrofit.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import k.example.mvvmandretrofit.AppExecutors;
import k.example.mvvmandretrofit.models.MovieModel;
import k.example.mvvmandretrofit.response.MovieSearchResponse;
import retrofit2.Call;
import retrofit2.Response;

import static k.example.mvvmandretrofit.utils.Credentials.API_KEY;

public class MovieApiClient {

    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    private MutableLiveData<List<MovieModel>> mMovies;

    private static MovieApiClient instance;

    public static MovieApiClient getInstance(){
        if (instance==null){
            instance=new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        mMovies=new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>>getMovies(){
        return mMovies;
    }


    public void SearchMovieApi(String query,int pageNumber){

        if (retrieveMoviesRunnable!=null){
            retrieveMoviesRunnable=null;

        }

        retrieveMoviesRunnable=new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandlar= AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlar.cancel(true);
            }
        },3000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest=false;
        }

        @Override
        public void run() {

            try{
                Response response=getMovies(query,pageNumber).execute();

                if (cancelRequest){
                    return;
                }

                if (response.code()==200){
                    List<MovieModel>list=new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());

                    if (pageNumber==1){
                        mMovies.postValue(list);
                    }else {
                        List<MovieModel>currentMovies=mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }

                }else {
                    Log.v("TAG", "error"+response.errorBody());
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<MovieSearchResponse>getMovies(String query,int pageNumber){
            return Services.getMovieApi().searchMovie(API_KEY,query, String.valueOf(pageNumber));
        }

        private void cancelRequest(){
            cancelRequest=true;
        }
    }
}
