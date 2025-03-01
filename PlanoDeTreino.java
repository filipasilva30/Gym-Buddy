import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe PlanoDeTreino que é definida com um id, uma data e uma lista de atividades que o constituem.
 * Tem também métodos associados à adição de atividades e à sua obtenção ("adicionarAtividade", "getAtividadesPlano" respetivamente),
 * e um método que permite calcular as calorias dispensadas num determinado plano.
*/

public class PlanoDeTreino {
    private Long id;
    private LocalDate data;
    private List<Atividade> atividades; 

    public PlanoDeTreino() {
        this.id = 0L;
        this.data = LocalDate.now();
        this.atividades = new ArrayList<>();
    }

    public PlanoDeTreino(Long id,LocalDate data, List<Atividade> atividades) {
        this.id = id;
        this.data = data;
        this.atividades = new ArrayList<>();
        for (Atividade atividade : atividades) {
            this.atividades.add(atividade.clone());
        }
    } 

    public PlanoDeTreino (PlanoDeTreino plano) {
        this.id = plano.getId();
        this.data = plano.getData();
        this.atividades = new ArrayList<>();
        Iterator<Atividade> it = plano.getAtividadesIterator();
        while (it.hasNext()) {
            this.atividades.add(it.next());
        }        
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

    public void adicionarAtividade(Atividade atividade) {
        this.atividades.add(atividade.clone());
    }

    public List<Atividade> getAtividadesPlano() {
        List<Atividade> aux = new ArrayList<>();
        for(Atividade atividade : this.atividades) {
            aux.add(atividade.clone());
        }
        return aux;
    }

    public double calcularCaloriasTotal(Utilizador user) {
        double totalCalorias = 0.0;
        for (Atividade atividade : this.atividades) {
            totalCalorias += atividade.calcularCalorias(user);
        }
        return totalCalorias;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PlanoDeTreino{data=" + this.getData() + ", atividades=" + this.getAtividadesPlano().toString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanoDeTreino p = (PlanoDeTreino) o;
        return this.getData().equals(p.getData()) && this.getAtividadesPlano().equals(p.getAtividadesPlano());
    }

    @Override
    public PlanoDeTreino clone() {
        return new PlanoDeTreino(this);
    }
}
