public class Cliente {
    private String nome;
    private int tamanhoGrupo;

    public Cliente(String nome, int tamanhoGrupo) {
        this.nome = nome;
        this.tamanhoGrupo = tamanhoGrupo;
    }

    public String getNome() {
        return nome;
    }

    public int getTamanhoGrupo() {
        return tamanhoGrupo;
    }

    public String toString() {
        return nome + " (Grupo: " + tamanhoGrupo + ")";
    }
}
