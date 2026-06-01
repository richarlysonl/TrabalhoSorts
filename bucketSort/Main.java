package bucketSort;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    
    static double[] lerArquivo(String caminho) throws IOException {
        String conteudo = new String(Files.readAllBytes(Paths.get(caminho)));

        // Remove colchetes caso existam: [-935, 12562, ...]
        conteudo = conteudo.replaceAll("[\\[\\]]", "");
        // Divide por vírgula, espaço, tab ou quebra de linha
        String[] tokens = conteudo.trim().split("[\\s,]+");

        List<Double> numeros = new ArrayList<>();
        for (String token : tokens) {
            token = token.trim();
            if (!token.isEmpty()) {
                numeros.add(Double.parseDouble(token));
            }
        }

        double[] arr = new double[numeros.size()];
        for (int i = 0; i < arr.length; i++) arr[i] = numeros.get(i);
        return arr;
    }

    static void escreverArquivo(String caminho, double[] arr) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write("[");
            for (int i = 0; i < arr.length; i++) {
                long intVal = (long) arr[i];
                bw.write(arr[i] == intVal ? String.valueOf(intVal) : String.valueOf(arr[i]));
                if (i < arr.length - 1) bw.write(", ");
            }
            bw.write("]");
        }
    }
    static boolean estahOrdenado(double[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[i - 1]) return false;
        return true;
    }
    static String preview(double[] arr, int n) {
        int qtd = Math.min(arr.length, n);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < qtd; i++) {
            long v = (long) arr[i];
            sb.append(arr[i] == v ? v : arr[i]);
            if (i < qtd - 1) sb.append(", ");
        }
        if (arr.length > n) sb.append(", ...");
        return sb.append("]").toString();
    }
    static String nomeSaida(String nomeEntrada) {
        int dot = nomeEntrada.lastIndexOf('.');
        return (dot >= 0 ? nomeEntrada.substring(0, dot) : nomeEntrada) + "_ordenado.txt";
    }
    static boolean testar(String arquivo) {
        System.out.println("\n+--------------------------------------------------+");
        System.out.printf ("|  Arquivo : %-38s|%n", arquivo);
        System.out.println("+--------------------------------------------------+");

        try {
            double[] dados = lerArquivo(arquivo);
            int n = dados.length;

            double min = dados[0], max = dados[0];
            for (double v : dados) { if (v < min) min = v; if (v > max) max = v; }

            System.out.printf("  Elementos : %d%n", n);
            System.out.printf("  Intervalo : [%.0f, %.0f]%n", min, max);
            System.out.println("  Antes     : " + preview(dados, 8));

            long inicio = System.nanoTime();
            BucketSort.Estatisticas est = BucketSort.sortDoubles(dados);
            long fim    = System.nanoTime();

            System.out.println("  Depois    : " + preview(dados, 8));
            System.out.printf ("  Tempo     : %.3f ms%n", (fim - inicio) / 1_000_000.0);
            System.out.printf("  Tempo         : %.3f ms%n", est.tempoMs);
            System.out.printf("  Comparacoes   : %d%n", est.comparacoes);
            System.out.printf("  Trocas        : %d%n", est.trocas);
            boolean ok = estahOrdenado(dados);
            System.out.println("  Ordenado? : " + (ok ? "OK - SIM" : "ERRO - NAO!"));

            String saida = nomeSaida(arquivo);
            escreverArquivo(saida, dados);
            System.out.println("  Salvo em  : " + saida);

            return ok;

        } catch (Exception e) {
            System.err.println("  ERRO ao processar: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("+=================================================+");
        System.out.println("|        BUCKET SORT - TESTE DE ARQUIVOS          |");
        System.out.println("+=================================================+");

        List<String> arquivos = new ArrayList<>();
        if (args.length > 0) {
            arquivos.addAll(Arrays.asList(args));
        } else {
            File dir = new File(".");
            File[] txts = dir.listFiles(f ->
                f.isFile()
                && f.getName().endsWith(".txt")
                && !f.getName().endsWith("_ordenado.txt")
            );
            if (txts != null) {
                Arrays.sort(txts, Comparator.comparing(File::getName));
                for (File f : txts) arquivos.add(f.getName());
            }
        }

        if (arquivos.isEmpty()) {
            System.out.println("\nNenhum arquivo .txt encontrado.");
            System.out.println("Uso: java Main dados1.txt dados2.txt ...");
            return;
        }

        System.out.printf("%nArquivos a processar: %d%n", arquivos.size());

        int ok = 0, erro = 0;
        long totalInicio = System.nanoTime();

        for (String arq : arquivos) {
            if (testar(arq)) ok++; else erro++;
        }

        long totalFim = System.nanoTime();

        System.out.println("\n+=================================================+");
        System.out.printf ("|  RESUMO: %d/%d corretos  |  Total: %.1f ms%n",
                ok, arquivos.size(), (totalFim - totalInicio) / 1_000_000.0);
        if (erro > 0)
            System.out.printf("|  ATENCAO: %d arquivo(s) com erro%n", erro);
        System.out.println("+=================================================+");
    }
}