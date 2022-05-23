INSERT INTO systems (id, name, status) VALUES (1, 'Forum', 1);
INSERT INTO systems (id, name, status) VALUES (2, 'Market', 1);

INSERT INTO services (id, name, status, url, system_id) VALUES (1, 'Topic', 1, 'www.topic.com', 1);
INSERT INTO services (id, name, status, url, system_id) VALUES (2, 'Payment', 1, 'www.payment.com', 2);
INSERT INTO services (id, name, status, url, system_id) VALUES (3, 'Cart', 0, 'www.cart.com', 2);

INSERT INTO transactions (id, name, status, url, service_id) VALUES (1, 'Create topic', 1, 'www.topic.com/create', 1);
INSERT INTO transactions (id, name, status, url, service_id) VALUES (2, 'Remove topic', 1, 'www.topic.com/remove', 1);
INSERT INTO transactions (id, name, status, url, service_id) VALUES (3, 'Answer topic', 0, 'www.topic.com/answer', 1);
INSERT INTO transactions (id, name, status, url, service_id) VALUES (4, 'Debit card', 1, 'www.payment.com/debit', 2);
INSERT INTO transactions (id, name, status, url, service_id) VALUES (5, 'Credit card', 1, 'www.payment.com/credit', 2);
INSERT INTO transactions (id, name, status, url, service_id) VALUES (6, 'Add item', 1, 'www.cart.com/add', 3);
INSERT INTO transactions (id, name, status, url, service_id) VALUES (7, 'Remove item', 0, 'www.cart.com/remove', 3);

INSERT INTO profiles (id, name, status) VALUES (10, 'ROLE_ADM', 1);
INSERT INTO profiles (id, name, status) VALUES (20, 'ROLE_SYS', 1);
INSERT INTO profiles (id, name, status) VALUES (30, 'ROLE_ITM', 0);
INSERT INTO profiles (id, name, status) VALUES (40, 'ROLE_ALG', 0);

INSERT INTO profile_transaction (profile_id, transaction_id) VALUES ('10', '3');
INSERT INTO profile_transaction (profile_id, transaction_id) VALUES ('10', '2');
INSERT INTO profile_transaction (profile_id, transaction_id) VALUES ('10', '1');

INSERT INTO users (id, login, name, password, register, status) VALUES (1, 'italomdd', 'Italo M', '$2a$10$O5RcFg58Kx93HlSSVK0ReOZ1FG.pbpqRbEVF.oNB/6sizBv1EpCTK', '2022-05-21', 1);
INSERT INTO users (id, login, name, password, register, status) VALUES (2, 'georgel', 'George L', '$2a$10$O5RcFg58Kx93HlSSVK0ReOZ1FG.pbpqRbEVF.oNB/6sizBv1EpCTK', '2022-05-21', 1);

INSERT INTO user_profile (start, profile_id, user_id) VALUES ('2022-05-21', '10', '1');
INSERT INTO user_profile (start, profile_id, user_id) VALUES ('2022-05-21', '10', '2');
INSERT INTO user_profile (start, profile_id, user_id) VALUES ('2022-05-21', '20', '1');
INSERT INTO user_profile (start, profile_id, user_id) VALUES ('2022-05-21', '30', '1');
INSERT INTO user_profile (end, start, profile_id, user_id) VALUES ('2022-05-21', '2021-05-21', '40', '1');