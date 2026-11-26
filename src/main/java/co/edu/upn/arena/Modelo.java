package co.edu.upn.arena;

public class Modelo {
    private int id;
    private String nombre;
    private String alias;
    private String correo;
    private String genero;

    // Constructor vacío (útil para formularios o marcos vacíos)
    public Modelo() {
    }

    // Constructor sin id (cuando creas un modelo nuevo antes de guardarlo)
    public Modelo(String nombre, String alias, String correo, String genero) {
        this.nombre = nombre;
        this.alias = alias;
        this.correo = correo;
        this.genero = genero;
    }

    // Constructor completo (cuando ya tiene id asignado)
    public Modelo(int id, String nombre, String alias, String correo, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
        this.correo = correo;
        this.genero = genero;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        if (alias != null && !alias.isBlank()) {
            return nombre + " (" + alias + ")";
        }
        return nombre;
    }
}
