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
 
~~~bash
   export DB_USER=root
   export DB_PASS=password
   export DB_SCHEMA=db
   export DB_URL=localhost:3306
   export USE_SSL=false
~~~

estas variables corresponden al contenedor antes inicializado
 

 
(El Dockerfile para crear la imagen ya tiene pre seteadas estas variables de ambiente)

con esto seteado podemos ejecutar la aplicación sin problemas con el comando:

**(si se decide ir por este camino, sin dockerizar, por favor cree la carpeta data en el root directory de la aplicacion. apicompliance/data)**
~~~bash
$ mvn spring-boot:run
~~~
## Docker
Para poder dockerizar esta aplicación es muy importante haber realizado el paso anterior y que la sesión del bash siga activa desde que se hicieron los exports, para que aun se mantengan las variables de ambiente a la hora de compilar la aplicación.
### **Compilación**
~~~bash
$ mvn package
~~~
esto generará el archivo .jar necesario para poder correr el docker file.
 
### **Crear Imagen**
~~~bash
$ docker build -t complianceapi:1 .
~~~
### **Crear el contenedor**
~~~bash
   $ pwd
   path/to/project
   $ docker run -d -v path/to/project/data:/usr/app/data --name compliance -p 8080:8080 complianceapi:1
~~~
Esto creará un volumen que te permitirá acceder a los archivos .json desde fuera del contenedor. en esta misma carpeta, en apicompliance/data

## ENDPOINTS

esta aplicacion consta de 5 endpoints para consultar los datos relacionales que se inicializaron con anterioridad.

ya sea que lo haya inicializado con docker o en su consula.

1. Agregar datos

Endpoint
~~~http
POST http://localhost:8080/api/v1/audit
~~~
Body
~~~json
{
    "users": [
        {
            "name": "randomname",
            "terminal": "console",
            "host": "198.1.1.1",
            "pid": 158
        },
        {
            "name": "alfuentes",
            "terminal": "ttys001",
            "host": null,
            "pid": 58276
        }
    ],
    "process": [
        {
            "username": "_atsserver",
            "ppid": 1,
            "name": "fontd",
            "pid": 83973
        },
        {
            "username": "root",
            "ppid": 1,
            "name": "com.crowdstrike.",
            "pid": 89332
        }
    ],
    "os_name": "Darwin",
    "os_version": "20.5.0",
    "architecture": "X86_64",
    "cpu_logical_cores": 8,
    "cpu_physical_cores": 4,
    "brand": "Intel(R) Core(TM) i7-1068NG7 CPU @ 2.30GHz",
    "timestamp": 1630504456584
}
~~~
Response


2. Consultar
Endpoint
~~~http
GET http://localhost:8080/api/v1/audit
~~~
Body
~~~json
{
    "users": [
        {
            "name": "randomname",
            "terminal": "console",
            "host": "198.1.1.1",
            "pid": 158
        },
        {
            "name": "alfuentes",
            "terminal": "ttys001",
            "host": null,
            "pid": 58276
        }
    ],
    "process": [
        {
            "username": "_atsserver",
            "ppid": 1,
            "name": "fontd",
            "pid": 83973
        },
        {
            "username": "root",
            "ppid": 1,
            "name": "com.crowdstrike.",
            "pid": 89332
        }
    ],
    "os_name": "Darwin",
    "os_version": "20.5.0",
    "architecture": "X86_64",
    "cpu_logical_cores": 8,
    "cpu_physical_cores": 4,
    "brand": "Intel(R) Core(TM) i7-1068NG7 CPU @ 2.30GHz",
    "timestamp": 1630504456584
}
~~~