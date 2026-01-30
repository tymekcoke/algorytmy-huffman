// Węzeł drzewa Huffmana
public class Node implements Comparable<Node> {

    char znak; // np. 'a' lub 'b'
    int czestotliwosc; // ile razy wystepuje
    Node lewy; // lewe dziecko
    Node prawy; // prawe dziecko

    // Konstruktor dla liścia
    public Node(char znak, int czestotliwosc) {
        this.znak = znak;
        this.czestotliwosc = czestotliwosc;
        this.lewy = null;
        this.prawy = null;
    }

    // Konstruktor dla węzła wewnętrznego
    public Node(Node lewy, Node prawy) {
        this.znak = '\0'; // nie ma znaku
        this.czestotliwosc = lewy.czestotliwosc + prawy.czestotliwosc;
        this.lewy = lewy;
        this.prawy = prawy;
    }

    // Sprawdza czy to liść
    public boolean czyLisc() {
        return lewy == null && prawy == null;
    }

    // Porównuje po częstotliwości
    @Override
    public int compareTo(Node inny) {
        return this.czestotliwosc - inny.czestotliwosc;
    }

    // Wypisuje węzeł
    @Override
    public String toString() {
        String wyswietlanyZnak = (znak == '\0') ? "WEZEL" : "'" + znak + "'";
        return "[" + wyswietlanyZnak + ", freq: " + czestotliwosc + "]";
    }
}
