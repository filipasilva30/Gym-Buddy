import java.time.LocalDateTime;

/**
 * Classe abstrata Atividade que serve de base para diferentes tipos de atividades físicas.
 * Cada atividade é identificada por um ID e possui atributos como nome, calorias gastas por hora,
 * data e hora de início e fim, duração e tipo de atividade.
 * Possui também métodos abstratos para cálculo de calorias, distância e altimetria que são implementados pelas subclasses.
 */

public abstract class Atividade {
    private Long id;
    private String nome;
    private double caloriasPorHora;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private double duracao; 
    private TipoAtividade tipo;

    public Atividade() {
        this.id = 0L;
        this.nome = null;
        this.caloriasPorHora = 0.0;
        this.dataHoraInicio = LocalDateTime.now();
        this.dataHoraFim = LocalDateTime.now();
        this.duracao = 0.0;
    }

    public Atividade(Long id, String nome, double caloriasPorHora, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, double duracao, TipoAtividade tipo) {
        this.id = id;
        this.nome = nome;
        this.caloriasPorHora = caloriasPorHora;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.duracao = duracao;
        this.tipo = tipo;
    }

    public Atividade(Atividade a) {
        this.id = a.getId();
        this.nome = a.getNome();
        this.caloriasPorHora = a.getCaloriasPorHora();
        this.dataHoraInicio = a.getDataHoraInicio();
        this.dataHoraFim = a.getDataHoraFim();
        this.duracao = a.getDuracao();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getCaloriasPorHora() {
        return this.caloriasPorHora;
    }

    public void setCaloriasPorHora(double caloriasPorHora) {
        this.caloriasPorHora = caloriasPorHora;
    }

    public LocalDateTime getDataHoraInicio() {
        return this.dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return this.dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public double getDuracao() {
        return this.duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public TipoAtividade getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoAtividade tipo) {
        this.tipo = tipo;
    }

    public abstract double calcularCalorias (Utilizador user);

    public abstract double getDistancia();

    public abstract double getAltimetria();

    public abstract Atividade clone();

    @Override
    public String toString() {
        return "Atividade{id=" + this.getId() + ", nome='" + this.getNome() + "', caloriasPorHora=" + this.getCaloriasPorHora() + "', dataHoraInicio=" +  this.getDataHoraInicio() + "', dataHoraFim=" +  this.getDataHoraFim() + "', duração=" + this.getDuracao() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atividade atividade = (Atividade) o;
        return this.getId().equals(atividade.getId()) && this.getNome().equals(atividade.getNome()) && this.getCaloriasPorHora() == atividade.getCaloriasPorHora() && this.getDataHoraInicio().equals(atividade.getDataHoraInicio()) && this.getDataHoraFim().equals(atividade.getDataHoraFim()) && this.getDuracao() == atividade.getDuracao();
    }
}