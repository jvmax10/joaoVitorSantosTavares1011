package questao2;

import java.text.DecimalFormat;

interface PerfilCobranca {
    double calcularValor(int minutosPermanencia);
    String getNomePerfil();
}

class PerfilRotativo implements PerfilCobranca {
    private static final double VALOR_POR_HORA = 10.0;

    @Override
    public double calcularValor(int minutos) {
        if (minutos <= 0) return 0.0;
        int horasIniciadas = (int) Math.ceil(minutos / 60.0);
        return horasIniciadas * VALOR_POR_HORA;
    }
    @Override
    public String getNomePerfil() {
        return "Rotativo";
    }
}
class PerfilMensalista implements PerfilCobranca {
    private static final double VALOR_FIXO = 150.0;

    @Override
    public double calcularValor(int minutos) {
        return VALOR_FIXO;
    }
    @Override
    public String getNomePerfil() {
        return "Mensalista";
    }
}

class PerfilConveniado implements PerfilCobranca {
    private static final int MINUTOS_GRATUITOS = 30;
    private static final double VALOR_POR_HORA = 8.0;
    private static final double PERCENTUAL_DESCONTO = 0.20;

    @Override
    public double calcularValor(int minutos) {
        if (minutos <= MINUTOS_GRATUITOS) {
            return 0.0;
        }
        int minutosCobranca = minutos - MINUTOS_GRATUITOS;
        int horasIniciadas = (int) Math.ceil(minutosCobranca / 60.0);
        double valorBruto = horasIniciadas * VALOR_POR_HORA;
        return valorBruto * (1.0 - PERCENTUAL_DESCONTO);
    }

    @Override
    public String getNomePerfil() {
        return "Conveniado";
    }
}

class Tiquete {
    private String placa;
    private int tempoPermanenciaMinutos;
    private PerfilCobranca perfilCobranca;

    public Tiquete(String placa, int tempoPermanenciaMinutos, PerfilCobranca perfilInicial) {
        this.placa = placa;
        this.tempoPermanenciaMinutos = tempoPermanenciaMinutos;
        this.perfilCobranca = perfilInicial;
    }

    public void setPerfilCobranca(PerfilCobranca novoPerfil) {
        this.perfilCobranca = novoPerfil;
    }

    public double calcularTotal() {
        return perfilCobranca.calcularValor(tempoPermanenciaMinutos);
    }

    public void exibirDetalhes() {
        DecimalFormat df = new DecimalFormat("R$ #,##0.00");
        System.out.println("Veículo [Placa: " + placa + 
                           "] | Tempo: " + tempoPermanenciaMinutos + " min" +
                           " | Perfil: " + perfilCobranca.getNomePerfil() + 
                           " | Total: " + df.format(calcularTotal()));
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE TARIFA DE ESTACIONAMENTO ===\n");

        int tempoTeste = 95; 
        String placaVeiculo = "ABC-1234";

        System.out.println("---: Cálculo para o mesmo tempo (" + tempoTeste + " min) nos 3 perfis ---");
        
        Tiquete tiqueteRotativo = new Tiquete(placaVeiculo, tempoTeste, new PerfilRotativo());
        tiqueteRotativo.exibirDetalhes();

        Tiquete tiqueteMensalista = new Tiquete(placaVeiculo, tempoTeste, new PerfilMensalista());
        tiqueteMensalista.exibirDetalhes();

        Tiquete tiqueteConveniado = new Tiquete(placaVeiculo, tempoTeste, new PerfilConveniado());
        tiqueteConveniado.exibirDetalhes();

        System.out.println("\n---: Demonstração de troca de perfil em tempo de execução no mesmo tíquete ---");
        
        Tiquete tiqueteDinamico = new Tiquete("XYZ-9876", 120, new PerfilRotativo());
        
        System.out.println("Estado inicial:");
        tiqueteDinamico.exibirDetalhes();

        System.out.println(">> Trocando para perfil Conveniado...");
        tiqueteDinamico.setPerfilCobranca(new PerfilConveniado());
        tiqueteDinamico.exibirDetalhes();

        System.out.println(">> Trocando para perfil Mensalista...");
        tiqueteDinamico.setPerfilCobranca(new PerfilMensalista());
        tiqueteDinamico.exibirDetalhes();
    }
}
