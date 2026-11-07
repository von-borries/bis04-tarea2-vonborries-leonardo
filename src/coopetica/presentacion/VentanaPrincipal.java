package coopetica.presentacion;

import coopetica.logica.AsociadoServicio;
import coopetica.modelo.Asociado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    // Unica dependencia de logica: el servicio (nunca el DAO)
    private final AsociadoServicio servicio;

    // Componentes como atributos para verlos desde cualquier metodo
    private JTextField campoCedula = new JTextField(10);
    private JTextField campoNombre = new JTextField(15);
    private JTextField campoSaldo = new JTextField(8);
    private JButton botonRegistrar = new JButton("Registrar");
    private JButton botonListar = new JButton("Listar");
    private JTextArea area = new JTextArea(12, 50);
    private JLabel estado = new JLabel("Listo");

    private JButton botonResumen = new JButton("Resumen");

    private JTextField campoBuscar = new JTextField(10);
    private JButton botonBuscar = new JButton("Buscar");

    public VentanaPrincipal() throws IOException {
        servicio = new AsociadoServicio();

        // 1. Configurar la ventana
        setTitle("CoopeTica - Leonardo Von Borries");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Panel del formulario
        JPanel formulario = new JPanel();
        formulario.add(new JLabel("Cedula:"));
        formulario.add(campoCedula);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Saldo:"));
        formulario.add(campoSaldo);
        formulario.add(botonRegistrar);

        // 3. Panel de acciones y busqueda
        JPanel acciones = new JPanel();
        acciones.add(botonListar);
        acciones.add(botonResumen);
        acciones.add(new JLabel("Buscar cedula:"));
        acciones.add(campoBuscar);
        acciones.add(botonBuscar);

        // 4. BorderLayout admite un solo componente por zona: agrupamos
        JPanel norte = new JPanel(new GridLayout(2, 1));
        norte.add(formulario);
        norte.add(acciones);

        // 5. Zona de resultados
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        add(norte, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(estado, BorderLayout.SOUTH);

        // 6. Botones (siempre antes del pack)
        botonRegistrar.addActionListener(e -> registrar());
        botonListar.addActionListener(e -> listar());
        botonResumen.addActionListener(e -> mostrarResumen());
        botonBuscar.addActionListener(e -> buscar());

        // 7. Cierre de la construccion
        pack();
        setLocationRelativeTo(null);

        listar(); // al abrir, ya se ven los asociados guardados
    }

    private void registrar() {
        try {
            String resultado = servicio.registrar(
                    campoCedula.getText(),
                    campoNombre.getText(),
                    campoSaldo.getText());

            if (resultado.equals("OK")) {
                campoCedula.setText("");
                campoNombre.setText("");
                campoSaldo.setText("");
                estado.setForeground(Color.BLACK);
                estado.setText("Asociado registrado correctamente.");
                JOptionPane.showMessageDialog(this,
                        "Asociado registrado correctamente.",
                        "Registro", JOptionPane.INFORMATION_MESSAGE);
                listar();
            } else {
                estado.setForeground(Color.RED);
                estado.setText(resultado);
                JOptionPane.showMessageDialog(this, resultado,
                        "No se pudo registrar", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            mostrarErrorDeArchivo();
        }
    }

    private void listar() {
        try {
            List<Asociado> lista = servicio.listar();
            StringBuilder texto = new StringBuilder();
            for (Asociado a : lista) {
                texto.append(a).append("\n");
            }
            area.setText(texto.toString());
        } catch (IOException ex) {
            mostrarErrorDeArchivo();
        }
    }

    private void mostrarResumen() {
        try {
            int cantidad = servicio.contarAsociados();
            double total = servicio.calcularTotalAportes();
            JOptionPane.showMessageDialog(this,
                    "Asociados registrados: " + cantidad
                    + "\nTotal de aportes: " + String.format("%,.2f", total),
                    "Resumen", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            mostrarErrorDeArchivo();
        }
    }

    private void buscar() {
        try {
            Asociado a = servicio.buscarPorCedula(campoBuscar.getText());
            if (a == null) {
                area.setText("No existe un asociado con esa cedula.");
            } else {
                area.setText("Cedula: " + a.getCedula()
                        + "\nNombre: " + a.getNombre()
                        + "\nAportes: " + a.getSaldoAportes());
            }
        } catch (IOException ex) {
            mostrarErrorDeArchivo();
        }
    }

    private void mostrarErrorDeArchivo() {
        JOptionPane.showMessageDialog(this,
                "No se pudo acceder al archivo de datos.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
