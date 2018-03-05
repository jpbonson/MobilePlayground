package app.movielistapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<MovieInfo> {

    private final Activity activity;
    private final ArrayList<MovieInfo> movies;

    public CustomListAdapter(Activity activity, int resource, int textViewResourceId,
                             ArrayList<MovieInfo> movies) {
        super(activity, resource, textViewResourceId, movies);
        this.activity = activity;
        this.movies = movies;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null,true);

        TextView movieName = rowView.findViewById(R.id.movie_name);
        TextView movieGenre = rowView.findViewById(R.id.movie_genre);
        TextView movieRelease = rowView.findViewById(R.id.movie_release_date);
        ImageView movieImage = rowView.findViewById(R.id.movie_image);

        MovieInfo movie = movies.get(position);
        movieName.setText(movie.getName());
        movieGenre.setText(movie.getGenre());
        movieRelease.setText(movie.getReleaseDate());
        Ion.with(movieImage).load(movie.getImageLink());

        return rowView;
    }
}
