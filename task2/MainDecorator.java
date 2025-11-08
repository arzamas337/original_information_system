package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class MainDecorator {
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        MyClientRep baseRepo = new MyClientDbAdapter(new MyClientRepDb());

        MyClientRep filteredRepo = new FilterTypePropertyDecorator(baseRepo);
        var filteredList = filteredRepo.get_k_n_short_list(0, 10);
        int filteredCount = filteredRepo.get_count();

        System.out.println("Фильтрация:");
        System.out.println("Количество: " + filteredCount);
        filteredList.forEach(System.out::println);
        System.out.println();

        MyClientRep sortedRepo = new SortOrganizationNameDecorator(baseRepo);
        var sortedList = sortedRepo.get_k_n_short_list(0, 10);
        int sortedCount = sortedRepo.get_count();

        System.out.println("Cортировка:");
        System.out.println("Количество: " + sortedCount);
        sortedList.forEach(System.out::println);
        System.out.println();

        MyClientRep filteredSortedRepo = new SortOrganizationNameDecorator(filteredRepo);
        var filteredSortedList = filteredSortedRepo.get_k_n_short_list(0, 10);
        int filteredSortedCount = filteredSortedRepo.get_count();

        System.out.println("Фильтрация и сортировка:");
        System.out.println("Количество: " + filteredSortedCount);
        filteredSortedList.forEach(System.out::println);
    }
}
