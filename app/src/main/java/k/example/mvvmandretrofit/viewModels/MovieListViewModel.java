package k.example.mvvmandretrofit.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import k.example.mvvmandretrofit.models.MovieModel;
import k.example.mvvmandretrofit.repository.MovieRepository;

public class MovieListViewModel extends ViewModel {


    private MovieRepository movieRepository;

    public MovieListViewModel(){
        movieRepository=MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>>getMovies(){
        return movieRepository.getMovies();
    }

    public void searchMovieApi(String query,int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);

    }

    public void SearchNextPage(){
        movieRepository.SearchNextPage();
    }

}
