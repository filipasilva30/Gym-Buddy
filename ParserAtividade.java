import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe ParserAtividade que contribui para a leitura dos dados relativos às atividades.
 */

public class ParserAtividade {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void parseCsvToAtividade(String filePath, GerenciaAtividades atividades) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] activityDetails = line.split(",");      
                TipoAtividade tipo;          
                try {
                    String tipoString = activityDetails[0].trim().toUpperCase();
                    tipo = TipoAtividade.valueOf(tipoString);

                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo de atividade inválido: " + activityDetails[0]);
                    continue; 
                }
                
                Long id = Long.parseLong(activityDetails[1]);
                String nome = activityDetails[2];
                double caloriasPorHora = Double.parseDouble(activityDetails[3]);
                LocalDateTime dataHoraInicio = LocalDateTime.parse(activityDetails[4], DATE_FORMAT);
                LocalDateTime dataHoraFim = LocalDateTime.parse(activityDetails[5], DATE_FORMAT);
                double duracao = Double.parseDouble(activityDetails[6]);

                Atividade atividade = null;

                switch (tipo) {
                    case DISTANCIA:
                        double distancia = Double.parseDouble(activityDetails[7]);
                        atividade = new Natacao(id, nome, caloriasPorHora, dataHoraInicio, dataHoraFim,duracao, distancia);
                        break;
                    case DISTANCIA_E_ALTIMETRIA:
                        distancia = Double.parseDouble(activityDetails[7]);
                        double altimetria = Double.parseDouble(activityDetails[8]);
                        atividade = new Corrida(id, nome, caloriasPorHora, dataHoraInicio, dataHoraFim, duracao, distancia, altimetria);
                        break;
                    case REPETICAO:
                        int repeticoes = Integer.parseInt(activityDetails[9]);
                        int sets = Integer.parseInt(activityDetails[10]);
                        atividade = new Abdominais(id, nome, caloriasPorHora, dataHoraInicio, dataHoraFim, duracao, repeticoes, sets);
                        break;
                    case REPETICAO_COM_PESO:
                        repeticoes = Integer.parseInt(activityDetails[9]);
                        sets = Integer.parseInt(activityDetails[10]);
                        double peso = Double.parseDouble(activityDetails[11]);
                        atividade = new AgachamentoPeso(id, nome, caloriasPorHora, dataHoraInicio, dataHoraFim, duracao, peso, repeticoes,sets);
                        break;
                }

                if (atividade != null) {
                    atividades.adicionarAtividade(atividade);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
