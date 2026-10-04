import java.util.ArrayList;
import java.util.List;

/** Doble de prueba: anota los mensajes en vez de enviar SMS real. */
public class NotificadorFalso implements Notificador {
    public final List<String> mensajesEnviados = new ArrayList<>();

    public void enviar(String destinatario, String mensaje) {
        mensajesEnviados.add(destinatario + ":" + mensaje);
    }
}
