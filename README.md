# Widget Service API

#####(Version 1.0.0)
This is the API service for Widget emulation. 

## Prerequisites

  - Java 1.8.
  - Maven 3.0
  - Tomcat 1.8


## API's


### Registration [POST]
-----------------------
Path : {widget Url}/api/v1/fills/{engineType}/{fuelType}/{quantity}

##### Path values

```
engineType: internal | steam
fuelType: petrol | diesel | coal | wood
quantity: number between 0 and 100
```


##### Response 
```json
{
    "status": "OK",
    "message": "Tank filled successfully"
}
```



### Emlation [GET]
-----------------------
Path : {widget Url}/api/v1/emulate/{engineType}/{fuelType}/produces/{quantity}

##### Prerequisites 

```
Fill tank with endpoint {widget Url}/api/v1/fills/{engineType}/{fuelType}/{quantity} 
```

##### Path values

```
engineType: internal | steam
fuelType: petrol | diesel | coal | wood
quantity: any number > 0
```

##### Response 
```
141.25 (the running production cost)
```





## Document Updated

 * v1.0.0
  - Initial Release - Gabriel Novoa Bonet (30/05/2015)