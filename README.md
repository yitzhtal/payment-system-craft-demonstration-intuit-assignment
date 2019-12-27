# Payment-System-Craft-Demonstration-Intuit-Assignment

# How to run the project?

1) Clone the project to your local directory: ```git clone https://github.com/talitz/Payment-System-Craft-Demonstration-Intuit-Assignment.git```, then cd into the project and provide permissions to the following shell running scripts:
- ```chmod +x run-rabbitmq.sh```.
- ```chmod +x run-rest-payment-service.sh```.
- ```chmod +x run-risk-engine.sh```.
- ```chmod +x terminate.sh```.

2) Run the RabbitMQ Message queue using the command: ```./run-rabbitmq.sh```.
Verify you are able to visit the RabbitMQ UI Management Tool here: http://localhost:8081.

3) Run the Rest Payment Service using the command: ```./run-rest-payment-service.sh```.

4) Run the Risk Engine Service using the command: ```./run-risk-engine.sh```.

You are now ready to go. 
- Create payments at: ```Payment-System-Craft-Demonstration-Intuit-Assignment/rest-payment-service/src/main/resources/templates/dist/index.html```.
- Watch the processed payments after the Risk Engine analysis at: ```COMPLETE ME```.

5) Terminate the project using: ```./terminate.sh```.

