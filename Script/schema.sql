CREATE TABLE authority
(
  name character varying(50) NOT NULL,
  CONSTRAINT authority_pkey PRIMARY KEY (name)
);

CREATE TABLE inspection_media
(
  id bigserial NOT NULL,
  annotatedmetadata character varying(40000),
  annotedcomments character varying(40000),
  assetid character varying(255),
  blobid character varying(255),
  comment character varying(40000),
  commentjson character varying(40000),
  defecttype character varying(255),
  description character varying(40000),
  inspectiondate timestamp without time zone,
  inspectionid character varying(255),
  inspectorid character varying(255),
  issueimage bytea,
  statustype character varying(255),
  "user" character varying(400),
  inspection_requirement character varying(40000),
  CONSTRAINT inspection_media_pkey1 PRIMARY KEY (id),
  CONSTRAINT ukva22graa6xmesqil9hh2f0ft UNIQUE (blobid, "user")
);

CREATE TABLE inspection_report
(
  user_id character varying(255) NOT NULL,
  comment character varying(400000),
  CONSTRAINT inspection_report_pkey PRIMARY KEY (user_id)
);

CREATE TABLE inspectorpreferrence
(
  id character varying(255),
  inspectorid character varying(255),
  logo character varying(255),
  approved_by character varying(255)
);

CREATE TABLE oauth_access_token
(
  token_id character varying(256) DEFAULT NULL::character varying,
  token bytea,
  authentication_id character varying(256) DEFAULT NULL::character varying,
  user_name character varying(256) DEFAULT NULL::character varying,
  client_id character varying(256) DEFAULT NULL::character varying,
  authentication bytea,
  refresh_token character varying(256) DEFAULT NULL::character varying
);

CREATE TABLE oauth_refresh_token
(
  token_id character varying(256) DEFAULT NULL::character varying,
  token bytea,
  authentication bytea
);

CREATE TABLE user_authority
(
  username character varying(50) NOT NULL,
  authority character varying(50) NOT NULL,
  CONSTRAINT user_authority_authority_fkey FOREIGN KEY (authority)
      REFERENCES authority (name) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_authority_username_fkey FOREIGN KEY (username)
      REFERENCES users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_authority_username_authority_key UNIQUE (username, authority)
);

CREATE TABLE users
(
  username character varying(50) NOT NULL,
  email character varying(50),
  password character varying(500),
  activated boolean DEFAULT false,
  activationkey character varying(50) DEFAULT NULL::character varying,
  resetpasswordkey character varying(50) DEFAULT NULL::character varying,
  inspectorid character varying(50) DEFAULT NULL::character varying,
  CONSTRAINT users_pkey PRIMARY KEY (username)
);
