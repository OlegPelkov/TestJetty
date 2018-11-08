# UnblockingTransferJetty
UnblockingTransferJetty
a API for money transfers between accounts.

The project have two versions of transfer operation, one use concurrent lock and second use non-blocking CAS algorithm.

For start service in CAS mode - use arg `casMode`. For example: `java -jar transferMoney.jar casMode`

For start service in concurrent lock mode: `java -jar transferMoney.jar`

## Usage
Run the application and send the `GET` request with params
For create new account with initial value
```
http://localhost:8081/moneyTransfer?command=create&value=1000

```
For delete account by id
```
http://localhost:8081/moneyTransfer?command=delete&id=1
```
For transfer money between two accounts 
```
http://localhost:8081/moneyTransfer?command=transfer&srcId=1&destId=2&value=400

```

## Running tests
To run tests use test classes in the folder with the source code.

For run as standalone you can unzip `transferMoney.zip` in `/bin` directory.

