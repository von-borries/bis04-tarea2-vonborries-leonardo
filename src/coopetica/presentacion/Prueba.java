package coopetica.presentacion;

import coopetica.logica.AsociadoServicio;
import coopetica.modelo.Asociado;

public class Prueba {

    public static void main(String[] args) throws Exception {

        AsociadoServicio servicio = new AsociadoServicio();

        System.out.println(servicio.registrar(new Asociado("12345678", "Pedro Herrrera", 350000)));
        System.out.println(servicio.registrar(new Asociado("78945612", "Juan Perez", 450000)));
        //System.out.println(servicio.registrar("118450236", "Otra Persona", "1000"));
        //System.out.println(servicio.registrar("301220788", "Marta Soto", "abc"));

        System.out.println("--- Lista ---");
        for (Asociado a : servicio.listar()) {
            System.out.println(a);
        }
    }
}