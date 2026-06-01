package selectionSort;



import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    static final String PASTA_DADOS = "dados";
    static final String PASTA_SAIDA = "dados_ordenados";

    static int[] lerArquivo(String caminho) throws IOException {

        String conteudo = new String(Files.readAllBytes(Paths.get(caminho)));

        conteudo = conteudo.replaceAll("[\\[\\]]", "");

        String[] tokens = conteudo.trim().split("[\\s,]+");

        int[] arr = new int[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            arr[i] = Integer.parseInt(tokens[i].trim());
        }

        return arr;
    }

    static void escreverArquivo(String caminho, int[] arr) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {

            bw.write("[");

            for (int i = 0; i < arr.length; i++) {

                bw.write(String.valueOf(arr[i]));

                if (i < arr.length - 1) {
                    bw.write(", ");
                }
            }

            bw.write("]");
        }
    }

    static boolean estahOrdenado(int[] arr) {

        for (int i = 1; i < arr.length; i++) {

            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }

        return true;
    }

    static void imprimirVetor(int[] arr) {

        System.out.print("[");

        for (int i = 0; i < arr.length; i++) {

            System.out.print(arr[i]);

            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }

    static String nomeSaida(String nomeArquivo) {

        int dot = nomeArquivo.lastIndexOf('.');

        String base = (dot >= 0)
                ? nomeArquivo.substring(0, dot)
                : nomeArquivo;

        return PASTA_SAIDA
                + File.separator
                + base
                + "_ordenado_selectionsort.txt";
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("+=================================================+");
        System.out.println("|             SELECTION SORT                      |");
        System.out.println("+=================================================+");

        File dir = new File(PASTA_DADOS);

        if (!dir.exists() || !dir.isDirectory()) {

            System.out.println("Pasta '" + PASTA_DADOS + "' nao encontrada.");
            System.out.println("Execute o programa a partir da raiz do projeto.");

            return;
        }

        File[] arquivos = dir.listFiles(
                f -> f.isFile() && f.getName().endsWith(".txt")
        );

        if (arquivos == null || arquivos.length == 0) {

            System.out.println("Nenhum arquivo .txt encontrado em '" + PASTA_DADOS + "'.");

            return;
        }

        Arrays.sort(arquivos, Comparator.comparing(File::getName));

        System.out.println("\nArquivos disponiveis:");
        System.out.println("-------------------------------------------------");

        for (int i = 0; i < arquivos.length; i++) {
            System.out.printf("  [%d] %s%n", i + 1, arquivos[i].getName());
        }

        System.out.println("-------------------------------------------------");

        System.out.print("\nEscolha o numero do arquivo: ");

        int escolha = -1;

        while (escolha < 1 || escolha > arquivos.length) {

            try {

                escolha = Integer.parseInt(scanner.nextLine().trim());

                if (escolha < 1 || escolha > arquivos.length) {

                    System.out.printf(
                            "Digite um numero entre 1 e %d: ",
                            arquivos.length
                    );
                }

            } catch (NumberFormatException e) {

                System.out.printf(
                        "Entrada invalida. Digite um numero entre 1 e %d: ",
                        arquivos.length
                );
            }
        }

        File arquivoEscolhido = arquivos[escolha - 1];

        System.out.println("\n+=================================================+");

        System.out.printf(
                "|  Arquivo : %-35s  |%n",
                arquivoEscolhido.getName()
        );

        System.out.println("+=================================================+");

        int[] dados = lerArquivo(arquivoEscolhido.getPath());

        System.out.println("\nElementos : " + dados.length);

        System.out.print("Antes     : ");
        imprimirVetor(dados);

        SelectionSort ss = new SelectionSort(dados);

        Estatisticas est = ss.ordenar();

        System.out.print("\nOrdenado  : ");
        imprimirVetor(dados);

        System.out.println("\n-------------------------------------------------");
        System.out.println("                   METRICAS");
        System.out.println("-------------------------------------------------");

        System.out.printf("  Tempo          : %.3f ms%n", est.tempoMs);

        System.out.println("  Comparacoes    : " + est.comparacoes);
        System.out.println("  Trocas         : " + est.trocas);

        System.out.println(
                "  Correto?       : "
                        + (estahOrdenado(dados) ? "SIM" : "ERRO")
        );

        System.out.println("-------------------------------------------------");

        new File(PASTA_SAIDA).mkdirs();

        String caminhoSaida = nomeSaida(arquivoEscolhido.getName());

        escreverArquivo(caminhoSaida, dados);

        System.out.println("  Salvo em       : " + caminhoSaida);

        System.out.println("+=================================================+");

        scanner.close();
    }
}