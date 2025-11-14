🧠 AyudaFilosófica
Un chat para dialogar con distintas corrientes filosóficas y obtener respuestas profundas a tus dudas personales.

📌 Descripción

AyudaFilosófica es una aplicación Android desarrollada con Jetpack Compose, diseñada para ayudarte a reflexionar sobre tus problemas o inquietudes a través de la mirada de diferentes filosofías.
El usuario elige dos filosofías y el chatbot adapta su forma de responder según esa combinación, ofreciendo un punto de vista más profundo y personalizado.

Es un proyecto creado para mi portafolio, aplicando arquitectura moderna, buenas prácticas y un diseño limpio pero elegante.

✨ Características principales

🧠 Elección de filosofías: selecciona una o dos corrientes que guiarán la conversación (estoicismo, existencialismo, etc.).

💬 Chat adaptativo: el asistente contesta según la filosofía escogida.

🎨 UI moderna con Jetpack Compose, Material 3 y navegación fluida.

🔗 Conexión con la API de OpenAI para generar respuestas contextuales.

🧭 Navegación con Navigation Compose.

⚙️ Arquitectura robusta, clara y escalable.

📱 Diseño casual, limpio y suave para una experiencia agradable.

🏛️ Arquitectura

La app utiliza una arquitectura moderna basada en:

MVS (Model – View – State)

Unidirectional Data Flow (UDF)

Contract + Reducer Pattern para manejar eventos y estados

ViewModel con StateFlow

Separación por capas siguiendo buenas prácticas modernas

UI declarativa con Jetpack Compose

Esta arquitectura permite un código más claro, mantenible y escalable, ideal para futuras ampliaciones del proyecto.

🛠️ Tecnologías utilizadas
Kotlin	Lenguaje principal
Jetpack Compose	Interfaz moderna y declarativa
Material 3	Estilos y componentes
ViewModel	Lógica y manejo de estado
StateFlow	Flujo de datos reactivo
Retrofit	Cliente HTTP
API de OpenAI	Generación de respuestas del chatbot
Architecture MVS + UDF	Manejo predecible del estado


🖼️ Capturas de pantalla

Pantalla de selección de filosofías(Tema claro / Tema oscuro ):
![Seleccion de filosofías](https://github.com/user-attachments/assets/27001757-0feb-4ae3-8adb-e8af06da465a)
![Seleccion de filosofíasa](https://github.com/user-attachments/assets/a5301709-e964-4607-824c-47d19f848d31)

Pantalla del chat(Tema claro / Tema oscuro ):
![Chat](https://github.com/user-attachments/assets/5be25348-75c6-4e43-92b9-9eec42ddb4f9)
![Chat](https://github.com/user-attachments/assets/d2198d28-2eb2-4b1c-8499-78db1d3920f4)



📚 Lo que aprendí al desarrollar esta app

Crear AyudaFilosófica me permitió profundizar en:

🧩 Manejo de estado en Jetpack Compose

🎯 Implementación de MVS, reducers y contratos

🔄 Flujo unidireccional de datos (UDF)

🔌 Consumo de APIs REST con Retrofit

🧠 Integración de modelos de IA con OpenAI

🧭 Navegación limpia con Navigation Compose

💎 Diseño moderno, coherente y minimalista

📦 Organización de paquetes por capas

🧪 Preparación para tests unitarios

Este proyecto fue clave para comprender cómo combinar una arquitectura moderna con Compose y servicios externos de IA.



🚧 Mejoras futuras

Estas son algunas de las funcionalidades planificadas:

📱 Versión multiplataforma (Android +IOS)

🔐 Sistema de registro e inicio de sesión

💾 Guardado en la nube de chats anteriores

🎨 Mejora de animaciones y diseño avanzado

🎯 Nuevas corrientes filosóficas y personalidades


🤝 Contribuciones

Este es un proyecto personal de portafolio, pero cualquier sugerencia o idea constructiva es bienvenida.
