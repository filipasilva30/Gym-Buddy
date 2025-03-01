import java.time.LocalDate;

/**
 * Subclasse Ocasional, que representa um tipo de Utilizador.
 * Implementa o método abstrato para calcular o fator que participa no cálculo de calorias, que considera a frequência cardíaca do 
 * utilizador que realiza a atividade.
 */

public class Ocasional extends Utilizador {
    public Ocasional() {
        super();
    }

    public Ocasional(String email, String codigo, String nome, String morada, char genero, double altura, double peso, LocalDate dataNasc, double freqCardio, String tipo){
        super(email, codigo, nome, morada, genero, altura, peso, dataNasc, freqCardio, tipo);
    }

    @Override
    public double calcularFator() {
        return 0.8 * (getFreqCardio() / 25);
    }

    @Override
    public Ocasional clone() {
        return new Ocasional(getEmail(), getCodigo(), getNome(), getMorada(), getGenero(), getAltura(), getPeso(), getDataNasc(), getFreqCardio(), getTipo());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
