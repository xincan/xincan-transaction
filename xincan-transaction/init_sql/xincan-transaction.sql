/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : xincan-transaction

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 13/11/2019 18:21:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tenant_datasource
-- ----------------------------
DROP TABLE IF EXISTS `tenant_datasource`;
CREATE TABLE `tenant_datasource`  (
  `id` bigint(20) NOT NULL,
  `tenant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '租户名称',
  `datasource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据源连接地址',
  `datasource_username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据源用户名',
  `datasource_password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据源密码',
  `datasource_driver` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据源驱动',
  `datasource_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '数据源连接池类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '租户数据源信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_datasource
-- ----------------------------
INSERT INTO `tenant_datasource` VALUES (1, 'tenant1', 'jdbc:mysql://localhost:3306/xincan-transaction-role?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&serverTimezone=GMT', 'root', '123456', 'com.mysql.jdbc.Driver', 'com.alibaba.druid.pool.DruidDataSource');
INSERT INTO `tenant_datasource` VALUES (2, 'tenant2', 'jdbc:mysql://localhost:3306/xincan-transaction-order?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&serverTimezone=GMT', 'root', '123456', 'com.mysql.jdbc.Driver', 'com.alibaba.druid.pool.DruidDataSource');
INSERT INTO `tenant_datasource` VALUES (3, 'tenant0', 'jdbc:mysql://localhost:3306/xincan-transaction?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&serverTimezone=GMT', 'root', '123456', 'com.mysql.jdbc.Driver', 'com.alibaba.druid.pool.DruidDataSource');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(3) NULL DEFAULT NULL,
  `sex` int(1) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3ff38f3701ccce44f4941f3a022b9603', '王五1', 23, 1, '2019-11-05 13:50:00');
INSERT INTO `user` VALUES ('4e348dfe852f11e987d668f7285847c8', '张三', 32, 1, '2019-06-05 09:46:06');
INSERT INTO `user` VALUES ('4e348fc6852f11e987d668f7285847c8', '李四', 12, 0, '2019-06-05 09:46:06');

SET FOREIGN_KEY_CHECKS = 1;
