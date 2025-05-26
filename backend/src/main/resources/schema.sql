CREATE TABLE IF NOT EXISTS users (
                                     id INTEGER PRIMARY KEY AUTOINCREMENT,
                                     email TEXT NOT NULL UNIQUE,
                                     password TEXT NOT NULL,
                                     first_name TEXT NOT NULL,
                                     last_name TEXT NOT NULL
);