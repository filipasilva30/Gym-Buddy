import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe Parserutilizador que contribui para a leitura dos dados relativos aos utilizadores..
 */

public class ParserUtilizador {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void parseCsvToUtilizadores(String filePath, Utilizadores utilizadores) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                String tipo = userDetails[0];
                String email = userDetails[1];
                String codigo = userDetails[2];
                String nome = userDetails[3];
                String morada = userDetails[4];
                char genero = userDetails[5].charAt(0);
                double altura = Double.parseDouble(userDetails[6]);
                double peso = Double.parseDouble(userDetails[7]);
                LocalDate dataNasc = LocalDate.parse(userDetails[8], DATE_FORMAT);
                double freqCardio = Double.parseDouble(userDetails[9]);

                Utilizador user = null;
                switch (tipo) {
                    case "Profissional":
                        user = new Profissional(email, codigo, nome, morada, genero, altura, peso, dataNasc, freqCardio, tipo);
                        break;
                    case "Ocasional":
                        user = new Ocasional(email, codigo, nome, morada, genero, altura, peso, dataNasc, freqCardio, tipo);
                        break;
                    case "Amador":
                        user = new Amador(email, codigo, nome, morada, genero, altura, peso, dataNasc, freqCardio, tipo);
                        break;
                }

                if (user != null) {
                    utilizadores.setUtilizador(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
