package mergeSort;

public class Estatisticas {

    public long   comparacoes = 0;
    public long   trocas      = 0;
    public double tempoMs     = 0.0;

    public void addComparacao() {
        comparacoes++;
    }
    public void addTroca() {
        trocas++;
    }

    public void setTempo(long inicioNano, long fimNano) {
        this.tempoMs = (fimNano - inicioNano) / 1_000_000.0;
    }

    public void reset() {
        comparacoes = 0;
        trocas      = 0;
        tempoMs     = 0.0;
    }

    @Override
    public String toString() {
        return String.format(
            "Comparacoes: %,d | Trocas: %,d | Tempo: %.3f ms",
            comparacoes, trocas, tempoMs
        );
    }
}