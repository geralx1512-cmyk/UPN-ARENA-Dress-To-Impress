Geraldine Mendez
Alessandro Rivero

### Flujo de Ejecución de la Aplicación

La aplicación organiza el proceso del casting de manera secuencial en cuatro etapas:

1. **Inscripción de Modelos (Participantes):** Se registran los datos individuales de cada modelo mediante el formulario principal, almacenando su información en el sistema a través del DAO.
2. **Conformación y División de Grupos:** A partir de los modelos inscritos, el sistema permite estructurar las postulaciones agrupándolas por modalidad: **Solos** (1 integrante), **Dúos** (2 integrantes) o **Tríos** (3 integrantes).
3. **Inscripción al Casting por Categoría:** Cada postulación (individual o grupal) se asigna y registra oficialmente en la fase del casting correspondiente según su modalidad y se le es asignado un horario.
4. **Evaluación y Tabla Final de Clasificación:** Una vez realizadas las evaluaciones, el sistema procesa y reorganiza automáticamente los resultados para mostrar el orden de mérito y las clasificaciones en la tabla final.

### Arquitectura y Gestión de Datos (DAO)

La aplicación separa la interfaz gráfica de la lógica de almacenamiento y organización de participantes mediante el patrón **DAO (Data Access Object)**:

`Pantalla (.fxml)` ➔ `Controlador (.java)` ➔ `Modelo (Clases)` ➔ `DAO` ➔ `Colección de Datos`

* **Rol del DAO:** Actúa como el gestor central de los registros del casting. Recibe los datos capturados desde los formularios, los almacena en las estructuras de datos del sistema y ejecuta de forma autónoma su clasificación, filtrado y reorganización (CRUD en memoria/archivo), manteniendo los controladores visuales limpios y enfocados únicamente en la interacción con el usuario.
