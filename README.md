# trade-store

Assumptions:
HTTP API REST to consumer Trades, ideally you would want to read from a messaging platform
CRON Job flips the trades to expiry

Description:
The project uses H2 in-memory DB, Spring Boot and Spring JPA
JUNIT Test Cases are also included following TDD approach

Requirements:
JAVA 8 and above
Maven

REST API Calls:
http://localhost:8080/trade-store/all-active
http://localhost:8080/trade-store/accept [HTTP POST]

Pay-load: {"tradeId": "T23", "version": 3, "createDate": "2022-05-04", "maturityDate": "2025-06-02", "cpId": "CP-1", "bookId": "B-1", "expired": "N" }

H2 Console: http://localhost:8080/h2-console/

