# Bitacora - Tarea 2

Nombre: Leonardo Von Borries

Metodos nuevos, todos en AsociadoServicio:
- registrar(String, String, String): recibe el texto crudo de la ventana, convierte el saldo y delega en registrar(Asociado).
- contarAsociados(): devuelve cuantos asociados hay.
- calcularTotalAportes(): suma los saldos de todos los asociados.
- buscarPorCedula(String): devuelve el asociado o null si no existe.

Por que en el servicio y no en la ventana: la suma y la busqueda son logica de negocio, no presentacion. Si el total vive en el servicio, un reporte u otra ventana lo pide sin reescribirlo. La ventana solo recoge datos, llama al servicio y muestra lo que recibe.

Por que la sobrecarga de registrar: el servicio original recibia un Asociado, con saldo ya convertido a double. Para que la ventana no valide nada (ni siquiera si el saldo es numero), la conversion y su mensaje de error quedaron en el servicio.

Por que no toque el DAO: los metodos nuevos reutilizan listar(), que ya lee el archivo.

tambien queria aclarar el uso de ia por este medio debido a que no me permito darme el merito entero de hacerlo cuando fue con ayudas
La ia me explico paso por paso donde como, que y por que agregar lo que se le agrega al codiogo, me ayudo a crear el repositorio y tambien el porque se uso cada metodo usado en esta tarea.

gracias a esto entendi por completo todo lo que se ve en el codigo y estoy dispuesto a explicar lo necesario

Dificultades: se me complico entender de buenas a primeras lo que se tenia que hacer y terminar de usar github, tambien se me complico la tarea por el cambio de fechas.
