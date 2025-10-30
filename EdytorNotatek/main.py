import tkinter as tk


bufor_notatki = ""

def zmien_tryb():
    if tryb_var.get() == "Normalny":
        text_area.config(state=tk.NORMAL)
    else:
        text_area.config(state=tk.DISABLED)

def buforuj():
    global bufor_notatki
    if bufor_var.get() == 1:
        bufor_notatki = text_area.get("1.0", tk.END)
        text_area.config(state=tk.NORMAL)
        text_area.delete("1.0", tk.END)
        text_area.insert(tk.END, "Treść została bezpiecznie zbuforowana i ukryta.")
        text_area.config(state=tk.DISABLED)
    else:
        text_area.config(state=tk.NORMAL)
        text_area.delete("1.0", tk.END)
        text_area.insert(tk.END, bufor_notatki)
        if tryb_var.get() == "Tylko do odczytu":
            text_area.config(state=tk.DISABLED)

root = tk.Tk()
root.title("Prosty Edytor Notatek")

text_area = tk.Text(root, height=10, width=50)
text_area.insert(tk.END, "Witaj w prostym edytorze notatek!")
text_area.pack(pady=10)

tryb_var = tk.StringVar(value="Normalny")

frame_tryb = tk.Frame(root)
frame_tryb.pack(pady=5)

tk.Label(frame_tryb, text="Tryb edycji:").pack(side=tk.LEFT)
tk.Radiobutton(frame_tryb, text="Normalny", variable=tryb_var, value="Normalny", command=zmien_tryb).pack(side=tk.LEFT)
tk.Radiobutton(frame_tryb, text="Tylko do odczytu", variable=tryb_var, value="Tylko do odczytu", command=zmien_tryb).pack(side=tk.LEFT)

bufor_var = tk.IntVar()

check_bufor = tk.Checkbutton(root, text="Buforuj i ukryj notatkę", variable=bufor_var, command=buforuj)
check_bufor.pack(pady=10)

root.mainloop()
