// Węzeł drzewa Huffmana
public class Node {

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

    // Konstruktor dla węzła wewnętrznego - łączenie dwóch węzłów
    public Node(Node lewy, Node prawy) {
        this.znak = '\0'; // węzeł wewnętrzny nie ma znaku
        this.czestotliwosc = lewy.czestotliwosc + prawy.czestotliwosc;
        this.lewy = lewy;
        this.prawy = prawy;
    }

    // Sprawdza czy to liść (czy ma znak)
    public boolean czyLisc() {
        return lewy == null && prawy == null;
    }
}
