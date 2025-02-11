import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
        public static void main(String[] args) {
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");

            String formattedDateTime = now.format(formatter);

            System.out.println(formattedDateTime);
        }
    }
