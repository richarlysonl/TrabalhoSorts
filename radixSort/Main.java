package radixSort;


import java.io.*;
import java.nio.file.*;
import java.util.*;
/**
 * Main para testar o RadixSort com exibição de estatísticas.
 *
 * Lê arquivos da pasta:   dados/
 * Salva resultados em:    dados_ordenados_RadixSort/
 *
 * Formato suportado:
 *   [-935, 12562, ...]   (colchetes + vírgulas)
 *   Um número por linha
 *   Números separados por espaço ou vírgula
 *
 * Uso (VS Code — botão Run) ou terminal:
 *   javac -d . radixSort/Estatisticas.java radixSort/RadixSort.java radixSort/Main.java
 *   java radixSort.Main
 */
public class Main {

    static final String PASTA_ENTRADA = "dados";
    static final String PASTA_SAIDA   = "dados_ordenados_RadixSort";

    // ─── Leitura ──────────────────────────────────────────────────────────

    static int[] lerArquivo(Path caminho) throws IOException {
        String conteudo = new String(Files.readAllBytes(caminho));
        conteudo = conteudo.replaceAll("[\\[\\]]", "");
        String[] tokens = conteudo.trim().split("[\\s,]+");

        List<Integer> nums = new ArrayList<>();
        for (String t : tokens) {
            t = t.trim();
            if (!t.isEmpty()) nums.add((int) Double.parseDouble(t));
        }

        int[] arr = new int[nums.size()];
        for (int i = 0; i < arr.length; i++) arr[i] = nums.get(i);
        return arr;
    }

    // ─── Escrita ──────────────────────────────────────────────────────────

