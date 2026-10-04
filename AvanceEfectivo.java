/**
 * Solo un producto que de verdad entrega efectivo contra un cupo (como una
 * tarjeta de crédito) implementa esto. Un crédito de vivienda, por ejemplo,
 * no la implementa: ya no tiene que fingir un "retirar" que no aplica.
 */
public interface AvanceEfectivo {
    void retirarAvance(double monto);
}
