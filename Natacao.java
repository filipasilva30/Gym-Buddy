import java.time.LocalDateTime;

/**
 * Subclasse Natacao, que representa uma atividade física específica de natação.
 * Contém atributos adicionais como a distância.
 * Implementa os métodos abstratos para calcular calorias, que considera o peso do utilizador, a duração da atividade e o número de metros 
 * percorridos em distância, bem como o fator multiplicativo que tem em conta o tipo de utilizador que realiza a atividade.
 */

public class Natacao extends Atividade {
    private double distancia;

    public Natacao() {
        super();
        this.distancia = 0.0;
    }

    public Natacao(Long id, String nome, double caloriasPorHora, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, double duracao, double distancia) {
        super(id, nome, caloriasPorHora, dataHoraInicio, dataHoraFim, duracao, TipoAtividade.DISTANCIA);
        this.distancia = distancia;
    }

    @Override
    public double getDistancia() {
        return this.distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    @Override
    public double getAltimetria() {
        return 0.0;
    }

    @Override
    public double calcularCalorias(Utilizador user) {
        double fator = user.calcularFator();
        return 500 * (user.getPeso() / 70) * (this.getDuracao() / 60) * fator * (this.getDistancia() / 100);
    }

    @Override
    public Natacao clone() {
        return new Natacao(getId(), getNome(), getCaloriasPorHora(), getDataHoraInicio(), getDataHoraFim(), getDuracao(), getDistancia());
    }

    @Override
    public String toString() {
        return super.toString() + ", Distância: " + this.getDistancia() + " metros";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Natacao)) return false;
        if (!super.equals(o)) return false;
        Natacao natacao = (Natacao) o;
        return Double.compare(natacao.getDistancia(), this.getDistancia()) == 0;
    }
}
