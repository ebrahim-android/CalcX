# 🔢 CalcX – Scientific Calculator App

CalcX is a modern scientific calculator built with Kotlin and Jetpack Compose, featuring a fully custom UI and a clean implementation of the MVC architecture.

The project focuses on precision, clear separation of responsibilities, and scalable design, serving both as a learning exercise and a professional showcase for advanced Android development.

CalcX is not just a scientific calculator—its goal is to evolve into a complete calculation toolkit, including unit converters for height, distance, temperature, and more.

---

# 🚀 Features

###  Scientific Calculator UI

- Fully custom button layout:
   - 🔢 **Number pad**
   - ➗ **Basic operators**
   - 🧮 **Scientific functions** (sin, cos, tan, log, ln, sqrt, etc.)
   - 🧱 **Parentheses ( )**
   - 🟰 **Equals**
   - 🔙 **Backspace**
   - ❌ **Clear**

- Professional and responsive design optimized for all screen sizes.

# Smart Expression Handling

CalcX implements an intelligent expression system designed to guide the user toward valid mathematical input while preserving flexibility and speed.
Real-time expression construction with full cursor awareness.

### Prevents invalid input scenarios:

-   Blocks multiple decimal points in the same number.
-   Avoids repeated or conflicting operators.
-   Automatically inserts 0. when a decimal is started without a leading number.
-   Context-aware negative number handling.
-   Smart parenthesis management:
-   Automatically decides between ( and ) based on context.
-   Prevents unbalanced expressions.
-   Graceful error handling to keep the app stable and predictable.

This logic ensures that every expression reaching the engine is mathematically valid.

# Internal Calculation Engine

All calculations are handled by a dedicated internal engine, designed as a standalone and testable component.

***Expressions are evaluated using a custom CalculatorEngine, built on top of exp4j.***

<img width="485" height="70" alt="image" src="https://github.com/user-attachments/assets/ec1fc0da-7a41-4622-9286-258fef052c2b" />

### Supports:

-   Scientific operations (sin, cos, tan, log, ln, √, etc.)
-   Unary and binary operators
-   Power, factorial, and custom symbols
-   Expression validation before evaluation
-   Completely decoupled from UI and Controller logic.
-   Designed with extensibility in mind:
-   Easy to add new functions and operators
-   Structured as a mini internal math library
-   Fully unit tested to guarantee accuracy and prevent regressions.

This engine acts as the mathematical core of the application, prioritizing correctness, clarity, and maintainability.

# Unit Testing & Quality Assurance

CalcX places a strong emphasis on reliable, testable, and maintainable code.
Unit tests are used not only to validate results, but also to protect behavior, prevent regressions, and support future refactors with confidence.

***Current Test Coverage***

At this stage, the project includes three dedicated test layers:

### 1 - CalculatorEngine Tests

Core mathematical logic is fully isolated and tested at the engine level.

Validates basic operations:

-   Addition, subtraction, multiplication, division

Validates scientific functions:

<img width="500" height="600" alt="image" src="https://github.com/user-attachments/assets/65e6710b-7137-4f4b-a2a1-ca92a4199dbc" />


Square root, power, factorial, etc.

Ensures numerical precision using tolerance-based assertions.
These tests guarantee that the calculation engine behaves correctly independently of the UI or controller.

### 2 - CalculatorController Tests

Controller tests focus on user interaction logic and expression handling.

Verifies correct behavior when:

-   Digits and operators are inserted
-   Invalid sequences are blocked (e.g. duplicate operators)
-   Cursor-based insertion and deletion work as expected
-   Special buttons (clear, delete, equals, memory actions) behave correctly
-   Ensures that UI actions translate into valid mathematical expressions.

  <img width="500" height="600" alt="image" src="https://github.com/user-attachments/assets/a2578d5e-376b-4760-bb92-26d5adcf3579" />


Helps maintain correctness as new buttons or behaviors are added.

### 3 - Parameterized Tests (Engine Validation)

To avoid duplicated test logic and improve scalability, parameterized tests are used.

Multiple expressions are validated using a single test structure:

<img width="600" height="700" alt="image" src="https://github.com/user-attachments/assets/d7ab218c-ae5c-4cb6-bc82-fee1ad5e162e" />

Makes it easy to extend coverage by adding new cases.
Improves readability and ensures consistent validation across many expressions.

***Testing Philosophy***

-   Tests are written with a behavior-driven mindset, focusing on what should happen, not implementation details.
-   Each layer is tested in isolation to keep failures easy to diagnose.
-   The testing strategy supports long-term evolution of the project, especially as new scientific features are added.

***Planned Improvements***

-   *Expand parameterized tests to cover edge cases and invalid expressions.*
-   *Add more controller interaction scenarios.*
-   *Increase test coverage alongside new calculator features.*

---

## ⚙️ Tech Stack

| 🧩 Category           | ⚙️ Technology                   |
| --------------------- | ------------------------------- |
| 💡 **Language**       | Kotlin                          |
| 🎨 **UI Framework**   | Jetpack Compose                 |
| 🧱 **Architecture**   | MVC (Model–View–Controller)     |
| 🧮 **Math Engine**    | Custom CalculatorEngine + exp4j |
| 🧭 **State Handling** | Compose State (mutableStateOf)  |
| 🛠 **IDE**            | Android Studio                  |

---

## 🖼️ Screenshots

<img width="240" height="500" alt="image" src="https://github.com/user-attachments/assets/c9a64641-5605-42be-bad3-6264c53c7cce" />

---

### 🗂️ Project Structure

📁 com.playStore.calcx
│
├── 🎨 view/ → Jetpack Compose UI (CalculatorScreen, button layout, display)
│
├── 🎮 controller/ → Handles all user input
│ ├── onDigitPressed()
│ ├── onOperatorPressed()
│ ├── onDecimalPointPressed()
│ ├── onParenthesisPressed()
│ ├── onDeleteLast()
│ └── onEqualsPressed()
│
├── 🧮 engine/ → CalculatorEngine (expression parsing & evaluation)
│
└── ⚙️ utils/ → Helpers, formatting, constants

---

### 🎯 Learning Goals

**CalcX is designed with a focus on**:

- Understanding MVC in modern Android development

- Building scalable UI with Compose

- Designing modular architectures

- Creating a reusable internal API (math engine)

- Strengthening Kotlin skills through a real-world, publishable app

---

### 🧭 Future Improvements

Here are planned enhancements for future versions of **CalcX**:

- 🎛️ **Unit Converters**
  Add length, temperature, currency, and more.

- 🎨 **Themes & Customization**
  Light/Dark mode, dynamic color support.

- 📜 **History Panel**
  Save and navigate past calculations.

- 📱 **Tablet UI Version**
  Responsive layout for large screens.

- ✨ **UI Polish**
  Animations, transitions, and haptic feedback.

---

### 🧠 About

CalcX is built as a serious learning project to explore Compose, MVC, and modular architecture—while creating a calculator app intended for Google Play in the future.

---

### 📬 Contact

✉️ **ebrahimsantana35@gmail.com**  
💼 **LinkedIn:** [Ebrahim Santana](https://www.linkedin.com/in/ebrahim-santana-75a188301/)
