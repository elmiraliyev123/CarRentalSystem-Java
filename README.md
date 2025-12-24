# Car Rental System (Java)

A Java-based **Car Rental System** project implementing core **OOP concepts** (inheritance, encapsulation, polymorphism) and a simple user interface layer (e.g., Swing/JFrame) depending on the project structure.

> Repository: `CarRentalSystem-Java`

---

## Features
- Vehicle/Car rental workflow (add / list / rent / return) *(depending on implementation)*
- Modular design with separate packages (GUI, Inheritance, MainSys)
- Clean separation of responsibilities (models / system logic / UI)

---

## Tech Stack
- **Java** (JDK 8+ recommended)
- (Optional) **Swing/JFrame** for GUI
- Git & GitHub for version control

---

## Project Structure
CarRentalSystem/
├── src/
│ ├── GUI/
│ ├── Inheritance/
│ └── MainSys/
├── bin/ # compiled output (ignored by git)
├── .gitignore
└── README.md




---

## How to Run

### Option A — Run with an IDE (Recommended)
**IntelliJ IDEA / Eclipse / VS Code**
1. Clone the repository:
   ```bash
   git clone https://github.com/elmiraliyev123/CarRentalSystem-Java.git


Option B — Run from Terminal
Replace MainClassName with your actual main class file name (e.g., Rental, CarRentalSystem, Main, etc.)
Go to project folder:
cd CarRentalSystem
Compile:
mkdir -p bin
javac -d bin $(find src -name "*.java")
Run:
java -cp bin MainClassName
