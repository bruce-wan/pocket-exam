-- USE `utxrkeuaz3u9no2h`;
USE `pocket_exam`;

DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id`           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `username`     varchar(50)                  DEFAULT NULL
  COMMENT '用户名',
  `password`     varchar(50)                  DEFAULT NULL
  COMMENT '密码',
  `nick_name`    varchar(200)                 DEFAULT NULL,
  `gender`       tinyint(2) UNSIGNED          DEFAULT NULL,
  `head_img_url` varchar(200)                 DEFAULT NULL,
  `salt`         varchar(50)                  DEFAULT NULL,
  `del_flg`      tinyint(2) UNSIGNED NOT NULL DEFAULT '0'
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERINFO_USERNAME` (`username`) USING HASH
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_user_identity`;
CREATE TABLE `t_user_identity` (
  `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `user_id`        bigint(20) UNSIGNED NOT NULL,
  `platform_id`    char(18)            NOT NULL,
  `third_party_id` varchar(50)         NOT NULL,
  `del_flg`        tinyint(2) UNSIGNED NOT NULL DEFAULT 0,
  `created_date`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `u_id` (`user_id`, `platform_id`, `third_party_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_role_info`;
CREATE TABLE `t_role_info` (
  `id`           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `role_code`    varchar(50)         NULL     DEFAULT NULL,
  `role_name`    varchar(50)         NULL     DEFAULT NULL,
  `del_flg`      tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_permission_info`;
CREATE TABLE `t_permission_info` (
  `id`              bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `parent_id`       bigint(20) UNSIGNED NOT NULL DEFAULT 0,
  `permission_code` varchar(50)         NULL     DEFAULT NULL,
  `permission_name` varchar(50)         NULL     DEFAULT NULL,
  `del_flg`         tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`    timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id`           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `user_id`      bigint(20) UNSIGNED NOT NULL,
  `role_id`      bigint(20) UNSIGNED NOT NULL,
  `del_flg`      tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_user_permission`;
CREATE TABLE `t_user_permission` (
  `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `user_id`       bigint(20) UNSIGNED NOT NULL,
  `permission_id` bigint(20) UNSIGNED NOT NULL,
  `del_flg`       tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `role_id`       bigint(20) UNSIGNED NOT NULL,
  `permission_id` bigint(20) UNSIGNED NOT NULL,
  `del_flg`       tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_platform`;
CREATE TABLE `t_platform` (
  `platform_id`     char(18)                      NOT NULL,
  `platform_secret` varchar(100)                  NOT NULL,
  `platform_scope`  varchar(100)                  NOT NULL,
  `platform_name`   varchar(50)                   NOT NULL,
  `platform_icon`   varchar(255)                           DEFAULT NULL,
  `grant_type`      varchar(250)                  NOT NULL,
  `callback_url`    varchar(1000)                          DEFAULT NULL
  COMMENT '多个url以逗号分隔',
  `expires_in`      int(20) UNSIGNED DEFAULT 7200 NOT NULL,
  `del_flg`         tinyint(2) UNSIGNED           NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`    timestamp                     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`    timestamp                     NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`platform_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;

INSERT INTO `t_platform` (`platform_id`, `platform_secret`, `platform_scope`, `platform_name`, `grant_type`) VALUES
  ('PB5K6oEjLy8wp9Ucnv', 'eSu3wkIq0NLwrmtTUD0TZBMaKtK0WfOh', 'all', 'wechat', 'client_credentials,authorization_code,refresh_token');
INSERT INTO `t_platform` (`platform_id`, `platform_secret`, `platform_scope`, `platform_name`, `grant_type`) VALUES
  ('fl8szmltwlqv32bnvs', '0v2xne0mvl2962tl9vgudh7ohsz5q3tu', 'all', 'baidu', 'client_credentials,authorization_code,refresh_token');
INSERT INTO `t_platform` (`platform_id`, `platform_secret`, `platform_scope`, `platform_name`, `grant_type`) VALUES
  ('3hB8x7SeUBLeZ0LDXO', 'kUlv3x99rKjVbgI5lK7nLc9P66Qse0ws', 'all', 'webpage', 'client_credentials');


DROP TABLE IF EXISTS `t_question_bank`;
CREATE TABLE `t_question_bank` (
  `id`           bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `catalog`      tinyint(2) UNSIGNED NOT NULL DEFAULT 0,
  `level`        tinyint(2) UNSIGNED NOT NULL DEFAULT 1,
  `type`         tinyint(2) UNSIGNED NOT NULL DEFAULT 1
  COMMENT '1 - SingleChoice, 2 - MultipleChoice, 3 - Judgment, 4 - BlankFill',
  `title`        longtext            NOT NULL,
  `answer`       varchar(500)        NOT NULL,
  `content`      longtext            NOT NULL,
  `remark`       varchar(200)                 DEFAULT NULL,
  `score`        tinyint(2) UNSIGNED          DEFAULT 0,
  `del_flg`      tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_exam_paper`;
CREATE TABLE `t_exam_paper` (
  `id`             bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`        bigint(20) UNSIGNED NOT NULL,
  `name`           varchar(100)                 DEFAULT NULL,
  `catalog`        tinyint(2) UNSIGNED NOT NULL DEFAULT 0,
  `level`          tinyint(2) UNSIGNED NOT NULL DEFAULT 1,
  `total_score`    int(5)                       DEFAULT NULL,
  `pass_score`     int(5)                       DEFAULT NULL,
  `user_score`     int(5)                       DEFAULT NULL,
  `score_grade`    tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0.不及格，1.及格，2.良好，3.优秀',
  `start_time`     datetime                     DEFAULT NULL,
  `end_time`       datetime                     DEFAULT NULL,
  `duration`       smallint(10)        NOT NULL DEFAULT 0,
  `content`        longtext,
  `remark`         text,
  `del_flg`        tinyint(2) UNSIGNED NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP
  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;

