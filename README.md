## Stock Exchange Service
---

The goal of the service is to list the close price of a ticker symbol listed in stock exchange.
Currently the service supports one of GE, AMZN, AAPL, IBM ticker symbols.

The service is built with Java Spring Boot Application and Java Version 17 and uses Maven for Dependency Management and Build System


### API Details / Usage
---
GET /v1/price

#### API Request Example:
---
GET /price?ticker=AMZN&from=2021-10-01&to=2021-11-01
### API Response Example:
---
```
[
    {
        "timestamp": "2021-11-01",
        "close": "148.9600"
    },
    {
        "timestamp": "2021-10-29",
        "close": "149.8000"
    },
    ...
]
```

### How to Build the application
---
1. Clone the repository to your local system
```
   $> git clone https://github.com/sahebray85/StockExchange.git
```
3. Build the Application

   2.a) Goto to your checkout location
   
        $> cd StockExchange
   
   2.b) Build using maven
   
         $> mvnw clean install (Windows)
         $> ./mvnw clean install (Unix Shell)
   
4. Run the application server.
  ```
   $> mvnw spring-boot:run (Windows)
   $> ./mvnw spring-boot:run (Unix Shell)
  ```
   The local server should be running on localhost:8080

### How to Test the server
1. Assuming you have curl installed on your system
2. Open Powelshell command prompt
3. Execute below command:
   
   3.a) In Windows PowerShell
   ```
   $> curl -Method GET -Uri 'http://localhost:8080/v1/price?ticker=AAPL&from=2021-10-01&to=2021-11-01' -Headers @{'Accept' = 'application/json'}
   ```
   
   3.b) In Cygwin / Unix
   ```
   $> curl -X GET \
              'http://localhost:8080/v1/price?ticker=AAPL&from=2021-10-01&to=2021-11-01' \
               --header 'Accept: application/json'
   ```

   ### The API Response should be printed on Console. Sample Response Below:
  ```
  StatusCode        : 200
  StatusDescription :
  Content           : [{"timestamp":"2021-11-01","close":"148.9600"},{"timestamp":"2021-10-29","close":"149.8000"},{"time
                      stamp":"2021-10-28","close":"152.5700"},{"timestamp":"2021-10-27","close":"148.8500"},{"timestamp":
                      "2...
  RawContent        : HTTP/1.1 202
                      Transfer-Encoding: chunked
                      Keep-Alive: timeout=60
                      Connection: keep-alive
                      Content-Type: application/json
                      Date: Wed, 17 Apr 2024 20:39:56 GMT
  
                      [{"timestamp":"2021-11-01","close":"14...
  Forms             : {}
  Headers           : {[Transfer-Encoding, chunked], [Keep-Alive, timeout=60], [Connection, keep-alive], [Content-Type,
                      application/json]...}
  Images            : {}
  InputFields       : {}
  Links             : {}
  ParsedHtml        : System.__ComObject
  RawContentLength  : 1013
  ```
   
  3.c) Using PostMan
  
   1. Add GET Request
   
   2. Paste the URL:
      ```
        http://localhost:8080/v1/price?ticker=AAPL&from=2021-10-01&to=2021-11-01
      ```
   
   3. Goto Headers Tab, Click on Bulk-Edit and Paste the content from below
      ```
        Accept:application/json
      ```
   
   4. Hit Send Button on Right
   
   5. The API Response should be available on Response Panel

   ### APi Response Sample
   
    [
        {
            "timestamp": "2021-11-01",
            "close": "148.9600"
        },
        {
            "timestamp": "2021-10-29",
            "close": "149.8000"
        },
        {
            "timestamp": "2021-10-28",
            "close": "152.5700"
        },
        {
            "timestamp": "2021-10-27",
            "close": "148.8500"
        },
        {
            "timestamp": "2021-10-26",
            "close": "149.3200"
        },
        {
            "timestamp": "2021-10-25",
            "close": "148.6400"
        },
        {
            "timestamp": "2021-10-22",
            "close": "148.6900"
        },
        {
            "timestamp": "2021-10-21",
            "close": "149.4800"
        },
        {
            "timestamp": "2021-10-20",
            "close": "149.2600"
        },
        {
            "timestamp": "2021-10-19",
            "close": "148.7600"
        },
        {
            "timestamp": "2021-10-18",
            "close": "146.5500"
        },
        {
            "timestamp": "2021-10-15",
            "close": "144.8400"
        },
        {
            "timestamp": "2021-10-14",
            "close": "143.7600"
        },
        {
            "timestamp": "2021-10-13",
            "close": "140.9100"
        },
        {
            "timestamp": "2021-10-12",
            "close": "141.5100"
        },
        {
            "timestamp": "2021-10-11",
            "close": "142.8100"
        },
        {
            "timestamp": "2021-10-08",
            "close": "142.9000"
        },
        {
            "timestamp": "2021-10-07",
            "close": "143.2900"
        },
        {
            "timestamp": "2021-10-06",
            "close": "142.0000"
        },
        {
            "timestamp": "2021-10-05",
            "close": "141.1100"
        },
        {
            "timestamp": "2021-10-04",
            "close": "139.1400"
        },
        {
            "timestamp": "2021-10-01",
            "close": "142.6500"
        }
    ]
