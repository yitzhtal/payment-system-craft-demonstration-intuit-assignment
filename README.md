# Payment-System-Craft-Demonstration-Intuit-Assignment

# How to run the project?

Prerequisites: Docker, Java and Maven installed.

1) Clone the project to your local directory:</br> ```git clone https://github.com/talitz/Payment-System-Craft-Demonstration-Intuit-Assignment.git```</br>Now cd into the project and provide permissions to the following shell running scripts:
- ```chmod +x *.sh```.

2) Run the RabbitMQ Message queue *first* using the command: ```sudo ./run-rabbitmq.sh```, provide your password after running the command.
Verify you are able to visit the RabbitMQ UI Management Tool with guest;guest here: http://localhost:8081.

3) Run the Rest Payment Service using the command: ```sudo ./run-rest-payment-service.sh```.

4) Run the Risk Engine Service using the command: ```sudo ./run-risk-engine.sh```.

You are now ready to go. 
- Create payments at: ```Payment-System-Craft-Demonstration-Intuit-Assignment/rest-payment-service/src/main/resources/templates/dist/index.html```.
- Watch the processed payments after the Risk Engine analysis at: ```COMPLETE ME```.

5) Terminate the project using: ```./terminate.sh```.

