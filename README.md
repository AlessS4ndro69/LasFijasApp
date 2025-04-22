# 📚 App de autoevaluación y seguimiento para estudiantes preuniversitarios

Bienvenidos a **LasFijas**, una aplicación diseñada especialmente para estudiantes preuniversitarios que se están preparando para su ingreso a la universidad. 🌟

## 🚀 Características Principales

- **Preguntas de examen**: Accede a una amplia colección de problemas resueltos paso a paso.
- **Categorías Temáticas**: Explora los temas organizados por álgebra, geometría, trigonometría, entre otros.
- **Categorías por dificultad**: Desde nivel básico hasta nivel preuniversitario.
- **Interfaz Intuitiva y Moderna**: Diseñada con un enfoque minimalista y agradable a la vista.

---

## 🖼️ Capturas de Pantalla

<p align="center">
  <img src="assets/Screenshot_20230804-153114_soykachimbo.jpg" alt="screenhome" width="200"/>
  <img src="assets/Screenshot_20230804-153107_soykachimbo.jpg" alt="screencourses" width="200"/>
  <img src="assets/Screenshot_20230804-153010_soykachimbo.jpg" alt="screenform" width="200"/>
  <img src="assets/Screenshot_1698338364.png" alt="screensolution" width="200"/>
</p>


---

## 🛠️ Tecnologías Utilizadas

- **Frontend**: Kotlin Compose Multiplatform 📱 Permite compartir código entre Android, Desktop y Web, logrando una experiencia nativa y eficiente en múltiples plataformas.
- **Backend**
  -AWS Lambda: Funciones serverless que ejecutan la lógica de negocio bajo demanda.
  -API Gateway: Exposición de endpoints RESTful que conectan al frontend con el backend.
  -AWS S3: Almacenamiento estático de recursos como PDFs, imágenes y datos generados. 
- **Estilos**: Jetpack Compose. El diseño de interfaces se realiza con un enfoque declarativo, permitiendo UI reactivas y modernas.
- **Clean Architecture**
  -Capa de Presentación
    -UI en Compose (Multiplatform)
    -Interacción con ViewModels
    -Comunicación con los casos de uso mediante eventos
  -Capa de Dominio
    -Casos de uso (UseCases)
    -Entidades puras (Entities)
    -Interfaces de repositorios
  -Capa de Datos
    -Implementación de repositorios
    -Clientes HTTP para consumir API Gateway
    -Acceso a AWS S3 u otros servicios si es necesario
  -Capa de Infraestructura (Backend)
    -Lógica de negocio en AWS Lambda
    -API RESTful en API Gateway
    -Conexión con S3 para almacenar o recuperar datos (por ejemplo, ejercicios en PDF)
- **Patrones de diseño**
  -MVVM (Model - View - ViewModel)
    -Desacople entre lógica, interfaz y datos
    -Uso de estados observables en Compose
  -Repository Pattern
    -Abstracción del origen de datos (API o local)
    -Facilita testeo y escalabilidad
  -Dependency Injection con Koin
    -Configuración modular de dependencias
    -Inyección automática en ViewModels, repositorios y casos de uso
---

## 🏗️ Instalación y Configuración como proyecto educativo(no olvides dejar estrella y follow)

Sigue estos pasos para ejecutar el proyecto localmente:

1. **Clona este repositorio**:
   ```bash
   git clone https://github.com/AlessS4ndro69/LasFijasApp.git
   ```

2. **Navega al directorio del proyecto**:
   ```bash
   cd LasFijasApp
   ```

3. **Importación**:
   ```bash
   import from Android Studio
   ```


## 🌐 Demo

> https://apkpure.com/p/org.aless4ndro.soykachimbo

---

## 💡 Contribuciones

¡Nos encantaría contar con tu ayuda! 🎉 Si deseas contribuir:

1. Haz un fork de este repositorio.
2. Crea una rama con tu funcionalidad:
   ```bash
   git checkout -b mi-nueva-funcionalidad
   ```
3. Realiza tus cambios y haz commit:
   ```bash
   git commit -m "Añadida nueva funcionalidad"
   ```
4. Haz un push a tu rama:
   ```bash
   git push origin mi-nueva-funcionalidad
   ```
5. Crea un Pull Request.

---

## 🤝 Licencia

Este proyecto está bajo la Licencia MIT. ¡Siéntete libre de usarlo y mejorarlo! 📝

---

## 🌟 Agradecimientos

A todos los estudiantes, profesores y colaboradores que inspiraron este proyecto. ¡Gracias por apoyar el aprendizaje y la educación! 🙌

---

### 📧 Contacto

Si tienes alguna pregunta o sugerencia, no dudes en contactarme:
- ✉️ **Email**: [emamanix@gmail.com](mailto:emamanix@egmail.com)
- 💻**GitHub**: [https://github.com/AlessS4ndro](https://github.com/AlessS4ndro69)
- 📞**Whatsapp**: [Envíame un mensaje](https://wa.me/51925968311)

---
