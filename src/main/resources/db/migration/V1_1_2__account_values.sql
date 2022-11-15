insert into account (amount_amt, firstname, middlename, lastname, currency_id) values
(1000, 'john', null, null, (select cur.id from currency cur where 1 = 1 and cur.title = 'RUB')),
(1000, 'mary', null, null, (select cur.id from currency cur where 1 = 1 and cur.title = 'RUB')),
(1000, 'donald', null, null, (select cur.id from currency cur where 1 = 1 and cur.title = 'USD'));