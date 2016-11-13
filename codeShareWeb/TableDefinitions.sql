DROP TABLE CodeLikesOrDislikes;
DROP TABLE CommentLikesOrDislikes;
DROP TABLE UserSubmittedComments;
DROP TABLE UserSubmittedCode;
DROP TABLE Users;


CREATE TABLE Users(
	UserID BIGINT NOT NULL Auto_Increment,
	Username VARCHAR(20) NOT NULL,
	Email VARCHAR(255) NOT NULL,
	PasswordHash VARCHAR(100) NOT NULL,
	PasswordSalt VARCHAR(100) NOT NULL,
	PRIMARY KEY (UserID)
);

CREATE TABLE UserSubmittedCode(
	CodeID BIGINT NOT NULL Auto_Increment,
	UserID BIGINT NOT NULL,
	CodeText TEXT,
	ProgrammingLanguage VARCHAR(30),
	Description VARCHAR(255),
	PRIMARY KEY (CodeID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE CodeLikesOrDislikes(
	CodeID BIGINT NOT NULL,
	UserID BIGINT NOT NULL,
	LikeValue SMALLINT,
	PRIMARY KEY (CodeID, UserID),
	FOREIGN KEY (CodeID) REFERENCES UserSubmittedCode(CodeID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE UserSubmittedComments(
	CommentID BIGINT NOT NULL Auto_Increment,
	CodeID BIGINT NOT NULL,
	CommentText TEXT,
	UserID BIGINT NOT NULL,
	PRIMARY KEY (CommentID, CodeID),
	FOREIGN KEY (CodeID) REFERENCES UserSubmittedCode(CodeID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

CREATE TABLE CommentLikesOrDislikes(
	CommentID BIGINT NOT NULL,
	UserID BIGINT NOT NULL,
	LikeValue SMALLINT,
	PRIMARY KEY (CommentID, UserID),
	FOREIGN KEY (CommentID) REFERENCES UserSubmittedComments(CommentID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);

insert into Users (Username, Email, PasswordHash) Value ('admin3','some@email.com', 'password');

Users
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| UserID       | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| Username     | varchar(20)  | NO   |     | NULL    |                |
| Email        | varchar(255) | NO   |     | NULL    |                |
| PasswordHash | varchar(100) | NO   |     | NULL    |                |
| PasswordSalt | varchar(100) | NO   |     | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+
UserSubmittedCode
+---------------------+--------------+------+-----+---------+----------------+
| Field               | Type         | Null | Key | Default | Extra          |
+---------------------+--------------+------+-----+---------+----------------+
| CodeID              | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| UserID              | bigint(20)   | NO   | MUL | NULL    |                |
| CodeText            | text         | YES  |     | NULL    |                |
| ProgrammingLanguage | varchar(30)  | YES  |     | NULL    |                |
| Description         | varchar(255) | YES  |     | NULL    |                |
+---------------------+--------------+------+-----+---------+----------------+
CodeLikesOrDislikes;
+-----------+-------------+------+-----+---------+-------+
| Field     | Type        | Null | Key | Default | Extra |
+-----------+-------------+------+-----+---------+-------+
| CodeID    | bigint(20)  | NO   | PRI | NULL    |       |
| UserID    | bigint(20)  | NO   | PRI | NULL    |       |
| LikeValue | smallint(6) | YES  |     | NULL    |       |
+-----------+-------------+------+-----+---------+-------+
UserSubmittedComments;
+-------------+------------+------+-----+---------+----------------+
| Field       | Type       | Null | Key | Default | Extra          |
+-------------+------------+------+-----+---------+----------------+
| CommentID   | bigint(20) | NO   | PRI | NULL    | auto_increment |
| CodeID      | bigint(20) | NO   | PRI | NULL    |                |
| CommentText | text       | YES  |     | NULL    |                |
| UserID      | bigint(20) | NO   | MUL | NULL    |                |
+-------------+------------+------+-----+---------+----------------+



