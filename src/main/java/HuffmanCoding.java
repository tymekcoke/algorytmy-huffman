import java.io.*;
import java.util.HashMap;

public class HuffmanCoding {

    private Node korzen; // korzen drzewa Huffmana
    private HashMap<Character, String> kodyHuffmana; // mapa: znak -> kod binarny

    // Krok 1: Zliczamy ile razy każy znak występuje w tekście
    private HashMap<Character, Integer> zliczZnaki(String tekst) {
        HashMap<Character, Integer> czestotliwosc = new HashMap<>();

        // Przechodzimy przez każdą literę w tekście
        for (int i = 0; i < tekst.length(); i++) {
            char znak = tekst.charAt(i);

            // Jeśli już był ten znak, zwiększamy licznik
            if (czestotliwosc.containsKey(znak)) {
                czestotliwosc.put(znak, czestotliwosc.get(znak) + 1);
            } else {
                // dodajemy z wartością 1
                czestotliwosc.put(znak, 1);
            }
        }

        return czestotliwosc;
    }

    // Krok 2: Budujemy drzewo Huffmana
    private Node zbudujDrzewo(HashMap<Character, Integer> czestotliwosc) {
        // Tworzymy tablicę węzłów z częstotliwościami
        Node[] wezly = new Node[czestotliwosc.size()];
        int i = 0;
        for (Character znak : czestotliwosc.keySet()) {
            wezly[i] = new Node(znak, czestotliwosc.get(znak));
            i++;
        }

        // Budujemy kolejkę z tablicy (algorytm Floyda - O(n))
        PriorityQueue kolejka = new PriorityQueue(256);
        kolejka.zbudujKolejke(wezly);

        // Łączymy węzły aż zostanie jeden (korzeń)
        while (kolejka.size() > 1) {
            Node lewy = kolejka.poll(); // bierzemy najmniejszy
            Node prawy = kolejka.poll(); // bierzemy drugi najmniejszy

            Node rodzic = new Node(lewy, prawy); // łączymy je
            kolejka.add(rodzic); // wrzucamy z powrotem
        }

        return kolejka.poll(); // ostatni element to korzeń
    }

    // Krok 3: Generujemy kody binarne dla każdego znaku (rekurencyjnie)
    private void wygenerujKody(Node wezel, String kod) {
        if (wezel == null) {
            return;
        }

        // Jeśli to liść, zapisujemy kod
        if (wezel.czyLisc()) {
            kodyHuffmana.put(wezel.znak, kod);
            return;
        }

        // W lewo dodajemy 0, w prawo 1
        wygenerujKody(wezel.lewy, kod + "0");
        wygenerujKody(wezel.prawy, kod + "1");
    }

