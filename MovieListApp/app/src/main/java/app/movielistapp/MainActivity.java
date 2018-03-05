package app.movielistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String SELECTED_MOVIE = "app.movielistapp.SELECTED_MOVIE";

    private ArrayList<MovieInfo> movies;
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(android.R.id.list);
        setListViewAdapter();
        setListViewOnItemClickListener();
        setListViewOnScrollListener();

        fillListWithFirstPageMovies();
    }

    private void setListViewAdapter() {
        movies = new ArrayList<>();
        adapter = new CustomListAdapter(this, R.layout.list_item, R.id.movie_name, movies);
        listView.setAdapter(adapter);
    }

    private void setListViewOnItemClickListener() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this, DisplayDetailsActivity.class);
                intent.putExtra(SELECTED_MOVIE, movies.get(position));
                startActivity(intent);
            }
        });
    }

    private void setListViewOnScrollListener() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = listView.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count - threshold) {
                        new APIProcessTask(MainActivity.this, movies, adapter).execute();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
        });
    }

    private void fillListWithFirstPageMovies() {
        new APIProcessTask(this, movies, adapter).execute();
    }
}