/*

тесты:
1 - добавление валидной оценки
2 - добавление невалидной оценки
3 - проверка, что equals/hashcode переопределен *
4 - проверка, что к одинаковому фильму добавляются разные оценки *
5 - расчет средней оценки, когда фильм есть в мапе
6 - расчет средней оценки, когда фильма нет в мапе
7 - тест на сортировку - элементы в полученном списке отсортированы

 */

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import task4.InvalidRatingException;
import task4.Movie;
import task4.MovieService;
import task4.Rating;

import static org.junit.jupiter.api.Assertions.*;

public class MovieServiceTest {

    // 1 - добавление валидной оценки
    @Test
    public void testAddValidRating() {
        Movie movie1 = new Movie("Война и мир");
        Rating<Double> rating1 = new Rating<>(9.9);
        MovieService<Double> movieService1 = new MovieService<>();

        movieService1.addRatingToMovie(movie1, rating1);

        // проверяем, что после добавления у нас создалась одна пара entry
        assertEquals(1, movieService1.getMovieServiceMap().size());

        // проверяем, что после добавления, в списке рейтингов - один рейтинг
        assertEquals(1, movieService1.getMovieServiceMap().get(movie1).size());

        // проверяем, что после добавления добавился ожидаемый фильм
        assertTrue(movieService1.getMovieServiceMap().containsKey(movie1));

        // проверяем, что после добавления в мапе содержится добавляемый рейтинг
        assertTrue(movieService1.getMovieServiceMap().get(movie1).contains(rating1));
    }


    // 2 - добавление невалидной оценки
    @ParameterizedTest
    @ValueSource(doubles = {-9.9, 0.0, 0.9, 10.1})
    public void testAddInvalidRating(Double ratingValue) {
        Movie movie1 = new Movie("Война и мир");
        Rating<Double> rating1 = new Rating<>(ratingValue);
        MovieService<Double> movieService1 = new MovieService<>();
        assertThrows(InvalidRatingException.class, () -> movieService1.addRatingToMovie(movie1, rating1));
    }


    // 3 - проверка, что equals/hashcode переопределен
    // 4 - проверка, что к одинаковому фильму добавляются разные оценки
    @Test
    public void testAddTwoRatingsToSameFilm() {
        Movie movie1 = new Movie("Война и мир");
        Movie movie2 = new Movie("Война и мир");

        Rating<Double> rating1 = new Rating<>(9.9);
        Rating<Double> rating2 = new Rating<>(9.9);

        MovieService<Double> movieService1 = new MovieService<>();

        // т к equals и hashcode переопределены - в мапе одна пара - фильм: список из двух рейтингов
        movieService1.addRatingToMovie(movie1, rating1);
        movieService1.addRatingToMovie(movie2, rating2);

        assertEquals(1, movieService1.getMovieServiceMap().size());
        assertEquals(2, movieService1.getMovieServiceMap().get(movie1).size());
    }


    // 5 - расчет средней оценки, когда фильм есть в мапе
    @Test
    public void testGetAvgValueByMovie() {
        Movie movie1 = new Movie("Война и мир");
        Rating<Double> rating1 = new Rating<>(9.0);
        Rating<Double> rating2 = new Rating<>(7.0);

        MovieService<Double> movieService1 = new MovieService<>();

        movieService1.addRatingToMovie(movie1, rating1);
        movieService1.addRatingToMovie(movie1, rating2);

        assertEquals(8.0, movieService1.calculateAvgRatingForFilm(movie1));
    }


    // 6 - расчет средней оценки, когда фильма нет в мапе
    @Test
    public void testGetAvgValueByMovieNotInTheMap() {
        Movie movie1 = new Movie("Война и мир");
        MovieService<Double> movieService1 = new MovieService<>();
        assertThrows(InvalidRatingException.class, () -> movieService1.calculateAvgRatingForFilm(movie1));
    }


    // 7 - тест на сортировку - элементы в полученном списке отсортированы
    @Test
    public void testFilmSortingByAvgValue() {

        Movie movie1 = new Movie("Война и мир");
        Rating<Double> rating1 = new Rating<>(9.0);
        Rating<Double> rating2 = new Rating<>(7.0);

        MovieService<Double> movieService1 = new MovieService<>();

        movieService1.addRatingToMovie(movie1, rating1);
        movieService1.addRatingToMovie(movie1, rating2);


        Movie movie2 = new Movie("Старикам здесь не место");
        Rating<Double> rating3 = new Rating<>(7.0);
        Rating<Double> rating4 = new Rating<>(5.0);

        movieService1.addRatingToMovie(movie2, rating3);
        movieService1.addRatingToMovie(movie2, rating4);

        assertEquals("Война и мир", movieService1.getSortedFilmListByAvgRating().getFirst());
        assertEquals("Старикам здесь не место",movieService1.getSortedFilmListByAvgRating().getLast());
    }


}
