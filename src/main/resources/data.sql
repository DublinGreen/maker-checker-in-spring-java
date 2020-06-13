INSERT INTO `autousers` (`id`, `password`, `role`, `username`,`status`) VALUES
(1, '$2a$10$s.8qwFifpt9LRlgpA3QIn.7zgWetfacrEGkDaHYLo6B03/RmvMAoi', 'ROLE_ADMIN', 'greendublin007',b'1'),
(2, '$2a$10$s.8qwFifpt9LRlgpA3QIn.7zgWetfacrEGkDaHYLo6B03/RmvMAoi', 'ROLE_USER', 'yemi',b'0');

INSERT INTO `permissions` (`id`, `created_at`, `created_by`, `name`, `role_name`, `status`, `updated_at`, `updated_by`) VALUES
(101, '2020-05-23 15:05:31', 'dublin-green', 'CAN CREATE SETTINGS','ROLE_ADMIN', b'1', '2020-05-23 15:05:31', 'dublin-green'),
(102, '2020-05-23 15:06:03', 'dublin-green', 'CAN APPROVE SETTINGS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(103, '2020-05-23 15:06:03', 'dublin-green', 'CAN DISAPPROVE SETTINGS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(104, '2020-05-23 15:06:03', 'dublin-green', 'CAN UPDATE SETTINGS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(105, '2020-05-23 15:06:03', 'dublin-green', 'CAN DELETE SETTINGS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(106, '2020-05-23 15:05:31', 'dublin-green', 'CAN CREATE CODES','ROLE_ADMIN', b'1', '2020-05-23 15:05:31', 'dublin-green'),
(107, '2020-05-23 15:06:03', 'dublin-green', 'CAN APPROVE CODES','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(108, '2020-05-23 15:06:03', 'dublin-green', 'CAN DISAPPROVE CODES','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(109, '2020-05-23 15:06:03', 'dublin-green', 'CAN UPDATE CODES','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(110, '2020-05-23 15:06:03', 'dublin-green', 'CAN DELETE CODES','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(111, '2020-05-23 15:06:03', 'dublin-green', 'CAN CREATE USERS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(112, '2020-05-23 15:06:03', 'dublin-green', 'CAN APPROVE USERS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(113, '2020-05-23 15:06:03', 'dublin-green', 'CAN UPDATE USERS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(114, '2020-05-23 15:06:03', 'dublin-green', 'CAN DELETE USERS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(115, '2020-05-23 15:06:03', 'dublin-green', 'CAN DISAPPROVE USERS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(116, '2020-05-23 15:06:03', 'dublin-green', 'CAN CREATE PERMISSIONS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(117, '2020-05-23 15:06:03', 'dublin-green', 'CAN APPROVE PERMISSIONS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(118, '2020-05-23 15:06:03', 'dublin-green', 'CAN UPDATE PERMISSIONS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(119, '2020-05-23 15:06:03', 'dublin-green', 'CAN DELETE PERMISSIONS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green'),
(120, '2020-05-23 15:06:03', 'dublin-green', 'CAN DISAPPROVE PERMISSIONS','ROLE_ADMIN', b'1', '2020-05-23 15:06:03', 'dublin-green');

INSERT INTO `codes` (`id`, `code`, `created_at`, `created_by`, `description`, `name`, `status`, `updated_at`, `updated_by`) VALUES 
(1, '101', '2020-06-05 00:00:00', 'dublin-green', 'test code', 'code_test', b'1', '2020-06-05 00:00:00', 'dublin_green');