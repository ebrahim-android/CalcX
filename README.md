<p align="center">🔢 CalcX</p>
<p align="center">The Precision-Engineered Scientific Toolkit for Android</p>
<p align="center"> <img src="https://img.shields.io/badge/Kotlin-1.9+-purple?style=for-the-badge&logo=kotlin" alt="Kotlin"> <img src="https://img.shields.io/badge/Jetpack_Compose-Latest-green?style=for-the-badge&logo=jetpackcompose" alt="Compose"> <img src="https://img.shields.io/badge/Architecture-MVC-blue?style=for-the-badge" alt="MVC"> <img src="https://img.shields.io/badge/Tests-Passing-brightgreen?style=for-the-badge&logo=junit5" alt="Tests"> </p>

---

CalcX is a high-performance scientific calculator built with a focus on mathematical precision and clean architecture. Built entirely with Kotlin and Jetpack Compose, it serves as a professional showcase of advanced Android development, separating complex business logic from a responsive, modern UI.

>  More than just a calculator: CalcX is evolving into a comprehensive toolkit, including unit converters for distance, temperature, and more.

---

## ✨ Key Features🧮 
Professional Scientific UICustom Layouts: Optimized number pads and operator grids.Scientific Functions: $sin, cos, tan, log, ln, \sqrt{x},$ and more.Responsive Design: Seamlessly adapts to any screen aspect ratio.

## 🧠 Smart Expression Handling

***The UI isn't just a grid of buttons; it's an intelligent input system that prevents errors before they happen:***

-   Anti-Error Logic: Blocks duplicate decimals and conflicting operators.
-   Auto-Correction: Automatically adds 0. for leading decimals and manages negative numbers contextually.
-   Smart Parentheses: Intelligently toggles between ( and ) based on mathematical context.
-   Real-time Cursor Awareness: Edit any part of the expression with live feedback.

***Prevents invalid input scenarios:***

-   Blocks multiple decimal points in the same number.
-   Avoids repeated or conflicting operators.
-   Automatically inserts 0. when a decimal is started without a leading number.
-   Context-aware negative number handling.
-   Smart parenthesis management:
-   Automatically decides between ( and ) based on context.
-   Prevents unbalanced expressions.
-   Graceful error handling to keep the app stable and predictable.

This logic ensures that every expression reaching the engine is mathematically valid.

## ⚙️ Internal Calculation Engine

All calculations are handled by a dedicated internal engine, designed as a standalone and testable component.

***Expressions are evaluated using a custom CalculatorEngine, built on top of exp4j.***

<p align="center"> <img width="500" src="https://github.com/user-attachments/assets/ec1fc0da-7a41-4622-9286-258fef052c2b" alt="Engine Preview" style="border-radius: 10px;"/> </p>

**Supports:**

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

## 🧪 Unit Testing & Quality Assurance

CalcX maintains a "Logic-First" philosophy. Every mathematical operation is verified through a multi-layered testing strategy.<details><summary><b>1. Engine Validation (Click to expand)</b></summary>Validates the raw mathematical output, ensuring $1 + 1$ is always $2$ and complex scientific functions return precise values using tolerance-based assertions.<img width="450" src="https://github.com/user-attachments/assets/65e6710b-7137-4f4b-a2a1-ca92a4199dbc" /></details><details><summary><b>2. Controller Interaction (Click to expand)</b></summary>Ensures that user clicks translate into valid expressions. It tests cursor placement, deletion logic, and state management.<img width="450" src="https://github.com/user-attachments/assets/a2578d5e-376b-4760-bb92-26d5adcf3579" /></details><details><summary><b>3. Parameterized Testing (Click to expand)</b></summary>We run hundreds of calculations through a single test suite to ensure edge-case stability.<img width="450" src="https://github.com/user-attachments/assets/d7ab218c-ae5c-4cb6-bc82-fee1ad5e162e" /></details>

***Planned Improvements***

-   *Expand parameterized tests to cover edge cases and invalid expressions.*
-   *Add more controller interaction scenarios.*
-   *Increase test coverage alongside new calculator features.*

---

📱 Visual Showcase
<p align="center"> <img width="280" alt="CalcX Screenshot" src="https://github.com/user-attachments/assets/c9a64641-5605-42be-bad3-6264c53c7cce" style="border-radius: 20px; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);"/> </p>

---

### 🗂️ Project Structure

<img width="658" height="216" alt="image" src="https://github.com/user-attachments/assets/e27664df-fb00-48bf-b84d-a447d54349ea" />


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

📬 Connect
<p align="left"> <a href="mailto:ebrahimsantana35@gmail.com"> <img src="https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white" /> </a> <a href="https://www.linkedin.com/in/ebrahim-santana-75a188301/"> <img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" /> </a> </p>

<p align="center">Developed with ❤️ by Ebrahim Santana</p>
