from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

from utils import generate_invoice_number, calculate_vat, calculate_brutto

pdfmetrics.registerFont(TTFont("Arial", "assets/arial.ttf"))


class PDFGenerator:

    @staticmethod
    def generate(path, data):

        c = canvas.Canvas(path, pagesize=A4)
        width, height = A4

        cena = float(data["cena"])
        vat = float(data["vat"])

        wartosc_vat = calculate_vat(cena, vat)
        brutto = calculate_brutto(cena, vat)

        # ==================================================
        # HEADER
        # ==================================================

        c.setFont("Arial", 24)
        c.drawString(50, height - 60, "FAKTURA VAT")

        numer = generate_invoice_number()

        c.setFont("Arial", 11)
        c.drawString(50, height - 90, f"Numer faktury: {numer}")
        c.drawString(50, height - 110, f"Data: {data['data']}")

        # ==================================================
        # SPRZEDAWCA / NABYWCA
        # ==================================================

        c.setFont("Arial", 14)
        c.drawString(50, height - 170, "Sprzedawca")
        c.drawString(320, height - 170, "Nabywca")

        c.setFont("Arial", 11)

        # SPRZEDAWCA
        c.drawString(50, height - 200, f"Nazwa: {data['sprzedawca_nazwa']}")
        c.drawString(50, height - 220, f"Adres: {data['sprzedawca_adres']}")
        c.drawString(50, height - 240, f"NIP: {data['sprzedawca_nip']}")

        # NABYWCA
        c.drawString(320, height - 200, f"Nazwa: {data['nabywca_nazwa']}")
        c.drawString(320, height - 220, f"Adres: {data['nabywca_adres']}")
        c.drawString(320, height - 240, f"NIP: {data['nabywca_nip']}")

        # ==================================================
        # TABELA
        # ==================================================

        start_y = height - 330

        c.setFont("Arial", 11)

        c.drawString(50, start_y, "Lp.")
        c.drawString(90, start_y, "Nazwa produktu")
        c.drawString(300, start_y, "Netto")
        c.drawString(380, start_y, "VAT")
        c.drawString(460, start_y, "Brutto")

        c.line(40, start_y + 20, 550, start_y + 20)
        c.line(40, start_y - 10, 550, start_y - 10)

        c.drawString(50, start_y - 35, "1")
        c.drawString(90, start_y - 35, data["produkt"])
        c.drawString(300, start_y - 35, f"{cena:.2f} PLN")
        c.drawString(380, start_y - 35, f"{vat:.0f}%")
        c.drawString(460, start_y - 35, f"{brutto:.2f} PLN")

        c.rect(40, start_y - 55, 510, 65)

        # ==================================================
        # PODSUMOWANIE
        # ==================================================

        summary_y = start_y - 140

        c.drawString(340, summary_y + 35, "Wartość netto:")
        c.drawString(470, summary_y + 35, f"{cena:.2f} PLN")

        c.drawString(340, summary_y + 10, "VAT:")
        c.drawString(470, summary_y + 10, f"{wartosc_vat:.2f} PLN")

        c.drawString(340, summary_y - 20, "Do zapłaty:")
        c.drawString(470, summary_y - 20, f"{brutto:.2f} PLN")

        c.rect(320, summary_y - 35, 230, 100)

        # ==================================================
        # PŁATNOŚĆ
        # ==================================================

        platnosc_y = summary_y - 90

        c.drawString(50, platnosc_y, f"Forma płatności: {data['platnosc']}")
        c.drawString(50, platnosc_y - 20, f"Termin płatności: {data['termin']}")

        # ==================================================
        # STOPKA
        # ==================================================

        c.setFont("Arial", 10)
        c.drawString(50, 50, "Faktura wygenerowana automatycznie")

        c.save()