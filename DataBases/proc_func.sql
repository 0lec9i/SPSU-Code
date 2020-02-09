DELIMITER //
create procedure addClient
	(in first VARCHAR(100), 
    in last VARCHAR(100), 
    in email VARCHAR(100))
begin
	INSERT INTO Client(First_Name, Last_Name, Email) VALUES (first, last, email);
end;

CALL addClient('Alexandr', 'Ivanov', 'alex.ivanov@yandex.ru');
select * from Client;

 
DELIMITER //
create procedure addSnowboard
	(
    in Model VARCHAR(100), 
    in Price DECIMAL(7,2),
    in Manufacturer_ID INT,
    in Creation_Year INT,
    in Mount_ID INT, 
    in Length INT, 
    in Width INT
    )
begin
	INSERT INTO Snowboard(Mount_ID, Length, Width) values (Mount_ID, Length, Width);
	INSERT INTO Product(Model, Price, Manufacturer_ID, Creation_Year, Snowboard_ID) VALUES (Model, Price, Manufacturer_ID, Creation_Year, (SELECT Auto_increment FROM information_schema.tables WHERE table_name='Snowboard') - 1);
end;

call addSnowboard('FREEK FREEWORLD', 28500, 5, 2018, 2, 150, 35);
select * from product;


DELIMITER //
create procedure addBoots
	(
    in Model VARCHAR(100), 
    in Price DECIMAL(7,2),
    in Manufacturer_ID INT,
    in Creation_Year INT,
    in Size INT
    )
begin
	INSERT INTO Boots(Size) values (Size);
	INSERT INTO Product(Model, Price, Manufacturer_ID, Creation_Year, Boots_ID) VALUES (Model, Price, Manufacturer_ID, Creation_Year, (SELECT Auto_increment FROM information_schema.tables WHERE table_name='Boots') - 1);
end;

call addBoots('FREEWORLD LIFE', 13750, 1, 2019, 43);
select * from product;


DELIMITER $$
create function countBookingPrice(id INT) returns DECIMAL(10,2)
deterministic
begin
	return (select sum(product.Price * booking_product.N) from booking
				inner join booking_product on booking.ID = booking_product.Booking_ID
				inner join product on booking_product.Product_ID = product.ID
					where booking.ID = id);
end;

select countBookingPrice(1);


/*
DROP PROCEDURE addBoots;
DROP PROCEDURE addClient;
DROP PROCEDURE addSnowboard;
DROP FUNCTION countBookingPrice;
*/