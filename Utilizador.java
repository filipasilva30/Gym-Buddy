import java.time.LocalDate;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata Utilizador que serve de base para diferentes tipos de utilizadores.
 * Cada utilizador é definido por um email e outros atributos como código, nome, morada, género, altura, peso,
 * data de nascimento, frequência cardíaca em repouso e o tipo de utilizador. 
 * Possui, para além disso, associado a si uma lista de planos de treino e uma lista de atividades, existindo métodos
 * responsáveis pela adição de cada um destes à lista por parte do utilizador, bem como à obtenção de determinados planos
 * ou atividades já existentes (isto pelos métodos getPlanosUser e getAtividadesUser).
 * Existe também um método abstrato, que entra no cálculo de calorias, denominado "calculaFator" que determina o fator
 * multiplicativo tendo em conta o tipo de utilizador.
 */

public abstract class Utilizador {
    private String email;
    private String codigo;
    private String nome; 
    private String morada;
    private char genero;
    private double altura;
    private double peso;
    private LocalDate dataNasc;
    private double freqCardio;
    private String tipo;
    private List<PlanoDeTreino> planosdeTreino;
    private List<Atividade> atividades;
    

    public Utilizador() {
        this("", "","","",' ',0,0,LocalDate.now(),0, "");
        this.planosdeTreino = new ArrayList<>();
        this.atividades = new ArrayList<>();
    }

    public Utilizador(String email, String codigo, String nome, String morada, char genero, double altura, double peso, LocalDate dataNasc, double freqCardio, String tipo) {
        this.email = email;
        this.codigo = codigo;
        this.nome = nome;
        this.morada = morada;
        this.genero = genero;
        this.altura = altura;
        this.peso = peso;
        this.dataNasc = dataNasc;
        this.freqCardio = freqCardio;
        this.tipo = tipo;
        this.planosdeTreino = new ArrayList<>();
        this.atividades = new ArrayList<>();
    }

    public Utilizador (Utilizador u) {
        this.email = u.getEmail();
        this.codigo = u.getCodigo();
        this.nome = u.getNome();
        this.morada = u.getMorada();
        this.genero = u.getGenero();
        this.altura = u.getAltura();
        this.peso = u.getPeso();
        this.dataNasc = u.getDataNasc();
        this.freqCardio = u.getFreqCardio();
        this.tipo = getTipo();
        this.planosdeTreino = new ArrayList<>();
        Iterator<PlanoDeTreino> it = u.getPlanosDeTreinoIterator();
        while (it.hasNext()) {
            this.planosdeTreino.add(it.next());
        }
        Iterator<Atividade> iterador = u.getAtividadesIterator();
        while (iterador.hasNext()) {
            this.atividades.add(iterador.next());
        }
    }

    public Iterator<PlanoDeTreino> getPlanosDeTreinoIterator() {
        return new Iterator<PlanoDeTreino>() {
            private final Iterator<PlanoDeTreino> internalIterator = planosdeTreino.iterator();

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public PlanoDeTreino next() {
                return internalIterator.next().clone();
            }
        };
    }
    
    public Iterator<Atividade> getAtividadesIterator() {
        return new Iterator<Atividade>() {
            private final Iterator<Atividade> internalIterator = atividades.iterator();

            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Atividade next() {
                return internalIterator.next().clone();
            }
        };
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return this.morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
    
    public char getGenero() {
        return this.genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public double getAltura() {
        return this.altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return this.peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getDataNasc() {
        return this.dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public double getFreqCardio() {
        return this.freqCardio;
    }

    public void setFreqCardio(double freqCardio) {
        this.freqCardio = freqCardio;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void adicionarPlanoDeTreino(PlanoDeTreino plano) {
        this.planosdeTreino.add(plano.clone());
    }

    public void adicionarAtividade(Atividade atividade) {
        this.atividades.add(atividade.clone());
    }

    public List<Atividade> getAtividadesUser() {
        List<Atividade> aux = new ArrayList<>();
        for(Atividade atividade : this.atividades) {
            aux.add(atividade.clone());
        }
        return aux;
    }

    public List<PlanoDeTreino> getPlanosUser() {
        List<PlanoDeTreino> aux = new ArrayList<>();
        for(PlanoDeTreino plano : this.planosdeTreino) {
            aux.add(plano.clone());
        }
        return aux;
    }

    public abstract double calcularFator();
    
    public abstract Utilizador clone();
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Utilizador user = (Utilizador) o;
        return this.getEmail().equals(user.getEmail()) &&
        this.getCodigo().equals(user.getCodigo()) &&
        this.getNome().equals(user.getNome()) &&
        this.getGenero() == user.getGenero() &&
        this.getAltura() == user.getAltura() &&
        this.getPeso() == user.getPeso() &&
        this.getDataNasc().equals(user.getDataNasc()) &&
        Double.compare(user.getFreqCardio(), this.getFreqCardio()) == 0 &&
        this.getTipo().equals(user.getTipo()) && 
        this.getPlanosUser().equals(user.getPlanosUser()) &&
        this.getAtividadesUser().equals(user.getAtividadesUser()); 
    }

    @Override
    public String toString() {
        return "Atividade = {email=" + this.getEmail() +
        ", codigo=' " + this.getCodigo() + 
        ", nome=' " + this.getNome() + 
        ", morada=' " + this.getMorada() + 
        ", genero= " + this.getGenero() +
        ", altura= " + this.getAltura() +
        ", peso= " + this.getPeso() +
        ", dataNasc= " + this.getDataNasc() +
        ", freqCardio= " + this.getFreqCardio() +
        ", tipo= " + this.getTipo() +
        ", planos de treino= " + this.getPlanosUser().toString() +
        ", atividades= " + this.getAtividadesUser().toString() + 
        '}';
    }
}
