insert into tool (tool_code, tool_type, brand) values ('CHNS', 'Chainsaw', 'Stihl');
insert into tool (tool_code, tool_type, brand) values ('LADW', 'Ladder', 'Werner');
insert into tool (tool_code, tool_type, brand) values ('JAKD', 'Jackhammer', 'DeWalt');
insert into tool (tool_code, tool_type, brand) values ('JAKR', 'Jackhammer', 'Ridgid');

insert into rental_terms (tool_type, daily_charge, weekday_charge, weekend_charge, holiday_charge)
                    values('Ladder', 1.99, true,true,false );
insert into rental_terms (tool_type, daily_charge, weekday_charge, weekend_charge, holiday_charge)
                    values('Chainsaw', 1.49, true,false,true );
insert into rental_terms (tool_type, daily_charge, weekday_charge, weekend_charge, holiday_charge)
                    values('Jackhammer', 2.99, true,false,false );