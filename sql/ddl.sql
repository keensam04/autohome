 CREATE TABLE device(  
  id int NOT NULL AUTO_INCREMENT,
  deviceName nvarchar(32),
  dateOfPurchase DATE,
  dateOfExpiry DATE,
  isSwitch boolean,
  powerRating float,
  roomId int,
  onBoardedBy nvarchar(64),
  offBoardedBY nvarchar(32);
  isActive boolean DEFAULT true,
  dateOfOnboarding TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP,
  dateOfModification TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
  PRIMARY KEY (id)
  );
  
CREATE TABLE room(
  id int NOT NULL AUTO_INCREMENT,
  roomName nvarchar(32),
  PRIMARY KEY (id)
  );
  
  
CREATE TABLE deviceStateLog(
  id int NOT NULL AUTO_INCREMENT,
  deviceId int NOT NULL,
  state TINYINT,
  roomId int NOT NULL,
  time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date DATE NOT NULL,
  user nvarchar(64),
  PRIMARY KEY (id),
  INDEX (date,deviceId)
  );


CREATE TABLE users(
    id int NOT NULL AUTO_INCREMENT,
    firstName nvarchar(64),
    lastName nvarchar(64),
    email nvarchar(128),
    inActive boolean DEFAULT false,
    picture nvarchar(256),
    role nvarchar(16),
    PRIMARY KEY (id)
    );

CREATE TABLE roles(
    role nvarchar(16) NOT NULL,
    scope nvarchar(16) NOT NULL,
    PRIMARY KEY(role)
    );


