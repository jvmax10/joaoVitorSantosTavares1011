package questao3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

interface ItemPedidoStrategy {
    String getDescricao();
    double getPreco();
}

class EspressoStrategy implements ItemPedidoStrategy {
    private static final double PRECO_ESPRESSO = 6.00;

    @Override
    public String getDescricao() {
        return "Espresso";
    }

    @Override
    public double getPreco() {
        return PRECO_ESPRESSO;
    }
}

class ChaStrategy implements ItemPedidoStrategy {
    private static final double PRECO_CHA = 5.00;

    @Override
    public String getDescricao() {
        return "Chá";
    }

    @Override
    public double getPreco() {
        return PRECO_CHA;
    }
}

class LeiteStrategy implements ItemPedidoStrategy {
    private static final double PRECO_LEITE = 2.00;

    @Override
    public String getDescricao() {
        return "Leite";
    }

    @Override
    public double getPreco() {
        return PRECO_LEITE;
    }
}

class ChantillyStrategy implements ItemPedidoStrategy {
    private static final double PRECO_CHANTILLY = 3.00;

    @Override
    public String getDescricao() {
        return "Chantilly";
    }

    @Override
    public double getPreco() {
        return PRECO_CHANTILLY;
    }
}

class CaldaStrategy implements ItemPedidoStrategy {
    private static final double PRECO_CALDA = 2.50;

    @Override
    public String getDescricao() {
        return "Calda";
    }

    @Override
    public double getPreco() {
        return PRECO_CALDA;
    }
}

class Pedido {
    private ItemPedidoStrategy bebidaBase;
    private List<ItemPedidoStrategy> complementos;

    public Pedido(ItemPedidoStrategy bebidaBase) {
        this.bebidaBase = bebidaBase;
        this.complementos = new ArrayList<>();
    }

    public void adicionarComplemento(ItemPedidoStrategy complemento) {
        this.complementos.add(complemento);
    }

    public String getDescricao() {
        StringBuilder sb = new StringBuilder(bebidaBase.getDescricao());
        for (ItemPedidoStrategy comp : complementos) {
            sb.append(" + ").append(comp.getDescricao());
        }
        return sb.toString();
    }

    public double getPrecoTotal() {
        double total = bebidaBase.getPreco();
        for (ItemPedidoStrategy comp : complementos) {
            total += comp.getPreco();
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("R$ #,##0.00");

        System.out.println("=== SISTEMA DE PEDIDOS DE CAFETERIA (Padrão Strategy) ===\n");

        Pedido pedido1 = new Pedido(new EspressoStrategy());
        pedido1.adicionarComplemento(new LeiteStrategy());

        Pedido pedido2 = new Pedido(new ChaStrategy());
        pedido2.adicionarComplemento(new CaldaStrategy());
        pedido2.adicionarComplemento(new ChantillyStrategy());

        Pedido pedido3 = new Pedido(new EspressoStrategy());
        pedido3.adicionarComplemento(new LeiteStrategy());
        pedido3.adicionarComplemento(new LeiteStrategy()); 
        pedido3.adicionarComplemento(new ChantillyStrategy());

        System.out.println("Pedido 1:");
        System.out.println("Descrição: " + pedido1.getDescricao());
        System.out.println("Preço Total: " + df.format(pedido1.getPrecoTotal()));
        System.out.println("--------------------------------------------------");

        System.out.println("Pedido 2:");
        System.out.println("Descrição: " + pedido2.getDescricao());
        System.out.println("Preço Total: " + df.format(pedido2.getPrecoTotal()));
        System.out.println("--------------------------------------------------");

        System.out.println("Pedido 3:");
        System.out.println("Descrição: " + pedido3.getDescricao());
        System.out.println("Preço Total: " + df.format(pedido3.getPrecoTotal()));
        System.out.println("--------------------------------------------------");
    }
}