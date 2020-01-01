DROP TABLE processed_payments IF EXISTS
CREATE TABLE processed_payments(id SERIAL, 
								amount VARCHAR(255), 
								currency VARCHAR(255), 
								userId VARCHAR(255), 
								payeeId VARCHAR(255), 
								paymentMethodId VARCHAR(255),
								riskScore VARCHAR(255),
								approved VARCHAR(255))