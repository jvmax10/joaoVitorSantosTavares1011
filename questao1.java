package questao1;

import java.util.ArrayList;
import java.util.List;

public class Questao1 {

    public interface Observer {
        void atualizar(String nomeProduto, int novaQuantidade);
    }
    public interface Subject {
        void adicionarObservador(Observer observer);
        void removerObservador(Observer observer);
        void notificarObservadores();
    }
    public static class Produto implements Subject {
        private String nome;
        private int quantidade;
        private List<Observer> observadores;

        public Produto(String nome, int quantidadeInicial) {
            this.nome = nome;
            this.quantidade = quantidadeInicial;
            this.observadores = new ArrayList<>();
        }
        public String getNome() {
            return nome;
        }
        public int getQuantidade() {
            return quantidade;
        }
        public void setQuantidade(int novaQuantidade) {
            if (this.quantidade != novaQuantidade) {
                this.quantidade = novaQuantidade;
                notificarObservadores();
            }
        }

        @Override
        public void adicionarObservador(Observer observer) {
            if (!observadores.contains(observer)) {
                observadores.add(observer);
            }
        }

        @Override
        public void removerObservador(Observer observer) {
            observadores.remove(observer);
        }

        @Override
        public void notificarObservadores() {
            for (Observer observer : observadores) {
                observer.atualizar(this.nome, this.quantidade);
            }
        }
    }

    public static class Compras implements Observer {
        private static final int limite_alerta = 5;

        @Override
        public void atualizar(String nomeProduto, int novaQuantidade) {
            if (novaQuantidade < limite_alerta) {
                System.out.println("[COMPRAS] ALERTA: O estoque do produto '" + nomeProduto + 
                                    "' ficou abaixo do limite seguro! Quantidade atual: " + novaQuantidade);
            }
        }
    }

    public static class Vendas implements Observer {
        private boolean estavaZerado = false;

        @Override
        public void atualizar(String nomeProduto, int novaQuantidade) {

            if (novaQuantidade == 0) {
                estavaZerado = true;
            } else if (estavaZerado && novaQuantidade > 0) {
                System.out.println("[VENDAS] NOTÍCIA: O produto '" + nomeProduto + 
                                    "' voltou a ter estoque após ter zerado! Disponível: " + novaQuantidade);
                estavaZerado = false;
            }
        }
    }

    public static class Auditoria implements Observer {
        @Override
        public void atualizar(String nomeProduto, int novaQuantidade) {
            System.out.println("[AUDITORIA] LOG: Mudança registrada para '" + nomeProduto + 
                                "'. Nova quantidade: " + novaQuantidade);
        }
    }

    public static void main(String[] args) {
        Produto notebook = new Produto("Notebook Gamer", 10);

       
        Observer compras = new Compras();
        Observer vendas = new Vendas();
        Observer auditoria = new Auditoria();

        notebook.adicionarObservador(compras);
        notebook.adicionarObservador(vendas);
        notebook.adicionarObservador(auditoria);

        System.out.println("=== ESTADO INICIAL: Estoque = 10 ===");
        
        System.out.println("\n--- Alteração 1: Quantidade vai para 4 (Abaixo de 5 -> Alerta de Compras) ---");
        notebook.setQuantidade(4); 

        System.out.println("\n--- Alteração 2: Quantidade vai para 0 (Estoque zera) ---");
        notebook.setQuantidade(0); 

        System.out.println("\n--- Alteração 3: Quantidade vai para 3 (Volta a ter estoque -> Alerta de Vendas) ---");
        notebook.setQuantidade(3); 

        System.out.println("\n--- Removendo a área de Auditoria em tempo de execução ---");
        notebook.removerObservador(auditoria);

        System.out.println("\n--- Alteração 4: Quantidade vai para 2 (Auditoria não deve registrar log) ---");
        notebook.setQuantidade(2); 
    }
}
