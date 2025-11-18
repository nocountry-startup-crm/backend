# nocountry-crm
Se utilizó una base de datos local por el momento. Se implementó con MapStruct. Se definió la clase de "Role" pero se utiliza únicamente el "RoleCode" para definir el rol de un usuario. Por defecto, todos los usuarios creados tienen el rol de "USER".

Ejemplos de como sirve en este punto:

<h2>Registro</h2>
POST: http://localhost:8080/api/v1/auth/register
<img width="510" height="260" alt="image" src="https://github.com/user-attachments/assets/2d8d5d79-dd6e-4b29-966c-b0e0c36be673" />

<h2>Log in</h2>
POST: http://localhost:8080/api/v1/auth/login
<img width="696" height="262" alt="image" src="https://github.com/user-attachments/assets/83bbb0a8-e46b-471e-ac88-21319fb6f975" />
