CREATE TABLE `ep_users`
(
    `user_id`   int PRIMARY KEY AUTO_INCREMENT,
    `user_name` varchar(100) NOT NULL,
    `password`  varchar(255) NOT NULL,
    `status`    int                   DEFAULT 1,
    `ts`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `crt_at`    datetime     NOT NULL,
    `crt_by`    varchar(25)  NOT NULL,
    `upd_at`    datetime     NOT NULL,
    `upd_by`    varchar(25)  NOT NULL
);

CREATE TABLE `ep_courses`
(
    `course_id`    int PRIMARY KEY AUTO_INCREMENT,
    `course_title` varchar(255) NOT NULL,
    `course_desc`  blob,
    `start_date`   datetime,
    `end_date`     datetime,
    `duration`     varchar(25),
    `status`       int                   DEFAULT 1,
    `ts`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `crt_at`       datetime     NOT NULL,
    `crt_by`       varchar(25)  NOT NULL,
    `upd_at`       datetime     NOT NULL,
    `upd_by`       varchar(25)  NOT NULL
);

CREATE TABLE `ep_course_modules`
(
    `module_id`    int PRIMARY KEY AUTO_INCREMENT,
    `course_id`    int          NOT NULL,
    `module_title` varchar(255) NOT NULL,
    `module_desc`  blob,
    `start_date`   datetime,
    `end_date`     datetime,
    `duration`     varchar(25),
    `status`       int                   DEFAULT 1,
    `ts`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `crt_at`       datetime     NOT NULL,
    `crt_by`       varchar(25)  NOT NULL,
    `upd_at`       datetime     NOT NULL,
    `upd_by`       varchar(25)  NOT NULL
);

CREATE TABLE `ep_students`
(
    `student_id`   int PRIMARY KEY AUTO_INCREMENT,
    `student_name` varchar(100) NOT NULL,
    `email_id`     varchar(255) NOT NULL,
    `mobile_no`    varchar(20)  NOT NULL,
    `address`      varchar(255),
    `city`         varchar(100),
    `state`        varchar(50),
    `country`      varchar(50),
    `status`       int                   DEFAULT 1,
    `ts`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `crt_at`       datetime     NOT NULL,
    `crt_by`       varchar(25)  NOT NULL,
    `upd_at`       datetime     NOT NULL,
    `upd_by`       varchar(25)  NOT NULL
);

CREATE TABLE `ep_student_courses`
(
    `student_id`  int,
    `course_id`   int,
    `enroll_date` datetime    NOT NULL,
    `remarks`     varchar(255),
    `status`      int                  DEFAULT 1,
    `ts`          timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `crt_at`      datetime    NOT NULL,
    `crt_by`      varchar(25) NOT NULL,
    `upd_at`      datetime    NOT NULL,
    `upd_by`      varchar(25) NOT NULL,
    PRIMARY KEY (`student_id`, `course_id`)
);

CREATE TABLE `ep_student_tests`
(
    `test_attempt_id` int PRIMARY KEY AUTO_INCREMENT,
    `student_id`      int         NOT NULL,
    `course_id`       int         NOT NULL,
    `module_id`       int,
    `test_id`         int         NOT NULL,
    `test_type`       int         NOT NULL DEFAULT 0,
    `test_start_time` datetime,
    `test_end_time`   datetime,
    `test_data`       blob,
    `test_score` double NOT NULL,
    `remarks`         varchar(255),
    `status`          int                  DEFAULT 1,
    `ts`              timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `crt_at`          datetime    NOT NULL,
    `crt_by`          varchar(25) NOT NULL,
    `upd_at`          datetime    NOT NULL,
    `upd_by`          varchar(25) NOT NULL
);

ALTER TABLE `ep_course_modules`
    ADD FOREIGN KEY (`course_id`) REFERENCES `ep_courses` (`course_id`);

ALTER TABLE `ep_student_courses`
    ADD FOREIGN KEY (`student_id`) REFERENCES `ep_students` (`student_id`);

ALTER TABLE `ep_student_courses`
    ADD FOREIGN KEY (`course_id`) REFERENCES `ep_courses` (`course_id`);
