import java.util.HashMap;
import java.util.Map;

/**
 * Registro de tipos de transferencia. Agregar un tipo nuevo (p. ej.
 * "EXPRESS") es registrar una clase nueva aquí desde el programa principal;
 * esta clase y TransaccionService no se tocan (principio O).
 */
public class CalculadoraComision {
    private final Map<String, TipoTransferencia> tipos = new HashMap<>();

    public void registrar(String nombre, TipoTransferencia tipo) {
        tipos.put(nombre, tipo);
    }

    public double calcular(String tipo, double monto) {
        TipoTransferencia t = tipos.get(tipo);
        if (t == null) throw new IllegalArgumentException("Tipo de transferencia desconocido");
        return t.calcularComision(monto);
    }
}
