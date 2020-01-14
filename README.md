# Payment-System-Craft-Demonstration-Intuit-Assignment

<img src="https://i.ibb.co/6PhCBGT/Screen-Shot-2020-01-01-at-20-24-01.png" align="center">

### Introduction:
Implementation of a peer to peer distributed payment system that can be integrated to one of the Intuit products.
- <b>REST Payment API</b> - Implemented using Spring Framework in Java, receives the payment requests and publishes to the rabbit-me message queue.
- <b>RabbitMQ Message Queue</b> - Receives the REST API requests and stores it in it's queue. 
- <b>Risk Engine</b> - consumes messages from the RabbitMQ Message queue, performs risk analysis and store the results in H2 Database.
- <b>MySQL 5.7</b> - an open-source relational database management system.

### Prerequisites:

- Docker 19.03.5. 
- Java 1.8.0_221.
- Maven 3.6.0.
- The following ports should be open before running the project: 8080, 8087, 5672, 8081.

### Running the Project:

Please <b>strictly</b> follow each step of the following:

1) Clone the project to your local directory:</br> ```git clone https://github.com/talitz/payment-system-craft-demonstration-intuit-assignment.git```.

2) CD into the directory: ```cd payment-system-craft-demonstration-intuit-assignment```.

3) Provide permissions to the shell running scripts: ```chmod 755 *.sh```.

4) Run the RabbitMQ Message queue first (provide your root password):</br> ```sudo ./run-rabbitmq-with-mysql.sh```.</br>

You can install 'MySQLWorkbench' and verify the database connection with the following details:
- Hostname: localhost; port 3360.
- user name and password are talitz;1234.

5) Run the Risk Engine Service: ```sudo ./run-risk-engine.sh```.

6) Open a new terminal, and run the Rest Payment Service: 
- ```cd payment-system-craft-demonstration-intuit-assignment```
- ```sudo ./run-rest-payment-service.sh```.

You are now ready to go. 

- Create payments under 'Create Payment' button here: ```payment-system-craft-demonstration-intuit-assignment/create_payment.html```:</br>
<img src="https://i.ibb.co/c1ygkw4/Screen-Shot-2019-12-29-at-17-51-21.png" align="center" height="448" width="348" ><br/>
* A successful payment must be sent with a valid credit card number, you can use "5196081888500645" for your manual testing / any other valid credit card number.

View the results in two ways:
- All database rows are written into the console log after each payment is processed:

<img src="https://i.ibb.co/cYNwHHG/Screen-Shot-2020-01-01-at-20-18-13.png" align="center" height="298" width="988" ><br/>

- In 'MySQLWorkbench' (or any other SQL monitoring tool), after achieving database connection (localhost:3360;talitz;1234):

<img src="https://i.ibb.co/GpNLvNq/Screen-Shot-2020-01-01-at-20-15-08.png" align="center" height="218" width="988" ><br/>

7) Terminate the project using: ```sudo ./terminate.sh```.

### What's Next?

- Run the Spring project in a containerized Java & Maven Docker environment.
- Secure the system - use specific username & password for connecting to RabbitMQ.
- Design a UI to present the DataBase rows under 'Processed Payments' html tab.
- Run the 'Risk Engine' process on multiple machines to achieve *Horizontal Scalability*.

<img src="https://i.ibb.co/rH0qsMS/1-vqm-Ige-JMu-Isgf-ISNCKLx8w.png" align="center" height="218" width="398" ><br/>

