package io.github.sng78.tasks;

import java.util.*;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 5, 2, 3, 3}));
        System.out.println();

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 8, 4, 6));
        System.out.println(oddOrEven(list) + "\n");

        list = new ArrayList<>(Arrays.asList(1, 2, 8, 4, 5));
        System.out.println(oddOrEven(list) + "\n");
    }

    public static int minValue(int[] values) {
        return Integer.parseInt(
                Arrays.stream(values)
                        .distinct()
                        .sorted()
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining("")));
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        int integersSum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return integers.stream()
                .filter(number ->
                        (integersSum % 2 == 0) == (number % 2 != 0))
                .collect(Collectors.toList());
    }
}
