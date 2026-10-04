import java.util.ArrayList;
import java.util.List;

/** Doble de prueba: guarda en memoria, nunca toca Oracle. */
public class RepositorioFalso implements RepositorioTransacciones {
    public final List<String> guardadas = new ArrayList<>();

    public void guardarTransaccion(String origen, String destino, double monto, double comision) {
        guardadas.add(origen + "->" + destino + ":" + monto + "/" + comision);
    }
}
