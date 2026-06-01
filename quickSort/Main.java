package quickSort;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    static int[] lerArquivo(String caminho) throws IOException {

        String conteudo = new String(Files.readAllBytes(Paths.get(caminho)));

        conteudo = conteudo.replaceAll("[\\[\\]]", "");

        String[] tokens = conteudo.trim().split("[\\s,]+");

        int[] arr = new int[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }

        return arr;
    }

    static boolean estahOrdenado(int[] arr) {

        for (int i = 1; i < arr.length; i++) {

            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }

        return true;
    }

    static String preview(int[] arr, int n) {

        int qtd = Math.min(arr.length, n);

        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < qtd; i++) {

            sb.append(arr[i]);

            if (i < qtd - 1) {
                sb.append(", ");
            }
        }

        if (arr.length > n) {
            sb.append(", ...");
        }

        sb.append("]");

        return sb.toString();
    }

    static String nomeSaida(String nomeEntrada) {

        int dot = nomeEntrada.lastIndexOf('.');

        return (dot >= 0
                ? nomeEntrada.substring(0, dot)
                : nomeEntrada)
                + "_quicksort.txt";
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

    static boolean testar(String arquivo) {

        System.out.println("\n+--------------------------------------------------+");
        System.out.printf("| Arquivo : %-39s|%n", arquivo);
        System.out.println("+--------------------------------------------------+");

        try {

            int[] dados = lerArquivo(arquivo);

            System.out.println("Elementos     : " + dados.length);

            System.out.println("Antes         : " + preview(dados, 8));

            QuickSort quick = new QuickSort(dados);

            Estatisticas est = quick.ordenar();

            System.out.println("Depois        : " + preview(dados, 8));

            System.out.printf("Tempo         : %.3f ms%n", est.tempoMs);

            System.out.println("Comparacoes   : " + est.comparacoes);

            System.out.println("Trocas        : " + est.trocas);

            boolean ok = estahOrdenado(dados);

            System.out.println("Ordenado?     : " + (ok ? "OK - SIM" : "ERRO"));

            String saida = nomeSaida(arquivo);

            escreverArquivo(saida, dados);

            System.out.println("Salvo em      : " + saida);

            return ok;

        } catch (Exception e) {

            System.out.println("ERRO: " + e.getMessage());

            return false;
        }
    }

    public static void main(String[] args) {

        System.out.println("+=================================================+");
        System.out.println("|            QUICK SORT - TESTE                  |");
        System.out.println("+=================================================+");

        File dir = new File(".");

        File[] arquivos = dir.listFiles(f ->
                f.isFile()
                        && f.getName().endsWith(".txt")
                        && !f.getName().contains("_quicksort")
        );

        if (arquivos == null || arquivos.length == 0) {

            System.out.println("Nenhum arquivo encontrado.");

            return;
        }

        Arrays.sort(arquivos, Comparator.comparing(File::getName));

        int ok = 0;

        long inicioTotal = System.nanoTime();

        for (File arq : arquivos) {

            if (testar(arq.getName())) {
                ok++;
            }
        }

        long fimTotal = System.nanoTime();

        System.out.println("\n+=================================================+");

        System.out.printf("| Arquivos corretos : %d/%d%n", ok, arquivos.length);

        System.out.printf("| Tempo total       : %.3f ms%n",
                (fimTotal - inicioTotal) / 1_000_000.0);

        System.out.println("+=================================================+");
    }
}