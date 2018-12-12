CREATE TABLE people (
  id          INT           NOT NULL  IDENTITY(1,1) PRIMARY KEY,
  first_name       VARCHAR(64)   NOT NULL,
  last_name     VARCHAR(64)     NOT NULL,
  created    DATE     NOT NULL,
  modified    DATE    NOT NULL
);
