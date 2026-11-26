package co.edu.upn.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClasificacionController {

    private static final int MIN_SOLOS = 7;
    private static final int MIN_DUOS = 5;
    private static final int MIN_TRIOS = 3;
    private static final int MAX_ESTRELLAS = 100;
    private static final int MAX_PUESTOS = 7;

    @FXML
    private TableView<FilaClasificacion> tblSolos;
    @FXML
    private TableColumn<FilaClasificacion, String> colPuestoSolos;
    @FXML
    private TableColumn<FilaClasificacion, String> colNombreSolos;
    @FXML
    private TableColumn<FilaClasificacion, Integer> colEstrellasSolos;

    @FXML
    private TableView<FilaClasificacion> tblDuos;
    @FXML
    private TableColumn<FilaClasificacion, String> colPuestoDuos;
    @FXML
    private TableColumn<FilaClasificacion, String> colNombreDuos;
    @FXML
    private TableColumn<FilaClasificacion, Integer> colEstrellasDuos;

    @FXML
    private TableView<FilaClasificacion> tblTrios;
    @FXML
    private TableColumn<FilaClasificacion, String> colPuestoTrios;
    @FXML
    private TableColumn<FilaClasificacion, String> colNombreTrios;
    @FXML
    private TableColumn<FilaClasificacion, Integer> colEstrellasTrios;

    private Random random = new Random();

    @FXML
    private void initialize() {
        configurarColumnas(colPuestoSolos, colNombreSolos, colEstrellasSolos);
        configurarColumnas(colPuestoDuos, colNombreDuos, colEstrellasDuos);
        configurarColumnas(colPuestoTrios, colNombreTrios, colEstrellasTrios);

        simularClasificacion();
    }

    private void configurarColumnas(
            TableColumn<FilaClasificacion, String> colPuesto,
            TableColumn<FilaClasificacion, String> colNombre,
            TableColumn<FilaClasificacion, Integer> colEstrellas) {

        colPuesto.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getPuesto()));
        colNombre.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().getParticipante()));
        colEstrellas.setCellValueFactory(dato -> new SimpleIntegerProperty(dato.getValue().getEstrellas()).asObject());
    }

    @FXML
    private void simularClasificacion() {
        List<String> solos = obtenerPorModalidad("Solo");
        List<String> duos = obtenerPorModalidad("Duo");
        List<String> trios = obtenerPorModalidad("Trio");

        tblSolos.getItems().setAll(generarRanking(solos, MIN_SOLOS));
        tblDuos.getItems().setAll(generarRanking(duos, MIN_DUOS));
        tblTrios.getItems().setAll(generarRanking(trios, MIN_TRIOS));
    }

    private List<String> obtenerPorModalidad(String modalidadBuscada) {
        List<String> lista = new ArrayList<>();
        for (int i = 0; i < DatosArena.grupos.size(); i++) {
            Grupo g = DatosArena.grupos.get(i);
            if (g.getModalidad() != null && g.getModalidad().equals(modalidadBuscada)) {
                lista.add(g.getNombre());
            }
        }
        return lista;
    }

    private List<FilaClasificacion> generarRanking(List<String> participantes, int minEstrellas) {
        List<FilaClasificacion> ranking = new ArrayList<>();

        int cantidad = participantes.size();
        if (cantidad > MAX_PUESTOS) {
            cantidad = MAX_PUESTOS;
        }

        for (int i = 0; i < cantidad; i++) {
            String nombre = participantes.get(i);
            int rango = MAX_ESTRELLAS - minEstrellas + 1;
            int estrellas = minEstrellas + random.nextInt(rango);
            ranking.add(new FilaClasificacion("", nombre, estrellas));
        }

        for (int i = 0; i < ranking.size(); i++) {
            for (int j = i + 1; j < ranking.size(); j++) {
                if (ranking.get(j).getEstrellas() > ranking.get(i).getEstrellas()) {
                    FilaClasificacion aux = ranking.get(i);
                    ranking.set(i, ranking.get(j));
                    ranking.set(j, aux);
                }
            }
        }

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPuesto((i + 1) + "°");
        }

        return ranking;
    }
}