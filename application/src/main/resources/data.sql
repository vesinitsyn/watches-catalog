insert into watch (id, name, price) values ('001', 'Rolex', 100)
insert into watch (id, name, price) values ('002', 'Michael Kors', 80)
insert into watch (id, name, price) values ('003', 'Swatch', 50)
insert into watch (id, name, price) values ('004', 'Casio', 30)
insert into quantity_discount(id, watch_id, quantity, price) values (nextval('quantity_discount_seq'), '001', 3, 200)
insert into quantity_discount(id, watch_id, quantity, price) values (nextval('quantity_discount_seq'), '002', 2, 120)