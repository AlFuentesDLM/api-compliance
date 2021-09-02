# API compliance
 
Esta aplicación web de compliance nos ayuda a almacenar los datos de diferentes sistemas que enviaran informacion a traves de un agente [python server-agent](https://github.com/AlFuentesDLM/server-agent)
 
Todos esos datos se almacenaran de dos formas, en una base de datos relacional y en un archivo json.
## Dependencias
 
- maven
- git
- java:11
- docker
- docker-compose
 
## Instalación
 
```bash
  git clone https://github.com/AlFuentesDLM/api-compliance
  cd api-compliance
```
### Seteando la base de datos local
 
```bash
   cd api-com
   docker-compose up -d
```
Esto creará un contenedor con todo lo que necesitamos para ejecutar la base de datos, además, creará un volumen en la carpeta donde se ejecuto el script.

### Variables
Para empezar necesitamos setear variables de entorno que tienen que ver con los datos de conexión a la base de datos.
 
Variables a setear:
 
- DB_USER
- DB_PASS
- DB_SCHEMA
- DB_URL
- USE_SSL
 
```bash
  export DB_USER=root
  export DB_PASS=password
  export DB_SCHEMA=db
  export DB_URL=localhost:3306
  export USE_SSL=false
```
 
Estas variables corresponden al contenedor antes inicializado
(El Dockerfile para crear la imagen ya tiene pre seteadas estas variables de ambiente)
 
```bash
$ mvn spring-boot:run
```
 
## Docker
 
Para poder dockerizar esta aplicación es muy importante haber realizado el paso anterior y que la sesión del bash siga activa desde que se hicieron los exports, para que aun se mantengan las variables de ambiente a la hora de compilar la aplicación.
 
### **Compilación**
 
```bash
$ mvn package
```
 
esto generará el archivo .jar necesario para poder correr el docker file.
 
### **Crear Imagen**
 
```bash
docker build -t complianceapi:1 .
```
 
### **Crear el contenedor**
 
```bash
~ pwd
path/to/project
~ docker run -d -v path/to/project/data:/usr/app/data --name compliance -p 8080:8080 complianceapi:1
```
 
Esto creará un volumen que te permitirá acceder a los archivos .json desde fuera del contenedor. en esta misma carpeta, en apicompliance/data
 
## ENDPOINTS
 
Esta aplicación consta de 5 endpoints para consultar los datos de los diferentes sistemas.
### 1. Agregar datos
 
Endpoint
 
```http
POST http://localhost:8080/api/v1/audit
```
 
- Body
 
```json
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
```
- Response
 
```json
{
 "audit_id": 10
}
```
 
### 2. Consultar audit
 
 
- Endpoint
```http
GET http://localhost:8080/api/v1/audit/{audit_id}
```
 
- Body
 
```json
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
```
 
 
2. Consultar audit
 
```http
GET http://localhost:8080/api/v1/audit/{audit_id}
```
 
- Response
 
```json
{
 "id": 10,
 "os_name": "Darwin",
 "os_version": "20.5.0",
 "architecture": "X86_64",
 "cpu_logical_cores": 8,
 "cpu_physical_cores": 4,
 "brand": "Intel(R) Core(TM) i7-1068NG7 CPU @ 2.30GHz",
 "audit_time": 1630504456588,
 "ip": "186.10.64.22"
}
```
 
3. Consultar procesos de un audit
 
```http
GET http://localhost:8080/api/v1/audit/{audit_id}/process
```
 
- Response
 
```json
[
 {
   "username": "_atsserver",
   "pid": 83973,
   "ppid": 1,
   "name": "fontd"
 },
 {
   "username": "root",
   "pid": 89332,
   "ppid": 1,
   "name": "com.crowdstrike."
 },
 {
   "username": "root",
   "pid": 89336,
   "ppid": 1,
   "name": "FXService"
 },
 {
   "username": "root",
   "pid": 89337,
   "ppid": 1,
   "name": "PredictService"
 }
]
```
### 3. consultar sesion de usuarios
- Endpoint
```http
GET http://localhost:8080/api/v1/audit/{audit_id}/users
```
 
- Response
 
```json
[
 {
   "name": "alfuentes",
   "terminal": "console",
   "host": "some:ip:adress",
   "number": 158
 },
 {
   "name": "alfuentes",
   "terminal": "ttys001",
   "host": "some:ip:adress",
   "number": 58276
 }
]
```
 
### 4. Consultar todas las audit de una misma ip
 
- Endpoint
 
```http
GET http://localhost:8080/api/v1/audit?ip=54.211.87.171
```
 
- Response
```json
[
 {
   "id": 2,
   "os_name": "Linux",
   "os_version": "4.19.0-14-cloud-amd64",
   "architecture": "X86_64",
   "cpu_logical_cores": 1,
   "cpu_physical_cores": 1,
   "brand": "Intel(R) Xeon(R) CPU E5-2676 v3 @ 2.40GHz",
   "audit_time": 1630522488531,
   "ip": "54.211.87.171"
 },
 {
   "id": 4,
   "os_name": "Linux",
   "os_version": "4.19.0-14-cloud-amd64",
   "architecture": "X86_64",
   "cpu_logical_cores": 1,
   "cpu_physical_cores": 1,
   "brand": "Intel(R) Xeon(R) CPU E5-2676 v3 @ 2.40GHz",
   "audit_time": 1630523947883,
   "ip": "54.211.87.171"
 }
]
```