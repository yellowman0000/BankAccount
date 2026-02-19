# 🏦 Bank Management System

This document explains the logic behind each class to ensure every team member understands their specific part and how it connects to the overall system and Java memory management (Stack & Heap).

---

## 📑 1. Interface: `IBankAccount`
**Responsibility:** Defines the "Contract" for the system.
- **What to do:** This file contains only method signatures (e.g., `deposit`, `withdraw`) without any implementation bodies.
- **Why we need it:** It ensures that any account type (Normal or Savings) follows the same set of rules and behaviors.
- **Memory Insight:** In the `TestBank` class, we use the Interface type for variables in the **Stack** to demonstrate **Abstraction**.

---

## 👤 2. Entity Class: `Customer`
**Responsibility:** Data storage for the account owner.
- **What to do:** Create private attributes like `name` and a standard constructor.
- **Memory Insight:** This class represents a **Heap Object**. The `BankAccount` will not store the customer's name directly; instead, it stores a **Reference** (memory address) pointing to this `Customer` object in the Heap.

---

## ⚙️ 3. Core Logic: `BankAccount`
**Responsibility:** Managing standard transactions and demonstrating the **Call Stack**.
- **What to do:**
  - Implement the `IBankAccount` interface.
  - **Nested Method Logic:** Design the `deposit()` method to call `validate()`, which then calls `updateBalance()`.
- **Memory Insight:**
  - **Stack Pointer:** This chain of calls demonstrates how multiple **Stack Frames** are pushed onto the stack and "unwind" (pop) once finished.
  - **Encapsulation:** Using `private` for `updateBalance` shows that internal stack operations are hidden from the outside world.

[Image of Java stack and heap memory allocation showing method call stack and object references]

---

## 💰 4. Subclass: `SavingsAccount`
**Responsibility:** Demonstrating Inheritance and Stack Errors.
- **What to do:**
  - Use `extends BankAccount` to reuse existing logic.
  - Implement the `applyInterest()` method.
  - Implement `triggerOverflow()`: A recursive method that calls itself without an exit condition.
- **Memory Insight:**
  - **Inheritance in Heap:** A `SavingsAccount` object in the Heap allocates space for both its own variables and those inherited from the parent class.
  - **StackOverflowError:** The recursive call is used to prove that the **Stack** has a limited size