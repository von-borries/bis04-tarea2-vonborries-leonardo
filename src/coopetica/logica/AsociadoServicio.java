package coopetica.logica;

import coopetica.datos.AsociadoArchivoDAO;
import coopetica.modelo.Asociado;
import java.io.IOException;
import java.util.ArrayList;

public class AsociadoServicio {

    private static final String MSG_OBLIGATORIOS = "La cedula y el nombre son obligatorios.";

    private AsociadoArchivoDAO dao;

    public AsociadoServicio() throws IOException {
        this.dao = new AsociadoArchivoDAO();
    }

    public ArrayList<Asociado> listar() throws IOException {
        return dao.listar();
    }

    public String registrar(Asociado a)
            throws IOException {

        if (a.getCedula().isEmpty() || a.getNombre().isEmpty()) {
            return MSG_OBLIGATORIOS;
        }

        if (a.getSaldoAportes() < 0) {
            return "El saldo no puede ser negativo.";
        }

        for (Asociado asociado : dao.listar()) {
            if (asociado.getCedula().equals(a.getCedula())) {
                return "Ya existe un asociado con esa cedula.";
            }
        }

        dao.agregar(a);
        return "OK";
    }

    // ---- ETAPA 2: version que recibe el texto crudo de la ventana ----
    public String registrar(String cedula, String nombre, String saldoTexto)
            throws IOException {

        if (cedula.trim().isEmpty() || nombre.trim().isEmpty()) {
            return MSG_OBLIGATORIOS;
        }

        double saldo;
        try {
            saldo = Double.parseDouble(saldoTexto.trim());
        } catch (NumberFormatException e) {
            return "El saldo debe ser un numero.";
        }

        return registrar(new Asociado(cedula.trim(), nombre.trim(), saldo));
    }

    // ---- ETAPA 3: resumen ----
    public int contarAsociados() throws IOException {
        return listar().size();
    }

    public double calcularTotalAportes() throws IOException {
        double total = 0;
        for (Asociado a : listar()) {
            total += a.getSaldoAportes();
        }
        return total;
    }

    // ---- ETAPA 4: busqueda ----
    public Asociado buscarPorCedula(String cedula) throws IOException {
        String buscada = cedula.trim();
        for (Asociado a : listar()) {
            if (a.getCedula().equals(buscada)) {
                return a;
            }
        }
        return null;
    }
}
