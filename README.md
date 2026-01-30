# Kodowanie Huffmana

Projekt zaliczeniowy - Algorytmy i Struktury Danych

## O projekcie

Program kompresujący pliki tekstowe algorytmem Huffmana.

## Co jest w projekcie

```
src/main/java/
├── Node.java              - klasa węzła drzewa
├── PriorityQueue.java     - własna kolejka priorytetowa na kopcu
├── HuffmanCoding.java     - algorytm kompresji/dekompresji
└── Main.java              - program główny
```

## Jak uruchomić

### Kompilacja:
```bash
javac src/main/java/*.java
```

### Uruchomienie (menu):
```bash
cd src/main/java
java Main
```

### Uruchomienie (argumenty):
```bash
cd src/main/java
java Main -c ../../../plik.txt wynik.huff        # Kompresja
java Main -d wynik.huff odkodowany.txt           # Dekompresja
```

## Menu programu

Program ma 4 opcje:

**1. Demo Kolejki Priorytetowej** - testowanie operacji na kolejce (Insert, Extract-Min, Build, Decrease-Priority, IsEmpty)

**2. Kompresuj plik** - kompresja pliku do .huff

**3. Dekompresuj plik** - dekompresja z powrotem do tekstu

**4. Wyjdź**

## Instrukcja

### 1. Demo Kolejki Priorytetowej

Jak uruchomić:
- Uruchom program: `java Main`
- Wybierz opcję **1**
- Testuj operacje na kolejce:
  - **Insert** - dodaje element do kolejki
  - **Extract Min** - pobiera element o najmniejszym priorytecie
  - **Build** - buduje kolejkę z listy elementów
  - **Decrease Priority** - zmniejsza priorytet wybranego elementu
  - **IsEmpty** - sprawdza czy kolejka jest pusta

### 2. Kompresja pliku

- Przygotuj plik tekstowy (np. `przyklad.txt`)
- Wybierz opcję 2
- Podaj nazwę pliku wejściowego
- Podaj nazwę wyjściową (np. `wynik.huff`)

### 3. Dekompresja pliku

- Wybierz opcję 3
- Podaj plik skompresowany (np. `wynik.huff`)
- Podaj nazwę docelową (np. `odkodowany.txt`)

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

Pierwsza linia: `a:5 b:3 c:2` (słownik)
Reszta: zakodowane bity

## Uwagi

- Własna implementacja PriorityQueue (bez gotowej z Javy)
- Kolejka zrobiona na kopcu binarnym
- Dla małych plików może być większy niż oryginał (bo słownik zajmuje miejsce)
- Działa tylko dla plików tekstowych
