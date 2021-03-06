# Take Bread.
Notify your office mates that you want bread

# Description.
Do you have breakfast in the office? you buy bread with your office mates and freeze it, right?

You are responsible of taking bread out of the freezer every morning and some days you don´t know if all office mates have breakfast in the office because they didn´t say nothing yesterday.

This App help you with this task. Each mate can notify if want bread and the App will count the total number of bread (normal and wholemeal) to defrost and will show it. Every morning, with a scheduler, the App reset the counter for the next day.

Each user has a security code which is required for notify if want bread.

#Technologies.
The back-end is implemented with JavaEE using Webservices, Servlet, Scheduler, Log4j and MongoDb database.

About the front-end, I use AngularJs with the modules ngMaterial (material style) and ngNotify (for manage the response of webservices).

About testing the REST API, I use REST-Assured: https://github.com/rest-assured/rest-assured

#Steps.
1. Generate a .war and put it in your application server. Copy the "opt" folder in your "opt" folder of your server.

2. Create a MongoDb database "takebread_db"

3. Insert in MongoDb each user with this POST call:

http://server:port/TakeBread/user/

4. Access from your web browser:

http://server:port/TakeBread

5. Select the bread and put your security code.

#Screens.

![alt tag](https://github.com/josekron/TakeBread/blob/master/screenshot_takebread.png)
