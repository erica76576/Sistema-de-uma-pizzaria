import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pizzaria {

    // Listas
    private static List<Cliente> filaDeEspera = new ArrayList<>();
    private List<Produto> cardapio;
    private List<Mesa> mesas;
    private List<Funcionario> funcionarios;

    public Pizzaria(List<Produto> cardapio, List<Mesa> mesas, List<Funcionario> funcionarios) {
        this.cardapio = cardapio;
        this.mesas = mesas;
        this.funcionarios = funcionarios;
    }

    // Cria uma lista de mesas
    public static ArrayList<Mesa> Mesas() {
        ArrayList<Mesa> listaMesas = new ArrayList<>();
        listaMesas.add(new Mesa(1, 2, false));
        listaMesas.add(new Mesa(2, 2, false));
        listaMesas.add(new Mesa(3, 4, false));
        listaMesas.add(new Mesa(4, 4, false));
        listaMesas.add(new Mesa(5, 10, false));
        return listaMesas;
    }

    // Cria uma lista de produtos
    public static List<Produto> criarCardapio() {
        List<Produto> cardapio = new ArrayList<>();
        cardapio.add(new Produto(1, " Pizza 4 queijos", 100.00));
        cardapio.add(new Produto(2, "Pizza muçarela", 60.00));
        cardapio.add(new Produto(3, "Pizza portuguesa", 80.00));
        cardapio.add(new Produto(4, "Água com gás", 15.00));
        cardapio.add(new Produto(5, "Água sem gás", 15.00));
        cardapio.add(new Produto(6, "Suco de limão", 20.00));
        cardapio.add(new Produto(7, "Suco de laranja", 25.00));
        return cardapio;
    }

    // Cria uma lista de funcionários
    public static ArrayList<Funcionario> funcionarios() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Gerente("Elena"));
        funcionarios.add(new Atendente("João"));
        funcionarios.add(new Atendente("Maria"));
        funcionarios.add(new Atendente("Carlos"));
        funcionarios.add(new Caixa("Paula"));
        return funcionarios;
    }

    // Registra a chegada de um cliente
    public void registrarChegada(Scanner sc) {
        System.out.print("\nNome do cliente: ");
        String nome = sc.nextLine();

        System.out.print("Tamanho do grupo: ");
        int tamanho = sc.nextInt();
        sc.nextLine();

        Cliente novoCliente = new Cliente(nome, tamanho);
        filaDeEspera.add(novoCliente); // adiciona na fila de espera

        System.out
                .println("\nChegou cliente " + nome + " com um grupo de " + tamanho + " pessoas. Cliente registrado.");
    }

    // Alocar cliente em mesa escolhendo o grupo da fila
    public void alocarClienteEmMesa(Scanner sc) {
        if (filaDeEspera.isEmpty()) {
            System.out.println("Não há clientes na fila de espera.");
            return;
        }

        System.out.println("\nClientes na fila:");
        for (int i = 0; i < filaDeEspera.size(); i++) {
            Cliente c = filaDeEspera.get(i);
            System.out.println((i + 1) + ". " + c.getNome() + " (grupo de " + c.getTamanhoGrupo() + ")");
        }

        System.out.print("Escolha o número do cliente a ser alocado: ");
        int escolha = sc.nextInt();
        sc.nextLine();

        if (escolha < 1 || escolha > filaDeEspera.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        Cliente cliente = filaDeEspera.get(escolha - 1);

        for (Mesa mesa : mesas) {
            if (!mesa.isOcupada() &&
                    cliente.getTamanhoGrupo() <= mesa.getLugares() &&
                    cliente.getTamanhoGrupo() >= mesa.getLugares() / 2) {

                mesa.ocupar();
                mesa.setCliente(cliente);
                filaDeEspera.remove(cliente);
                System.out.println("Cliente " + cliente.getNome() + " alocado na mesa " + mesa.getNumero());
                return;
            }
        }

        System.out.println("Nenhuma mesa disponível para o grupo de " + cliente.getTamanhoGrupo() + " pessoas.");
    }

    // Criar um novo pedido
    public void criarNovoPedido(Mesa mesa, Scanner scanner) {
        Pedido pedido = new Pedido();
        boolean adicionarMais = true;

        while (adicionarMais) {
            exibirCardapio();

            System.out.print("\nDigite o código do produto: ");
            int codigo = scanner.nextInt();

            Produto produto = buscarProdutoPorCodigo(codigo);
            if (produto == null) {
                System.out.println("Produto não encontrado!");
                continue;
            }

            System.out.print("Digite a quantidade: ");
            int quantidade = scanner.nextInt();

            pedido.adicionarItem(new ItemPedido(produto, quantidade));

            System.out.print("Deseja adicionar outro item? (s/n): ");
            String opcao = scanner.next();
            adicionarMais = opcao.equalsIgnoreCase("s");
        }

        mesa.adicionarPedido(pedido); // adiciona o pedido a mesa
        System.out.println("Pedido registrado com sucesso!");
    }

    // Mostra o cardápio
    public void exibirCardapio() {
        System.out.println("\n===== CARDÁPIO =====");
        for (Produto p : cardapio) {
            System.out.println(p.getCodigo() + " - " + p.getNome() + " - R$ " + p.getPreco());
        }
    }

    private Produto buscarProdutoPorCodigo(int codigo) {
        for (Produto p : cardapio) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    // Mostra a coonta do cliente
   public void gerarConta(Mesa mesa) {
    if (mesa.getCliente() == null) {
        System.out.println("Mesa sem cliente associado.");
        return;
    }

    System.out.println("\n===== CONTA MESA " + mesa.getNumero() + " =====");
    System.out.println("Cliente: " + mesa.getCliente().getNome());

    double total = 0;
    for (Pedido pedido : mesa.getPedidos()) {
        for (ItemPedido item : pedido.getItens()) {
            double subtotal = item.getProduto().getPreco() * item.getQuantidade();
            String linha = item.getProduto().getNome() + " x " + item.getQuantidade() + " - R$ " + String.format("%.2f", subtotal);
            System.out.println(linha);
            total += subtotal;
        }
    }

    System.out.println("TOTAL: R$ " + String.format("%.2f", total));
}


    // Valida o gerente com senha
    private boolean validarGerente(Scanner sc) {
        System.out.print("Digite o nome do funcionário: ");
        String nome = sc.nextLine();

        for (Funcionario f : this.funcionarios) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                if (f instanceof Gerente) {
                    System.out.print("Digite a senha do gerente: ");
                    String senha = sc.nextLine();
                    if (senha.equals("1042")) {//senha
                        return true;
                    } else {
                        System.out.println("Senha incorreta.");
                        return false;
                    }
                } else {
                    System.out.println("Funcionário sem autorização para esta ação.");
                    return false;
                }
            }
        }

        System.out.println("Funcionário não encontrado.");
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Produto> cardapio = criarCardapio();
        ArrayList<Mesa> mesas = Mesas();
        ArrayList<Funcionario> funcionarios = funcionarios();
        Pizzaria pizzaria = new Pizzaria(cardapio, mesas, funcionarios);

        int escolha;

        do {
            System.out.println("\n--- Menu da Pizzaria ---");
            System.out.println("1. Registrar chegada de cliente e quantidade de lugares solicitados");
            System.out.println("2. Alocar cliente em mesa");
            System.out.println("3. Anotar pedido");
            System.out.println("4. Gerar conta");
            System.out.println("5. Gerar faturamento acumulado do dia");
            System.out.println("6. Listar funcionários");
            System.out.println("7. Encerrar");
            System.out.print("Escolha uma opção: ");

            escolha = sc.nextInt();
            sc.nextLine();

            switch (escolha) {
                case 1:
                    pizzaria.registrarChegada(sc);
                    break;

                case 2:
                    pizzaria.alocarClienteEmMesa(sc);
                    break;

                case 3:
                    System.out.print("\nDigite o número da mesa: ");
                    int numMesa = sc.nextInt();
                    sc.nextLine();

                    Mesa mesaEscolhida = null;
                    for (Mesa m : mesas) {
                        if (m.getNumero() == numMesa) {
                            mesaEscolhida = m;
                            break;
                        }
                    }

                    if (mesaEscolhida != null && mesaEscolhida.isOcupada()) {
                        pizzaria.criarNovoPedido(mesaEscolhida, sc);
                    } else {
                        System.out.println("Mesa inválida ou desocupada.");
                    }
                    break;

                case 4:
                    System.out.print("Digite o número da mesa: ");
                    int numConta = sc.nextInt();
                    sc.nextLine();

                    Mesa mesaConta = null;
                    for (Mesa m : mesas) {
                        if (m.getNumero() == numConta) {
                            mesaConta = m;
                            break;
                        }
                    }

                    if (mesaConta != null) {
                        pizzaria.gerarConta(mesaConta);
                    } else {
                        System.out.println("Mesa inválida.");
                    }
                    break;

                case 5:
                    System.out.println("\nAcesso restrito ao gerente.");
                    if (pizzaria.validarGerente(sc)) {

                        double faturamento = 0;
                        for (Mesa m : mesas) {
                            for (Pedido p : m.getPedidos()) {
                                for (ItemPedido item : p.getItens()) {
                                    faturamento += item.getProduto().getPreco() * item.getQuantidade();
                                }
                            }
                        }
                        System.out.printf("\nFaturamento total do dia: R$ %.2f\n", faturamento);
                    } else {
                        System.out.println("Acesso negado.");
                    }
                    break;

                case 6:
                    System.out.println("\nAcesso restrito ao gerente.");
                    if (pizzaria.validarGerente(sc)) {
                        System.out.println("\nLista de funcionários:");
                        for (Funcionario f : funcionarios) {
                            System.out.println(f);
                        }
                    } else {
                        System.out.println("Acesso negado.");
                    }
                    break;

                case 7:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (escolha != 7);

        sc.close();
    }
}
