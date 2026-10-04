public class AntifraudeConsola implements Antifraude {

    @Override
    public void reportar(String origen, String destino, double monto) {
        System.out.println(
                "[ANTIFRAUDE] " + origen + " -> " + destino + " $" + monto
        );
    }
}
