CREATE TABLE users (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
  email VARCHAR(50),
  password VARCHAR(500),
  activated BOOLEAN DEFAULT FALSE,
  activationkey VARCHAR(50) DEFAULT NULL,
  resetpasswordkey VARCHAR(50) DEFAULT NULL
);

CREATE TABLE authority (
  name VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE user_authority (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (authority) REFERENCES authority (name),
    UNIQUE INDEX user_authority_idx_1 (username, authority)
);

CREATE TABLE oauth_access_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BLOB,
  authentication_id VARCHAR(256) DEFAULT NULL,
  user_name VARCHAR(256) DEFAULT NULL,
  client_id VARCHAR(256) DEFAULT NULL,
  authentication BLOB,
  refresh_token VARCHAR(256) DEFAULT NULL
);

CREATE TABLE oauth_refresh_token (
  token_id VARCHAR(256) DEFAULT NULL,
  token BLOB,
  authentication BLOB
);

CREATE TABLE inspection_dtls
(
  blob_id character varying(50) NOT NULL,
  asset_id character varying(255),
  facility_id character varying(255),
  inspection_id character varying(255),
  inspection_phase_id character varying(255),
  inspection_point_id character varying(255),
  inspection_start timestamp with time zone,
  inspection_stop timestamp with time zone,
  inspection_type character varying(255),
  inspector_id character varying(255),
  required_field_of_view integer NOT NULL,
  required_resolution integer NOT NULL,
  sensor_name character varying(255),
  sensor_sensor_mode character varying(255),
  sensor_sensor_type character varying(255),
  "timestamp" timestamp with time zone,
  visibility character varying(255),
  CONSTRAINT inspection_dtls_pkey PRIMARY KEY (blob_id)
);

CREATE TABLE inspection_media
(
  comment_id integer NOT NULL,
  asset_id character varying(255),
  blob_id character varying(255),
  comment character varying(255),
  inspection_date timestamp without time zone,
  inspector_id character varying(255),
  issue_date timestamp with time zone,
  issue_id character varying(255),
  issue_type character varying(255),
  CONSTRAINT inspection_media_pkey PRIMARY KEY (comment_id)
);

