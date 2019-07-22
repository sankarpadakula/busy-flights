**Travix BusyFlights **

**Background:**

BusyFlights is a flights search solution which aggregates flight results initially from 2 different suppliers (CrazyAir and ToughJet). A future iteration (not part of the test) may add more suppliers.

To build:
```$xslt
mvn clean package
```

To run as standalone:
```$xslt
java -jar target/busyflights-1.0.jar
```

Note: To run in tomcat:
```$xslt
1. change in pom.xml  <packaging>war</packaging> insted of  <packaging>jar</packaging>
2. build the application using mvn clean package
3. deploy the war into tomcat


Once running, the rest service can either be accessed via the [SwaggerUI](http://localhost:8080/swagger-ui.html),
or can use any rest tools line postman, Advanced Rest Client...
or by curling requests at the relevant endpoints documented below.

##Note
Flight data are stored in ./data/CrazyAirs.json and ./data/ToughJets.json, application will take those files as 
masterdata and return matched flights.


## Endpoints

###### Search Flights
POST localhost:8080/busyflights/search 
Body:
```
{
  "origin":"MAA",
  "destination":"LHR",
  "departureDate":"01-08-2019",
  "returnDate":"10-08-2019",
  "numberOfPassengers": 2
}

```
Example:
```
curl -X POST "http://localhost:8080/busyflights/search" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"origin\": \"MAA\", \"destination\": \"LHR\", \"departureDate\": \"01-08-2019\", \"returnDate\": \"10-08-2019\", \"numberOfPassengers\": 2}"
```


**Busy Flights API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| numberOfPassengers | Maximum 4 passengers |

**Response**

| Name | Description |
| ------ | ------ |
| airline | Name of Airline |
| supplier | Eg: CrazyAir or ToughJet |
| fare | Total price rounded to 2 decimals |
| departureAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| destinationAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_DATE_TIME format |
| arrivalDate | ISO_DATE_TIME format |

The service should connect to the both the suppliers using HTTP.

**CrazyAir API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| passengerCount | Number of passengers |

**Response**


| Name | Description |
| ------ | ------ |
| airline | Name of the airline |
| price | Total price |
| cabinclass | E for Economy and B for Business |
| departureAirportCode | Eg: LHR |
| destinationAirportCode | Eg: LHR |
| departureDate | ISO_LOCAL_DATE_TIME format |
| arrivalDate | ISO_LOCAL_DATE_TIME format |

**ToughJet API**

**Request**

| Name | Description |
| ------ | ------ |
| from | 3 letter IATA code(eg. LHR, AMS) |
| to | 3 letter IATA code(eg. LHR, AMS) |
| outboundDate |ISO_LOCAL_DATE format |
| inboundDate | ISO_LOCAL_DATE format |
| numberOfAdults | Number of passengers |

**Response**

| Name | Description |
| ------ | ------ |
| carrier | Name of the Airline |
| basePrice | Price without tax(doesn't include discount) |
| tax | Tax which needs to be charged along with the price |
| discount | Discount which needs to be applied on the price(in percentage) |
| departureAirportName | 3 letter IATA code(eg. LHR, AMS) |
| arrivalAirportName | 3 letter IATA code(eg. LHR, AMS) |
| outboundDateTime | ISO_INSTANT format |
| inboundDateTime | ISO_INSTANT format |
