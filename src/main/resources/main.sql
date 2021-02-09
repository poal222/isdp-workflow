CREATE TABLE se_deployment_instance (
  id  SERIAL ,
  gmt_create date NOT NULL   ,
  gmt_modified date NOT NULL  ,
  process_definition_id varchar(255) NOT NULL  ,
  process_definition_version varchar(255) DEFAULT NULL  ,
  process_definition_type varchar(255) DEFAULT NULL  ,
  process_definition_code varchar(255) DEFAULT NULL  ,
  process_definition_name varchar(255) DEFAULT NULL  ,
  process_definition_desc varchar(255) DEFAULT NULL  ,
  process_definition_content text NOT NULL  ,
  deployment_user_id varchar(128) NOT NULL  ,
  deployment_status varchar(64) NOT NULL   ,
  logic_status varchar(64) NOT NULL  ,

  PRIMARY KEY (id)
)  ;

CREATE TABLE se_process_instance (
  id  SERIAL  ,
  gmt_create date NOT NULL  ,
  gmt_modified date NOT NULL  ,
  process_definition_id_and_version varchar(128) NOT NULL  ,
  process_definition_type varchar(255) DEFAULT NULL ,
  status varchar(64) NOT NULL ,
  parent_process_instance_id bigint  DEFAULT NULL   ,
  parent_execution_instance_id bigint  DEFAULT NULL   ,
  start_user_id varchar(128) DEFAULT NULL  ,
  biz_unique_id varchar(255) DEFAULT NULL  ,
  reason varchar(255) DEFAULT NULL   ,
  comment varchar(255) DEFAULT NULL   ,
  title varchar(255) DEFAULT NULL  ,
  tag varchar(255) DEFAULT NULL  ,

  PRIMARY KEY (id)
)   ;

CREATE TABLE se_activity_instance (
  id  SERIAL  ,
  gmt_create date NOT NULL   ,
  gmt_modified date NOT NULL  ,
  process_instance_id bigint  DEFAULT NULL  ,
  process_definition_id_and_version varchar(255) NOT NULL  ,
  process_definition_activity_id varchar(64) NOT NULL ,
  PRIMARY KEY (id)
)  ;

CREATE TABLE se_task_instance (
  id  SERIAL  ,
  gmt_create date NOT NULL   ,
  gmt_modified date NOT NULL  ,
  process_instance_id bigint  NOT NULL  ,
  process_definition_id_and_version varchar(128) DEFAULT NULL  ,
  process_definition_type varchar(255) DEFAULT NULL  ,
  activity_instance_id bigint  NOT NULL   ,
  process_definition_activity_id varchar(255) NOT NULL  ,
  execution_instance_id bigint  NOT NULL  ,
  claim_user_id varchar(255) DEFAULT NULL   ,
  title varchar(255) DEFAULT NULL ,
  priority int DEFAULT 500 ,
  tag varchar(255) DEFAULT NULL  ,
  claim_time date DEFAULT NULL ,
  complete_time date DEFAULT NULL ,
  status varchar(255) NOT NULL ,
  comment varchar(255) DEFAULT NULL  ,
  extension varchar(255) DEFAULT NULL ,

  PRIMARY KEY (id)
)   ;

CREATE TABLE se_execution_instance (
  id  SERIAL ,
  gmt_create date NOT NULL   ,
  gmt_modified date NOT NULL  ,
  process_instance_id bigint  NOT NULL  ,
  process_definition_id_and_version varchar(255) NOT NULL  ,
  process_definition_activity_id varchar(255) NOT NULL ,
  activity_instance_id bigint  NOT NULL ,
  active int NOT NULL ,
  PRIMARY KEY (id)
)   ;


CREATE TABLE se_task_assignee_instance (
  id  SERIAL  ,
  gmt_create date NOT NULL  ,
  gmt_modified date NOT NULL   ,
  process_instance_id bigint  NOT NULL  ,
  task_instance_id bigint  NOT NULL  ,
  assignee_id varchar(255) NOT NULL  ,
  assignee_type varchar(128) NOT NULL  ,
  PRIMARY KEY (id)
)  ;


CREATE TABLE se_variable_instance (
  id  SERIAL  ,
  gmt_create date NOT NULL  ,
  gmt_modified date NOT NULL  ,
  process_instance_id bigint  NOT NULL   ,
  execution_instance_id bigint  DEFAULT NULL   ,
  field_key varchar(128) NOT NULL   ,
  field_type varchar(128) NOT NULL   ,
  field_double_value decimal(65,30) DEFAULT NULL   ,
  field_long_value bigint DEFAULT NULL  ,
  field_string_value varchar(4000) DEFAULT NULL  ,

  PRIMARY KEY (id)
)  ;
