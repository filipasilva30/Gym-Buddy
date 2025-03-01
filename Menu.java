import java.util.Scanner;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Classe Menu que é responsável pela parte gráfica da aplicação, dividindo-se em pequenos menus, como de utilizador
 * ou estatísticas que contribuem para uma melhor organização da aplicação e proporcionam uma parte estética mais atrativa.
 */

public class Menu {
    private Utilizadores users;
    private GerenciaAtividades atividades;
    private GerenciaPlanos planos;
    private LocalDate data;
    private Utilizador userLogged;
    private Gestor gestor;
    private Estatisticas stats;
    private Scanner scanner;

    public Menu() {
        this.users = new Utilizadores();
        this.atividades = new GerenciaAtividades();
        this.planos = new GerenciaPlanos();
        this.data = LocalDate.now();
        this.gestor = new Gestor();
        this.stats = new Estatisticas();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        ParserUtilizador.parseCsvToUtilizadores("recursos/utilizadores.csv", users);
        ParserAtividade.parseCsvToAtividade("recursos/atividades.csv", atividades);
        ParserPlano.parseCsvToPlanosDeTreino("recursos/planos.csv", planos, atividades);
        
        clear();
        Visual.nome();

        String escolha;
        do {        

            System.out.println("\nMenu Principal");
            System.out.println("1. Registar Utilizador");
            System.out.println("2. Iniciar sessão");
            System.out.println("3. Ver Todos os Utilizadores");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextLine();
            clear();

        switch (escolha) { 
            case "1":
                System.out.print("Escreva o seu email: ");
                String email = scanner.nextLine();

                if (users.getUtilizador(email) != null) {
                    System.out.println("Já existe um utilizador registado com este email");
                    break;
                }

                String tipo = escolherTipoUtilizador(scanner);
                if(tipo == null) {
                    return;
                } 

                System.out.print("Escreva o seu código: ");
                String codigo = scanner.nextLine();
                System.out.print("Escreva o seu nome: ");
                String nome = scanner.nextLine();
                System.out.print("Escreva a sua morada: ");
                String morada = scanner.nextLine();
                System.out.print("Coloque o seu género(M/F): ");
                char genero = scanner.nextLine().charAt(0);
                System.out.print("Escreva a sua altura(em m): ");
                double altura = Double.parseDouble(scanner.nextLine());
                System.out.print("Escreva o seu peso(em kilogramas): ");
                double peso = Double.parseDouble(scanner.nextLine());
                System.out.print("Data de nascimento(YYYY-MM-DD): ");
                LocalDate dataNasc = LocalDate.parse(scanner.nextLine());
                System.out.print("Escreva a sua frequência cardíaca em repouso: ");
                double freqCardio = Double.parseDouble(scanner.nextLine());

                Utilizador novoUser = gestor.registarUtilizador(email,codigo,nome,morada,genero,altura,peso,dataNasc,freqCardio,tipo);
                if (novoUser != null) {
                    users.setUtilizador(novoUser);
                    System.out.println("Utilizador registado com sucesso!");
                } else {
                    System.out.println("Erro ao criar utilizador!"); 
                };
                break;
            case "2":
                int option = iniciarSessao(scanner);
                if (option == 1) menuUtilizador(scanner);
                break;

            case "3":
                verUtilizadores();
                break;

            case "4":
                System.out.println("A sair...");
                scanner.close();
                break;

            default:
                System.out.println("Opção inválida, tente novamente.");
            }
        } while (!escolha.equals("4"));
    }

    private String escolherTipoUtilizador(Scanner scanner) {
        System.out.println("Escolha o tipo de utilizador:");
        System.out.println("1 - Profissional");
        System.out.println("2 - Amador");
        System.out.println("3 - Ocasional");
        System.out.print("Opção: ");
        char opcao = scanner.nextLine().charAt(0);

        switch (opcao) {
            case '1':
                return "Profissional";
            case '2':
                return "Amador";
            case '3':
                return "Ocasional";
            default:
                System.out.println("Opção inválida");
                return null;
        }
    }

    private int iniciarSessao(Scanner scanner) {
        int flag = 0;
        System.out.print("Escreva o seu email para login: ");
        String email = scanner.nextLine();

        userLogged = users.getUtilizador(email);
        if (userLogged != null) {
            System.out.println("Bem-vindo(a), " + userLogged.getNome() + "!");
            flag = 1;
        } else {
        System.out.println("Utilizador não encontrado. Verifique o e-mail e tente novamente.");
        }

        return flag;
    }

