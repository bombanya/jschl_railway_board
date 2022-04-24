# Railway Company Information System

## Automatically updated board
The application provides a web page displaying the timetable of trains 
arriving or departing from the station on the current day. Updates in the 
schedule are automatically synchronized with the server using a message broker.

## Realisation:
- Java 8
- Java EE
- MDB for the asynchronous processing of the messages from the Active MQ Artemis
- JSF for the front page with the WebSocket for the automatic updates

## Application Server:
- Tested on the WildFly 26.0.1