-- USE `utxrkeuaz3u9no2h`;
USE `pocket_exam`;

DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(200) DEFAULT NULL,
  `gender` tinyint(2) DEFAULT NULL,
  `head_img_url` varchar(200) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `del_flg` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `platform_id`    char(16)            NOT NULL,
  `third_party_id` varchar(50)         NOT NULL,
  `del_flg`        tinyint(2)          NOT NULL DEFAULT 0,
  `created_date`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`   timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `u_id` (`user_id`, `platform_id`, `third_party_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_role_info`;
CREATE TABLE `t_role_info` (
  `id`           bigint(20) UNSIGNED               NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `role_code`    varchar(50) CHARACTER SET utf8mb4 NULL     DEFAULT NULL,
  `role_name`    varchar(50) CHARACTER SET utf8mb4 NULL     DEFAULT NULL,
  `del_flg`      tinyint(2)                        NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_permission_info`;
CREATE TABLE `t_permission_info` (
  `id`              bigint(20) UNSIGNED               NOT NULL AUTO_INCREMENT
  COMMENT '主键ID',
  `parent_id`       bigint(20)                        NOT NULL DEFAULT 0,
  `permission_code` varchar(50) CHARACTER SET utf8mb4 NULL     DEFAULT NULL,
  `permission_name` varchar(50) CHARACTER SET utf8mb4 NULL     DEFAULT NULL,
  `del_flg`         tinyint(2)                        NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`    timestamp                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`    timestamp                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `del_flg`      tinyint(2)          NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `del_flg`       tinyint(2)          NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `del_flg`       tinyint(2)          NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date`  timestamp           NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  AUTO_INCREMENT = 1;


DROP TABLE IF EXISTS `t_platform`;
CREATE TABLE `t_platform` (
  `platform_id` char(18) NOT NULL,
  `platform_secret` varchar(100) NOT NULL,
  `platform_scope` varchar(100) NOT NULL,
  `platform_name` varchar(50) NOT NULL,
  `platform_icon` varchar(255) DEFAULT NULL,
  `grant_type` varchar(250) NOT NULL,
  `callback_url` varchar(1000) DEFAULT NULL COMMENT '多个url以逗号分隔',
  `expires_in` int(20) DEFAULT 7200 NOT NULL,
  `del_flg`       tinyint(2)          NOT NULL DEFAULT 0
  COMMENT '0 - not deleted, 1 - deleted',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`platform_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO `t_platform` (`platform_id`, `platform_secret`, `platform_scope`, `platform_name`, `grant_type`) VALUES ('PB5K6oEjLy8wp9Ucnv', 'eSu3wkIq0NLwrmtTUD0TZBMaKtK0WfOh', 'all', 'miniprogram', 'client_credentials,authorization_code,refresh_token');
INSERT INTO `t_platform` (`platform_id`, `platform_secret`, `platform_scope`, `platform_name`, `grant_type`) VALUES ('fl8szmltwlqv32bnvs', '0v2xne0mvl2962tl9vgudh7ohsz5q3tu', 'all', 'publicaccount', 'client_credentials,authorization_code,refresh_token');
