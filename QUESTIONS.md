# PARTE TEORICA

### Lifecycle

#### Explica el ciclo de vida de una Activity.

##### ¿Por qué vinculamos las tareas de red a los componentes UI de la aplicación?
Porque cuando esa actividad sea destruida, las tareas de red seran canceladas.

##### ¿Qué pasaría si intentamos actualizar la recyclerview con nuevos streams después de que el usuario haya cerrado la aplicación?
No es posible.El usuario deberia volver a la aplicación.

##### Describe brevemente los principales estados del ciclo de vida de una Activity.
onCreate() La actividad es creada.
onPause() La actividad es pausada ya que el usuario navega a otra activity de la aplicación o a otra aplicación.
onResume() El usuario vuelve a la activity y esta debe recuperar su estado anterior.
onDestroy() La actividad se destruye con todos sus componentes ya que el usuario no la necesita más.

---

### Paginación 

#### Explica el uso de paginación en la API de Twitch.
Se usa la paginación para ser más eficiente al mostrar la información al usuario.
Y a medida que el usuario demanda más información se le entrega.
En el caso de los "Streams" se entregan en grupos de 20.

##### ¿Qué ventajas ofrece la paginación a la aplicación?
Una respuesta mucha más rapida y más eficiente con el consumo de datos del usuario.

##### ¿Qué problemas puede tener la aplicación si no se utiliza paginación?
Se necesitaria mucho más tiempo de espera para que la respuesta del servidor llegara al dispositivo del usuario.
El consumo de datos del usuario seria excesivo.

##### Lista algunos ejemplos de aplicaciones que usan paginación.
Youtube, Instagram y Amazon.
