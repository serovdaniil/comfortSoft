package ru.comfort.soft.utils;

import java.util.List;

/**
 * Утилитарный класс для эффективного поиска N-го минимального элемента в коллекции
 * без полной сортировки данных.
 *
 * <p>Использует алгоритм на основе Max-Heap для оптимального поиска с временной сложностью
 * O(M log N) и пространственной сложностью O(N), где M - количество элементов, N - параметр поиска.
 *
 * <p><b>Преимущества перед полной сортировкой:</b>
 * <ul>
 *   <li>Не требует сортировки всего массива</li>
 *   <li>Эффективен для больших наборов данных</li>
 *   <li>Поддерживает потоковую обработку</li>
 * </ul>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 2);
 * int thirdMin = HeapSelectionUtils.findNthMinimum(numbers, 3); // Возвращает 3
 * }</pre>
 *
 * @author Serov Daniil
 */
public class HeapSelectionUtils {

    /**
     * Находит N-ный минимальный элемент в переданной коллекции чисел.
     *
     * <p><b>Алгоритм:</b>
     * <ol>
     *   <li>Создает Max-Heap из первых N элементов</li>
     *   <li>Для каждого последующего элемента, если он меньше максимального в heap,
     *       заменяет максимальный элемент и восстанавливает свойства heap</li>
     *   <li>По завершении максимальный элемент в heap является N-ным минимальным
     *       в исходной коллекции</li>
     * </ol>
     *
     * @param numbers коллекция целых чисел для поиска (не может быть null или пустой)
     * @param n порядковый номер минимального элемента для поиска (1-based)
     * @return N-ный минимальный элемент
     * @throws IllegalArgumentException если:
     *         <ul>
     *           <li>{@code numbers} null или пустой</li>
     *           <li>{@code n} меньше 1 или больше размера коллекции</li>
     *         </ul>
     * @throws NullPointerException если коллекция содержит null элементы
     *
     * @see <a href="https://en.wikipedia.org/wiki/Heap_(data_structure)">Heap Data Structure</a>
     */
    public static int findNthMinimum(List<Integer> numbers, int n) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("Numbers collection cannot be null or empty");
        }
        if (n <= 0 || n > numbers.size()) {
            throw new IllegalArgumentException("N must be between 1 and " + numbers.size());
        }

        int[] heap = new int[n];

        for (int i = 0; i < n; i++) {
            heap[i] = numbers.get(i);
        }

        buildMaxHeap(heap);

        for (int i = n; i < numbers.size(); i++) {
            int current = numbers.get(i);

            if (current < heap[0]) {
                heap[0] = current;
                heapify(heap, 0, n);
            }
        }

        return heap[0];
    }

    /**
     * Преобразует массив в Max-Heap.
     *
     * <p>Max-Heap - это бинарное дерево, где значение каждого узла больше или равно
     * значениям его потомков. Корень heap содержит максимальный элемент.
     *
     * @param arr массив для преобразования в Max-Heap
     */
    private static void buildMaxHeap(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, i, n);
        }
    }

    /**
     * Восстанавливает свойства Max-Heap для поддерева с корнем в заданном индексе.
     *
     * <p>Рекурсивно обеспечивает, чтобы родительский узел был больше своих потомков.
     * Если это не так, производится обмен и рекурсивный вызов для затронутого поддерева.
     *
     * @param arr массив, представляющий heap
     * @param i индекс корня поддерева для heapify
     * @param n размер heap (может быть меньше размера массива)
     */
    private static void heapify(int[] arr, int i, int n) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);

            heapify(arr, largest, n);
        }
    }

    /**
     * Меняет местами два элемента в массиве.
     *
     * @param arr массив для модификации
     * @param i индекс первого элемента
     * @param j индекс второго элемента
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}