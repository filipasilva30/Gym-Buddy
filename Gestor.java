import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Gestor {
    private Utilizadores utilizadores;

    public Gestor() {
        this.utilizadores = new Utilizadores();
    } 

    public Atividade registarAtividade(Utilizador user, Long id, String nome, LocalDateTime dataHoraInicio,LocalDateTime dataHoraFim, double duracao, TipoAtividade tipo, double distancia, double altimetria, double peso, int nRep, int nSet) {
        Atividade atividade;
        
        if (tipo == TipoAtividade.DISTANCIA) {
            atividade = new Natacao();
            atividade.setId(id);
            atividade.setNome(nome);
            atividade.setDataHoraInicio(dataHoraInicio);
            atividade.setDataHoraFim(dataHoraFim);
            atividade.setDuracao(duracao);
            ((Natacao) atividade).setDistancia(distancia);
            double calorias = atividade.calcularCalorias(user);
            atividade.setCaloriasPorHora(calorias);
            user.adicionarAtividade(atividade.clone());
            return atividade;
        }
        else if (tipo == TipoAtividade.DISTANCIA_E_ALTIMETRIA) {
            atividade = new Corrida();
            atividade.setId(id);
            atividade.setNome(nome);
            atividade.setDataHoraInicio(dataHoraInicio);
            atividade.setDataHoraFim(dataHoraFim);
            atividade.setDuracao(duracao);
            ((Corrida) atividade).setDistancia(distancia);
            ((Corrida) atividade).setAltimetria(altimetria);
            double calorias = atividade.calcularCalorias(user);
            atividade.setCaloriasPorHora(calorias);
            user.adicionarAtividade(atividade.clone());
            return atividade;
        }
        else if (tipo == TipoAtividade.REPETICAO) {
            atividade = new Abdominais();
            atividade.setId(id);
            atividade.setNome(nome);
            atividade.setDataHoraInicio(dataHoraInicio);
            atividade.setDataHoraFim(dataHoraFim);
            atividade.setDuracao(duracao);
            ((Abdominais) atividade).setnRep(nRep);
            ((Abdominais) atividade).setnSet(nSet);
            double calorias = atividade.calcularCalorias(user);
            atividade.setCaloriasPorHora(calorias);
            user.adicionarAtividade(atividade.clone());
            return atividade;
        }
        else if (tipo == TipoAtividade.REPETICAO_COM_PESO) {
            atividade = new AgachamentoPeso();
            atividade.setId(id);
            atividade.setNome(nome);
            atividade.setDataHoraInicio(dataHoraInicio);
            atividade.setDataHoraFim(dataHoraFim);
            atividade.setDuracao(duracao);
            ((AgachamentoPeso) atividade).setPeso(peso);
            ((AgachamentoPeso) atividade).setnRep(nRep);
            ((AgachamentoPeso) atividade).setnSet(nSet);
            double calorias = atividade.calcularCalorias(user);
            atividade.setCaloriasPorHora(calorias);
            user.adicionarAtividade(atividade.clone());
            return atividade;
        }
        return null;
    }

    public PlanoDeTreino registarPlano (Utilizador user, LocalDate data, int numero, Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        PlanoDeTreino plano = new PlanoDeTreino();

        while(numero > 0) {
            System.out.print("Digite o tipo da atividade a ser registada: ");
            TipoAtividade tipo = TipoAtividade.valueOf(scanner.nextLine());
            System.out.print("Qual o id da atividade:");
            Long id = Long.parseLong(scanner.nextLine());
            System.out.print("Escreva o nome da atividade: ");
            String nome = scanner.nextLine();
            System.out.print("Digite a data e hora do início da atividade (formato dd/MM/yyyy HH:mm): ");
            LocalDateTime dateIn = LocalDateTime.parse(scanner.nextLine(), formatter);
            System.out.print("Digite a data e hora do início da atividade (formato dd/MM/yyyy HH:mm): ");
            LocalDateTime dateFim = LocalDateTime.parse(scanner.nextLine(), formatter);
            System.out.print("Digite a duração da atividade em horas: ");
            double duracao = Double.parseDouble(scanner.nextLine());
            double distancia = 0.0;
            double altimetria = 0.0;
            double peso = 0.0;
            int nRep = 0;
            int nSet = 0;

            if (tipo == TipoAtividade.DISTANCIA || tipo == TipoAtividade.DISTANCIA_E_ALTIMETRIA) {
                System.out.print("Número de metros percorridos: ");
                distancia = Double.parseDouble(scanner.nextLine());
            }
            if (tipo == TipoAtividade.DISTANCIA_E_ALTIMETRIA) {
                System.out.print("Altimetria em metros: ");
                altimetria = Double.parseDouble(scanner.nextLine());
            }
            if (tipo == TipoAtividade.REPETICAO || tipo == TipoAtividade.REPETICAO_COM_PESO) {
                System.out.print("Quantas repetições foram realizadas: ");
                nRep = Integer.parseInt(scanner.nextLine());
                System.out.print("Quantos sets foram realizados: ");
                nSet = Integer.parseInt(scanner.nextLine());
            }
            if (tipo == TipoAtividade.REPETICAO_COM_PESO) {
                System.out.print("Quanto peso em kg foi utilizado: ");
                peso = Double.parseDouble(scanner.nextLine());
            }
            Atividade atividade = registarAtividade(user, id, nome, dateIn, dateFim, duracao, tipo, distancia, altimetria, peso, nRep, nSet);
            plano.adicionarAtividade(atividade);
            numero--;
        }
        plano.setData(data);
        user.adicionarPlanoDeTreino(plano);
        return plano;
    }

    public Utilizador registarUtilizador(String email, String codigo, String nome, String morada, char genero, double altura, double peso, LocalDate dataNasc, double freqCardio, String tipo) {
        Utilizador novoUser;
        switch(tipo){
            case "Profissional":
                novoUser = new Profissional(email, codigo, nome, morada, genero, altura, peso, dataNasc, freqCardio, tipo);
                break;
            case "Amador":
                novoUser = new Amador(email, codigo, nome, morada, genero, altura, peso, dataNasc, freqCardio, tipo);
                break;
            case "Ocasional":
                novoUser = new Ocasional(email, codigo, nome, morada, genero, altura, peso, dataNasc, freqCardio, tipo);
                break;
            default:
            return null;  // Tipo inválido
        }
        return novoUser;
    }
}