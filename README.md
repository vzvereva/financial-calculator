### API description
The application implements spring-boot based service which: 

* uses JAVA 1.8 features 
* exposes an endpoint which accept as a request payload the below json responses with json which for each client from the request calculates:
  * balance on a day when the request was made 
  * total turnover on accounts - from last day with known balance until when the request was made  
  * total incomes - from last day with known balance until when the request was made  
  * total expenditures - from last day with known balance until when the request was made  

### Prerequisites:
1. JDK v8
2. Maven v3

### How to run 
To start the application execute the following command:  
mvn spring-boot:run 

### Sample Request
POST /calculate HTTP/1.1 
Host: localhost:8080  
Content-Type: application/json

```json
{
  "clients": {
    "client": [
      {
        "info": {
          "name": "Tomasz",
          "surname": "Karcznski"
        },
        "balance": {
          "total": "12110",
          "currency": "PLN",
          "date": "01.05.2020"
        },
        "transactions": [
          {
            "type": "income",
            "description": "salary",
            "date": "04.05.2020",
            "value": "7500",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "mortgage",
            "date": "06.05.2020",
            "value": "1100",
            "currency": "PLN"
          },
          {
            "type": "income",
            "description": "interests",
            "date": "10.05.2020",
            "value": "1700",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "transfer",
            "date": "11.05.2020",
            "value": "1200",
            "currency": "PLN"
          }
        ]
      },
      {
        "info": {
          "name": "Natalia",
          "surname": "Nowak",
          "country": "Poland"
        },
        "balance": {
          "total": "6750",
          "currency": "PLN",
          "date": "01.05.2020"
        },
        "transactions": [
          {
            "type": "income",
            "description": "salary",
            "date": "04.05.2020",
            "value": "10500",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "transfer",
            "date": "10.05.2020",
            "value": "1200",
            "currency": "PLN"
          },
          {
            "type": "outcome",
            "description": "transfer",
            "date": "11.05.2020",
            "value": "1050,50",
            "currency": "PLN"
          }
        ]
      }
    ]
  }
}
```