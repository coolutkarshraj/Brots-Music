#!/bin/bash

nfile="$(pwd)/nginx.conf"
ngLoc="/etc/nginx/sites-enabled"
dnLoc="$ngLoc/default"
indexFileL="$(pwd)/dist/brots-client/"

CWD=$(pwd)
 
rm -f $nfile 
rm -rf dist/

npm install
ng build --prod

cat > $nfile << EOF1
server {
    listen 80 default_server;
    root "$indexFileL";
    index index.html index.htm;
    server_name brotsmusic;
    location / {
        try_files \$uri \$uri/ /index.html;
    }
} 
EOF1

if [ -f "$dnLoc" ]
then
	sudo rm -f $dnLoc
fi

sudo cp $nfile "$ngLoc/"
sudo service nginx restart 
sudo service nginx reload