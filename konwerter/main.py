import tkinter as tk

root = tk.Tk()
root.title("Prosty Konwerter Jednostek")

label_prompt = tk.Label(root, text="Wprowadź wartość (metry):")
label_prompt.pack()

entry_value = tk.Entry(root)
entry_value.pack()

button_convert = tk.Button(root, text="Konwertuj na stopy")
button_convert.pack()

label_result = tk.Label(root, text="Wynik: 0.0 stóp")
label_result.pack()

root.mainloop()
