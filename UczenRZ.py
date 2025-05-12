import csv


class Uczen:
    def __init__(x, imie, wiek, przedmioty):
        x.imie = imie
        x.wiek = wiek
        x.przedmioty = przedmioty

    def __str__(x):
        return f"{x.imie:10} | {x.wiek:<3} | {', '.join(x.przedmioty)}"


def wczytaj_uczniow_z_pliku(uczen):
    uczniowie = {}
    try:
        with open(uczen, newline='', encoding='utf-8') as csvfile:
            reader = csv.reader(csvfile)
            for row in reader:
                if len(row) == 3:
                    imie = row[0]
                    wiek = int(row[1])
                    przedmioty = row[2].split(';')
                    uczniowie[imie] = Uczen(imie, wiek, przedmioty)
    except FileNotFoundError:
        pass
    return uczniowie


def zapisz_uczniow_do_pliku(uczen, uczniowie):
    with open(uczen, mode='w', newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile)
        for uczen_obj in uczniowie.values():
            writer.writerow([uczen_obj.imie, uczen_obj.wiek, ';'.join(uczen_obj.przedmioty)])


def dodaj_ucznia(uczniowie):
    while True:
        imie = input("Podaj imię ucznia ('stop'): ")
        if imie.lower() == 'stop':
            break

        try:
            wiek = int(input("Podaj wiek ucznia: "))
        except ValueError:
            print("⚠️ Błąd")
            continue

        przedmioty_str = input("Podaj ulubione przedmioty (przecinki): ")
        przedmioty = [p.strip() for p in przedmioty_str.split(',')]
        uczniowie[imie] = Uczen(imie, wiek, przedmioty)


def wypisz_uczniow(uczniowie):
    print("\n📋 Lista uczniów:")
    print("IMIĘ      | WIEK | PRZEDMIOTY")
    print("-" * 40)
    for uczen in uczniowie.values():
        print(uczen)


def filtruj_uczniow_po_wieku(uczniowie, minimalny_wiek):
    return list(filter(lambda u: u.wiek > minimalny_wiek, uczniowie.values()))


def main():
    uczen = "uczen.csv"
    uczniowie = wczytaj_uczniow_z_pliku(uczen)

    dodaj_ucznia(uczniowie)
    zapisz_uczniow_do_pliku(uczen, uczniowie)

    wypisz_uczniow(uczniowie)

    try:
        wiek = int(input("\nPodaj minimalny wiek: "))
        starsi_uczniowie = filtruj_uczniow_po_wieku(uczniowie, wiek)
        print(f"\n👴 Uczniowie starsi niz {wiek} lat:")
        for uczen in starsi_uczniowie:
            print(uczen)
    except ValueError:
        print("⚠️ Blad: Nieprawidlowy wiek!")


if __name__ == "__main__":
    main()
