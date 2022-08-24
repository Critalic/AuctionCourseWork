# Auction web application
This repository contains code for my software engineering class
course work project. It's an auction website where users can search for lots
and make bids on them, and lot owners can create, update and delete their lots. 
Detailed requirements, use cases and class diagrams can be found [here](https://docs.google.com/document/d/1l7PJY_uS_KNs8tnArPabn-X4kCJ8Uwf0/edit?usp=sharing&ouid=101538184761084668100&rtpof=true&sd=true).   

## Technology stack

- Java EE (v.8.0.1)
- Apache Tomcat (v.9.0.65)
- MySQL (remote database on a [CleverCloud server](https://www.clever-cloud.com/)) 


## Quick overview
The application is designed according to the MVC pattern:

- ***M***odel - POJOs for data transfer.
- ***V***iew  - JSPs using [Tailwind CSS][2] 
- ***C***ontroller - Java EE WebServlet with Front controller pattern.

The user requests are handled by the [FrontController](./src/main/java/com/example/cw/controllers/FrontController.java) 
class, which uses [StrategySelector](./src/main/java/com/example/cw/controllers/StrategySelector.java) to select
 the necessary [strategy](./src/main/java/com/example/cw/controllers/Strats).


The strategies use [service classes](./src/main/java/com/example/cw/services) to execute
the necessary business logic.

The service classes interact with the database via [data access objects](./src/main/java/com/example/cw/dao). 

The DAOs use JPA to work with the database.

```
Note:
Java reflection mechanism was used in constructing database requests to take advantade of DB side indexing.
```

[2]:(https://tailwindcss.com/)

