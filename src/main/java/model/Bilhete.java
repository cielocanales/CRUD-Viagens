package model;

import java.time.LocalDateTime;

public class Bilhete {
    private int id;
    private int passageiroId;
    private int viagemId;
    private int numeroPoltrona;
    private LocalDateTime dataCompra;
    private String status; // "PENDENTE", "PAGO" ou "CANCELADO"

    public Bilhete() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPassageiroId() { return passageiroId; }
    public void setPassageiroId(int passageiroId) { this.passageiroId = passageiroId; }
    public int getViagemId() { return viagemId; }
    public void setViagemId(int viagemId) { this.viagemId = viagemId; }
    public int getNumeroPoltrona() { return numeroPoltrona; }
    public void setNumeroPoltrona(int numeroPoltrona) { this.numeroPoltrona = numeroPoltrona; }
    public LocalDateTime getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDateTime dataCompra) { this.dataCompra = dataCompra; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
