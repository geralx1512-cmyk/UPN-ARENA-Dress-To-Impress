package co.edu.upn.arena;

import java.io.InputStream;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ModelosController {

    @FXML
    private HBox raizModelos;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtAlias;

    @FXML
    private TextField txtCorreo;

    @FXML
    private ComboBox<String> cmbGenero;

    @FXML
    private TableView<Modelo> tblModelos;

    @FXML
    private TableColumn<Modelo, String> colNombre;

    @FXML
    private TableColumn<Modelo, String> colAlias;

    @FXML
    private TableColumn<Modelo, String> colCorreo;

    @FXML
    private TableColumn<Modelo, String> colGenero;

    @FXML
    private ImageView imgPersonaje;

    @FXML
    private Label lblPersonaje;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getNombre()));
        colAlias.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getAlias()));
        colCorreo.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getCorreo()));
        colGenero.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getGenero()));
        tblModelos.getItems().addAll(DatosArena.modelos);
    }

    public void cambiarImagenTema(boolean esOscuro) {
        String ruta = "Imagenes/predeterminado.png";

        if (esOscuro) {
            ruta = "Imagenes/oscuro.png";
        }

        cargarImagenPersonaje(ruta);
    }

    @FXML
    private void guardarModelo() {
        String nombre = txtNombre.getText().trim();
        String alias = txtAlias.getText().trim();
        String correo = txtCorreo.getText().trim();
        String genero = cmbGenero.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || genero == null) {
            Mensajes.error("Completa al menos Nombre, Correo y Género.");
            return;
        }

        boolean tieneNumero = false;
        for (int i = 0; i < nombre.length(); i++) {
            if (Character.isDigit(nombre.charAt(i))) {
                tieneNumero = true;
                break;
            }
        }
        if (tieneNumero) {
            Mensajes.error("El nombre no puede tener números.");
            return;
        }

        if (!correo.contains("@")) {
            Mensajes.error("El correo debe contener un @.");
            return;
        }

        Modelo nuevo = new Modelo(nombre, alias, correo, genero);
        DatosArena.modelos.add(nuevo);
        tblModelos.getItems().add(nuevo);
        aplicarTemaPorGenero(genero);

        txtNombre.clear();
        txtAlias.clear();
        txtCorreo.clear();
        cmbGenero.getSelectionModel().clearSelection();

        Mensajes.info("Modelo guardado: " + nuevo.toString());
    }

    private void aplicarTemaPorGenero(String genero) {
        raizModelos.getStyleClass().remove("tema-male");
        raizModelos.getStyleClass().remove("tema-inbetween");
        raizModelos.getStyleClass().remove("tema-none");

        if (genero.equals("Male")) {
            if (!raizModelos.getStyleClass().contains("tema-male")) {
                raizModelos.getStyleClass().add("tema-male");
            }
            cargarImagenPersonaje("Imagenes/male.png");
            
        } else if (genero.equals("Inbetween")) {
            if (!raizModelos.getStyleClass().contains("tema-inbetween")) {
                raizModelos.getStyleClass().add("tema-inbetween");
            }
            cargarImagenPersonaje("Imagenes/inbetween.png");

        } else if (genero.equals("None")) {
            if (!raizModelos.getStyleClass().contains("tema-none")) {
                raizModelos.getStyleClass().add("tema-none");
            }
            cargarImagenPersonaje("Imagenes/none.png");

        } else {
            cargarImagenPersonaje("Imagenes/female.png");
        }
    }

    private void cargarImagenPersonaje(String rutaRelativa) {
        if (imgPersonaje == null) {
            return;
        }
        InputStream stream = getClass().getResourceAsStream(rutaRelativa);
        if (stream != null) {
            imgPersonaje.setImage(new Image(stream));
        } else {
            imgPersonaje.setImage(null);
        }
    }

    @FXML
    private void eliminarModelo() {
        Modelo seleccionado = tblModelos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Mensajes.error("Selecciona un modelo en la tabla para eliminar.");
            return;
        }

        if (Mensajes.confirmar("¿Eliminar a " + seleccionado.toString() + "?")) {
            DatosArena.modelos.remove(seleccionado);
            tblModelos.getItems().remove(seleccionado);
        }
    }
}