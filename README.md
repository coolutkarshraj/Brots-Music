# Brots Discover The Music

Free Open Source Music app anyone can use and Modify The code it contains three part
1.Brots music App
2.Brots Server
3.Brots Admin which is divided in two part First Artist Registration and 2 nd is Admin who will manage all the artist.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Feature
    # User And Artist Login
    # user see list of song
    # User can like artist song after hearing minimum 15 sec 
    # if user like song then it will automatic save into instant mix song
    # if two user gives his 60% point to artist and two user are hearing same song then they can chat and part of crew.
    # follow Artist
    # Send Friend Request to user
    # if user accept request then they can chat
    # user can save upto 500 mb song to cloud memory after that they have to buy premimum plan
    # Artist login 
    # Artist registration
    # Artist upload 30 sec song or Full Song
    # Artist  can see his trending song
    # Artist can see his total follower 
    # if artist add any note then notification automatically goes to all his follower
    
    # Admin
      # Admin can suspend profile of artist or user
      # Admin can see all artist or user
      # artist can make any song trending of any arytist
       or a lot of feature it have  
       
    ** note to see all feature kindly run this project and explore some of main feature are mentioned

### Installing

    A step by step series of examples that tell you how to get a development env running
    For Server side installation 
     # install node
     # npm run compile
     # npm run dev

### if you want to run through pm2 then 
    # install Pm2
    # then after compiling rum 
    # pm2 start node dist/app.js
    # to Stop pm2 stop node dist/app.js

### For Admin  Which is in Angular 

    # First go to this File Tiktok-Clone/blob/master/brots-admin/src/app/services/constant.ts
    # Edit base url replace the ip with your ip of machine or cloud instance ip.

### For Local run Admin panel
    # npm install
    # ng serve

### For run angular in production
    # install ngnix on system
    # run ./deploy.sh in project directory

### For Aws S3 configuration just  edit this file and put access key or secret key
    # brots-server/src/s3Services/FileUpload.ts 


### For Android
    # edit brots_android/app/src/main/java/com/brots/music/application/Config.java
      change base url with your ip address

