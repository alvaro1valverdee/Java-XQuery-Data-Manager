# Java XQuery Data Manager ⚙️

Una aplicación backend desarrollada en Java para interactuar con bases de datos documentales nativas XML (eXist-db). 

Este proyecto demuestra la implementación de la API XML:DB para ejecutar consultas XQuery complejas, parsear los resultados y presentarlos de forma estructurada en consola.

### 🚀 Características Técnicas
- **Conexión Segura:** Conexión a la instancia de eXist-db mediante el driver `DatabaseImpl`.
- **Prevención de Inyecciones:** Uso de variables externas (`declare variable external`) inyectadas desde Java para parametrizar las consultas de forma segura.
- **Procesamiento de Datos:** Cálculo dinámico de métricas (multiplicación y suma de nodos XML) utilizando las funciones nativas de XQuery 3.1.
- **Limpieza de Código:** Separación de la lógica de conexión, la consulta a base de datos y la interfaz de usuario en consola.

### 🛠️ Stack Tecnológico
- Java (JDK)
- API XML:DB
- eXist-db (XQuery 3.1 & XPath)

### ⚙️ Cómo ejecutarlo
1. Asegúrate de tener una instancia de eXist-db corriendo en `localhost:8080`.
2. Actualiza las credenciales de conexión en la clase `Programa.java`.
3. Compila y ejecuta la clase principal.
