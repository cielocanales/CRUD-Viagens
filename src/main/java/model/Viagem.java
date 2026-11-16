package model;

import java.time.LocalDateTime;

public class Viagem {
    private int id;
    private int empresaId;
    private int origemId;
    private int destinoId;
    private LocalDateTime dataSaida;
    private double precoBase;
    private int assentosTotais;

    public Viagem() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }
    public int getOrigemId() { return origemId; }
    public void setOrigemId(int origemId) { this.origemId = origemId; }
    public int getDestinoId() { return destinoId; }
    public void setDestinoId(int destinoId) { this.destinoId = destinoId; }
    public LocalDateTime getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }
    public double getPrecoBase() { return precoBase; }
    public void setPrecoBase(double precoBase) { this.precoBase = precoBase; }
    public int getAssentosTotais() { return assentosTotais; }
    public void setAssentosTotais(int assentosTotais) { this.assentosTotais = assentosTotais; }
}