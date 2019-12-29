# Payment-System-Craft-Demonstration-Intuit-Assignment

<img src="https://i.ibb.co/n1WhrLG/Screen-Shot-2019-12-29-at-18-31-10.png" align="center">

# How to run the project?

<u><b>Prerequisites:</b></u> Docker, Java and Maven installed.

1) Clone the project to your local directory:</br> ```git clone https://github.com/talitz/Payment-System-Craft-Demonstration-Intuit-Assignment.git```.
2) CD into the directory: ```cd Payment-System-Craft-Demonstration-Intuit-Assignment```.
2) Provide permissions to the shell running scripts: ```chmod 755 *.sh```.
3) Run the RabbitMQ Message queue first (provide your root password9:</br> ```sudo ./run-rabbitmq.sh```.</br>
Please verify you are able to visit the RabbitMQ UI Management Tool with guest;guest here: http://localhost:8081.

3) Run the Rest Payment Service: ```sudo ./run-rest-payment-service.sh```.

4) Open a new terminal, and run the Risk Engine Service:</br>
- ```cd Payment-System-Craft-Demonstration-Intuit-Assignment```
- ```sudo ./run-risk-engine.sh```.

You are now ready to go. 

- Create payments at: ```Payment-System-Craft-Demonstration-Intuit-Assignment/create_payment.html```:
<img src="https://i.ibb.co/c1ygkw4/Screen-Shot-2019-12-29-at-17-51-21.png" align="center" height="448" width="348" ><br/>
* A successful payment must be sent with a valid credit card number, you can use "5196081888500645" for your manual testing / any other valid credit card number.
-

5) Terminate the project using: ```sudo ./terminate.sh```.

