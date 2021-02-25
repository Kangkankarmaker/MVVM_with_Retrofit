package k.example.mvvmandretrofit.request;

import k.example.mvvmandretrofit.utils.MovieApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static k.example.mvvmandretrofit.utils.Credentials.BASE_URL;

public class Services {

    private static Retrofit.Builder retrofitBuilder=
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    public static MovieApi movieApi=retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
        return movieApi;
    }

}
