import java.io.*;
import java.net.*;
import java.util.regex.*;
import java.util.Scanner;

public class Finder {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Podaj pierwsze trzy cyfry konta: ");
            String firstThreeDigits = scanner.nextLine();

            if (firstThreeDigits.length() != 3 || !firstThreeDigits.matches("\\d{3}")) {
                System.out.println("Nieprawidłowy format cyfr.");
                return;
            }

            String urlString = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";
            URL url = new URL(urlString);

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            boolean found = false;

            Pattern pattern = Pattern.compile("^\\d{3}\\s+([A-Z]{3})\\s+(.+)$");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.trim());

                if (matcher.matches()) {
                    String bankPrefix = matcher.group(1);
                    String bankName = matcher.group(2);

                    if (bankPrefix.equals(firstThreeDigits)) {
                        System.out.println("Konto znaleziono w banku: " + bankName);
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Nie znaleziono banku.");
            }

            reader.close();

        } catch (MalformedURLException e) {
            System.out.println("Błąd URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Błąd podczas odczytu pliku: " + e.getMessage());
        }
    }
}
