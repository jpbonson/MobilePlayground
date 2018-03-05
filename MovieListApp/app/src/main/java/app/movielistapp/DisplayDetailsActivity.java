package app.movielistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

public class DisplayDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);

        Intent intent = getIntent();
        MovieInfo movie = (MovieInfo) intent.getExtras().getSerializable(MainActivity.SELECTED_MOVIE);

        TextView textView = findViewById(R.id.movie_name);
        textView.setText(movie.getName());

        textView = findViewById(R.id.movie_genre);
        textView.setText(movie.getGenre());

        textView = findViewById(R.id.movie_release_date);
        textView.setText(movie.getReleaseDate());

        textView = findViewById(R.id.movie_overview);
        textView.setText(movie.getOverview());
        textView.setMovementMethod(new ScrollingMovementMethod());

        ImageView movie_image = findViewById(R.id.movie_image);
        Ion.with(movie_image).load(movie.getImageLink());
    }
}
