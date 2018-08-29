/*
 Navicat Premium Data Transfer

 Source Server         : 172.168.1.17_5432
 Source Server Type    : PostgreSQL
 Source Server Version : 90609
 Source Host           : 172.168.1.17:5432
 Source Catalog        : test2
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90609
 File Encoding         : 65001

 Date: 29/08/2018 20:20:53
*/


-- ----------------------------
-- Sequence structure for seq_comment
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."seq_comment";
CREATE SEQUENCE "public"."seq_comment" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for seq_goods
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."seq_goods";
CREATE SEQUENCE "public"."seq_goods" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for seq_ship
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."seq_ship";
CREATE SEQUENCE "public"."seq_ship" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for seq_user_info
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."seq_user_info";
CREATE SEQUENCE "public"."seq_user_info" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS "public"."comment";
CREATE TABLE "public"."comment" (
  "id" int8 NOT NULL DEFAULT nextval('seq_comment'::regclass),
  "sender_id" varchar(64) COLLATE "pg_catalog"."default",
  "provider_id" varchar(64) COLLATE "pg_catalog"."default",
  "content" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamptz(6),
  "comment_type" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS "public"."goods";
CREATE TABLE "public"."goods" (
  "id" int8 NOT NULL DEFAULT nextval('seq_goods'::regclass),
  "uid" varchar(255) COLLATE "pg_catalog"."default",
  "price" int4,
  "unit" varchar(255) COLLATE "pg_catalog"."default",
  "province" varchar(255) COLLATE "pg_catalog"."default",
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamptz(6),
  "specification" varchar(255) COLLATE "pg_catalog"."default",
  "images" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "wechat" varchar(255) COLLATE "pg_catalog"."default",
  "content" varchar(255) COLLATE "pg_catalog"."default",
  "location" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(255) COLLATE "pg_catalog"."default",
  "lat" float8,
  "lng" float8,
  "geohash_long" int8,
  "geohash5" varchar(5) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for ship
-- ----------------------------
DROP TABLE IF EXISTS "public"."ship";
CREATE TABLE "public"."ship" (
  "id" int8 NOT NULL DEFAULT nextval('seq_ship'::regclass),
  "uid" varchar(255) COLLATE "pg_catalog"."default",
  "price" int4,
  "unit" varchar(255) COLLATE "pg_catalog"."default",
  "province" varchar(255) COLLATE "pg_catalog"."default",
  "city" varchar(255) COLLATE "pg_catalog"."default",
  "ais" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamptz(6),
  "images" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "wechat" varchar(255) COLLATE "pg_catalog"."default",
  "content" varchar(255) COLLATE "pg_catalog"."default",
  "location" varchar(255) COLLATE "pg_catalog"."default",
  "lat" float8,
  "lng" float8,
  "geohash_long" int8,
  "geohash5" varchar(5) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_info";
CREATE TABLE "public"."user_info" (
  "id" int8 NOT NULL DEFAULT nextval('seq_user_info'::regclass),
  "uid" varchar(255) COLLATE "pg_catalog"."default",
  "open_id" varchar(255) COLLATE "pg_catalog"."default",
  "avatar_url" varchar(255) COLLATE "pg_catalog"."default",
  "nick_name" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamptz(6)
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
SELECT setval('"public"."seq_comment"', 118, true);
SELECT setval('"public"."seq_goods"', 164, true);
SELECT setval('"public"."seq_ship"', 20893, true);
SELECT setval('"public"."seq_user_info"', 2, true);

-- ----------------------------
-- Primary Key structure for table comment
-- ----------------------------
ALTER TABLE "public"."comment" ADD CONSTRAINT "comment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table goods
-- ----------------------------
CREATE INDEX "goods_geohash_long_idx" ON "public"."goods" USING btree (
  "geohash_long" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table goods
-- ----------------------------
ALTER TABLE "public"."goods" ADD CONSTRAINT "goods_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ship
-- ----------------------------
CREATE INDEX "ship_geohash_long_idx" ON "public"."ship" USING btree (
  "geohash_long" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table ship
-- ----------------------------
ALTER TABLE "public"."ship" ADD CONSTRAINT "ship_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE "public"."user_info" ADD CONSTRAINT "user_info_pkey" PRIMARY KEY ("id");
