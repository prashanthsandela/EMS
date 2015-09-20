# EMS
Event Management System

## Project Setup
1. Open Eclipse > Import > Git > Project From Git > Clone URI and hit next
2. Type URI: https://github.com/prashanthsandela/EMS <br /> User: yourGithubEmailAddress@xyz.com<br />Password: Your github password <br /> Check secure store and hit next
3. Next screen select Dev and hit next and next
4. Now project import setup wizard will apprear. Click on "Import existing Eclipse Project", hit Next and hit finish.
5. Once the project is created, right click on the project Maven > Update project, this will download all the dependencies.

## Downloads & Installations
1. Tomcat : https://tomcat.apache.org/download-70.cgi
2. Maven : https://maven.apache.org/download.cgi
3. MongoDB : https://www.mongodb.org/dr/fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-3.0.6-signed.msi/download

### Tomcat Configurations
1. Server Shutdown Port 8005
2. HTTP/1.1 Connector Port 8080
3. AJP/1.3 Connector Port 8009
4. Windows Service Name Tomcat8
5. Username : admin
6. Password : password
7. Roles : manager-gui

## Jax RS tutorials
https://www.youtube.com/watch?v=xkKcdK1u95s&list=PLqq-6Pq4lTTZh5U8RbdXq0WaYvZBz2rbn

## Configure Users in Tomcat

    <tomcat-users>
    <user username="admin" password="password" roles="manager-gui" />
    <role rolename="admin"/>
         <role rolename="manager-script"/>
         <role rolename="manager-gui"/>
         <role rolename="manager-jmx"/>
         <user username="admin" password="password" roles="manager-gui,admin,manager-jmx,manager-script" />
    </tomcat-users>

## Quora Advice
https://www.quora.com/Ive-just-started-developing-a-product-with-couple-of-my-friends-which-I-believe-is-going-to-be-game-changing-We-all-are-new-grads-and-we-lack-experience-selecting-architecture-design-patterns-Where-should-we-look-for-guidance-reasearch?__snids__=1353514617&__nsrc__=1&__filter__=all
