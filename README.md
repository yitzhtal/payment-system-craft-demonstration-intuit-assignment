# Payment-System-Craft-Demonstration-Intuit-Assignment

<img src="https://i.ibb.co/n1WhrLG/Screen-Shot-2019-12-29-at-18-31-10.png" align="center">

### Introduction:
Implementation of a peer to peer distributed payment system that can be integrated to one of the Intuit products.
- <b>REST Payment API</b> - Implemented using Spring Framework in Java, receives the payment requests and publishes to the rabbit-me message queue.
- <b>RabbitMQ Message Queue</b> - Receives the REST API requests and stores it in it's queue. 
- <b>Risk Engine</b> - consumes messages from the RabbitMQ Message queue, performs risk analysis and store the results in H2 Database.
- <b>H2 Database</b> - in-memory database, embedded in our Java application (integrated with our Spring Boot).

### Prerequisites:

- Docker 19.03.5. 
- Java 1.8.0_221.
- Maven 3.6.0.

### Running the Project:

1) Clone the project to your local directory:</br> ```git clone https://github.com/talitz/Payment-System-Craft-Demonstration-Intuit-Assignment.git```.

2) CD into the directory: ```cd Payment-System-Craft-Demonstration-Intuit-Assignment```.

3) Provide permissions to the shell running scripts: ```chmod 755 *.sh```.

4) Run the RabbitMQ Message queue first (provide your root password):</br> ```sudo ./run-rabbitmq.sh```.</br>
Please verify you are able to visit the RabbitMQ UI Management Tool with guest;guest here: http://localhost:8081.

5) Run the Rest Payment Service: ```sudo ./run-rest-payment-service.sh```.

6) Open a new terminal, and run the Risk Engine Service:</br>
- ```cd Payment-System-Craft-Demonstration-Intuit-Assignment```
- ```sudo ./run-risk-engine.sh```.

You are now ready to go. 

- Create payments at: ```Payment-System-Craft-Demonstration-Intuit-Assignment/create_payment.html```:
<img src="https://i.ibb.co/c1ygkw4/Screen-Shot-2019-12-29-at-17-51-21.png" align="center" height="448" width="348" ><br/>
* A successful payment must be sent with a valid credit card number, you can use "5196081888500645" for your manual testing / any other valid credit card number.

- Click on 'Process Payments' to view the Database (Insert the following <b>JDBC URL</b>: ```jdbc:h2:mem:testdb```).
7) Terminate the project using: ```sudo ./terminate.sh```.

### What's Next?

- Run the project in Containrized environment that contains Java & Maven.
- Secure the system - use specific username & password for connecting to RabbitMQ.
