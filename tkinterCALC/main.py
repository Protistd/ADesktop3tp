import tkinter as tk
from tkinter import messagebox

class Kalkulator:
    def __init__(self, root):
        self.root = root
        self.root.title("kalkulejtor")
        self.root.geometry("300x400")
        self.root.resizable(False, False)

        self.wyrazenie = ""

        self.wynik_var = tk.StringVar()

        self.stworz_gui()

    def stworz_gui(self):
        pole_wyniku = tk.Entry(self.root, textvariable=self.wynik_var, font=("Arial", 20), state='readonly', justify='right')
        pole_wyniku.grid(row=0, column=0, columnspan=4, sticky='nsew', padx=5, pady=5)

        przyciski = [
            ('7', 1, 0), ('8', 1, 1), ('9', 1, 2), ('/', 1, 3),
            ('4', 2, 0), ('5', 2, 1), ('6', 2, 2), ('*', 2, 3),
            ('1', 3, 0), ('2', 3, 1), ('3', 3, 2), ('-', 3, 3),
            ('0', 4, 0), ('.', 4, 1), ('C', 4, 2), ('+', 4, 3),
            ('=', 5, 0, 4)
        ]

        for (text, row, col, colspan) in [p if len(p) == 4 else (*p, 1) for p in przyciski]:
            b = tk.Button(self.root, text=text, font=("Arial", 18), command=lambda t=text: self.kliknij(t))
            b.grid(row=row, column=col, columnspan=colspan, sticky='nsew', padx=2, pady=2)

        for i in range(6):
            self.root.grid_rowconfigure(i, weight=1)
        for i in range(4):
            self.root.grid_columnconfigure(i, weight=1)

    def kliknij(self, wartosc):
        if wartosc == "C":
            self.wyrazenie = ""
            self.wynik_var.set("")
        elif wartosc == "=":
            try:
                wynik = eval(self.wyrazenie)
                self.wynik_var.set(str(wynik))
                self.wyrazenie = str(wynik)
            except ZeroDivisionError:
                self.wynik_var.set("pamietaj cholero!")
                self.wyrazenie = ""
            except Exception:
                self.wynik_var.set("blad wyrazenia")
                self.wyrazenie = ""
        else:
            self.wyrazenie += wartosc
            self.wynik_var.set(self.wyrazenie)

if __name__ == "__main__":
    root = tk.Tk()
    app = Kalkulator(root)
    root.mainloop()
