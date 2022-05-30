package util;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputReader {

    private final Scanner scanner;
    public static final String EMAIL_REGEX = "^\\w+@\\w+.\\w+(.\\w*)?";
    public static final String PHONE_NUMBER_FORMAT = "^\\(\\d{3}\\)-\\d{3}-\\d{4}$";

    public InputReader(InputStream in) {
        scanner = new Scanner(in);
    }

    public String getString(String msg, String err, Predicate<String> tester) {
        while (true) {
            System.out.print(msg);
            String str = scanner.nextLine().trim();
            if (tester.test(str)) {
                return str;
            }
            System.out.println(err);
        }
    }

    public String getString(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }

    public int getInteger(String msg, String err, Predicate<Integer> tester) {
        int num;
        while (true) {
            System.out.print(msg);
            try {
                num = Integer.parseInt(scanner.nextLine());
                if (tester.test(num)) {
                    return num;
                }
            } catch (NumberFormatException ex) {
                System.err.println(ex.getMessage());
            }
            System.out.println(err);
        }
    }

    public LocalDate getDate(String msg, String err) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        while (true) {
            System.out.print(msg);
            String str = scanner.nextLine();
            try {
                return df.parse(str).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (ParseException ex) {
                System.out.println(err);
            }
        }
    }
}
