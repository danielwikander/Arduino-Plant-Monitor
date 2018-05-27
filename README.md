Arduino Plant Monitor

The Arduino Plant Monitor system is a group project from four computer science students at Malmö University.

The project uses an arduino equipped with soilmoisture, humidity, light and temperature sensors to measure values from a potted plant. The values are sent to a server, which stores them in a database for clients to retrieve. The client then presents the data using graphs. 
This way, users will have a digital representation of the plants enviroment. The plants needs can be easily observed and the user can adjust its water and light exposure accordingly.

How to use the system:

Necessary preparations:
    All libraries within the "lib" folder must be added to the projects library structure in the users IDE.
    The IP-address of the server must be added to the whitelist of the database that is used.

Starting the system:
    The server should be started first, using the MainServer class in the server package.
    After a server is started, the client can be started using the Main class in the client package.
