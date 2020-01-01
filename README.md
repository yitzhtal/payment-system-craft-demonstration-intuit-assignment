# Payment-System-Craft-Demonstration-Intuit-Assignment

<img src="https://i.ibb.co/n1WhrLG/Screen-Shot-2019-12-29-at-18-31-10.png" align="center">

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

### Running the Project:

Please <b>strictly</b> follow each step of the following:

1) Clone the project to your local directory:</br> ```git clone https://github.com/talitz/Payment-System-Craft-Demonstration-Intuit-Assignment.git```.

2) CD into the directory: ```cd Payment-System-Craft-Demonstration-Intuit-Assignment```.

3) Provide permissions to the shell running scripts: ```chmod 755 *.sh```.

4) Run the RabbitMQ Message queue first (provide your root password):</br> ```sudo ./run-rabbitmq-with-mysql.sh```.</br>

You can install 'MySQLWorkbench' and verify the database connection with the following details:
- Hostname: localhost; port 3306.
- user name and password are talitz;1234.

5) Run the Risk Engine Service: ```sudo ./run-risk-engine.sh```.

6) Open a new terminal, and run the Rest Payment Service: 
- ```cd Payment-System-Craft-Demonstration-Intuit-Assignment```
- ```sudo ./run-rest-payment-service.sh```.

You are now ready to go. 

- Create payments under 'Create Payment' button here: ```Payment-System-Craft-Demonstration-Intuit-Assignment/create_payment.html```:
<img src="https://i.ibb.co/c1ygkw4/Screen-Shot-2019-12-29-at-17-51-21.png" align="center" height="448" width="348" ><br/>
* A successful payment must be sent with a valid credit card number, you can use "5196081888500645" for your manual testing / any other valid credit card number.

View the results in two ways:
- All database rows are written into the console log after each payment is processed:

<img src="https://i.ibb.co/cYNwHHG/Screen-Shot-2020-01-01-at-20-18-13.png" align="center" height="448" width="348" ><br/>

- In 'MySQLWorkbench' (or any other SQL monitoring tool), after you connected with the former details to the database:

<img src="https://i.ibb.co/GpNLvNq/Screen-Shot-2020-01-01-at-20-15-08.png" align="center" height="448" width="348" ><br/>

7) Terminate the project using: ```sudo ./terminate.sh```.

### What's Next?

- Run the project in Containrized environment that contains Java & Maven.
- Secure the system - use specific username & password for connecting to RabbitMQ.
- Design a UI to present the DataBase rows under 'Processed Payments' html tab.
