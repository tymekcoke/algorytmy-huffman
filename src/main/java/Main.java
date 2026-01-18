public class Main {

    public static void main(String[] args) {
        // Sprawdzamy czy są argumenty
        if (args.length < 2) {
            wypiszInstrukcje();
            return;
        }

        String opcja = args[0];
        String plikWejsciowy = args[1];
        String plikWyjsciowy;

        // Jeśli nie podano pliku wyjściowego, używamy domyślnego
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

    private static void wypiszInstrukcje() {
        System.out.println("Użycie: java Main [OPCJA] [PLIK_WEJSCIOWY] [OPCJONALNIE: PLIK_WYJSCIOWY]");
        System.out.println("Opcje:");
        System.out.println("  -c    Kompresja (domyślny output: skompresowany.huff)");
        System.out.println("  -d    Dekompresja (domyślny output: odkodowany.txt)");
    }
}
