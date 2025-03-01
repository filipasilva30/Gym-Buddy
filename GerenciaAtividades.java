import java.util.HashMap;
import java.util.Map;

/**
 * Classe GerenciaAtividades que é responsável por gerir todas as atividades que já existem na aplicação,
 * facilitando a sua obtenção. Possui métodos que permitem a adição de atividades e a sua obtenção, tanto
 * de forma individual (getAtividade) ou na pluralidade(getAtividades).
 */

public class GerenciaAtividades {
    private Map<Long, Atividade> atividades;

    public GerenciaAtividades() {
        this.atividades = new HashMap<>();
    }

    public void adicionarAtividade(Atividade atividade) {
        this.atividades.put(atividade.getId(), atividade.clone());
    }
    
    public Atividade getAtividade(Long id) {
        if (!this.atividades.containsKey(id)) {
            return null;
        }
        return this.atividades.get(id).clone();
    }

    public Map<Long, Atividade> getAtividades() {
        Map<Long, Atividade> safeCopy = new HashMap<>();
        for (Map.Entry<Long, Atividade> entry : this.atividades.entrySet()) {
            safeCopy.put(entry.getKey(), entry.getValue().clone());
        }
        return safeCopy;
    }

    public void setAtividades(Map<Long, Atividade> novasAtividades) {
        this.atividades.clear();
        for (Map.Entry<Long, Atividade> entry : novasAtividades.entrySet()) {
            this.atividades.put(entry.getKey(), entry.getValue().clone());
        }
    }
}