    static void escreverArquivo(Path caminho, int[] arr) throws IOException {
        Files.createDirectories(caminho.getParent());
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho.toFile()))) {
            bw.write("[");
            for (int i = 0; i < arr.length; i++) {
                bw.write(String.valueOf(arr[i]));
                if (i < arr.length - 1) bw.write(", ");
            }
            bw.write("]");
        }
    }

    // ─── Utilitários ──────────────────────────────────────────────────────

    static boolean estahOrdenado(int[] arr) {
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < arr[i - 1]) return false;
        return true;
    }

    static String preview(int[] arr, int n) {
        int qtd = Math.min(arr.length, n);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < qtd; i++) {
            sb.append(arr[i]);
            if (i < qtd - 1) sb.append(", ");
        }
        if (arr.length > n) sb.append(", ...");
        return sb.append("]").toString();
    }

    static String truncar(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 2) + "..";
    }

    // ─── Teste de um arquivo ──────────────────────────────────────────────

    static void testar(String nomeArquivo, List<String[]> resumoGeral) {
        Path entrada = Paths.get(PASTA_ENTRADA, nomeArquivo);
        Path saida   = Paths.get(PASTA_SAIDA,   nomeArquivo);

        System.out.println("\n+--------------------------------------------------+");
        System.out.printf ("|  Arquivo : %-38s|%n", nomeArquivo);
        System.out.println("+--------------------------------------------------+");

        try {
            int[] dados = lerArquivo(entrada);

            int min = dados[0], max = dados[0];
            for (int v : dados) { if (v < min) min = v; if (v > max) max = v; }

            System.out.printf("  Elementos   : %,d%n", dados.length);
            System.out.printf("  Intervalo   : [%d,  %d]%n", min, max);
            System.out.println("  Antes       : " + preview(dados, 8));

            // ── ORDENAÇÃO ─────────────────────────────────────────────────
            Estatisticas est = RadixSort.sort(dados);
            // ─────────────────────────────────────────────────────────────

            boolean ok = estahOrdenado(dados);

            System.out.println("  Depois      : " + preview(dados, 8));
            System.out.println();
            System.out.println("  +----------- ESTATISTICAS -----------+");
            System.out.printf ("  | Comparacoes : %,20d |%n", est.comparacoes);
            System.out.printf ("  | Trocas      : %,20d |%n", est.trocas);
            System.out.printf ("  | Tempo       : %19.3f ms |%n", est.tempoMs);
            System.out.println("  +------------------------------------+");
            System.out.println("  Ordenado?   : " + (ok ? "OK - SIM" : "ERRO - NAO!"));

            escreverArquivo(saida, dados);
            System.out.printf("  Salvo em    : %s%n", saida);

            resumoGeral.add(new String[]{
                nomeArquivo,
                String.format("%,d", dados.length),
                String.format("%,d", est.comparacoes),
                String.format("%,d", est.trocas),
                String.format("%.3f ms", est.tempoMs),
                ok ? "OK" : "ERRO"
            });

        } catch (NoSuchFileException e) {
            System.err.printf("  ERRO: Arquivo nao encontrado: %s%n", entrada);
            resumoGeral.add(new String[]{nomeArquivo, "-", "-", "-", "-", "ERRO"});
        } catch (Exception e) {
            System.err.println("  ERRO: " + e.getMessage());
            resumoGeral.add(new String[]{nomeArquivo, "-", "-", "-", "-", "ERRO"});
        }
    }

    // ─── Tabela resumo ────────────────────────────────────────────────────

    static void imprimirResumo(List<String[]> resumo, double totalMs) {
        System.out.println("\n");
        System.out.println("+=============================================================+");
        System.out.println("|                     RESUMO GERAL                           |");
        System.out.println("+--------------------+----------+----------+----------+------+");
        System.out.printf ("|  %-18s| %8s | %8s | %8s |%-6s|%n",
                "Arquivo", "Elem.", "Compar.", "Trocas", "Status");
        System.out.println("+--------------------+----------+----------+----------+------+");

        for (String[] r : resumo) {
            System.out.printf("|  %-18s| %8s | %8s | %8s | %-4s |%n",
                    truncar(r[0], 18), r[1], r[2], r[3], r[5]);
        }

        System.out.println("+--------------------+----------+----------+----------+------+");
        System.out.printf ("|  Total arquivos: %-4d              Tempo total: %9.1f ms |%n",
                resumo.size(), totalMs);
        System.out.println("+=============================================================+");
    }

    // ─── main ─────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("+=================================================+");
        System.out.println("|         RADIX SORT - TESTE DE ARQUIVOS          |");
        System.out.println("|  Entrada : dados/                               |");
        System.out.println("|  Saida   : dados_ordenados_RadixSort/           |");
        System.out.println("+=================================================+");

        List<String> arquivos = new ArrayList<>();

        if (args.length > 0) {
            for (String arg : args)
                arquivos.add(Paths.get(arg).getFileName().toString());
        } else {
            Path pastaEntrada = Paths.get(PASTA_ENTRADA);
            if (!Files.isDirectory(pastaEntrada)) {
                System.err.println("\nERRO: Pasta '" + PASTA_ENTRADA + "/' nao encontrada.");
                System.err.println("Crie a pasta e coloque seus arquivos .txt dentro dela.");
                return;
            }
            try {
                Files.list(pastaEntrada)
                     .filter(p -> p.toString().endsWith(".txt"))
                     .map(p -> p.getFileName().toString())
                     .sorted()
                     .forEach(arquivos::add);
            } catch (IOException e) {
                System.err.println("ERRO ao listar pasta: " + e.getMessage());
                return;
            }
        }

        if (arquivos.isEmpty()) {
            System.out.println("\nNenhum arquivo .txt encontrado em '" + PASTA_ENTRADA + "/'.");
            System.out.println("Coloque seus arquivos .txt dentro da pasta dados/");
            return;
        }

        System.out.printf("%nArquivos encontrados: %d%n", arquivos.size());

        List<String[]> resumo = new ArrayList<>();
        long totalInicio = System.nanoTime();

        for (String arq : arquivos) testar(arq, resumo);

        double totalMs = (System.nanoTime() - totalInicio) / 1_000_000.0;
        imprimirResumo(resumo, totalMs);
    }
}