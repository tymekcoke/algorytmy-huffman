import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Jeśli są argumenty, uruchamiamy z argumentami
        if (args.length >= 2) {
            uruchomZArgumentami(args);
        } else {
            // Jeśli nie ma, to menu
            uruchomMenu();
        }
    }

    // Uruchomienie z argumentami
    private static void uruchomZArgumentami(String[] args) {
        String opcja = args[0];
        String plikWejsciowy = args[1];
        String plikWyjsciowy;

        if (args.length >= 3) {
            plikWyjsciowy = args[2];
        } else {
            if (opcja.equals("-c")) {
                plikWyjsciowy = "skompresowany.huff";
            } else if (opcja.equals("-d")) {
                plikWyjsciowy = "odkodowany.txt";
            } else {
                wypiszInstrukcje();
                return;
            }
        }

        HuffmanCoding huffman = new HuffmanCoding();

        if (opcja.equals("-c")) {
            System.out.println("[KOMPRESJA] Plik wyjściowy: " + plikWyjsciowy);
            huffman.compress(plikWejsciowy, plikWyjsciowy);
        } else if (opcja.equals("-d")) {
            System.out.println("[DEKOMPRESJA] Plik wyjściowy: " + plikWyjsciowy);
            huffman.decompress(plikWejsciowy, plikWyjsciowy);
        } else {
            System.out.println("Błąd: Nieznana opcja " + opcja);
            wypiszInstrukcje();
        }
    }

    // Interaktywne menu
    private static void uruchomMenu() {
        Scanner scanner = new Scanner(System.in);
        HuffmanCoding huffman = new HuffmanCoding();
        boolean dziala = true;

        System.out.println("=== PROGRAM DO KODOWANIA HUFFMANA ===");

        while (dziala) {
            System.out.println("\n--- MENU GŁÓWNE ---");
            System.out.println("1. Demo Kolejki Priorytetowej");
            System.out.println("2. Kompresuj plik");
            System.out.println("3. Dekompresuj plik");
            System.out.println("4. Wyjdź");
            System.out.print("Twój wybór: ");

            String wybor = scanner.nextLine();

            switch (wybor) {
                case "1":
                    uruchomDemoKolejki(scanner);
                    break;
                case "2":
                    System.out.print("Podaj nazwę pliku do kompresji (np. przyklad.txt): ");
                    String plikKompresji = scanner.nextLine();
                    System.out.print("Podaj nazwę pliku wynikowego (np. wynik.huff): ");
                    String plikWynikowyKompresji = scanner.nextLine();
                    huffman.compress(plikKompresji, plikWynikowyKompresji);
                    break;
                case "3":
                    System.out.print("Podaj nazwę pliku do dekompresji (np. wynik.huff): ");
                    String plikDekompresji = scanner.nextLine();
                    System.out.print("Podaj nazwę pliku wyjściowego (np. odkodowany.txt): ");
                    String plikWynikowyDekompresji = scanner.nextLine();
                    huffman.decompress(plikDekompresji, plikWynikowyDekompresji);
                    break;
                case "4":
                    dziala = false;
                    System.out.println("Do widzenia!");
                    break;
                default:
                    System.out.println("Nieprawidłowa opcja. Spróbuj ponownie.");
            }
        }

        scanner.close();
    }

    // Demo kolejki priorytetowej
    private static void uruchomDemoKolejki(Scanner scanner) {
        PriorityQueue kolejka = new PriorityQueue(10);
        boolean wDemo = true;

        System.out.println("\n=== DEMO KOLEJKI PRIORYTETOWEJ ===");
        System.out.println("Kolejka jest teraz pusta.");

        while (wDemo) {
            System.out.println("\n--- OPERACJE KOLEJKI ---");
            System.out.println("1. Dodaj element (Insert)");
            System.out.println("2. Pobierz element o najmniejszym priorytecie (Extract Min)");
            System.out.println("3. Zbuduj całą kolejkę z danych użytkownika (Build)");
            System.out.println("4. Zmień priorytet elementu (Decrease Priority)");
            System.out.println("5. Sprawdź czy kolejka jest pusta (IsEmpty)");
            System.out.println("6. Pokaż zawartość kolejki");
            System.out.println("7. Powrót do menu głównego");
            System.out.print("Wybór: ");

            String wybor = scanner.nextLine();

            try {
                switch (wybor) {
                    case "1":
                        System.out.print("Podaj znak: ");
                        String wejscieZnak = scanner.nextLine();
                        char znak = wejscieZnak.length() > 0 ? wejscieZnak.charAt(0) : '?';

                        System.out.print("Podaj częstotliwość: ");
                        int czestotliwosc = Integer.parseInt(scanner.nextLine());

                        kolejka.add(new Node(znak, czestotliwosc));
                        System.out.println("✓ Dodano element [" + znak + ": " + czestotliwosc + "]");
                        break;

                    case "2":
                        Node minimum = kolejka.poll();
                        if (minimum != null) {
                            System.out.println("✓ Pobrano: " + minimum);
                        } else {
                            System.out.println("✗ Kolejka jest pusta!");
                        }
                        break;

                    case "3":
                        System.out.println("--- BUDOWANIE KOLEJKI ---");
                        System.out.print("Ile elementów? ");
                        int liczba = Integer.parseInt(scanner.nextLine());

                        if (liczba <= 0) {
                            System.out.println("✗ Liczba musi być większa od 0.");
                            break;
                        }

                        Node[] noweWezly = new Node[liczba];
                        for (int i = 0; i < liczba; i++) {
                            System.out.println("Element " + (i + 1) + "/" + liczba + ":");
                            System.out.print("  Znak: ");
                            String s = scanner.nextLine();
                            char nowyZnak = s.length() > 0 ? s.charAt(0) : '?';

                            System.out.print("  Częstotliwość: ");
                            int nowaCzestotliwosc = Integer.parseInt(scanner.nextLine());

                            noweWezly[i] = new Node(nowyZnak, nowaCzestotliwosc);
                        }

                        kolejka.zbudujKolejke(noweWezly);
                        System.out.println("✓ Kolejka zbudowana.");
                        break;

                    case "4":
                        System.out.println("Indeksy:");
                        kolejka.wyswietlKolejke();

                        System.out.print("Indeks elementu: ");
                        int indeks = Integer.parseInt(scanner.nextLine());

                        System.out.print("Nowa częstotliwość (mniejsza): ");
                        int nowyCzestotliwosc = Integer.parseInt(scanner.nextLine());

                        kolejka.zmienPriorytet(indeks, nowyCzestotliwosc);
                        break;

                    case "5":
                        if (kolejka.isEmpty()) {
                            System.out.println("✓ Kolejka PUSTA");
                        } else {
                            System.out.println("✓ Kolejka NIE jest pusta (rozmiar: " + kolejka.size() + ")");
                        }
                        break;

                    case "6":
                        kolejka.wyswietlKolejke();
                        break;

                    case "7":
                        wDemo = false;
                        break;

                    default:
                        System.out.println("Nie ma takiej opcji.");
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ Błąd: Wprowadzono tekst zamiast liczby!");
            } catch (Exception e) {
                System.out.println("✗ Wystąpił błąd: " + e.getMessage());
            }
        }
    }

    private static void wypiszInstrukcje() {
        System.out.println("Użycie: java Main [OPCJA] [PLIK_WEJSCIOWY] [OPCJONALNIE: PLIK_WYJSCIOWY]");
        System.out.println("Opcje:");
        System.out.println("  -c    Kompresja (domyślny output: skompresowany.huff)");
        System.out.println("  -d    Dekompresja (domyślny output: odkodowany.txt)");
        System.out.println();
        System.out.println("Lub uruchom bez argumentów aby włączyć interaktywne menu.");
    }
}
