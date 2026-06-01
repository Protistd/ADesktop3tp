import customtkinter as ctk
from tkinter import filedialog, messagebox
from datetime import datetime

from pdf_generator import PDFGenerator
from config import APP_WIDTH, APP_HEIGHT


class InvoiceApp(ctk.CTk):

    def __init__(self):
        super().__init__()

        self.title("Generator Faktur")
        self.geometry(f"{APP_WIDTH}x{APP_HEIGHT}")

        self.build_ui()

    def build_ui(self):

        ctk.CTkLabel(
            self,
            text="Generator Faktur",
            font=("Arial", 28, "bold")
        ).pack(pady=20)

        self.frame = ctk.CTkScrollableFrame(
            self,
            width=540,
            height=700
        )

        self.frame.pack(fill="both", expand=True, padx=20, pady=10)

        # SPRZEDAWCA
        self.entry_sprzedawca_nazwa = self.make_entry("SPRZEDAWCA - nazwa")
        self.entry_sprzedawca_adres = self.make_entry("SPRZEDAWCA - adres")
        self.entry_sprzedawca_nip = self.make_entry("SPRZEDAWCA - NIP")

        # NABYWCA
        self.entry_nabywca_nazwa = self.make_entry("NABYWCA - nazwa")
        self.entry_nabywca_adres = self.make_entry("NABYWCA - adres")
        self.entry_nabywca_nip = self.make_entry("NABYWCA - NIP")

        # PRODUKT
        self.entry_produkt = self.make_entry("Produkt")
        self.entry_cena = self.make_entry("Cena netto")
        self.entry_vat = self.make_entry("VAT (%)")

        # PŁATNOŚĆ
        ctk.CTkLabel(self.frame, text="Forma płatności").pack()

        self.platnosc = ctk.StringVar(value="Przelew")

        ctk.CTkOptionMenu(
            self.frame,
            values=["Gotówka", "Przelew"],
            variable=self.platnosc
        ).pack()

        self.entry_termin = self.make_entry("Termin płatności")
        self.entry_termin.insert(0, datetime.now().strftime("%Y-%m-%d"))

        ctk.CTkButton(
            self.frame,
            text="Generuj PDF",
            command=self.generate_pdf
        ).pack(pady=20)

    def make_entry(self, text):

        ctk.CTkLabel(self.frame, text=text).pack(pady=(10, 2))

        entry = ctk.CTkEntry(self.frame, width=400, height=40)
        entry.pack()

        return entry

    def generate_pdf(self):

        data = {
            "sprzedawca_nazwa": self.entry_sprzedawca_nazwa.get(),
            "sprzedawca_adres": self.entry_sprzedawca_adres.get(),
            "sprzedawca_nip": self.entry_sprzedawca_nip.get(),

            "nabywca_nazwa": self.entry_nabywca_nazwa.get(),
            "nabywca_adres": self.entry_nabywca_adres.get(),
            "nabywca_nip": self.entry_nabywca_nip.get(),

            "produkt": self.entry_produkt.get(),
            "cena": self.entry_cena.get(),
            "vat": self.entry_vat.get(),

            "platnosc": self.platnosc.get(),
            "termin": self.entry_termin.get(),

            "data": datetime.now().strftime("%Y-%m-%d")
        }

        path = filedialog.asksaveasfilename(
            defaultextension=".pdf",
            filetypes=[("PDF", "*.pdf")]
        )

        if not path:
            return

        try:
            PDFGenerator.generate(path, data)
            messagebox.showinfo("OK", "Faktura wygenerowana")

        except Exception as e:
            messagebox.showerror("Błąd", str(e))