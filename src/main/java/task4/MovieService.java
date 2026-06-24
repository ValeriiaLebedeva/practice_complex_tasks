package task4;

import java.util.*;
import java.util.stream.Collectors;

/*
MovieService: Сервис для управления фильмами и их рейтингами.
Управление рейтингами:
Хранение оценок в Map<Movie, List<Rating>>.

Метод для добавления оценки к фильму. Метод должен быть потокобезопасным и валидировать оценку на
допустимость (например, оценка должна быть в пределах от 1 до 10).

Возможность расчета средней оценки для каждого фильма.

Обработка данных:
Использование Stream API для подсчёта средней оценки.
Использование Stream API и лямбда-выражений для сортировки фильмов по средней оценке.
 */
public class MovieService<T extends Number> {

    // Хранение оценок в Map<Movie, List<Rating>>.
    private final Map<Movie, List<Rating<T>>> movieServiceMap = new HashMap<>();


    // Метод для добавления оценки к фильму. Метод должен быть потокобезопасным и валидировать оценку на
    // допустимость (например, оценка должна быть в пределах от 1 до 10).

    public synchronized void addRatingToMovie(Movie movie, Rating<T> rating) {

        double doubleRatingValue = rating.getRating().doubleValue();

        if (doubleRatingValue < 1 || doubleRatingValue > 10) {
            throw new InvalidRatingException("Оценка должна быть в пределах от 1 до 10!");
        }

        movieServiceMap.computeIfAbsent(movie, key -> new ArrayList<Rating<T>>())
                .add(rating);
    }

    // Метод для расчета средней оценки
    // Использование Stream API для подсчёта средней оценки.
    public synchronized double calculateAvgRatingForFilm(Movie movie) {

        List<Rating<T>> listForCalcAvgValue = movieServiceMap.get(movie);

        if (listForCalcAvgValue == null || listForCalcAvgValue.isEmpty()) {
            throw new InvalidRatingException("Отсутствуют данные для расчета средней оценки!");
        }

        return listForCalcAvgValue.stream()
                .mapToDouble(rating -> rating.getRating().doubleValue())
                .average()
                .orElse(0.0);
    }


    // Метод для сортировки фильмов по средней оценке
    // Использование Stream API и лямбда-выражений для сортировки фильмов по средней оценке.
    public synchronized List<String> getSortedFilmListByAvgRating() {
        return movieServiceMap.entrySet().stream()
                .sorted((e1, e2)
                        -> Double.compare(average(e2.getValue()),
                        average(e1.getValue())))
                .map(Map.Entry::getKey)
                .map(Movie::getName)
                .collect(Collectors.toList());
    }


    // Вспомогательный метод для расчета средней оценки у списка рейтингов
    private synchronized double average(List<Rating<T>> ratingList) {
        return ratingList.stream()
                .mapToDouble(rating -> rating.getRating().doubleValue())
                .average()
                .orElse(0.0);
    }

    // Метод по получению копии всех оценок
    public synchronized Map<Movie, List<Rating<T>>> getMovieServiceMap() {
        return movieServiceMap.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entry -> List.copyOf(entry.getValue())
                ));
    }
}
