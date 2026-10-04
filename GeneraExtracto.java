/**
 * La capacidad de generar un extracto es transversal: la comparten cuentas,
 * tarjetas y créditos, aunque no tengan nada más en común. Por eso es una
 * interfaz propia y pequeña, no parte de un contrato gigante.
 */
public interface GeneraExtracto {
    String generarExtracto();
}
