package model;

public class Localizacao {
    private int id;
    private String cidade;
    private String estado;

    public Localizacao() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}