# API compliance
Este servicio de compliance contiene el código necesario para almacenar datos de distintos sistemas, datos tales como, cpu, procesos en ejecución, usuarios conectados, nombre del sistema operativo.
 
todos esos datos se almacenan de dos formas, en una base de datos relacional y en un archivo json, a través de este documento iremos mostrando las diferentes formas de ejecutarlo y setearlo en un ambiente productivo.
 
## dependencias
 
- docker
- docker-compose (opcional)
- maven
- git
- java:11
## Seteando la base de datos local
   Si en su sistema tiene docker-compose ejecute el siguiente comando
   ~~~bash
       docker-compose up -d
   ~~~
   si no, tendrá que que ir editando las diferentes variables de ambiente para que se asimilen a lo que hay en su sistema
## Instalación
~~~bash
   git clone https://github.com/AlFuentesDLM/api-compliance
   cd api-compliance
~~~
 
Para empezar a trabajar necesitamos setear diferentes variables de ambiente que ayudarán a nuestra aplicación identificar en qué tipo de ambiente estamos o más que nada a donde debemos apuntar.
 
Variables a setear:
 
- DB_USER
- DB_PASS
- DB_SCHEMA
- DB_URL
- USE_SSL
 
Si estás trabajando en un ambiente local con una base de datos corriendo en tu sistema, puedes ejecutar el archivo env.sh para que este setee todas las variables por defecto que vienen en el archivo docker-compose.yml (esto es solo si estas corriendo la base de datos mysql con docker-compose)
 
~~~bash
   $ chmod +x env.sh
   $ ./env.sh
~~~
 
(El Dockerfile para crear la imagen ya tiene pre seteadas estas variables de ambiente)
ya con esto seteado podemos ejecutar la aplicación sin problemas con el comando
~~~bash
   $ mvn spring-boot:run
~~~
## Docker
Para poder dockerizar esta aplicación es muy importante haber realizado el paso anterior y que la sesión del bash siga activa desde que se hicieron los exports, para que aun se mantengan las variables de ambiente a la hora de compilar la aplicación.
### **Compilación**
~~~bash
   mvn package
~~~
esto generará el archivo .jar necesario para poder correr el docker file.
 
### **Crear Imagen**
~~~bash
   docker build -t complianceapi:1 .#crear la imagen
~~~
### **Crear el contenedor**
~~~bash
   $ pwd
   path/to/project
   $ docker run -d -v path/to/project/data:/usr/app/data --name compliance -p 8080:8080 complianceapi:1
~~~
Esto creará un volumen que te permitirá acceder a los archivos .json desde fuera del contenedor. en esta misma carpeta, en apicompliance/data
