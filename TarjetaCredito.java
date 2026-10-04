public class TarjetaCredito implements GeneraExtracto, GeneraIntereses, PagaCuota, AvanceEfectivo {
    private double deuda;
    private final double cupo;

    public TarjetaCredito(double cupo) { this.cupo = cupo; }

    public void retirarAvance(double monto) {
        if (deuda + monto > cupo) throw new IllegalStateException("Cupo insuficiente");
        deuda += monto;
    }

    public double calcularIntereses() { return deuda * 0.028; }
    public void pagarCuota(double monto) { deuda -= monto; }
    public String generarExtracto() { return "Tarjeta - deuda: $" + deuda; }
}
