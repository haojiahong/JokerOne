/*
Navicat MySQL Data Transfer

Source Server         : bend
Source Server Version : 50609
Source Host           : localhost:3306
Source Database       : jokerone

Target Server Type    : MYSQL
Target Server Version : 50609
File Encoding         : 65001

Date: 2015-10-26 22:42:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `my_organization`
-- ----------------------------
DROP TABLE IF EXISTS `my_organization`;
CREATE TABLE `my_organization` (
  `ORG_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FROM_DATE` datetime DEFAULT NULL,
  `GROUP_CODE` varchar(255) DEFAULT NULL,
  `GROUP_LEVEL` bigint(20) DEFAULT NULL,
  `GROUP_NAME` varchar(255) DEFAULT NULL,
  `GROUP_NAME_SHORT` varchar(255) DEFAULT NULL,
  `HAVE_SUB` varchar(8) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  `UP_ORG_ID` bigint(20) DEFAULT NULL,
  `GROUP_ATT` bigint(20) DEFAULT NULL,
  `SORT_CODE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ORG_ID`),
  KEY `FKCD24E0E665A36BDC` (`UP_ORG_ID`),
  CONSTRAINT `FKCD24E0E665A36BDC` FOREIGN KEY (`UP_ORG_ID`) REFERENCES `my_organization` (`ORG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_organization
-- ----------------------------
INSERT INTO `my_organization` VALUES ('-1', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `my_organization` VALUES ('1', '2015-07-06 00:00:00', '1', null, '总公司', '总公司', 'Y', '88', '-1', '1', '10');
INSERT INTO `my_organization` VALUES ('2', '2015-07-06 00:00:00', '2', null, '分公司', '分公司', 'Y', '', '1', '4', '33');
INSERT INTO `my_organization` VALUES ('3', '2015-07-07 00:00:00', '3', null, '孙公司三号', '孙公司', 'N', '66', '2', '1', '66');
INSERT INTO `my_organization` VALUES ('4', '2015-07-06 00:00:00', '6', null, '孙公司一号', '6', '', '6', '2', '1', '6');
INSERT INTO `my_organization` VALUES ('6', '2015-07-06 00:00:00', '55', null, '孙公司二号', '55', '', '5555', '2', '1', '55');

-- ----------------------------
-- Table structure for `my_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `my_privilege`;
CREATE TABLE `my_privilege` (
  `up_privilege_id` char(32) DEFAULT NULL,
  `privilege_id` char(32) NOT NULL,
  `privilege_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `have_sub` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_privilege
-- ----------------------------
INSERT INTO `my_privilege` VALUES (null, '0', '首页', null, 'Y');
INSERT INTO `my_privilege` VALUES ('xtgl', 'cdgl', '菜单管理 ', 'privtreegridez.jsp', null);
INSERT INTO `my_privilege` VALUES ('qtgl', 'cscd', '测试菜单', null, null);
INSERT INTO `my_privilege` VALUES ('xtgl', 'gwgl', '岗位管理', 'rolelist.jsp', null);
INSERT INTO `my_privilege` VALUES ('xtgl', 'jsgl', '部门管理', 'orgtreegridez.jsp', null);
INSERT INTO `my_privilege` VALUES ('0', 'qtgl', '其他管理', null, 'Y');
INSERT INTO `my_privilege` VALUES ('0', 'xtgl', '系统管理', null, 'Y');
INSERT INTO `my_privilege` VALUES ('xtgl', 'yhgl', '用户管理', 'userlist.jsp', null);

-- ----------------------------
-- Table structure for `my_role`
-- ----------------------------
DROP TABLE IF EXISTS `my_role`;
CREATE TABLE `my_role` (
  `role_id` char(32) NOT NULL,
  `role_name` varchar(128) DEFAULT NULL,
  `role_description` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_role
-- ----------------------------
INSERT INTO `my_role` VALUES ('1034fb5891fa41248b45aa93788f0604', '超级管理员', '超级管理员');
INSERT INTO `my_role` VALUES ('24821c92848f4c2e824b90814992f82f', '项目经理', '项目经理222');
INSERT INTO `my_role` VALUES ('2870902e979349aeaefbe420ec4e79af', 'Java研发工程师', 'Java研发工程师');
INSERT INTO `my_role` VALUES ('463a777bd5ad44629e3c585ae6c1dd94', 'Android工程师', 'Android工程师');
INSERT INTO `my_role` VALUES ('edbabdc5e9004522b31b717d4f3b8ed5', 'Java实施工程师', 'Java实施工程师');

-- ----------------------------
-- Table structure for `my_role_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `my_role_privilege`;
CREATE TABLE `my_role_privilege` (
  `ROLE_PRIVILEGE_ID` char(32) NOT NULL,
  `PRIVILEGE_ID` char(32) DEFAULT NULL,
  `ROLE_ID` char(32) DEFAULT NULL,
  PRIMARY KEY (`ROLE_PRIVILEGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_role_privilege
-- ----------------------------
INSERT INTO `my_role_privilege` VALUES ('07c2a5ef907a4331ae12f2d2877ffd7c', 'qtgl', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('1bf065077ad34b2f89da3bac1799ea87', 'xtgl', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('1d6da696b4be446984a0933f6be58d82', 'yhgl', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('243d9647c93540f2add792888691c06c', 'cdgl', '2870902e979349aeaefbe420ec4e79af');
INSERT INTO `my_role_privilege` VALUES ('27c2cf1d025b4ff992202d3686e42f01', 'jsgl', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('3855a8f3c6de47b08ccc1bd194761224', '0', '2870902e979349aeaefbe420ec4e79af');
INSERT INTO `my_role_privilege` VALUES ('4343814069ba4307aefb80228dcd2e1c', 'cdgl', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('5191f0f1c3344d349882d8ef48289704', 'yhgl', '2870902e979349aeaefbe420ec4e79af');
INSERT INTO `my_role_privilege` VALUES ('557e3c168c224d44bd5969469ca05fe0', '0', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('5b1fd4279e90472eab30f26b0b7c7fc6', 'cdgl', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('77dcea2bec524c36a7c6c54f703c0364', 'cscd', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('8128e7cd2b4b42d096d2997ab5a574f6', '0', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('946cb60e35e745758885a260a39cd955', 'yhgl', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('947d9c88265944b58b525795c17c3804', 'gwgl', '2870902e979349aeaefbe420ec4e79af');
INSERT INTO `my_role_privilege` VALUES ('9fe72130123d4e36b2b1e56448613b08', 'jsgl', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('b20ab1a50a5c4681952755dc216bbeb6', 'cscd', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('bbaabdcb80d3425ea1c5fabd68ecd120', 'qtgl', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('cd019e0ea0e44f8fb2e2e30488a09ffb', 'xtgl', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('ddb3bb7724dd456fa6c2d5cb77a61be4', 'xtgl', '2870902e979349aeaefbe420ec4e79af');
INSERT INTO `my_role_privilege` VALUES ('e46a270a76ae40d59607838781a6e221', 'gwgl', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_role_privilege` VALUES ('e8a2a6692acd4d949e45a2a6952b5cf5', 'gwgl', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_role_privilege` VALUES ('f8bcc9f65cf24c10abba401573c3a202', 'jsgl', '2870902e979349aeaefbe420ec4e79af');

-- ----------------------------
-- Table structure for `my_simupload`
-- ----------------------------
DROP TABLE IF EXISTS `my_simupload`;
CREATE TABLE `my_simupload` (
  `UPLOAD_ID` char(32) NOT NULL,
  `ENTITY_ID` char(32) DEFAULT NULL,
  `ENTITY_TYPE` varchar(255) DEFAULT NULL,
  `FILE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UPLOAD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_simupload
-- ----------------------------

-- ----------------------------
-- Table structure for `my_user`
-- ----------------------------
DROP TABLE IF EXISTS `my_user`;
CREATE TABLE `my_user` (
  `USER_ID` char(32) NOT NULL,
  `USER_DESCRIPTION` varchar(255) DEFAULT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `LOGIN_NAME` varchar(255) DEFAULT NULL,
  `ORG_ID` bigint(20) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PHONE_NUMBER` varchar(255) DEFAULT NULL,
  `ROLE_ID` char(32) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `FK59F3427E8E6911B8` (`ORG_ID`),
  CONSTRAINT `FK59F3427E8E6911B8` FOREIGN KEY (`ORG_ID`) REFERENCES `my_organization` (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_user
-- ----------------------------
INSERT INTO `my_user` VALUES ('14aaabbbccc123eeertyfcccvbnhujse', '111', '超级管理员', '111', '1', 'admin', '1', '81dc9bdb52d04dc20036dbd8313ed055', '110', '1034fb5891fa41248b45aa93788f0604');
INSERT INTO `my_user` VALUES ('180f429cf1294927a678ec3d16e17cd8', '111', '周杰伦', '111', '1', 'zhoujielun', '1', '81dc9bdb52d04dc20036dbd8313ed055', '111', '2870902e979349aeaefbe420ec4e79af');
INSERT INTO `my_user` VALUES ('1fffc52f50a9408492eac82caba472f7', '222', '张惠妹', '222', '2', 'zhanghuimei', '1', '', '222', '24821c92848f4c2e824b90814992f82f');
INSERT INTO `my_user` VALUES ('651bdaba70374e57bd02a23ebeccc938', '333', '庾澄庆', '333', '1', 'yvchengqing', '1', '', '333', 'edbabdc5e9004522b31b717d4f3b8ed5');

-- ----------------------------
-- Table structure for `my_user_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `my_user_privilege`;
CREATE TABLE `my_user_privilege` (
  `USER_PRIVILEGE_ID` char(32) NOT NULL,
  `USER_ID` char(32) DEFAULT NULL,
  `PRIVILEGE_ID` char(32) DEFAULT NULL,
  PRIMARY KEY (`USER_PRIVILEGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of my_user_privilege
-- ----------------------------