    private void menuUtilizador(Scanner scanner) {
        String escolha;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter form = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        do {
            System.out.println("\nMenu Utilizador");
            System.out.println(data);
            System.out.println("1. Registar Atividade");
            System.out.println("2. Registar Plano de Treino");
            System.out.println("3. Ver Atividades");
            System.out.println("4. Ver Planos de Treino");
            System.out.println("5. Estatísticas");
            System.out.println("6. Gerir tempo");
            System.out.println("7. Ver perfil");
            System.out.println("8. Terminar sessão");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextLine();
            clear();

        switch (escolha) { 
            case "1":
                TipoAtividade tipo = escolherTipoAtividade(scanner);
                if(tipo == null) {
                    return;
                } 

                System.out.print("Qual o id da atividade:");
                Long id = Long.parseLong(scanner.nextLine());
                System.out.print("Escreva o nome da atividade: ");
                String nome = scanner.nextLine();
                System.out.print("Qual a data e hora do início da atividade (formato dd/MM/yyyy HH:mm): ");
                LocalDateTime dataHoraInicio = LocalDateTime.parse(scanner.nextLine(), formatter);
                System.out.print("Qual a data e hora de fim da atividade (formato dd/MM/yyyy HH:mm): ");
                LocalDateTime dataHoraFim = LocalDateTime.parse(scanner.nextLine(), formatter);
                System.out.print("Qual a duração da atividade em horas: ");
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

                if (gestor.registarAtividade(userLogged, id, nome, dataHoraInicio, dataHoraFim, duracao, tipo, distancia, altimetria, peso, nRep, nSet) != null) {
                    System.out.println("Atividade registada com sucesso!");
                } else {
                    System.out.println("Erro ao registar a atividade.");
                }
                break;

            case "2":
                System.out.print("Qual a data do plano (formato dd/MM/yyyy): ");
                LocalDate dataPlano = LocalDate.parse(scanner.nextLine(),form);
                System.out.print ("Qual o número de atividades que deseja registar: ");
                int numero = Integer.parseInt(scanner.nextLine());
                if (gestor.registarPlano(userLogged, dataPlano, numero, scanner) != null)  {
                    System.out.println("Plano registado com sucesso!");
                } else {
                    System.out.println("Erro ao registar o plano.");
                }
                break;

            case "3":
                verAtividades();
                break;

            case "4":
                verPlanos();
                break;

            case "5":
                menuEstatisticas(scanner);
                break;

            case "6": 
                clear();
                data = tempo(scanner);
                break;

            case "7":
                verPerfil();
                break;

            case "8":
                System.out.println("Terminar sessão");
                clear();
                break;
                
            default:
                System.out.println("Opção inválida, tente novamente.");
            }
        } while (!escolha.equals("8"));
    }
        
    private TipoAtividade escolherTipoAtividade(Scanner scanner) {
        System.out.println("Escolha o tipo de atividade a ser registada:");
        System.out.println("1 - DISTANCIA");
        System.out.println("2 - DISTANCIA_E_ALTIMETRIA");
        System.out.println("3 - REPETICAO");
        System.out.println("3 - REPETICAO_COM_PESO");
        System.out.print("Opção: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                return TipoAtividade.DISTANCIA;
            case "2":
                return TipoAtividade.DISTANCIA_E_ALTIMETRIA;
            case "3":
                return TipoAtividade.REPETICAO;
            case "4":
                return TipoAtividade.REPETICAO_COM_PESO;
            default:
                System.out.println("Opção inválida");
                return null;
        }
    }

