/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : boke_auth

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2019-10-05 13:05:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `dept_no` varchar(18) DEFAULT NULL COMMENT '部门编号(规则：父级关系编码+自己的编码)',
  `name` varchar(300) DEFAULT NULL COMMENT '部门名称',
  `pid` varchar(64) NOT NULL COMMENT '父级id',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态(1:正常；0:弃用)',
  `relation_code` varchar(3000) DEFAULT NULL COMMENT '为了维护更深层级关系',
  `dept_manager_id` varchar(64) DEFAULT NULL COMMENT '部门经理user_id',
  `manager_name` varchar(255) DEFAULT NULL COMMENT '部门经理名称',
  `phone` varchar(20) DEFAULT NULL COMMENT '部门经理联系电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1d193c11-a5bd-4aef-83e0-328dfb777377', 'YXD000002', '博克文化呼市分布', '24f41c71-5a95-4ef4-9493-174574f3b0c5', '1', 'YXD000001YXD000002', 'a8be6fd4-f540-4e27-a0ef-0bef9130f142', '阿丝', '13888888888', '2019-09-19 18:06:11', '2019-09-22 12:26:40', '1');
INSERT INTO `sys_dept` VALUES ('24f41c71-5a95-4ef4-9493-174574f3b0c5', 'YXD000001', '博克文欢总部', '0', '1', 'YXD000001', 'a8be6fd4-f540-4e27-a0ef-0bef9130f142', '阿丝', '13888888888', '2019-09-19 18:02:12', null, '1');
INSERT INTO `sys_dept` VALUES ('a8ab2ad9-22a9-412c-b211-9069f777760c', 'YXD000004', '博克分部鄂尔多斯分校', '1d193c11-a5bd-4aef-83e0-328dfb777377', '1', 'YXD000001YXD000002YXD000004', 'a8be6fd4-f540-4e27-a0ef-0bef9130f142', '小霍', '13888888888', '2019-09-22 12:22:45', '2019-09-22 12:24:29', '1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `time` int(11) DEFAULT NULL COMMENT '响应时间',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('022d99bd-b7a8-4efb-90f3-a2d613905b22', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-26 11:05:34');
INSERT INTO `sys_log` VALUES ('06c2fe68-dec8-4b84-a806-e2bcc72e9cd6', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":0,\"pageSize\":0}', '0:0:0:0:0:0:0:1', '2019-09-25 11:37:54');
INSERT INTO `sys_log` VALUES ('13fbdd41-18d2-4f06-9da8-7cca8d7ae12a', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-26 11:09:24');
INSERT INTO `sys_log` VALUES ('1de0978f-d0cc-45d4-8a32-159db4c6cd55', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 11:12:57');
INSERT INTO `sys_log` VALUES ('28c7e001-95fe-4977-95b9-97b3cbc4d95b', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-更新用户信息', null, 'UserController.updateUserInfo()', '{\"id\":\"d574c119-52f6-4cfe-9da8-5b1dabefce4f\",\"roleIds\":[\"dddc090f-7f48-4fe3-8ada-1fd4422ded7b\"]}', '0:0:0:0:0:0:0:1', '2019-09-25 14:02:46');
INSERT INTO `sys_log` VALUES ('2ee47b1e-aefd-4ed8-9564-4f35b35a2831', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 11:02:43');
INSERT INTO `sys_log` VALUES ('31ceca58-1463-43d1-bda9-486f16855c50', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 10:51:29');
INSERT INTO `sys_log` VALUES ('33ded0dd-444a-46d8-a252-2204d588df5f', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 11:00:56');
INSERT INTO `sys_log` VALUES ('4d50ffa2-3227-462a-9af6-ad6ca2edfaef', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 10:52:17');
INSERT INTO `sys_log` VALUES ('54a9a0de-3ec2-459c-8e23-e59a07544932', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 10:15:02');
INSERT INTO `sys_log` VALUES ('5909c51c-f685-44ac-b904-6b8f35a10db6', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 10:56:45');
INSERT INTO `sys_log` VALUES ('605b3104-499d-456a-ab82-9bd9c5261ab9', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 12:48:12');
INSERT INTO `sys_log` VALUES ('64f94237-6790-4835-b9d1-40ce50f7679e', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 11:11:23');
INSERT INTO `sys_log` VALUES ('696691a7-4913-46e3-ae09-a2ffecfe23ef', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 11:10:54');
INSERT INTO `sys_log` VALUES ('6c422fc3-a68d-4228-a46e-61d3911b8ac0', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 11:46:01');
INSERT INTO `sys_log` VALUES ('6e2200c9-196b-4d19-b051-c5f955e34c38', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 10:21:29');
INSERT INTO `sys_log` VALUES ('71b0523a-ede0-4abe-872e-652a63347cab', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-24 19:55:33');
INSERT INTO `sys_log` VALUES ('722d0e75-b552-45d3-8be5-58eccaf3611a', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-26 11:05:44');
INSERT INTO `sys_log` VALUES ('74530cfd-6464-4a15-beb9-6a5e21d627bd', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 11:00:36');
INSERT INTO `sys_log` VALUES ('81219597-ba26-41f7-b494-041258338e0e', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '角色管理-新增角色', null, 'RoleController.addRole()', '{\"description\":\"普通用户所拥有的角色\",\"name\":\"普通用户所拥有的角色\",\"permissions\":[\"2c167006-5eb4-4c5c-b8f1-86d277001f0a\",\"d6214dcb-8b6d-494b-88fa-f519fc08ff8f\",\"697476e4-ac18-4522-a0cd-078f55755eeb\",\"7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4\",\"1a236128-7a0a-45e4-b523-0d49c9c1b6b9\",\"d668756b-fe1d-4cf7-8039-76b7736dda7b\",\"697476e4-ac18-4522-a0cd-078f55755ddc\"]}', '0:0:0:0:0:0:0:1', '2019-09-25 14:01:16');
INSERT INTO `sys_log` VALUES ('8aa1dab9-c5cb-4848-8389-1c6890f5cfa3', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 10:34:24');
INSERT INTO `sys_log` VALUES ('8ee363ed-8d8e-4cfd-9f39-c94715a7cae1', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-26 11:04:56');
INSERT INTO `sys_log` VALUES ('93b02906-f376-4fe9-8b94-7bfa53294eb4', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 10:56:07');
INSERT INTO `sys_log` VALUES ('a520b77b-cc6a-4334-9ee4-b9684a2ef8e4', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 10:58:00');
INSERT INTO `sys_log` VALUES ('a5bc3be1-9477-4c80-b601-347c9926cd4a', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 11:47:13');
INSERT INTO `sys_log` VALUES ('a63cbed3-47a1-40be-9fd7-07d5ea3b7639', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 10:40:27');
INSERT INTO `sys_log` VALUES ('bca62a46-5684-4b33-9f25-44ca899b91f4', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-24 20:12:04');
INSERT INTO `sys_log` VALUES ('bf88a6ca-b894-4616-acf3-92feae4805a5', 'd574c119-52f6-4cfe-9da8-5b1dabefce4f', 'xh123', '用户管理-用户刷新token', null, 'UserController.refreshToken()', null, '0:0:0:0:0:0:0:1', '2019-09-25 14:18:01');
INSERT INTO `sys_log` VALUES ('c5aed049-d466-477d-9159-ba5f064c05fe', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-24 16:20:57');
INSERT INTO `sys_log` VALUES ('d3b0199b-d0e0-4f3a-bd24-1486e2bbe988', 'd574c119-52f6-4cfe-9da8-5b1dabefce4f', 'xh123', '角色管理-更新角色信息', null, 'RoleController.updateDept()', '{\"id\":\"dddc090f-7f48-4fe3-8ada-1fd4422ded7b\",\"permissions\":[\"2c167006-5eb4-4c5c-b8f1-86d277001f0a\",\"d6214dcb-8b6d-494b-88fa-f519fc08ff8f\",\"697476e4-ac18-4522-a0cd-078f55755eeb\",\"7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4\",\"1a236128-7a0a-45e4-b523-0d49c9c1b6b9\",\"d668756b-fe1d-4cf7-8039-76b7736dda7b\",\"697476e4-ac18-4522-a0cd-078f55755ddc\"]}', '0:0:0:0:0:0:0:1', '2019-09-25 14:18:13');
INSERT INTO `sys_log` VALUES ('dc4eaebd-9fca-4ac8-b8b7-c03020c275fa', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 12:47:29');
INSERT INTO `sys_log` VALUES ('dd5c6aa3-4538-47e5-90d7-71bb90324627', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-26 11:06:00');
INSERT INTO `sys_log` VALUES ('dda39d30-622a-4c60-b39d-1cb5d6547413', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 11:31:32');
INSERT INTO `sys_log` VALUES ('e1761ce4-7126-4cd7-8ea8-e105cea15320', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 10:57:20');
INSERT INTO `sys_log` VALUES ('e2d41fea-4215-4633-846d-4d9f47a62436', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-退出', null, 'UserController.logout()', null, '0:0:0:0:0:0:0:1', '2019-09-25 11:38:26');
INSERT INTO `sys_log` VALUES ('e6385109-eb83-4fb2-8011-701d82c7addf', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 10:50:36');
INSERT INTO `sys_log` VALUES ('e9d71eb3-f32e-4ff8-9e72-fc19ae38b525', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '用户管理-分页获取用户列表', null, 'UserController.pageInfo()', '{\"pageNum\":1,\"pageSize\":10}', '0:0:0:0:0:0:0:1', '2019-09-25 11:30:32');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `code` varchar(64) DEFAULT NULL COMMENT '菜单权限编码',
  `name` varchar(300) DEFAULT NULL COMMENT '菜单权限名称',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `url` varchar(100) DEFAULT NULL COMMENT '访问地址URL',
  `method` varchar(10) DEFAULT NULL COMMENT '资源请求类型',
  `pid` varchar(64) DEFAULT NULL COMMENT '父级菜单权限名称',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `type` tinyint(4) DEFAULT NULL COMMENT '菜单权限类型(1:目录;2:菜单;3:按钮)',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态1:正常 0：禁用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1a236128-7a0a-45e4-b523-0d49c9c1b6b9', 'btn-user-update', '更新', 'sys:user:update', '/sys/user', 'PUT', '7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4', '0', '3', '1', '2019-09-22 16:26:08', null, '1');
INSERT INTO `sys_permission` VALUES ('1a2ec857-e775-4377-9fb7-e3c77738b3e5', 'btn-role-add', '新增', 'sys:role:add', '/sys/role', 'POST', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '0', '3', '1', '2019-09-22 16:00:59', null, '1');
INSERT INTO `sys_permission` VALUES ('1c51c1e5-6cd8-4056-a3cc-06b2dd4a5748', 'btn-dept-detail', '详情', 'sys:dept:detail', '/sys/*/dept', 'GET', '8f222e40-03b9-4fae-9eba-7fcd080aa054', '0', '3', '0', '2019-09-23 17:13:09', null, '1');
INSERT INTO `sys_permission` VALUES ('2c167006-5eb4-4c5c-b8f1-86d277001f0a', null, '系统管理', null, 'xx.html', null, '0', '0', '1', '1', '2019-09-23 16:30:42', null, '1');
INSERT INTO `sys_permission` VALUES ('2d693d0c-6b12-43d5-afca-0836e9496f0a', 'btn-permission-deleted', '删除', 'sys:permission:deleted', '/sys/*/permission', 'DELETED', '3dac936c-c4e1-4560-ac93-905502f61ae0', '0', '3', '1', '2019-09-22 15:31:43', null, '1');
INSERT INTO `sys_permission` VALUES ('3dac936c-c4e1-4560-ac93-905502f61ae0', null, '组织管理-菜单权限管理', '', '', '', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '0', '2', '1', '2019-09-22 15:18:12', null, '1');
INSERT INTO `sys_permission` VALUES ('463b5783-69ff-4395-b58f-131bee590314', 'btn-dept-user-list', '机构用户列表', 'sys:dept:user:list', '/sys/dept/users', 'POST', '8f222e40-03b9-4fae-9eba-7fcd080aa054', '0', '3', '0', '2019-09-23 17:15:06', null, '1');
INSERT INTO `sys_permission` VALUES ('4b11ac7a-c9a7-4987-b5e5-11db11801827', 'btn-permission-update', '更新', 'sys:permission:update', '/sys/permission', 'PUT', '3dac936c-c4e1-4560-ac93-905502f61ae0', '0', '3', '1', '2019-09-22 15:33:20', null, '1');
INSERT INTO `sys_permission` VALUES ('54544935-7e26-4eb7-a6d1-40ed75e3eebb', 'btn-dept-add', '新增', 'sys:dept:add', '/sys/dept', 'POST', '8f222e40-03b9-4fae-9eba-7fcd080aa054', '0', '3', '0', '2019-09-23 17:09:19', null, '1');
INSERT INTO `sys_permission` VALUES ('58612968-d93c-4c21-8fdc-a825c0ab0275', 'btn-role-list', '列表', 'sys:role:list', '/sys/roles', 'POST', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '0', '3', '1', '2019-09-22 16:04:33', null, '1');
INSERT INTO `sys_permission` VALUES ('697476e4-ac18-4522-a0cd-078f55755ddc', 'btn-log-list', '列表', 'sys:log:list', '/sys/logs', 'POST', '697476e4-ac18-4522-a0cd-078f55755eeb', '0', '3', '1', '2019-09-23 16:40:35', null, '1');
INSERT INTO `sys_permission` VALUES ('697476e4-ac18-4522-a0cd-078f55755eeb', null, '系统管理-操作日志管理', null, null, null, '2c167006-5eb4-4c5c-b8f1-86d277001f0a', '0', '2', '1', '2019-09-23 16:37:53', null, '1');
INSERT INTO `sys_permission` VALUES ('7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4', '', '组织管理-用户管理', '', '', '', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '0', '2', '1', '2019-09-22 16:24:14', null, '1');
INSERT INTO `sys_permission` VALUES ('8f222e40-03b9-4fae-9eba-7fcd080aa054', null, '组织管理-机构管理', null, null, null, 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '0', '2', '1', '2019-09-23 17:07:43', null, '1');
INSERT INTO `sys_permission` VALUES ('992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', 'btn-role-update', '更新', 'sys:role:update', '/sys/role', 'PUT', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '0', '3', '1', '2019-09-22 16:03:46', null, '1');
INSERT INTO `sys_permission` VALUES ('9be88ee8-7659-495a-b984-deacb537904a', 'btn-dept-deleted', '删除', 'sys:dept:deleted', '/sys/*/dept', 'DELETED', '8f222e40-03b9-4fae-9eba-7fcd080aa054', '0', '3', '0', '2019-09-23 17:10:30', null, '1');
INSERT INTO `sys_permission` VALUES ('b01614ab-0538-4cca-bb61-b46f18c60aa4', 'btn-role-detail', '详情', 'sys:role:detail', '/sys/*/role', 'GET', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '0', '3', '1', '2019-09-22 16:06:13', null, '1');
INSERT INTO `sys_permission` VALUES ('c30389e8-eb3e-4a0d-99c4-639e1893a05f', 'btn-permission-list', '列表', 'sys:permission:list', '/sys/permissions', 'POST', '3dac936c-c4e1-4560-ac93-905502f61ae0', '0', '3', '1', '2019-09-22 15:26:45', null, '1');
INSERT INTO `sys_permission` VALUES ('c3ed5016-a2af-4df7-8b49-6ad23cf5ed2c', 'btn-role-deleted', '删除', 'sys:role:deleted', '/sys/*/role', 'DELETED', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '0', '3', '1', '2019-09-22 16:03:04', null, '1');
INSERT INTO `sys_permission` VALUES ('c92ea720-8796-4f98-b0bb-3ec897a5dce1', 'btn-permission-detail', '详情', 'sys:permission:detail', '/sys/*/permission', 'GET', '3dac936c-c4e1-4560-ac93-905502f61ae0', '0', '3', '1', '2019-09-22 15:34:56', null, '1');
INSERT INTO `sys_permission` VALUES ('cb460d8f-6f8b-491c-bb91-881380a22604', 'btn-user-list', '列表', 'sys:user:list', '/sys/users', 'POST', '7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4', '0', '3', '1', '2019-09-22 16:26:49', null, '1');
INSERT INTO `sys_permission` VALUES ('d6214dcb-8b6d-494b-88fa-f519fc08ff8f', null, '组织管理', '', 'xx.html', '', '0', '0', '1', '1', '2019-09-22 15:16:14', null, '1');
INSERT INTO `sys_permission` VALUES ('d668756b-fe1d-4cf7-8039-76b7736dda7b', 'btn-user-detail', '详情', 'sys:user:detail', '/sys/*/user', 'GET', '7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4', '0', '3', '1', '2019-09-22 16:27:43', '2019-09-22 16:58:51', '1');
INSERT INTO `sys_permission` VALUES ('e0b16b95-09de-4d60-a283-1eebd424ed47', '', '组织管理-角色管理', '', '', '', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '0', '2', '1', '2019-09-22 15:45:45', null, '1');
INSERT INTO `sys_permission` VALUES ('e881e45d-1090-46aa-99a9-5030ced5ced5', 'btn-permission-add', '新增', 'sys:permission:add', '/sys/permission', 'POST', '3dac936c-c4e1-4560-ac93-905502f61ae0', '0', '3', '1', '2019-09-22 15:35:22', null, '1');
INSERT INTO `sys_permission` VALUES ('eb7cca8c-aefc-4ea4-83f2-bb3c660cc568', 'btn-dept-update', '更新', 'sys:dept:update', '/sys/dept', 'PUT', '8f222e40-03b9-4fae-9eba-7fcd080aa054', '0', '3', '0', '2019-09-23 17:11:59', null, '1');
INSERT INTO `sys_permission` VALUES ('f86a5cb4-72ae-447f-b2fc-dbda8ac3a26a', 'btn-dept-list', '列表', 'sys:dept:list', '/sys/depts', 'POST', '8f222e40-03b9-4fae-9eba-7fcd080aa054', '0', '3', '0', '2019-09-23 17:14:11', null, '1');
INSERT INTO `sys_permission` VALUES ('fc0f1095-c5f9-45e2-9145-d77ebf4bc078', 'btn-user-add', '新增', 'sys:user:add', '/sys/user', 'POST', '7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4', '0', '3', '1', '2019-09-22 16:58:28', null, '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(300) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '1' COMMENT '状态(1:正常0:弃用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '超级管理员', '超级管理员用户所有权限', '1', '2019-09-22 19:22:12', '2019-09-23 20:30:18', '1');
INSERT INTO `sys_role` VALUES ('dddc090f-7f48-4fe3-8ada-1fd4422ded7b', '普通用户所拥有的角色', '普通用户所拥有的角色', '1', '2019-09-25 14:01:16', '2019-09-25 14:18:13', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `permission_id` varchar(64) DEFAULT NULL COMMENT '菜单权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1d90ff0e-d5fc-4968-b4d3-627e16348299', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'b01614ab-0538-4cca-bb61-b46f18c60aa4', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('2afec0fd-c771-48a7-b2cd-dab5eaeb5557', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '58612968-d93c-4c21-8fdc-a825c0ab0275', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('32681030-bc0b-4349-b8a8-50d148b25718', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '3dac936c-c4e1-4560-ac93-905502f61ae0', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('3a923624-5119-4fa8-bff8-c7978c4edefe', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', '2c167006-5eb4-4c5c-b8f1-86d277001f0a', '2019-09-25 14:18:13');
INSERT INTO `sys_role_permission` VALUES ('427f049f-fae0-4a28-bc3b-ffc765e60561', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', '697476e4-ac18-4522-a0cd-078f55755ddc', '2019-09-25 14:18:13');
INSERT INTO `sys_role_permission` VALUES ('4704e287-f717-4d3f-9431-fc3a7eb91942', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '2d693d0c-6b12-43d5-afca-0836e9496f0a', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('478a4dba-3faf-4dc5-80b5-5afcaf55e4f1', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '697476e4-ac18-4522-a0cd-078f55755eeb', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('48734843-6c21-440e-ab9e-f32d514a4e9f', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'c30389e8-eb3e-4a0d-99c4-639e1893a05f', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('523f633e-5d5d-473d-ad0d-8ed5a0943988', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'cb460d8f-6f8b-491c-bb91-881380a22604', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('571368f6-4976-45af-83a3-7acb634cb09d', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '1c51c1e5-6cd8-4056-a3cc-06b2dd4a5748', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('5c0390dd-025f-496c-a3ba-53f930a16337', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '54544935-7e26-4eb7-a6d1-40ed75e3eebb', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('64458b1f-cabf-49d3-96e1-569505d360c9', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', 'd668756b-fe1d-4cf7-8039-76b7736dda7b', '2019-09-25 14:18:13');
INSERT INTO `sys_role_permission` VALUES ('6db7fe47-4874-4250-9471-006cf44fa92f', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'f86a5cb4-72ae-447f-b2fc-dbda8ac3a26a', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('7bffc5ee-1ddc-417e-abf2-965cff5bd92d', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '1a2ec857-e775-4377-9fb7-e3c77738b3e5', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('81b1c554-8863-4651-9a9d-670b6bef2fc6', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'd668756b-fe1d-4cf7-8039-76b7736dda7b', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('82c3575a-4f7f-41b0-ae7c-fcaf81acdda1', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('89447efa-c5d4-41c7-b2cb-f458a696c88c', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', '1a236128-7a0a-45e4-b523-0d49c9c1b6b9', '2019-09-25 14:18:13');
INSERT INTO `sys_role_permission` VALUES ('905fc2b8-3854-4b27-b30a-582a890c7eba', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'e881e45d-1090-46aa-99a9-5030ced5ced5', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('9b6ad889-3991-453a-b29f-05bf84be1ccf', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'c3ed5016-a2af-4df7-8b49-6ad23cf5ed2c', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('9d4a51dd-be96-42de-ac67-f77eb558ddb4', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '697476e4-ac18-4522-a0cd-078f55755ddc', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('b15a598e-2c18-46d3-a2bd-16274da805b6', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '1a236128-7a0a-45e4-b523-0d49c9c1b6b9', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('b424304c-adc4-4aec-bb48-0754e23b9a70', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '8f222e40-03b9-4fae-9eba-7fcd080aa054', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('d2161b52-9180-4e48-8273-57248542c757', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'fc0f1095-c5f9-45e2-9145-d77ebf4bc078', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('da849243-4af6-460a-b4e9-a251a3971e3d', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '463b5783-69ff-4395-b58f-131bee590314', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('dfe038d9-84d1-4b6b-b455-9cb20af96ccb', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', '7b4a8e7b-0b75-440b-bed0-ae7d49ff51e4', '2019-09-25 14:18:13');
INSERT INTO `sys_role_permission` VALUES ('e0862eea-28a0-45c3-9932-c87f217da3aa', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'eb7cca8c-aefc-4ea4-83f2-bb3c660cc568', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('e4b5fd28-99f0-40ac-860f-6bc51e6d485a', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', '697476e4-ac18-4522-a0cd-078f55755eeb', '2019-09-25 14:18:13');
INSERT INTO `sys_role_permission` VALUES ('e6d70aab-e970-4a79-8914-85a3906c783f', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'e0b16b95-09de-4d60-a283-1eebd424ed47', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('e787a88e-f269-4aaf-8e15-72c9a6e00532', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '2c167006-5eb4-4c5c-b8f1-86d277001f0a', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('e828f86b-66f1-46f4-8aa7-6b32108695e1', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '4b11ac7a-c9a7-4987-b5e5-11db11801827', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('f106948b-c85c-42f7-a950-7f6353c01c3c', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '9be88ee8-7659-495a-b984-deacb537904a', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('f2de9454-e97b-4949-953d-b627008b4d2b', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '992d1a8d-b5f8-44fc-9a48-4b3e60a7b15e', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('fa8731f3-b2ed-48b6-899f-18b64ee0ff25', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2019-09-25 14:18:13');
INSERT INTO `sys_role_permission` VALUES ('fc68b4a4-2af8-48f9-b692-734699636c81', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'c92ea720-8796-4f98-b0bb-3ec897a5dce1', '2019-09-23 20:30:18');
INSERT INTO `sys_role_permission` VALUES ('ffa8b89c-069b-49b1-8390-c1cab6576f6e', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', 'd6214dcb-8b6d-494b-88fa-f519fc08ff8f', '2019-09-23 20:30:18');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `username` varchar(50) NOT NULL COMMENT '账户名称',
  `salt` varchar(20) DEFAULT NULL COMMENT '加密盐值',
  `password` varchar(200) NOT NULL COMMENT '用户密码密文',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `dept_id` varchar(64) DEFAULT NULL COMMENT '部门id',
  `real_name` varchar(60) DEFAULT NULL COMMENT '真实名称',
  `nick_name` varchar(60) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱(唯一)',
  `status` tinyint(4) DEFAULT '1' COMMENT '账户状态(1.正常 2.锁定 )',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别(1.男 2.女)',
  `deleted` tinyint(4) DEFAULT '1' COMMENT '是否删除(1未删除；0已删除)',
  `create_id` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_id` varchar(64) DEFAULT NULL COMMENT '更新人',
  `create_where` tinyint(4) DEFAULT NULL COMMENT '创建来源(1.web 2.android 3.ios )',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', 'admin', '324ce32d86224b00a02b', 'ac7e435db19997a46e3b390e69cb148b', '13888888888', '24f41c71-5a95-4ef4-9493-174574f3b0c5', null, null, 'yingxue@163.com', '1', null, '1', null, null, '3', '2019-09-22 19:38:05', null);
INSERT INTO `sys_user` VALUES ('d574c119-52f6-4cfe-9da8-5b1dabefce4f', 'xh123', '36da52c3637a4409b600', '4fefaf9ce9cd8a1598f6a3c93a42fc43', '13877777777', '1d193c11-a5bd-4aef-83e0-328dfb777377', '小黄', '黄', 'yingxuexh@163.com', '1', '1', '1', null, '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', '3', '2019-09-22 12:54:45', '2019-09-25 14:02:46');
INSERT INTO `sys_user` VALUES ('f6f76c4d-789a-4a21-9e29-74b93bb67167', 'xc123', '834edf2c5ee84ff3b330', '9cf6b887043a876f30615bba96175f69', '13866666666', 'a8ab2ad9-22a9-412c-b211-9069f777760c', '小陈', '陈', 'yingxuexc@163.com', '1', '2', '1', null, 'a8be6fd4-f540-4e27-a0ef-0bef9130f142', '3', '2019-09-22 12:52:29', '2019-09-22 13:13:57');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `user_id` varchar(64) DEFAULT NULL,
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('5958884d-1a85-42d2-af5f-748b790e06c6', '9a26f5f1-cbd2-473d-82db-1d6dcf4598f8', '4866b3fc-1c3d-4c2b-8a2f-e187cd3b9713', '2019-09-22 19:38:05');
INSERT INTO `sys_user_role` VALUES ('e725a967-485c-41f7-bafa-6540eaa0449f', 'd574c119-52f6-4cfe-9da8-5b1dabefce4f', 'dddc090f-7f48-4fe3-8ada-1fd4422ded7b', '2019-09-25 14:02:46');
