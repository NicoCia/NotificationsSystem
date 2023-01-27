# NotificationsSystem

Notification system to send alerts to subscribed users on topics of interest.
Sistema de notificaciones, permite registrar topicos y suscribir usuarios para recibir alertas sobre los temas de su interes.

El sistema se ejecuta desde linea de comandos.

Lo comandos aceptados y su sintaxis son los siguientes:

- *register -class[topic, user] -name=\<user or topic name\>* :
Registra el topico o usuario (segun indique el campo "class") con el nombre indicado en "name".

- *subscribe -user=\<user name\> -topic=\<topic name\>* :
Suscribe al usuario del campo "user" al topico indicado por "topic"

- *sendAlert -topic=\<topic name\> -type=[urgente, informativa] -user=[all, \<especific user name\>] -text=\<notification text\> -expirationDate=[YYYY-MM-DDTHH:MM:SS]* : Cuando "user" se setea en 'all' envia un alerta para todos los usuarios suscriptos al topico indicado por "topic", sino es posible definir un usuario especifico para que reciba la alerta indicando su nombre. El usuario debe estar previamente registrado en el sistema. "espirationDate" permite setear una fecha y hora de expiracion para la alerta, la cual debe ser ingresada en respetando el formato ISO-8601 como se muestra en la sintaxis. Tanto "expirationDate" como "text" son campos opcionales.

- *markAlertAsRead -user=\<user name\> -alertIndex=\<unique integer index for each alert\>* : Un usuario puede marcar una alerta como leída. Es necesario indicar el nombre del usuario y el indice unico que identifica a la alerta a marcar como leida.

- *recoverUserPendingAlerts -user=\<user name\>* : Permite obtener todas las alertas no expiradas de un usuario que aún no ha leído, ordenadas primero las Urgentes y luego las informativas de la más reciente a la más antigua.

- *recoverTopicUnexpiredAlerts -name=\<topic name\>* : Permite obtener todas las alertas no expiradas para un tema (primero las Urgentes y luego las Informativas de la más reciente a la más antigua). Se informa para cada alerta si es para todos los usuarios o para uno específico.


Supuestos aplicados:

- las notificaciones leidas o expiradas no cumplen funcion alguna en el sistema por lo que "marcarlas como leidas" en realidad son borradas de la estructura de datos.

- al enviar una alerta a un usuario en especifico el mismo debe ser un usuario registrado pero no es necesario que este suscrito al topico de la alerta

- la informacion de si una alerta fue enviada a un usuario o a todos se encuentra almacenado en un campo especifico de cada alerta por lo que es posible recuperar dicha informacion leyendo el campo

- no se especificaba en que formato deberia ser devueltas las consultas no expiradas y no leidas por topico o usuario por lo que las mismas son devueltas en una estructura del tipo  `ArrayList<>`
