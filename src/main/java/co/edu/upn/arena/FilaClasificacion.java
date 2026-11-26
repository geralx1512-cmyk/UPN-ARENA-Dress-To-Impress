package co.edu.upn.arena;

public class FilaClasificacion {
    private String puesto;
    private String participante;
    private int estrellas;

    public FilaClasificacion(String puesto, String participante, int estrellas) {
        this.puesto = puesto;
        this.participante = participante;
        this.estrellas = estrellas;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getParticipante() {
        return participante;
    }

    public int getEstrellas() {
        return estrellas;
    }
}