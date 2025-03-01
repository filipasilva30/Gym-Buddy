import java.time.LocalDateTime;

/**
 * Subclasse AgachamentoPeso, que representa uma atividade física específica com a utilização de peso.
 * Contém atributos adicionais como o peso, número de repetições e séries.
 * Implementa os métodos abstratos para calcular calorias, que considera o peso do utilizador, o número de repetições e séries 
 * e o peso utilizado na atividade, bem como o fator multiplicativo que tem em conta o tipo de utilizador que realiza a atividade.
 */

public class AgachamentoPeso extends Atividade {
    private double peso;
    private int nRep;
    private int nSet;

    public AgachamentoPeso() {
        super();
        this.peso = 0.0;
        this.nRep = 0;
        this.nSet = 0;
    }

    public AgachamentoPeso(Long id, String nome, double caloriasPorHora, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, double duracao, double peso, int nRep, int nSet) {
        super(id, nome, caloriasPorHora, dataHoraInicio, dataHoraFim, duracao, TipoAtividade.REPETICAO_COM_PESO);
        this.peso = peso;
        this.nRep = nRep;
        this.nSet = nSet; 
    }

    public double getPeso() {
        return this.peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
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
        return this.getnRep() * this.getnSet() * (0.3 + this.getPeso() * 0.02) * (user.getPeso() / 70) * fator;
    }

    @Override
    public AgachamentoPeso clone() {
        return new AgachamentoPeso(getId(), getNome(), getCaloriasPorHora(), getDataHoraInicio(), getDataHoraFim(), getDuracao(), getPeso(), getnRep(), getnSet());
    }

    @Override
    public String toString() {
        return super.toString() + ", Peso: " + this.getPeso() + ", Repetições: " + this.getnRep() + ", Séries: " + this.getnSet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgachamentoPeso)) return false;
        if (!super.equals(o)) return false;
        AgachamentoPeso that = (AgachamentoPeso) o;
        return Double.compare(that.getPeso(), getPeso()) == 0 && this.getnRep() == that.getnRep() && this.getnSet() == that.getnSet();
    }
}
