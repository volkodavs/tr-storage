[![Build Status](https://travis-ci.org/volkodavs/tr-storage.svg)](https://travis-ci.org/volkodavs/tr-storage)

# Code Challenge


## Task

Build RESTful web service that stores some transactions and returns information about those transactions.
The transactions to be stored have a type and an amount. The service should support returning all transactions of a type. Also, transactions can be linked to each other (using a "parent_id") and we need to know the total amount involved for all transactions linked to a particular transaction.

### Endpoint

Base path = `/transactionservice`

|HTTP Method| URL| Request| Response |
|---|--- | --- | --- | --- 
| PUT | `/transaction/$transaction_id` | `{"amount":double, "type":string, "parent_id":long}`|`{"status": "ok"}`|
| GET | `/transaction/$transaction_id` | | `{"amount":double, "type":string, "parent_id":long}`| 
| GET | `/types/$type` | | `[long, long, .. ]`|
| GET | `/sum/$transaction_id` | | `{"sum", double}`|

* **transaction_id** is a long specifying a new transaction
* **amount** is a double specifying the amount
* **type is** a string specifying a type of the transaction.
* **parent_id** is an optional long that may specify the parent transaction of this transaction.

## Implementation


### Build Application 

```
$ gradle buildDocker
```

### Start Application

```
$ docker-compose up
```

### API Endpoint

```
http://{ip}:{port}/trstorage/swagger/
```

![](http://i.imgur.com/0sbFO1I.png)


### Performance Test

```
$ jmeter -t src/test/resources/jmeter/tr_store.jmx
```

![] (http://i.imgur.com/JuHJVrG.png)



