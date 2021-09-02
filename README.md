dependencias 

- docker 
- maven
- git
- java:11

comandos

export ...

mvn package

edit docker file

sudo if needed docker build -t complianceapi:1 .
sudo if needed docker run -d --name complianceapi -p 80:8080 complianceapi:1
sudo docker run -d -v ./data:/usr/app/data complianceapi
pwd
docker run -d -v {use pwd output here}/data:/usr/app/data --name compliance -p 8080:8080 complianceapi:1
