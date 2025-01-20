package main;

public class Main {
    public static void main(String[] args) {

        Object[] inputArray = {1, 2, 3, 4, 5};

        // Создаем фильтр, который будет умножать каждый элемент на 2
        Filter filter = new Filter() {
            @Override
            public Object apply(Object o) {
                if (o instanceof Number) {
                    return ((Number) o).intValue() * 2;
                }
                return o; // Если не Number, возвращаем как есть
            }
        };

        // Применяем фильтр
        Object[] filteredArray = ArrayFilter.filter(inputArray, filter);

        // Выводим результат
        for (Object obj : filteredArray) {
            System.out.println(obj);
        }
    }
}

