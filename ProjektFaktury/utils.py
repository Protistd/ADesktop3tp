import os
from datetime import datetime

COUNTER_FILE = "counter.txt"


def generate_invoice_number():

    year = datetime.now().strftime("%Y")

    # jeśli plik nie istnieje -> tworzymy
    if not os.path.exists(COUNTER_FILE):
        with open(COUNTER_FILE, "w") as f:
            f.write("1")

    # czytamy numer
    with open(COUNTER_FILE, "r") as f:
        number = int(f.read().strip())

    # zwiększamy i zapisujemy
    with open(COUNTER_FILE, "w") as f:
        f.write(str(number + 1))

    # format FV/0001/2026
    return f"FV/{number:04d}/{year}"


def calculate_vat(cena, vat):
    return (cena * vat) / 100


def calculate_brutto(cena, vat):
    return cena + calculate_vat(cena, vat)