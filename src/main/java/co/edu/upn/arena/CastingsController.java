package co.edu.upn.arena;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CastingsController {

    private static final String[] HORARIOS_SOLO = {
        "9:00 - 9:20",
        "9:20 - 9:40",
        "9:40 - 10:00",
        "10:00 - 10:20",
        "10:20 - 10:40",
        "10:40 - 11:00",
        "11:00 - 11:20"
    };

    private static final String[] HORARIOS_DUO = {
        "11:20 - 12:00",
        "12:00 - 12:40",
        "1:40 - 2:20",
        "2:20 - 3:00",
        "3:00 - 3:40"
    };

    private static final String[] HORARIOS_TRIO = {
        "3:40 - 4:40",
        "4:40 - 5:40",
        "5:40 - 6:40"
    };

    @FXML
    private TableView<FilaCasting> tblCastingsInfo;

    @FXML
    private TableColumn<FilaCasting, String> colCastingNombre;

    @FXML
    private TableColumn<FilaCasting, String> colCastingModalidad;

    @FXML
    private TableColumn<FilaCasting, String> colCastingCupos;

    @FXML
    private ComboBox<Grupo> cmbGrupos;

    @FXML
    private TableView<Audicion> tblAudiciones;

    @FXML
    private TableColumn<Audicion, String> colAudGrupo;

    @FXML
    private TableColumn<Audicion, String> colAudCasting;

    @FXML
    private TableColumn<Audicion, String> colAudHorario;

    private ObservableList<FilaCasting> filasCasting = FXCollections.observableArrayList();

    public static class FilaCasting {
        private final SimpleStringProperty casting;
        private final SimpleStringProperty modalidad;
        private final SimpleStringProperty cupos;

        public FilaCasting(String casting, String modalidad, String cupos) {
            this.casting = new SimpleStringProperty(casting);
            this.modalidad = new SimpleStringProperty(modalidad);
            this.cupos = new SimpleStringProperty(cupos);
        }

        public String getCasting() {
            return casting.get();
        }

        public String getModalidad() {
            return modalidad.get();
        }

        public String getCupos() {
            return cupos.get();
        }
    }

    @FXML
    private void initialize() {
        colCastingNombre.setCellValueFactory(new PropertyValueFactory<>("casting"));
        colCastingModalidad.setCellValueFactory(new PropertyValueFactory<>("modalidad"));
        colCastingCupos.setCellValueFactory(new PropertyValueFactory<>("cupos"));

        colAudGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
        colAudCasting.setCellValueFactory(new PropertyValueFactory<>("casting"));
        colAudHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));

        for (Grupo g : DatosArena.grupos) {
            if (!yaAudiciono(g.toString())) {
                cmbGrupos.getItems().add(g);
            }
        }

        tblAudiciones.getItems().addAll(DatosArena.audiciones);
        actualizarTablaCastings();
    }

    private boolean yaAudiciono(String nombreGrupo) {
        for (Audicion a : DatosArena.audiciones) {
            if (a.getGrupo().equals(nombreGrupo)) {
                return true;
            }
        }
        return false;
    }

    private void actualizarTablaCastings() {
        int libresSolo = HORARIOS_SOLO.length - DatosArena.turnoSolo;
        int libresDuo = HORARIOS_DUO.length - DatosArena.turnoDuo;
        int libresTrio = HORARIOS_TRIO.length - DatosArena.turnoTrio;

        filasCasting.setAll(
            new FilaCasting("Solo Star", "Solo", libresSolo + " / " + HORARIOS_SOLO.length),
            new FilaCasting("Rising Duet", "Duo", libresDuo + " / " + HORARIOS_DUO.length),
            new FilaCasting("Big Three", "Trio", libresTrio + " / " + HORARIOS_TRIO.length)
        );
        tblCastingsInfo.setItems(filasCasting);
    }

    @FXML
    private void audicionar() {
        Grupo grupoSel = cmbGrupos.getValue();

        if (grupoSel == null) {
            Mensajes.error("Selecciona un grupo.");
            return;
        }

        if (yaAudiciono(grupoSel.toString())) {
            Mensajes.error("Este grupo ya tiene un horario asignado.");
            return;
        }

        String modalidad = grupoSel.getModalidad();
        String nombreCasting;
        String horarioAsignado;

        if (modalidad.equals("Solo")) {
            if (DatosArena.turnoSolo >= HORARIOS_SOLO.length) {
                Mensajes.error("grupo lleno");
                return;
            }
            nombreCasting = "Solo Star";
            horarioAsignado = HORARIOS_SOLO[DatosArena.turnoSolo];
            DatosArena.turnoSolo++;
        } else if (modalidad.equals("Duo")) {
            if (DatosArena.turnoDuo >= HORARIOS_DUO.length) {
                Mensajes.error("grupo lleno");
                return;
            }
            nombreCasting = "Rising Duet";
            horarioAsignado = HORARIOS_DUO[DatosArena.turnoDuo];
            DatosArena.turnoDuo++;
        } else {
            if (DatosArena.turnoTrio >= HORARIOS_TRIO.length) {
                Mensajes.error("grupo lleno");
                return;
            }
            nombreCasting = "Big Three";
            horarioAsignado = HORARIOS_TRIO[DatosArena.turnoTrio];
            DatosArena.turnoTrio++;
        }

        actualizarTablaCastings();

        Audicion nueva = new Audicion(grupoSel.toString(), nombreCasting, horarioAsignado);
        DatosArena.audiciones.add(nueva);
        tblAudiciones.getItems().add(nueva);

        cmbGrupos.getItems().remove(grupoSel);
        cmbGrupos.getSelectionModel().clearSelection();

        Mensajes.info("Quedaste inscrito para audicionar en el horario: " + horarioAsignado);
    }
}