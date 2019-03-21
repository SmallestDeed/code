-- 新增评论置顶排序字段
USE `app_online_30`;
ALTER TABLE user_reviews ADD COLUMN `is_top` INT (11) DEFAULT '0' COMMENT "置顶排序";
