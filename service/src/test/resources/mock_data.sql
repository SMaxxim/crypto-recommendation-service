CREATE TABLE prices (
	symbol varchar,
	price_timestamp timestamp,
	price_unix_timestamp numeric,
	price numeric(15, 5)
);
CREATE TABLE cryptos (
	symbol varchar NOT NULL,
	"name" varchar,
	description varchar,
	CONSTRAINT cryptos_pkey PRIMARY KEY (symbol)
);
insert into cryptos values ('BTC', 'Bitcoin', null);
insert into cryptos values ('ETH', 'Ethereum', null);
insert into prices (symbol, price_timestamp, price) values ('BTC', '2022-01-01 04:00:00', 10);
insert into prices (symbol, price_timestamp, price) values ('BTC', '2022-01-01 05:00:00', 10.5);
insert into prices (symbol, price_timestamp, price) values ('BTC', '2022-01-02 10:00:00', 11);
insert into prices (symbol, price_timestamp, price) values ('ETH', '2022-01-01 04:00:00', 10);
insert into prices (symbol, price_timestamp, price) values ('ETH', '2022-01-02 10:00:00', 12);