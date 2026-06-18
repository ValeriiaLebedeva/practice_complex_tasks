package task4;

//Rating<T extends Number>: Класс для хранения рейтинга фильма. T может быть Integer, Double и т.д.

public class Rating<T extends Number> {

    private T rating;

    public Rating(T rating) {
        this.rating = rating;
    }

    public T getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rating=" + rating +
                '}';
    }
}
