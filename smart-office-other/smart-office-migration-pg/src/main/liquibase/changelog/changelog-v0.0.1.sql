--liquibase formatted sql

--changeset lebedeva:001_create_bookings_table

CREATE TYPE "status_type" AS ENUM ('active', 'completed', 'cancelled');

CREATE TABLE "bookings" (
	"id" text primary key constraint bookings_id_length_ctr check (length("id") < 64),
	"user_id" text not null constraint bookings_user_id_length_ctr check (length("user_id") < 64),
	"floor_id" text not null constraint bookings_floor_id_length_ctr check (length("floor_id") < 64),
	"room_id" text not null constraint bookings_room_id_length_ctr check (length("room_id") < 64),
	"workspace_id" text not null constraint bookings_workspace_id_length_ctr check (length("workspace_id") < 64),
	"start_time" timestamptz not null,
	"end_time" timestamptz not null,
	"status" status_type not null,
	"lock" text not null constraint bookings_lock_length_ctr check (length("lock") < 64)
);

CREATE INDEX bookings_user_id_idx on "bookings" using hash ("user_id");

CREATE INDEX bookings_status_type_idx on "bookings" using hash ("status");

