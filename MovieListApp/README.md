# Upcoming Movies App

Android application that lists the upcoming movies using the TMDb API.

Java. Android Studio. API 19 (Android 4.4).

### TODOs

- To avoid memory leak, use WeakReference, so the activity will not be 'locked' to the other class/task. In the case of APIProcessTask, I would need to add code to check if the activity is not null before trying to show dialogs.
- Fix code so the app is not 'broken' by rotation
- Add better tests

### Screenshots

![alt tag](https://github.com/jpbonson/MobilePlayground/blob/master/MovieListApp/screenshots/app_and_ide.png)

![alt tag](https://github.com/jpbonson/MobilePlayground/blob/master/MovieListApp/screenshots/app_error.png)

![alt tag](https://github.com/jpbonson/MobilePlayground/blob/master/MovieListApp/screenshots/loading_movies.png)

![alt tag](https://github.com/jpbonson/MobilePlayground/blob/master/MovieListApp/screenshots/movie_details.png)

![alt tag](https://github.com/jpbonson/MobilePlayground/blob/master/MovieListApp/screenshots/on_phone1.jpg)
