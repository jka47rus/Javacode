package main;

public class ArrayFilter {

    public static Object[] filter(Object[] array, Filter filter) {
        // Создаем новый массив с тем же размером, что и исходный
        Object[] result = new Object[array.length];

        // Применяем метод apply к каждому элементу массива
        for (int i = 0; i < array.length; i++) {
            result[i] = filter.apply(array[i]);
        }

        return result;
    }
}
