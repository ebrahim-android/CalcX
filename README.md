# 🔢 CalcX – Scientific Calculator App

A modern scientific calculator built with Kotlin, using the MVC architecture and a fully custom UI designed in Jetpack Compose.
CalcX focuses on precision, professional design, and clean architecture—created as a learning and showcase project for advanced Android development.

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

## 🔤 Smart Expression Handling

- Real-time expression building.

- Prevents invalid input:

  - No double decimal points.

  - No repeated operators.

  - Auto-inserts 0. when needed.

- Intelligent parentheses management.

- Clean error handling.

## 🧮 Internal Calculation Engine

- Expressions are evaluated using a custom CalculatorEngine built on top of exp4j.

  - Support for:

  - Scientific operations

  - Unary and binary operators

  - Expression validation

  - Future extensibility for custom functions

- Clean API-like architecture to simulate a mini internal math library.

## 🧪 Unit Testing

- CalculatorEngine unit tests are implemented to verify core math operations and scientific functions:

   - cos(0) returns 1.0

   - sin(0) returns 0.0

    - tan(0) returns 0.0

    - Ensures correctness of calculations and prevents regression.

- Future tests will cover CalculatorController behaviors.

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
