# Kodowanie Huffmana

Projekt zaliczeniowy - Algorytmy i Struktury Danych

## O projekcie

Program kompresujący pliki tekstowe algorytmem Huffmana.

## Co jest w projekcie

```
src/main/java/
├── Node.java              - klasa węzła drzewa
├── PriorityQueue.java     - własna kolejka priorytetowa
├── HuffmanCoding.java     - algorytm kompresji/dekompresji
└── Main.java              - program główny
```

## Jak uruchomić

### Kompilacja:
```bash
mvn clean compile
```

### Kompresja pliku:
```bash
mvn exec:java -Dexec.args="-c plik.txt wynik.huff"
```

### Dekompresja:
```bash
mvn exec:java -Dexec.args="-d wynik.huff odkodowany.txt"
```

## Przykład

```bash
mvn exec:java -Dexec.args="-c przyklad.txt skompresowany.huff"
mvn exec:java -Dexec.args="-d skompresowany.huff odkodowany.txt"
diff przyklad.txt odkodowany.txt
```

## Jak to działa

**Kompresja:**
1. Zliczam ile razy każda litera występuje w tekście
2. Buduję drzewo binarne (najmniejsze częstotliwości łączę)
3. Generuję kody - w lewo to 0, w prawo to 1
4. Zapisuję słownik i zakodowany tekst do pliku

**Dekompresja:**
1. Czytam słownik z pierwszej linii
2. Odbudowuję drzewo
3. Czytam bity i przechodzę po drzewie (0=lewo, 1=prawo)
4. Jak dojdę do liścia to mam literę

## Format pliku

Pierwsza linia: `A:5 B:3 C:2` (słownik)
Reszta: zakodowane bity

## Uwagi

- Własna implementacja PriorityQueue (bez gotowej z Javy)
- Dla małych plików może być większy niż oryginał (bo słownik zajmuje miejsce)
- Działa tylko dla plików tekstowych