    private void menuEstatisticas(Scanner scanner) {
        String escolha;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        do {
            System.out.println("\nMenu de Estatísticas");
            System.out.println("1. Utilizador com mais calorias gastas");
            System.out.println("2. Utilizador com mais atividades");
            System.out.println("3. Tipo de atividade mais realizada");
            System.out.println("4. Quantos km foram percorridos por um utilizador");
            System.out.println("5. Quantos m de altimetria totalizou um utilizador");
            System.out.println("6. Qual o plano de treino mais exigente");
            System.out.println("7. Lista de atividades de um utilizador");
            System.out.println("8. Voltar");
            System.out.print("Escolha uma opção: ");
            escolha = scanner.nextLine();
            clear();

            try {
                switch(escolha) {
                    case "1":
                        System.out.print("Qual a data de inicio que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime inicio = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.print("Qual a data de fim que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime fim = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.print("O utilizador com mais calorias gastas é: " + stats.utilizadorComMaisCaloriasGastas(inicio,fim));
                        break;    
                    
                    case "2":
                        System.out.print("Qual a data de inicio que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime datainicio = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.print("Qual a data de fim que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime datafim = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.println("O utilizador com mais atividades realizadas é: " + stats.utilizadorComMaisAtividades(datainicio,datafim));
                        break;

                    case "3":
                        System.out.println(stats.tipoAtividadeMaisRealizada());
                        break;

                    case "4":
                        System.out.print("Digite o email do utilizador: ");
                        String mail = scanner.nextLine();
                        if (users.getUtilizador(mail) == null) {
                            System.out.println("Não existe nenhum utilizador com esse email.");
                            break;
                        }
                        System.out.print("Qual a data de inicio que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime datein = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.print("Qual a data de fim que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime datefim = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.println("O utilizador: " + users.getUtilizador(mail) + "já percorreu " + stats.calcularKmUtilizador(mail, datein, datefim) + "km");
                        break;

                    case "5":
                        System.out.print("Digite o email do utilizador: ");
                        String userEmail = scanner.nextLine();
                        if (users.getUtilizador(userEmail) == null) {
                            System.out.println("Não existe nenhum utilizador com esse email.");
                            break;
                        }
                        System.out.print("Qual a data de inicio que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime dataIn = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.print("Qual a data de fim que pretende ver (formato dd/MM/yyyy HH:mm): ");
                        LocalDateTime dataFim = LocalDateTime.parse(scanner.nextLine(),formatter);
                        System.out.println("O utilizador: " + users.getUtilizador(userEmail) + "tem um total de " + stats.calcularAltimetriaUtilizador(userEmail, dataIn, dataFim) + "metros de altimetria");
                        break;
                    
                    case "6":
                        System.out.print(stats.encontrarPlanoMaisExigente());
                        break;

                    case "7":
                        System.out.print("Digite o email do utilizador: ");
                        String userMail = scanner.nextLine();
                        System.out.println(Estatisticas.listarAtividadesUtilizador(userMail));
                        break;

                    case "8":
                        System.out.print("A voltar");
                        break;

                    default:
                    System.out.println("Opção inválida, tente novamente.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido.");
            }     
        }   while (!escolha.equals("8"));
    }

    private  void verAtividades() {
        List<Atividade> atividades = userLogged.getAtividadesUser();
        if(atividades == null || atividades.isEmpty()) {
            System.out.println("Não existem atividades registadas.");
        } else {
            System.out.println(userLogged.getAtividadesUser().toString());
        }
    }

    private void verPlanos() {
        List<PlanoDeTreino> planos = userLogged.getPlanosUser();
        if (planos == null || planos.isEmpty()) {
            System.out.println("Não há planos de treino registados.");
        } else {
            System.out.print (userLogged.getPlanosUser().toString());
        }
    }

    private void verUtilizadores() {
        Map<String, Utilizador> todos = users.getUsers();
        if (todos.isEmpty()) {
            System.out.println("Não há utilizadores registados.");
        } else {
            System.out.println("Lista de todos os utilizadores:");
            for (Utilizador user : todos.values()) {
                System.out.println("Email: " + user.getEmail() + " - Nome: " + user.getNome());
            }
        }
    }
    
    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private LocalDate tempo(Scanner scanner) {
        String data = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate novaData = LocalDate.parse(data,formatter);
        return novaData;
    }

    private void verPerfil() {
        System.out.println("Email: " + userLogged.getEmail());
        System.out.println("Código: " + userLogged.getCodigo());
        System.out.println("Nome: " + userLogged.getNome());
        System.out.println("Morada: " + userLogged.getMorada());
        System.out.println("Género: " + userLogged.getGenero());
        System.out.println("Altura: " + userLogged.getAltura());
        System.out.println("Peso: " + userLogged.getPeso());
        System.out.println("Data de nascimento: " + userLogged.getDataNasc());
        System.out.println("Frequência cardíaca em repouso: " + userLogged.getFreqCardio());
        System.out.println("Tipo de utilizador: " + userLogged.getTipo());
    }
}