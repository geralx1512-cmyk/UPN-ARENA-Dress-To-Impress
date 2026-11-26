package co.edu.upn.arena;

public class Grupo {
    private int id;
    private String nombre;
    private String modalidad;
    private String integrantes;

    public Grupo() {
    }

    public Grupo(String nombre, String modalidad, String integrantes) {
        this.nombre = nombre;
        this.modalidad = modalidad;
        this.integrantes = integrantes;
    }

    public Grupo(int id, String nombre, String modalidad, String integrantes) {
        this.id = id;
        this.nombre = nombre;
        this.modalidad = modalidad;
        this.integrantes = integrantes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(String integrantes) {
        this.integrantes = integrantes;
    }

    @Override
    public String toString() {
        return nombre + " (" + modalidad + ")";
    }
}