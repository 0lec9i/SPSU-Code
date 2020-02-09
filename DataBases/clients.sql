-- (1) Базовая информация про клиента
select * from client;


-- (2) Сортировка клиентов по количеству заказов
select client.ID, client.Email, c.Count from client, 
	(select client.ID as ID, count(*) as Count from client 
		inner join deposit on deposit.Client_ID = client.ID 
			group by client.ID
	 union
     select client.ID as ID, 0 as Count from client 
		left join deposit on deposit.Client_ID = client.ID 
			where deposit.Client_ID is null) as c
			where c.ID = client.ID
	order by c.Count DESC;
  
        
-- (3)  Сортировка клиентов по общей сумме всех заказов
select client.ID, client.Email, sum(countBookingPrice(booking.ID)) as BookingSum from client 
	inner join deposit on deposit.Client_ID = client.ID 
    inner join booking on booking.ID = deposit.Booking_ID
		group by client.ID
union
select client.ID, client.Email, 0 from client 
		join deposit on deposit.Client_ID = client.ID 
			where deposit.Client_ID is null
    order by BookingSum DESC;
    

-- (4) Список заказов с суммой
select ID, countBookingPrice(ID), Status from booking;


-- (5) Список товаров, которые еще не вернули
select manufacturer.Name, product.Model, product.Price from product
	inner join manufacturer on manufacturer.ID = product.Manufacturer_ID
	inner join booking_product on booking_product.Product_ID = product.ID
    inner join booking on booking.ID = booking_product.Booking_ID
		where booking.Status = 0
			group by manufacturer.Name, product.Model
				order by booking.ID;

-- (6) Общая информация про товары
select * from product_view
	order by product_view.ID;
    
    
-- (7) Список товаров, которые брали в аренду
select manufacturer.Name, product.Model, product.Price from product
	inner join manufacturer on manufacturer.ID = product.Manufacturer_ID
	inner join booking_product on booking_product.Product_ID = product.ID
    inner join booking on booking.ID = booking_product.Booking_ID
		where booking.Status = 1
			group by manufacturer.Name, product.Model
				order by booking.ID;

    
-- (8) Сортировка сноубордов по цене
select * from snowboards_view
	order by Price;


-- (9) Сортировка ботинок по цене
select * from boots_view
	order by Price;

    
-- (10) Сортировка товаров по цене
select * from product_view
	order by Price;

    
-- (11) Количество заказов каждого продукта
select pr.ID, pr.Manufacturer, pr.Model, sum(booking_product.N) as Count from product_view as pr
	inner join booking_product on booking_product.Product_ID = pr.ID
		group by pr.ID
union
select pr.ID, pr.Manufacturer, pr.Model, 0 from product_view as pr
	left join booking_product on booking_product.Product_ID = pr.ID
		where booking_product.Booking_ID is null;
        

-- (12) Количество заказов каждого производителя
select manufacturer.ID, manufacturer.Name as Manufacturer, sum(product_count.Count) as Count  from manufacturer
	inner join 
(select pr.Manufacturer as Manufacturer, pr.Model, sum(booking_product.N) as Count from product_view as pr
inner join booking_product on booking_product.Product_ID = pr.ID
					group by pr.ID
				union
				select pr.Manufacturer as Manufacturer, pr.Model, 0 from product_view as pr
					left join booking_product on booking_product.Product_ID = pr.ID
						where booking_product.Booking_ID is null) as product_count on manufacturer.Name = product_count.Manufacturer
		group by manufacturer.ID
union
select manufacturer.ID, manufacturer.Name AS Manufacturer, 0 from manufacturer
	left join (select pr.Manufacturer as Manufacturer, pr.Model, sum(booking_product.N) as Count from product_view as pr
				inner join booking_product on booking_product.Product_ID = pr.ID
					group by pr.ID
				union
				select pr.Manufacturer as Manufacturer, pr.Model, 0 from product_view as pr
					left join booking_product on booking_product.Product_ID = pr.ID
						where booking_product.Booking_ID is null) as product_count on manufacturer.Name = product_count.Manufacturer
		where product_count.Manufacturer is null
	order by Manufacturer;
    

-- (13) Количество товаров по году создания
select product.Creation_Year, count(*) from product
	group by product.Creation_Year
		having product.Creation_Year > 2015;