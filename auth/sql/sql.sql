CREATE TABLE public.oauth_client_details
(
  client_id VARCHAR(128) PRIMARY KEY NOT NULL,
  resource_ids VARCHAR(256) DEFAULT NULL,
  client_secret VARCHAR(256) DEFAULT NULL,
  scope VARCHAR(256) DEFAULT NULL,
  authorized_grant_types VARCHAR(256) DEFAULT NULL,
  web_server_redirect_uri VARCHAR(256) DEFAULT NULL,
  authorities VARCHAR(256) DEFAULT NULL,
  access_token_validity INT DEFAULT NULL,
  refresh_token_validity INT DEFAULT NULL,
  additional_information VARCHAR(4096) DEFAULT NULL,
  autoapprove VARCHAR(256) DEFAULT NULL
);

INSERT INTO oauth_client_details VALUES ('bjc-cloud', NULL, 'bjc-cloud-2019', 'root', 'password', '', NULL, NULL, NULL, NULL, NULL);