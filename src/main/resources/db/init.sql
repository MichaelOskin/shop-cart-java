--Create Phones table

CREATE TABLE IF NOT EXISTS Phones (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    Price REAL,
    Name TEXT,
    Model TEXT,
    NameCPU TEXT,
    Cores INTEGER,
    Freq REAL,
    hDisplay INTEGER,
    sDisplay DOUBLE,
    wDisplay INTEGER,
    RAM INTEGER,
    ROM INTEGER,
    type TEXT,
    ver TEXT,
    date DATE
);

--Create Users table

CREATE TABLE IF NOT EXISTS Users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    tier INTEGER CHECK (tier >= 0 AND tier <= 10)
);

--Create Cart table

CREATE TABLE IF NOT EXISTS Basket (
    session_id INTEGER NOT NULL,
    product_id INTEGER,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (session_id) REFERENCES Sessions(id_session),
    FOREIGN KEY (product_id) REFERENCES Phones(id)
);

--Create Session Cart table

CREATE TABLE IF NOT EXISTS Sessions (
    id_session INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER,
    is_active INTEGER NOT NULL,
    date DATE,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

--Create storage login&password table

CREATE TABLE IF NOT EXISTS Passwords (
    user_id INTEGER,
    login TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

--Create UserWallet table

CREATE TABLE IF NOT EXISTS UserWallet (
    user_id INTEGER PRIMARY KEY,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- admin (1 ...) default
INSERT INTO Users (id, name, tier) VALUES
(1, 'name-admin', '10');

INSERT INTO UserWallet (user_id, balance) VALUES
(1, 0);

INSERT INTO Sessions (id_session, user_id, is_active) VALUES
(1, 1, 1);

INSERT INTO Passwords (user_id, login, password) VALUES
(1, 'admin', 'Admin123');