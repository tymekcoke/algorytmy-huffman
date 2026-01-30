import java.util.Arrays;

// Kolejka priorytetowa na kopcu binarnym
public class PriorityQueue {

    private Node[] kopiec; // tablica z węzłami
    private int rozmiar; // ile elementów
    private int pojemnosc; // max rozmiar tablicy

    // Tworzy pustą kolejkę
    public PriorityQueue(int pojemnosc) {
        this.pojemnosc = pojemnosc;
        this.rozmiar = 0;
        this.kopiec = new Node[pojemnosc];
    }

    // Czy pusta
    public boolean isEmpty() {
        return rozmiar == 0;
    }

    // Ile elementów
    public int size() {
        return rozmiar;
    }

    // Dodaje element
    public void add(Node wezel) {
        if (rozmiar == pojemnosc) {
            powieksz();
        }

        kopiec[rozmiar] = wezel;
        wypchajWGore(rozmiar);
        rozmiar++;
    }

    // Pobiera najmniejszy element
    public Node poll() {
        if (isEmpty()) {
            return null;
        }

        Node najmniejszy = kopiec[0];
        kopiec[0] = kopiec[rozmiar - 1];
        rozmiar--;

        if (rozmiar > 0) {
            naprawKopiec(0);
        }

        return najmniejszy;
    }

    // Zmienia priorytet
    public void zmienPriorytet(int indeks, int nowaCzestotliwosc) {
        if (indeks < 0 || indeks >= rozmiar) {
            System.out.println("Błąd: Nieprawidłowy indeks.");
            return;
        }

        if (nowaCzestotliwosc > kopiec[indeks].czestotliwosc) {
            System.out.println("Błąd: Nowy priorytet musi być mniejszy.");
            return;
        }

        kopiec[indeks].czestotliwosc = nowaCzestotliwosc;
        wypchajWGore(indeks);
        System.out.println("Priorytet zmieniony.");
    }

    // Buduje kolejkę z tablicy
    public void zbudujKolejke(Node[] wezly) {
        this.rozmiar = wezly.length;
        this.pojemnosc = wezly.length;
        if (this.pojemnosc == 0) {
            this.pojemnosc = 1;
        }

        this.kopiec = Arrays.copyOf(wezly, this.pojemnosc);

        // Budujemy kopiec od środka
        for (int i = (rozmiar / 2) - 1; i >= 0; i--) {
            naprawKopiec(i);
        }
    }

    // Naprawia kopiec w dół
    private void naprawKopiec(int indeks) {
        int najmniejszy = indeks;
        int leweDziecko = 2 * indeks + 1;
        int praweDziecko = 2 * indeks + 2;

        if (leweDziecko < rozmiar && kopiec[leweDziecko].compareTo(kopiec[najmniejszy]) < 0) {
            najmniejszy = leweDziecko;
        }

        if (praweDziecko < rozmiar && kopiec[praweDziecko].compareTo(kopiec[najmniejszy]) < 0) {
            najmniejszy = praweDziecko;
        }

        if (najmniejszy != indeks) {
            zamien(indeks, najmniejszy);
            naprawKopiec(najmniejszy);
        }
    }

    // Naprawia kopiec w górę
    private void wypchajWGore(int indeks) {
        int rodzic = (indeks - 1) / 2;

        while (indeks > 0 && kopiec[indeks].compareTo(kopiec[rodzic]) < 0) {
            zamien(indeks, rodzic);
            indeks = rodzic;
            rodzic = (indeks - 1) / 2;
        }
    }

    // Zamienia elementy
    private void zamien(int i, int j) {
        Node temp = kopiec[i];
        kopiec[i] = kopiec[j];
        kopiec[j] = temp;
    }

    // Powiększa tablicę
    private void powieksz() {
        pojemnosc = pojemnosc * 2;
        kopiec = Arrays.copyOf(kopiec, pojemnosc);
    }

    // Wyświetla kolejkę
    public void wyswietlKolejke() {
        if (isEmpty()) {
            System.out.println("Kolejka jest pusta.");
            return;
        }

        System.out.println("Stan kolejki:");
        for (int i = 0; i < rozmiar; i++) {
            System.out.print(i + ": " + kopiec[i] + "  ");
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
}
