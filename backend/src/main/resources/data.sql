INSERT INTO campaign(id, start_date, end_date, name, amount, campaign_uid) values (1, CURRENT_DATE() - 1, CURRENT_DATE() + 7, 'Spring Campaign', 10.99, random_uuid());
INSERT INTO campaign(id, start_date, end_date, name, amount, campaign_uid) values (2, CURRENT_DATE() - 15, CURRENT_DATE() + 15, 'Eastern Campaign', 9.99, random_uuid());

INSERT INTO condition(id, campaign_id, name, expression) values(1, 1, 'All conditions', 'countryCode == ''POLAND'' && depositAmount >= 10');
INSERT INTO condition(id, campaign_id, name, expression) values(2, 2, 'Regional condition', 'countryCode == ''UKRAINE''');
INSERT INTO condition(id, campaign_id, name, expression) values(3, 2, 'Deposit condition', 'depositAmount > 100');

INSERT INTO offer(id, expiration_date, offer_uid, company_name, campaign_id) values(1, CURRENT_DATE() + 7, random_uuid(), 'abc', 1);
INSERT INTO offer(id, expiration_date, offer_uid, company_name, campaign_id) values(2, CURRENT_DATE() + 10, random_uuid(), 'abc', 2);

