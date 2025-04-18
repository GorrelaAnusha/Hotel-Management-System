create database RK_HOTEL;
use RK_HOTEL;
create table room_reservations(booking_id int auto_increment primary key,
							   customer_name varchar(100) NOT NULL ,
							    room_num int NULL ,
								contact_num VARCHAR(10) NOT NULL,
								booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
select* from room_reservations;