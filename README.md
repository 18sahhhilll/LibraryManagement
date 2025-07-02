# 📚 Library Management System (Java + MySQL)

A simple, console-based **Library Management System** written in Java using **JDBC (MySQL Connector/J)**.  
It allows you to manage books and members efficiently through full **CRUD operations** and book issue/return tracking.

---

## 🧾 Features

### 📘 Book Management
- ➕ Add new books with title, author, ISBN, and category  
- 🔍 Search books by title (supports partial search)  
- 👁 View all books  
- 🔄 Automatically update availability status (issue/return)  
- ❌ Delete books (including their borrow history)  

### 👤 Member Management
- ➕ Register new members  
- ❌ Delete members (along with their borrow history)  

### 🔁 Book Transactions
- ✅ Issue books to members (marks book as unavailable)  
- 📥 Return books (marks book as available)  
- 🧾 Automatically track borrow/return history  

---

## 🛠️ Technologies Used

- **Java (JDK 8+)**  
- **MySQL**  
- **JDBC (MySQL Connector/J)**  

---

## 📁 Project Structure

```
LibraryManagement/
├── lib/
│   └── mysql-connector-j-9.3.0.jar       # MySQL JDBC driver
├── src/
│   ├── db/
│   │   └── DBConnection.java             # DB utility class
│   ├── manager/
│   │   └── LibraryManager.java           # Core logic for library operations
├── Main.java                             # CLI menu and user interaction
├── settings.json                         # VS Code project settings
```

---

## 🗃️ Database Schema (MySQL)

```sql
CREATE DATABASE IF NOT EXISTS LibraryDB;
USE LibraryDB;

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(50),
    category VARCHAR(100),
    available BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS borrow_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    book_id INT,
    borrow_date DATE,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
```

---

## ⚙️ Setup Instructions

### ✅ Prerequisites

- Java JDK 8 or above  
- MySQL Server running on `localhost:3306`  
- `lib/mysql-connector-j-9.3.0.jar` in your project  

### 🧩 Steps

1. **Set up the database:**  
   Run the SQL schema above using MySQL CLI or phpMyAdmin.

2. **Update DB credentials:**  
   In `DBConnection.java`, replace with your credentials:
   ```java
   private static final String USER = "root";         // your MySQL username
   private static final String PASSWORD = "password"; // your MySQL password
   ```

3. **Compile and Run the Project:**

#### 🔧 For Windows:
```cmd
javac -cp "lib/mysql-connector-j-9.3.0.jar" -d bin src\db\DBConnection.java src\manager\LibraryManager.java Main.java
java -cp "bin;lib/mysql-connector-j-9.3.0.jar" Main
```

#### 🔧 For Linux/Mac:
```bash
javac -cp "lib/mysql-connector-j-9.3.0.jar" -d bin src/db/DBConnection.java src/manager/LibraryManager.java Main.java
java -cp "bin:lib/mysql-connector-j-9.3.0.jar" Main
```

---

## 💻 CLI Menu Example

```
========= Library Management Menu =========
1. Add Book
2. Register Member
3. View All Books
4. Issue Book
5. Return Book
6. Delete Book
7. Delete Member
8. Exit
9. Search Book by Title
```

---

## 📌 Notes

- Uses `com.mysql.cj.jdbc.Driver` (recommended for MySQL 8+)  
- Ensure your MySQL server is up and running before launching the program  
- Designed to be **fully console-based**, no frontend needed  

---

## 📃 License

This project is open source and available under the [MIT License](https://opensource.org/licenses/MIT)

---

## 🙌 Author

**Sahil Sangle**  
> 💬 Contributions, improvements, and feedback are welcome!
