CREATE VIEW Snowboards_View 
AS
SELECT snowboard.ID as ID, 
		manufacturer.Name as Manufacturer, 
        product.Model as Model,
        snowboard.Length,
        snowboard.Width,
        mount.Orientation_Type,
        product.Price,
        product.Creation_Year
        FROM snowboard
	INNER JOIN Mount on snowboard.Mount_ID = Mount.ID
	INNER JOIN Product on Product.Snowboard_ID = snowboard.ID
    INNER JOIN Manufacturer on Product.Manufacturer_ID = Manufacturer.ID;
    

CREATE VIEW Boots_View
AS
SELECT boots.ID as ID, 
		manufacturer.Name as Manufacturer, 
        product.Model as Model,
        boots.Size,
        product.Price,
        product.Creation_Year
        FROM boots
	INNER JOIN Product on Product.Snowboard_ID = boots.ID
    INNER JOIN Manufacturer on Product.Manufacturer_ID = Manufacturer.ID;
    


CREATE VIEW Product_View
AS
select product.ID, 
		'SNOWBOARD' as Product_Type,
		manufacturer.Name as Manufacturer, 
        product.Model, 
        product.Price,
        product.Creation_Year from product
	inner join manufacturer on manufacturer.ID = product.Manufacturer_ID
		where product.Snowboard_ID is not null
union
select product.ID, 
		'BOOTS' as Product_Type,
		manufacturer.Name as Manufacturer, 
        product.Model, 
        product.Price,
        product.Creation_Year from product
	inner join manufacturer on manufacturer.ID = product.Manufacturer_ID
		where product.Boots_ID is not null
	order by Manufacturer;


CREATE VIEW Booking_View
as
select booking.ID,
		booking.Status,
        countBookingPrice(booking.ID) as BookingPrice,
        client.First_Name,
        client.Last_Name,
        client.Email,
        deposit.Document_Type as DepositDocumentType,
        deposit.Document as DepositDocument
		from booking
	inner join Deposit on deposit.Booking_ID = booking.ID
    inner join client on client.ID = deposit.Client_ID;

/*
DROP VIEW Product_View;
DROP VIEW Snowboard_View;
DROP VIEW Boots_View;
DROP VIEW Booking_View;
*/