    // KOMPRESJA
    public void compress(String plikWejsciowy, String plikWyjsciowy) {
        try {
            // Czytamy cały plik
            FileInputStream fis = new FileInputStream(plikWejsciowy);
            byte[] bajty = new byte[fis.available()];
            fis.read(bajty);
            fis.close();

            String tekst = new String(bajty);

            if (tekst.isEmpty()) {
                System.out.println("Plik jest pusty!");
                return;
            }

            // Budujemy drzewo
            HashMap<Character, Integer> czestotliwosc = zliczZnaki(tekst);
            korzen = zbudujDrzewo(czestotliwosc);

            // Generujemy kody
            kodyHuffmana = new HashMap<>();
            wygenerujKody(korzen, "");

            // Zapisujemy do pliku
            FileOutputStream fos = new FileOutputStream(plikWyjsciowy);

            // LINIA 1: Słownik (znak:częstotliwość)
            for (Character znak : czestotliwosc.keySet()) {
                if (znak == '\n') {
                    fos.write("\\n:".getBytes());
                } else if (znak == '\r') {
                    fos.write("\\r:".getBytes());
                } else {
                    fos.write(znak);
                    fos.write(':');
                }
                fos.write((czestotliwosc.get(znak) + " ").getBytes());
            }
            fos.write('\n');

            // LINIA 2+: Zakodowany tekst w bitach
            int bufor = 0; // tutaj zbieramy 8 bitów zanim zapiszemy
            int liczbaBitow = 0; // ile bitów już mamy w buforze

            // Przechodzimy przez każdy znak w tekście
            for (int i = 0; i < tekst.length(); i++) {
                char znak = tekst.charAt(i);
                String kod = kodyHuffmana.get(znak); // np. 'a' -> "101"

                // Przepisujemy każdy bit ('0' lub '1') do bufora
                for (int j = 0; j < kod.length(); j++) {
                    bufor = bufor << 1; // robimy miejsce na nowy bit (przesuwamy w lewo)

                    if (kod.charAt(j) == '1') {
                        bufor = bufor | 1; // ustawiamy ostatni bit na 1
                    }
                    // jeśli był '0' to nic nie robimy, bo już jest 0

                    liczbaBitow++;

                    // Jak mamy pełny bajt (8 bitów), zapisujemy do pliku
                    if (liczbaBitow == 8) {
                        fos.write(bufor);
                        bufor = 0; // czyścimy bufor
                        liczbaBitow = 0; // resetujemy licznik
                    }
                }
            }

            // Jeśli zostały jakieś bity (<8) - dopełniamy zerami
            if (liczbaBitow > 0) {
                bufor = bufor << (8 - liczbaBitow); // przesuwamy w lewo, żeby dopełnić zerami
                fos.write(bufor);
            }

            fos.close();
            System.out.println("Kompresja zakończona!");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }

    // DEKOMPRESJA
    public void decompress(String plikWejsciowy, String plikWyjsciowy) {
        try {
            FileInputStream fis = new FileInputStream(plikWejsciowy);

            // CZYTAMY LINIĘ 1: Słownik
            StringBuilder slownikBuilder = new StringBuilder(); // StringBuilder bo jest szybszy - zwykły String jest niezmienny, za każdym razem jak coś dodajemy toworzy się nowy String
            int bajt; // Zmienna do przechowania kodu ASCII char'a
            // fis.read() zwróci '-1' gdy będzie koniec pliku
            // kończymy na '\n' bo tak się kończy pierwsza linia!
            while ((bajt = fis.read()) != -1 && bajt != '\n') {
                slownikBuilder.append((char) bajt);
            }

            String slownikLinia = slownikBuilder.toString();

            // Parsujemy słownik
            HashMap<Character, Integer> czestotliwosc = new HashMap<>();
            long sumaZnakow = 0;

            int pozycja = 0;
            while (pozycja < slownikLinia.length()) {
                // Znajdujemy dwukropek
                int dwukropek = slownikLinia.indexOf(':', pozycja);
                if (dwukropek == -1) break;

                // Znak to jeden znak przed dwukropkiem
                String klucz = slownikLinia.substring(pozycja, dwukropek);

                // Znajdujemy spację po liczbie (lub koniec stringa)
                int spacja = slownikLinia.indexOf(' ', dwukropek);
                if (spacja == -1) spacja = slownikLinia.length();

                // Liczba to wszystko między dwukropkiem a spacją
                String wartoscStr = slownikLinia.substring(
                    dwukropek + 1,
                    spacja
                );

                // Dekodujemy znak
                char znak;
                if (klucz.equals("\\n")) {
                    znak = '\n';
                } else if (klucz.equals("\\r")) {
                    znak = '\r';
                } else if (klucz.length() > 0) {
                    znak = klucz.charAt(0);
                } else {
                    // Pusty klucz oznacza że znak był przed początkiem - pomijamy
                    pozycja = spacja + 1;
                    continue;
                }

                int wartosc = Integer.parseInt(wartoscStr);
                czestotliwosc.put(znak, wartosc);
                sumaZnakow += wartosc;

                // Przeskakujemy do następnej pary
                pozycja = spacja + 1;
            }

            // Odbudowujemy drzewo
            korzen = zbudujDrzewo(czestotliwosc);

            // Dekodujemy bajty do StringBuilder
            StringBuilder wynik = new StringBuilder();
            Node aktualny = korzen; // zaczynamy od korzenia drzewa
            long zdekodowaneZnaki = 0;

            // Czytamy bajt po bajcie z pliku
            while (zdekodowaneZnaki < sumaZnakow && (bajt = fis.read()) != -1) {
                // Przetwarzamy każdy bit z bajtu (od lewej do prawej: 7,6,5,4,3,2,1,0)
                for (int i = 7; i >= 0 && zdekodowaneZnaki < sumaZnakow; i--) {
                    int bit = (bajt >> i) & 1; // wyciągamy i-ty bit (0 lub 1)

                    // W drzewie idziemy w lewo (0) lub prawo (1)
                    if (bit == 0) {
                        aktualny = aktualny.lewy; // bit=0 -> lewo
                    } else {
                        aktualny = aktualny.prawy; // bit=1 -> prawo
                    }

                    // Jeśli doszliśmy do liścia (końca kodu), to mamy znak!
                    if (aktualny.czyLisc()) {
                        wynik.append(aktualny.znak); // zapisujemy odkodowany znak
                        zdekodowaneZnaki++;
                        aktualny = korzen; // wracamy do korzenia żeby odkodować następny znak
                    }
                }
            }

            fis.close();

            // Zapisujemy wynik do pliku
            FileOutputStream fos = new FileOutputStream(plikWyjsciowy);
            fos.write(wynik.toString().getBytes());
            fos.close();

            System.out.println("Dekompresja zakończona!");
        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }
    }
}
