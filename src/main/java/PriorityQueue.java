import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue {

    private ArrayList<Node> lista; // przechowuje węzły
    private Comparator<Node> comparator; // jak porównywać węzły

    // Konstruktor - tworzy pustą kolejkę
    public PriorityQueue(Comparator<Node> comparator) {
        this.lista = new ArrayList<>();
        this.comparator = comparator;
    }

    // Dodaje węzeł do kolejki (na koniec listy)
    public void add(Node wezel) {
        lista.add(wezel);
    }

    // Przeszukuje listę, znajduje najmniejszy element, usuwa go i zwraca
    public Node poll() {
        if (lista.isEmpty()) {
            return null;
        }

        // Szukamy najmniejszego elementu
        Node najmniejszy = lista.get(0);
        int indeksNajmniejszego = 0;

        for (int i = 1; i < lista.size(); i++) {
            Node aktualny = lista.get(i);

            if (comparator.compare(aktualny, najmniejszy) < 0) {
                najmniejszy = aktualny;
                indeksNajmniejszego = i;
            }
        }

        // Usuwamy znaleziony najmniejszy element
        lista.remove(indeksNajmniejszego);

        return najmniejszy;
    }

    // Zwraca liczbę elementów w kolejce
    public int size() {
        return lista.size();
    }

    // Sprawdza czy kolejka jest pusta
    public boolean isEmpty() {
        return lista.isEmpty();
    }
}
