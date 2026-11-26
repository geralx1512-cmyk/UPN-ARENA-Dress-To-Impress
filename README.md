Geraldine Mendez
Alessandro Rivero

### Arquitectura y Gestión de Datos (DAO)

La aplicación separa la interfaz gráfica de la lógica de almacenamiento y organización de participantes mediante el patrón **DAO (Data Access Object)**:

`Pantalla (.fxml)` ➔ `Controlador (.java)` ➔ `Modelo (Clases)` ➔ `DAO` ➔ `Colección de Datos`

* **Rol del DAO:** Actúa como el gestor central de los registros del casting. Recibe los datos capturados desde los formularios, los almacena en las estructuras de datos del sistema y ejecuta de forma autónoma su clasificación, filtrado y reorganización (CRUD en memoria/archivo), manteniendo los controladores visuales limpios y enfocados únicamente en la interacción con el usuario.
