package io.github.sng78.tasks;

import java.util.*;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class MainStream {
    public static void main(String[] args) {
        int[] digits = {1, 5, 2, 3, 8, 3};
        System.out.println(Arrays.toString(digits) + "\n" +
                minValue(digits) + "\n");

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 8, 4, 6));
        System.out.println(oddOrEven(list) + "\n");

        list = new ArrayList<>(Arrays.asList(1, 2, 8, 4, 5));
        System.out.println(oddOrEven(list) + "\n");
    }

    public static int minValue(int[] digits) {
        return Arrays.stream(digits)
                .distinct()
                .sorted()
                .reduce(0, (acc, number) -> acc * 10 + number);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(partitioningBy(number -> number % 2 == 0, toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
