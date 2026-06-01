GENERATOR FAKTUR VAT (Python)

Opis:
Prosta aplikacja desktopowa do generowania faktur VAT w PDF.

Funkcje:
- formularz danych sprzedawcy i nabywcy
- dodawanie produktu (nazwa, cena netto, VAT)
- automatyczne obliczanie VAT i brutto
- automatyczna numeracja faktur
- zapis do pliku PDF
- wybór formy płatności
- ustawienie terminu płatności
- tryb dark mode

Wymagania:
- Python 3.10+
- arial.ttf (w assets)

Instalacja:
1. Zainstaluj biblioteki:
   pip install -r requirements.txt

2. Jeśli nie ma, dodaj do pliku assets czcionke arial.ttf z windowsa

3. Uruchom aplikację:
   python main.py

Struktura projektu:
- main.py -> start programu
- gui.py -> interfejs użytkownika
- pdf_generator.py -> generowanie PDF
- utils.py -> funkcje pomocnicze
- config.py -> ustawienia
- counter.txt -> licznik faktur (tworzy się automatycznie)
