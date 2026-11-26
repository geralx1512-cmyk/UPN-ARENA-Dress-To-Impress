package co.edu.upn.arena;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class GruposController {

    private static final int LIMITE_SOLO = 7;
    private static final int LIMITE_DUO = 5;
    private static final int LIMITE_TRIO = 3;

    @FXML
    private TextField txtNombre;

    @FXML
    private RadioButton rbSolo;

    @FXML
    private RadioButton rbDuo;

    @FXML
    private RadioButton rbTrio;

    @FXML
    private ComboBox<String> cmbInt1;

    @FXML
    private ComboBox<String> cmbInt2;

    @FXML
    private ComboBox<String> cmbInt3;

    @FXML
    private TableView<Grupo> tblGrupos;

    @FXML
    private TableColumn<Grupo, String> colNombre;

    @FXML
    private TableColumn<Grupo, String> colModalidad;

    @FXML
    private TableColumn<Grupo, String> colIntegrantes;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colIntegrantes.setCellValueFactory(new PropertyValueFactory<>("integrantes"));

        tblGrupos.getItems().addAll(DatosArena.grupos);
        actualizarModalidad();
        cargarCombosIniciales();
    }

    private boolean modeloYaUsado(String nombreModelo) {
        for (Grupo g : DatosArena.grupos) {
            String[] partes = g.getIntegrantes().split(", ");
            for (String p : partes) {
                if (p.equals(nombreModelo)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<String> obtenerModelosLibres() {
        List<String> libres = new ArrayList<>();
        for (Modelo m : DatosArena.modelos) {
            String etiqueta = m.toString();
            if (!modeloYaUsado(etiqueta)) {
                libres.add(etiqueta);
            }
        }
        return libres;
    }

    private void cargarCombosIniciales() {
        List<String> libres = obtenerModelosLibres();
        cmbInt1.getItems().setAll(libres);
        cmbInt2.getItems().setAll(libres);
        cmbInt3.getItems().setAll(libres);
        cmbInt1.setValue(null);
        cmbInt2.setValue(null);
        cmbInt3.setValue(null);
    }

    @FXML
    private void actualizarOpcionesIntegrantes() {
        String sel1 = cmbInt1.getValue();
        String sel2 = cmbInt2.getValue();
        String sel3 = cmbInt3.getValue();

        List<String> libres = obtenerModelosLibres();

        List<String> op2 = new ArrayList<>();
        for (String m : libres) {
            if (sel1 == null || !m.equals(sel1)) {
                op2.add(m);
            }
        }
        cmbInt2.getItems().setAll(op2);
        if (sel2 != null && !op2.contains(sel2)) {
            cmbInt2.setValue(null);
            sel2 = null;
        }

        List<String> op3 = new ArrayList<>();
        for (String m : libres) {
            boolean igual1 = (sel1 != null && m.equals(sel1));
            boolean igual2 = (sel2 != null && m.equals(sel2));
            if (!igual1 && !igual2) {
                op3.add(m);
            }
        }
        cmbInt3.getItems().setAll(op3);
        if (sel3 != null && !op3.contains(sel3)) {
            cmbInt3.setValue(null);
        }
    }

    @FXML
    private void actualizarModalidad() {
        if (rbSolo.isSelected()) {
            cmbInt1.setDisable(false);
            cmbInt2.setDisable(true);
            cmbInt2.setValue(null);
            cmbInt3.setDisable(true);
            cmbInt3.setValue(null);
        } else if (rbDuo.isSelected()) {
            cmbInt1.setDisable(false);
            cmbInt2.setDisable(false);
            cmbInt3.setDisable(true);
            cmbInt3.setValue(null);
        } else {
            cmbInt1.setDisable(false);
            cmbInt2.setDisable(false);
            cmbInt3.setDisable(false);
        }
        actualizarOpcionesIntegrantes();
    }

    private int contarModalidad(String modalidadBuscada) {
        int contador = 0;
        for (Grupo g : DatosArena.grupos) {
            if (g.getModalidad().equals(modalidadBuscada)) {
                contador++;
            }
        }
        return contador;
    }

    @FXML
    private void guardarGrupo() {
        String nombre = txtNombre.getText().trim();
        String i1 = cmbInt1.getValue();
        String i2 = cmbInt2.getValue();
        String i3 = cmbInt3.getValue();

        if (nombre.isEmpty()) {
            Mensajes.error("Completa el nombre del equipo o participante.");
            return;
        }

        String modalidad;
        String integrantes;

        if (rbSolo.isSelected()) {
            if (contarModalidad("Solo") >= LIMITE_SOLO) {
                Mensajes.error("grupo lleno");
                return;
            }
            if (i1 == null || i1.isEmpty()) {
                Mensajes.error("Escoge el modelo para Solo.");
                return;
            }
            modalidad = "Solo";
            integrantes = i1;
        } else if (rbDuo.isSelected()) {
            if (contarModalidad("Duo") >= LIMITE_DUO) {
                Mensajes.error("grupo lleno");
                return;
            }
            if (i1 == null || i2 == null || i1.isEmpty() || i2.isEmpty()) {
                Mensajes.error("Escoge los 2 integrantes para Duo.");
                return;
            }
            modalidad = "Duo";
            integrantes = i1 + ", " + i2;
        } else {
            if (contarModalidad("Trio") >= LIMITE_TRIO) {
                Mensajes.error("grupo lleno");
                return;
            }
            if (i1 == null || i2 == null || i3 == null || i1.isEmpty() || i2.isEmpty() || i3.isEmpty()) {
                Mensajes.error("Escoge los 3 integrantes para Trio.");
                return;
            }
            modalidad = "Trio";
            integrantes = i1 + ", " + i2 + ", " + i3;
        }

        Grupo nuevo = new Grupo(nombre, modalidad, integrantes);
        DatosArena.grupos.add(nuevo);
        tblGrupos.getItems().add(nuevo);

        txtNombre.clear();
        cargarCombosIniciales();
        actualizarModalidad();

        Mensajes.info("Guardado: " + nuevo.toString());
    }

    @FXML
    private void eliminarGrupo() {
        Grupo sel = tblGrupos.getSelectionModel().getSelectedItem();
        if (sel == null) {
            Mensajes.error("Selecciona un grupo para eliminar.");
            return;
        }
        DatosArena.grupos.remove(sel);
        tblGrupos.getItems().remove(sel);
        cargarCombosIniciales();
        actualizarModalidad();
        Mensajes.info("Grupo eliminado. Los modelos vuelven a estar disponibles.");
    }
}