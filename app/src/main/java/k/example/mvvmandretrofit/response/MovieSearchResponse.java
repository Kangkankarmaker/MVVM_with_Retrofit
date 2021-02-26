package k.example.mvvmandretrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import k.example.mvvmandretrofit.models.MovieModel;

public class MovieSearchResponse {

    @SerializedName("total_results")
    @Expose
    private int total_results;

    @SerializedName("results")
    @Expose
    private List<MovieModel>results=null;

    public int getTotal_result() {
        return total_results;
    }

    public List<MovieModel> getMovies() {
        return results;
    }

}
