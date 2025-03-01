import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Classe Estatisticas que corresponde a critérios específicos cuja informação sobre eles se deseja obter.
 */

public class Estatisticas {
    private static Utilizadores users;
    private GerenciaAtividades atividades;

    public Estatisticas() {
        this.users = new Utilizadores();
        this.atividades = new GerenciaAtividades();
    }

    public String utilizadorComMaisCaloriasGastas(LocalDateTime inicio, LocalDateTime fim) {
        double maxCalorias = 0;
        String utilizadorMaxCalorias = "Nenhum utilizador encontrado";

        for (Utilizador user : this.users.getUsers().values()) {
            double totalCalorias = calcularCaloriasUtilizador(user, inicio,fim);
            if (totalCalorias > maxCalorias) {
                maxCalorias = totalCalorias;
                utilizadorMaxCalorias = user.getNome() + " com " + totalCalorias + " calorias gastas" + (inicio != null && fim != null ? " entre " + inicio + " e " + fim : "");
            }
        }

        return utilizadorMaxCalorias;
    }

    private double calcularCaloriasUtilizador(Utilizador user, LocalDateTime inicio, LocalDateTime fim) {
        double totalCalorias = 0.0;
        for (Atividade atividade : user.getAtividadesUser()) {
            if ((inicio == null || !atividade.getDataHoraInicio().isBefore(inicio)) &&
                (fim == null || !atividade.getDataHoraFim().isAfter(fim))) {
                totalCalorias += atividade.calcularCalorias(user);
            }
        }
        return totalCalorias;
    }

    public String utilizadorComMaisAtividades(LocalDateTime inicio, LocalDateTime fim) {
        int maxAtividades = 0;
        String utilizadorMaxAtividades = "Nenhum utilizador encontrado";
    
        for (Utilizador user : this.users.getUsers().values()) {
            int totalAtividades = contarAtividadesUtilizador(user, inicio, fim);
            if (totalAtividades > maxAtividades) {
                maxAtividades = totalAtividades;
                utilizadorMaxAtividades = user.getNome() + " com " + totalAtividades + " atividades realizadas" + (inicio != null && fim != null ? " entre " + inicio + " e " + fim : "");
            }
        }
    
        return utilizadorMaxAtividades;
    }
    
    private int contarAtividadesUtilizador(Utilizador user, LocalDateTime inicio, LocalDateTime fim) {
        int totalAtividades = 0;
        for (Atividade atividade : user.getAtividadesUser()) {
            if ((inicio == null || atividade.getDataHoraInicio().isAfter(inicio) || atividade.getDataHoraInicio().isEqual(inicio)) &&
                (fim == null || atividade.getDataHoraFim().isBefore(fim) || atividade.getDataHoraFim().isEqual(fim))) {
                totalAtividades++;
            }
        }
        return totalAtividades;
    }

    public String tipoAtividadeMaisRealizada() {
        Map<TipoAtividade, Integer> contagemTipos = new HashMap<>();
        for (Atividade atividade : this.atividades.getAtividades().values()) {
            TipoAtividade tipo = atividade.getTipo();
            contagemTipos.put(tipo, contagemTipos.getOrDefault(tipo, 0) + 1);
        }

        // Determinar o tipo de atividade mais frequente
        TipoAtividade tipoMaisFrequente = null;
        int maxCount = 0;
        for (Map.Entry<TipoAtividade, Integer> entry : contagemTipos.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                tipoMaisFrequente = entry.getKey();
            }
        }
        return tipoMaisFrequente == null ? "Nenhum tipo predominante" : "Tipo mais realizado: " + tipoMaisFrequente;
    }
    
    public double calcularKmUtilizador(String email, LocalDateTime inicio, LocalDateTime fim) {
        Utilizador user = this.users.getUtilizador(email);
        if (user == null) {
            System.out.println("Utilizador não encontrado.");
            return 0.0;
        }

        double distanciaTotal = 0.0;
        for (Atividade atividade : user.getAtividadesUser()) {
            if ((inicio == null || atividade.getDataHoraInicio().isAfter(inicio) || atividade.getDataHoraInicio().isEqual(inicio)) &&
                (fim == null || atividade.getDataHoraFim().isBefore(fim) || atividade.getDataHoraFim().isEqual(fim))) {
                distanciaTotal += atividade.getDistancia();
            }
        }
        return distanciaTotal;
    }

    public double calcularAltimetriaUtilizador (String email, LocalDateTime inicio, LocalDateTime fim) {
        Utilizador user = this.users.getUtilizador(email);
        if (user == null) {
            System.out.println("Utilizador não encontrado.");
            return 0.0;
        }

        double altimetriaTotal = 0.0;
        for (Atividade atividade : user.getAtividadesUser()) {
            if ((inicio == null || atividade.getDataHoraInicio().isAfter(inicio) || atividade.getDataHoraInicio().isEqual(inicio)) &&
                (fim == null || atividade.getDataHoraFim().isBefore(fim) || atividade.getDataHoraFim().isEqual(fim))) {
                altimetriaTotal += atividade.getAltimetria();
            }
        }
        return altimetriaTotal;
    }

    public String encontrarPlanoMaisExigente() {
        double maxCalorias = 0;
        PlanoDeTreino planoMaisExigente = null;
        Utilizador utilizador = null;

        for (Utilizador user : this.users.getUsers().values()) {
            for (PlanoDeTreino plano : user.getPlanosUser()) {
                double calorias = plano.calcularCaloriasTotal(user);
                if (calorias > maxCalorias) {
                    maxCalorias = calorias;
                    planoMaisExigente = plano;
                    utilizador = user;
                }
            }
        }

        if (planoMaisExigente != null && utilizador != null) {
            return "O plano de treino mais exigente é do utilizador " + utilizador.getNome() + ", com " + maxCalorias + " calorias, no dia " + planoMaisExigente.getData() + ".";
        } else {
            return "Nenhum plano de treino foi encontrado.";
        }
    }

    public static String listarAtividadesUtilizador(String email) {
        Utilizador user = users.getUtilizador(email);
        if (user == null) {
            return "Utilizador não encontrado.";
        }

        List<Atividade> atividades = user.getAtividadesUser();
        if (atividades.isEmpty()) {
            return "Não há atividades registadas para o utilizador " + user.getNome() + ".";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Atividades de ").append(user.getNome()).append(":\n");
        for (Atividade atividade : atividades) {
            sb.append(atividade.toString()).append("\n");
        }

        return sb.toString();
    }
}