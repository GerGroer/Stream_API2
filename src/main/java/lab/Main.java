package lab;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Path.of(args.length > 0 ? args[0] : "books.json");
        List<Visitor> visitors;
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            Type type = new TypeToken<List<Visitor>>() {}.getType();
            visitors = new Gson().fromJson(reader, type);
        }

        // № 1
        System.out.println("=== № 1.                                    Посетители ===");
        visitors.stream().forEach(System.out::println);
        System.out.println("Количество: " + visitors.stream().count());

        // № 2
        List<Book> uniqueBooks = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .distinct()
                .toList();
        System.out.println("\n=== № 2.                         Книги без повторений ===");
        uniqueBooks.forEach(System.out::println);
        System.out.println("Количество: " + uniqueBooks.size());

        // № 3
        System.out.println("\n=== № 3.                     Книги по году издания ===");
        uniqueBooks.stream()
                .sorted(Comparator.comparingInt(Book::getPublishingYear))
                .forEach(System.out::println);

        // № 4
        boolean hasAusten = visitors.stream()
                .flatMap(v -> v.getFavoriteBooks().stream())
                .anyMatch(b -> "Jane Austen".equals(b.getAuthor()));
        System.out.println("\n=== № 4. Есть книга Jane Austen? ===");
        System.out.println(hasAusten);

        // № 5
        int max = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .max()
                .orElse(0);
        System.out.println("\n=== № 5. Максимум избранных книг у одного посетителя ===");
        System.out.println(max);

        // № 6
        double avg = visitors.stream()
                .mapToInt(v -> v.getFavoriteBooks().size())
                .average()
                .orElse(0);
        List<SmsMessage> messages = visitors.stream()
                .filter(Visitor::isSubscribed)
                .map(v -> {
                    int n = v.getFavoriteBooks().size();
                    String text = n > avg ? "you are a bookworm"
                            : n < avg ? "read more"
                            : "fine";
                    return new SmsMessage(v.getPhone(), text);
                })
                .toList();
        System.out.println("\n=== № 6. SMS (среднее = " + avg + ") ===");
        messages.forEach(System.out::println);
    }
}