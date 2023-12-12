# Proyecto Práctico de Aplicación Integrador

## Descripción

Este proyecto se desarrolla en el marco del trabajo final de la materia Diseño de Sistemas de Información de la FRC UTN. En esta entrega nosotros rediseñamos los diagramas de análisis del caso de uso _Consultar Encuesta_ y desarrollamos un programa de aplicación con interfaz gráfica.

# Tecnologías y herramientas utilizadas
- **Lenguaje de programación**: Java
- **Interfaz gráfica**: JavaFX

### Autores
- Agostina Avalle
- Juan Cruz López Freytas

---

### Caso de uso implementado

![](/images/caso-de-uso.png)

### Identificación del patrón de diseño implementado

Ante la necesidad de recorrer elementos, se identificó la utilidad de implementar el patrón **iterator**. Este patrón ofrece una solución óptima para la funcionalidad que buscamos incorporar, proporcionando un acceso estandarizado a los elementos de una colección sin necesidad de exponer su estructura interna.

### Vistas dinámicas del patrón iterator en el caso de uso

![](/images/iterador-llamada.jpg)
![](/images/consultar-encuesta.jpg)

---

### Journey

Entra al apartado Consultar encuestas del sistema:

![](/images/journey1.png)


Selecciona fecha de inicio y fecha fin con los DatePickers:

![](/images/journey2.png)


Aprieta el botón buscar y no se encuentran encuestas respondidas en el periodo seleccionado:

![](/images/journey3.png)


Aprieta el botón buscar y el sistema encuentra llamadas con encuesta respondida para ese periodo, y las muestra en un alista con su id y el nombre del cliente:

![](/images/journey4.png)


Selecciona alguna llamada y el sistema muestra los datos de la llamada: cliente, estado actual, duración de la llamada, y los datos de las respuestas del cliente:

![](/images/journey5.png)


Selecciona alguna de las preguntas correspondientes a esa encuesta:

![](/images/journey6.png)


Visualiza la respuesta del cliente correspondiente a la pregunta:

![](/images/journey7.png)

Aprieta el botón generar CSV y genera el CSV exitosamente:

![](/images/journey8.png)
