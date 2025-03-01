import java.time.LocalDateTime;

/**
 * Subclasse Abdominais, que representa uma atividade física específica de abdominais.
 * Contém atributos adicionais como número de repetições e séries.
 * Implementa os métodos abstratos para calcular calorias, que considera o peso do utilizador e o número de repetições e séries, bem como
 * o fator multiplicativo que tem em conta o tipo de utilizador que realiza a atividade.
 */

public class Abdominais extends Atividade {
    private int nRep;
    private int nSet;

    public Abdominais() {
        super();
        this.nRep = 0;
        this.nSet = 0;
    }
    
    public Abdominais(Long id, String nome, double caloriasPorHora, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, double duracao, int nRep, int nSet) {
        super(id, nome, caloriasPorHora, dataHoraInicio, dataHoraFim, duracao, TipoAtividade.REPETICAO);
        this.nRep = nRep;
        this.nSet = nSet;
    }

    public int getnRep() {
        return this.nRep;
    }

    public void setnRep(int nRep) {
        this.nRep = nRep;
    }

    public int getnSet() {
        return this.nSet;
    }

    public void setnSet(int nSet) {
        this.nSet = nSet;
    }

    @Override
    public double getDistancia() {
        return 0.0;
    }

    @Override
    public double getAltimetria() {
        return 0.0;
    }

    @Override
    public double calcularCalorias(Utilizador user) {
        double fator = user.calcularFator();
        return user.getPeso() * this.getnRep() * this.getnSet() * 0.1 * fator;
    }

    @Override
    public Abdominais clone() {
        return new Abdominais(getId(), getNome(), getCaloriasPorHora(), getDataHoraInicio(), getDataHoraFim(), getDuracao(), getnRep(), getnSet());
    }

    @Override
    public String toString() {
        return super.toString() + ", Repetições: " + this.getnRep() + ", Séries: " + this.getnSet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Abdominais)) return false;
        if (!super.equals(o)) return false;
        Abdominais abdominais = (Abdominais) o;
        return this.getnRep() == abdominais.getnRep() && this.getnSet() == abdominais.getnSet();
    }
}
