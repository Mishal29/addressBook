CREATE TABLE IF NOT EXISTS user(
	user_id VARCHAR(30) PRIMARY KEY,
	user_name VARCHAR(50),
	pw VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS address(
	address_id INT PRIMARY KEY AUTO_INCREMENT,
	fullname VARCHAR(50) NOT NULL,
	furigana VARCHAR(50),
	address VARCHAR(80) NOT NULL,
	tel VARCHAR(15),
	mail VARCHAR(50),
	note VARCHAR(100),
	user_id VARCHAR(30),
	FOREIGN KEY (user_id) REFERENCES user(user_id)
);
