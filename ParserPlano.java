import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ParserPlano que contribui para a leitura dos dados relativos aos planos.
 */

public class ParserPlano {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void parseCsvToPlanosDeTreino(String filePath, GerenciaPlanos gerenciaPlanos, GerenciaAtividades gerenciaAtividades) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] planoDetails = line.split(",");
                Long id = Long.parseLong(planoDetails[0]);
                LocalDate data = LocalDate.parse(planoDetails[1], DATE_FORMAT);
                String[] idsAtividades = planoDetails[2].split("\\|");

                List<Atividade> atividades = new ArrayList<>();
                for (String idAtiv : idsAtividades) {
                    Atividade atividade = gerenciaAtividades.getAtividade(Long.parseLong(idAtiv));
                    if (atividade != null) {
                        atividades.add(atividade);
                    }
                }

                PlanoDeTreino novoPlano = new PlanoDeTreino(id, data, atividades);
                gerenciaPlanos.adicionarPlano(novoPlano);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
