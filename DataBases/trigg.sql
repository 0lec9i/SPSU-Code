delimiter //
CREATE TRIGGER checkInsertPriceTrigger BEFORE INSERT ON Product
	 FOR EACH ROW
     BEGIN
         IF NEW.Price<0 THEN
             SIGNAL sqlstate '45001' set message_text = "Product.Price must be >= 0";
        END IF;
    END;
    
call addBoots('TO FREE WORLD', -1, 1, 2018, 43);


delimiter //
CREATE TRIGGER checkUpdatePriceTrigger BEFORE UPDATE ON Product
	 FOR EACH ROW
     BEGIN
         IF NEW.Price < 0 THEN
             SIGNAL sqlstate '45001' set message_text = "Product.Price must be >= 0";
        END IF;
    END;

update Product set product.Price = -1
	where product.ID = 1
    
    
delimiter //
CREATE TRIGGER checkDeleteBookingTrigger BEFORE DELETE ON booking
	 FOR EACH ROW
     BEGIN
         IF OLD.Status = 0 THEN
             SIGNAL sqlstate '45001' set message_text = "You can't delete not ended booking";
        END IF;
    END;
    
delete from booking where booking.ID=2;