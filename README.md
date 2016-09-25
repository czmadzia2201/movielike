# movielike
My first bigger trial with Spring MVC - Magda is becoming a developer

To make the project run on your computer you will need a database with data.

============================================================================

To import database:

1. Create database 'movielike' in your local database (I use phpMyAdmin).
2. Create param.bat file in the project main folder that will contain login and password to your local database:

set user=your_login
set password=your_password

(Replace your_login and your_password respectively.)

2. In the main project folder there's file dbexport.sql - it contains an exported database filled with data, you need to import it locally.
3. Make sure you have environmental path for your sql isn't set - it will be needed to correctly run commands in the .bat file. 
If not, set it up - it should be the path to the calatogue where your local database was installed, 
for example C:\Program Files\wamp\bin\mysql\mysql5.6.17\bin and try to launch the bat file again.
4. Run importDatabase.bat just by clicking on it - it should do all the job. 
If you already have some data in the movielike database, you don't need to delete it, it will be done authomatically 
(unless the table names changed - in this case you will need to clean up the old tables manually, but this is not need it to run the application correctly)

============================================================================

To export database:

1. Run dumpDatabase.bat just by clicking on it - it will dump all database to dbexport.sql file.

============================================================================

To run application:

1. Edit data in spring-database.xml to fit your personal info: host, port, user, password.
2. Run tomcat in your IDE
3. In the browser type address: http://localhost:8080/movielike

