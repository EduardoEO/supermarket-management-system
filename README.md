# 🛒 AdminSupermercado - Sistema de Gestión de Supermercado

Proyecto desarrollado para la asignatura de **Ingeniería de Software** (2.º curso del Grado en Ingeniería Informática).

**AdminSupermercado** es una aplicación de escritorio desarrollada en **Java** orientada a la administración y digitalización integral de los procesos operativos de un supermercado: gestión de inventario/almacén, compras a proveedores, ventas, pedidos, promociones comerciales temporales y autenticación con control de acceso basado en roles.

---

## 📋 Tabla de Contenidos
- [Características Principales](#-características-principales)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Documentación de Ingeniería del Software](#-documentación-de-ingeniería-del-software)
- [Modelo de Clases y Arquitectura](#-modelo-de-clases-y-arquitectura)
- [Requisitos del Sistema](#-requisitos-del-sistema)
- [Compilación y Ejecución](#-compilación-y-ejecución)
- [Usuarios y Credenciales de Prueba](#-usuarios-y-credenciales-de-prueba)

---

## ✨ Características Principales

* 🔐 **Control de Acceso y Roles:**
  * **Administrador:** Gestión de empleados (alta, baja, consulta), creación y eliminación de promociones y acceso completo a los menús de empleado.
  * **Empleado:** Gestión del catálogo de productos, compras/ventas de almacén, pedidos y aplicación de descuentos.
* 📦 **Catálogo de Productos:**
  * Registro con identificador, nombre, descripción, precio base, fecha de caducidad y formato de empaquetado (`esPack`).
  * Soporte para múltiples unidades de medida: `KILOGRAMOS`, `LITROS` y `UNIDADES`.
* 🏪 **Almacén e Inventario:**
  * **Compras (Aprovisionamiento):** Entrada de mercancía registrando fecha, cantidad y precio de coste.
  * **Ventas:** Reducción de existencias con validación previa de stock disponible.
* 💰 **Reglas de Negocio y Precios Dinámicos:**
  * Margen de venta automático sobre el coste de compra:
    * **+15%** para productos individuales.
    * **+10%** para productos en lote/pack.
  * Cálculo dinámico de precios con descuento si el producto tiene una promoción activa.
* 🏷️ **Campañas y Promociones:**
  * Descuentos porcentuales configurables con control de ventana temporal (`fechaInicio` y `fechaFin`).
* 📋 **Gestión de Pedidos:**
  * Registro, edición y cancelación de pedidos con fechas programadas y desglose de cantidades por producto.

---

## 📁 Estructura del Proyecto

```text
ProyectoSoftware/
├── AdminSupermercado/              # Proyecto Java (IntelliJ IDEA)
│   ├── .idea/                      # Configuración del entorno IntelliJ
│   ├── out/                        # Binarios compilados (.class)
│   ├── src/                        # Código fuente Java
│   │   ├── Empleado.java           # Modelo de empleado y credenciales
│   │   ├── GestorAutenticacion.java# Servicio de validación de acceso
│   │   ├── InicioSesion.java       # Control de menús y flujos de consola
│   │   ├── Main.java               # Punto de entrada y precarga de datos
│   │   ├── Operacion.java          # Registro de movimientos de almacén
│   │   ├── Pedido.java             # Modelo de pedidos
│   │   ├── Producto.java           # Modelo de producto
│   │   ├── Promocion.java          # Modelo de promociones temporales
│   │   ├── Supermercado.java       # Controlador principal del negocio
│   │   ├── TipoMedida.java         # Enum: KILOGRAMOS, LITROS, UNIDADES
│   ├── launcher_stub.bin           # Cabecera PE del lanzador de Windows
│   └── AdminSupermercado.iml       # Definición de módulo IntelliJ
├── data/                           # Documentación técnica y requisitos
│   ├── AdminSuperDocumentoSRS.pdf  # Especificación de Requisitos Software
│   ├── AdminSupermercadoSRSComp.pdf# SRS Completo con Casos de Uso y Diagramas
│   └── Diagramas de Secuencia.pdf  # Diagramas de secuencia UML detallados
├── scripts/                        # Scripts de compilación y empaquetado
│   ├── build_exe.bat               # Generar/actualizar .exe (doble clic)
│   └── build_exe.ps1               # Script PowerShell de empaquetado
├── AdminSupermercado.exe           # Ejecutable empaquetado para Windows
├── .gitignore                      # Gitignore raíz del repositorio
└── README.md                       # Documentación del proyecto
```

---

## 📑 Documentación de Ingeniería del Software

* **Documentos en la carpeta `data/`:**
  * **Documento SRS (IEEE 830) ([`data/AdminSupermercadoSRSComp.pdf`](file:///c:/Users/eduar/Desktop/UNI-INF/2-SegundoINF/Ingenier%C3%ADa%20de%20software/ProyectoSoftware/data/AdminSupermercadoSRSComp.pdf) y [`data/AdminSuperDocumentoSRS.pdf`](file:///c:/Users/eduar/Desktop/UNI-INF/2-SegundoINF/Ingenier%C3%ADa%20de%20software/ProyectoSoftware/data/AdminSuperDocumentoSRS.pdf)):**
    * Especificación detallada de Requisitos Funcionales (RF), No Funcionales (RNF, como tiempos de respuesta inferiores a 5 segundos) y Requisitos de Dominio (normativas de caducidad y trazabilidad de alimentos perecederos).
    * Tablas descriptivas de Casos de Uso (actores, precondiciones, flujo básico, escenarios alternativos y postcondiciones).
  * **Modelado UML ([`data/Diagramas de Secuencia.pdf`](file:///c:/Users/eduar/Desktop/UNI-INF/2-SegundoINF/Ingenier%C3%ADa%20de%20software/ProyectoSoftware/data/Diagramas%20de%20Secuencia.pdf)):**
    * Diagrama de clases con atributos, métodos y cardinalidades.
    * Diagramas de secuencia para los casos de uso principales: compra/venta en almacén, autenticación, creación de promociones y modificación de pedidos.

---

## 🏛️ Modelo de Clases y Arquitectura

* **`Supermercado`:** Contenedor central del dominio. Mantiene las listas maestras de empleados, productos, pedidos, promociones y las operaciones activas en el almacén.
* **`Producto`:** Entidad que encapsula las propiedades de un artículo, con validación de precios positivos y fechas de caducidad válidas.
* **`Operacion`:** Representa un movimiento de inventario (`COMPRA` o `VENTA`) junto con su cantidad, fecha y precio de compra.
* **`Empleado` / `GestorAutenticacion`:** Maneja la identidad del usuario y verifica si posee privilegios de administrador.
* **`InicioSesion`:** Encargado de la interacción con el usuario mediante consola, dividiendo la experiencia en Menú Principal, Menú de Administrador y Menú de Empleado.

---

## ⚙️ Requisitos del Sistema

* **Java JDK:** Versión 17 o superior (probado y compatible con Java 23).
* **Sistema Operativo:** Windows, macOS o Linux.
* **IDE recomendado:** IntelliJ IDEA, Eclipse o VS Code.

---

## 🚀 Compilación y Ejecución

### Opción 1: Desde Terminal / Consola

1. Situarse en la carpeta del proyecto Java:
   ```powershell
   cd AdminSupermercado
   ```

2. Compilar todos los archivos fuente al directorio de salida:
   ```powershell
   javac -d out/production/AdminSupermercado src/*.java
   ```

3. Ejecutar la aplicación:
   ```powershell
   java -cp out/production/AdminSupermercado Main
   ```

### Opción 2: Desde IntelliJ IDEA

1. Abrir la carpeta `AdminSupermercado` en IntelliJ IDEA.
2. Asegurarse de que la carpeta `src` esté marcada como *Sources Root*.
3. Ejecutar la clase `Main.java` pulsando en el botón verde de ejecución (*Run*).

### Opción 3: Ejecutable directo (Windows)

En entornos Windows compatibles, se puede ejecutar directamente el archivo empaquetado:
```powershell
.\AdminSupermercado.exe
```

### Opción 4: Regenerar / Actualizar el `.exe`

Si modificas el código fuente Java en `AdminSupermercado/src` y deseas actualizar el archivo `AdminSupermercado.exe`, puedes regenerarlo automáticamente de dos formas:

* **Con doble clic:** Ejecuta el archivo [`scripts/build_exe.bat`](file:///c:/Users/eduar/Desktop/UNI-INF/2-SegundoINF/Ingenier%C3%ADa%20de%20software/ProyectoSoftware/scripts/build_exe.bat) desde el explorador de archivos.
* **Desde PowerShell:**
  ```powershell
  .\scripts\build_exe.ps1
  ```
El script compilará automáticamente los `.java` más recientes, generará el archivo JAR interno y reensamblará el ejecutable nativo de Windows en la raíz del proyecto.

---

## 🔑 Usuarios y Credenciales de Prueba

La clase `Main.java` inicializa el sistema con datos de prueba listos para su uso:

| ID Empleado | Nombre | Rol | Contraseña |
|:---:|:---:|:---:|:---:|
| **1** | Paco | Empleado | `12Hola` |
| **2** | Sara | Administrador | `Hola1234` |
| **3** | Carlos | Empleado | `Carlos123` |
| **4** | Lucia | Empleado | `Lucia123` |
| **5** | Manuel | Empleado | `Manu123` |
| **6** | Elena | Empleado | `Elena123` |
| **7** | David | Empleado | `David123` |
| **8** | Laura | Administrador | `LauraAdmin` |
