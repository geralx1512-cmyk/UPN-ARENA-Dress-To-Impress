package co.edu.upn.arena;

public class Audicion {
    private String grupo;
    private String casting;
    private String horario;

    public Audicion() {
    }

    public Audicion(String grupo, String casting, String horario) {
        this.grupo = grupo;
        this.casting = casting;
        this.horario = horario;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCasting() {
        return casting;
    }

    public void setCasting(String casting) {
        this.casting = casting;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}