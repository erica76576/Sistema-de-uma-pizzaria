import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private int numero;
    private int lugares;
    private boolean ocupada;
    private List<Pedido> pedidos;
    private Cliente cliente;  

    public Mesa(int numero, int lugares, boolean ocupada) {
        this.numero = numero;
        this.lugares = lugares;
        this.ocupada = ocupada;
        this.pedidos = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getLugares() {
        return lugares;
    }

    public void setLugares(int lugares) {
        this.lugares = lugares;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    // Verifica se a mesa está disponível
    public boolean estaDisponivel() {
        return !ocupada;
    }

    // Ocupa a mesa
    public void ocupar() {
        this.ocupada = true;
    }

    // Desocupa a mesa
    public void desocupar() {
        this.ocupada = false;
        this.cliente = null;
        this.pedidos.clear();
    }

    // Adiciona um pedido à mesa
    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public String toString() {
        return "Mesa " + numero + " - Lugares: " + lugares + (ocupada ? " (Ocupada)" : " (Livre)");
    }
}
