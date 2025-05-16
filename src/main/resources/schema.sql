create table note (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    description VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL,
    temperature DOUBLE
);