package k.example.mvvmandretrofit.repository;

import androidx.lifecycle.LiveData;
import java.util.List;

import k.example.mvvmandretrofit.models.MovieModel;
import k.example.mvvmandretrofit.request.MovieApiClient;

public class MovieRepository {

    public static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNum;

    public static MovieRepository getInstance(){
        if (instance==null){
            instance=new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient=MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>>getMovies(){return movieApiClient.getMovies();}

    public void searchMovieApi(String query,int pageNumber){
        mQuery=query;
        mPageNum=pageNumber;
        movieApiClient.SearchMovieApi(query,pageNumber);
    }

    public void SearchNextPage(){
        movieApiClient.SearchMovieApi(mQuery,mPageNum+1);
    }
}
