package app.movielistapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class APIProcessTask extends AsyncTask<Void, Void, String> {

    private static final String API_KEY = "1f54bd990f1cdfb230adb312546d765d";
    private static final String API_URL = "https://api.themoviedb.org/3";
    private static final String QUERY_MOVIES = "/movie/upcoming?";
    private static final String QUERY_GENRES = "/genre/movie/list";
    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/w185/";

    private static int current_page = 1;
    private static HashMap<String, String> movieGenres;

    private Activity activity;
    private ProgressDialog dialog;
    private ArrayList<MovieInfo> movies;
    private CustomListAdapter adapter;

    protected APIProcessTask(Activity activity, ArrayList<MovieInfo> movies, CustomListAdapter adapter) {
        super();
        this.activity = activity;
        this.movies = movies;
        this.adapter = adapter;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setTitle("Upcoming Movies");
        dialog.setMessage("Loading more...");
        dialog.setIndeterminate(false);
        dialog.show();
    }

    protected String doInBackground(Void... urls) {
        try {
            if (movieGenres == null) {
                setMovieGenres();
            }
            URL moviesUrl = new URL(API_URL + QUERY_MOVIES + "&page=" + current_page + "&api_key=" + API_KEY);
            return this.readUrlData(moviesUrl);
        } catch(MalformedURLException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch(IOException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    private void setMovieGenres() throws IOException, JSONException {
        URL genresUrl = new URL(API_URL + QUERY_GENRES + "?api_key=" + API_KEY);
        movieGenres = new HashMap<>();
        String response = this.readUrlData(genresUrl);
        processGenresData(response);
    }

    private String readUrlData(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } finally {
            urlConnection.disconnect();
        }
    }

    private void processGenresData(String response) throws JSONException {
        JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
        JSONArray results = object.getJSONArray("genres");
        JSONObject obj;
        for (int i = 0; i < results.length(); i++) {
            obj = results.getJSONObject(i);
            String id = obj.get("id").toString();
            String name = obj.get("name").toString();
            movieGenres.put(id, name);
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            handleError();
            return;
        }

        try {
            processMoviesData(response);
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
            handleError();
            return;
        }
        current_page++;
        adapter.notifyDataSetChanged();
        dialog.dismiss();
    }

    private void handleError() {
        dialog.dismiss();
        showAlertDialog();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("It was not possible to connect to the TMDb API. Please try again later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void processMoviesData(String response) throws JSONException {
        JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
        JSONArray results = object.getJSONArray("results");
        JSONObject obj;
        MovieInfo movie;
        for (int i = 0; i < results.length(); i++) {
            obj = results.getJSONObject(i);
            movie = createMovieInfo(obj);
            movies.add(movie);
        }
    }

    private MovieInfo createMovieInfo(JSONObject obj) throws JSONException {
        String name = obj.get("title").toString();
        String genre_ids = obj.get("genre_ids").toString();
        String genres = formatMovieGenre(genre_ids);
        String release_date = obj.get("release_date").toString();
        String overview = obj.get("overview").toString();
        String poster_path = obj.get("poster_path").toString();
        return new MovieInfo(name, genres, release_date, overview, IMAGE_PATH+poster_path);
    }

    private String formatMovieGenre(String genre_ids) {
        if (genre_ids.equals("[]")) {
            return "";
        }
        String[] ids = genre_ids.substring(1, genre_ids.length() - 1).split(",");
        ArrayList<String> genres = new ArrayList<>();
        for(String value: ids) {
            genres.add(movieGenres.get(value));
        }
        return TextUtils.join(", ", genres);
    }
}
