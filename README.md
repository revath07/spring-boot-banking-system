# Mini Banking Backend System

A secure banking backend built using Spring Boot that supports user authentication, account management, and financial transactions.

## Features

### Authentication & Security
- User Registration
- Email OTP Verification
- Password Encryption (BCrypt)
- JWT Authentication
- Transaction PIN
- PIN brute-force protection

### Account Management
- Create bank account
- Fetch account details
- Get all user accounts

### Transactions
- Deposit
- Withdraw
- Transfer between accounts

### Financial Safety
- Daily transfer limit
- Daily withdrawal limit
- Idempotent transactions (duplicate transaction protection)

### Transaction Management
- Transaction history
- Transaction filtering
- Statement by date
- Pagination

### Notifications
- Email notifications for:
  - Deposit
  - Withdraw
  - Transfer

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- MySQL
- Java Mail Sender

## Project Architecture
Cotroller->Service->Repository->Database
