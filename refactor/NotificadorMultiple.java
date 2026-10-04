import java.util.ArrayList;
import java.util.List;

public class NotificadorMultiple implements Notificador {

    private final List<Notificador> notificadores;

    public NotificadorMultiple(List<Notificador> notificadores) {
        this.notificadores = new ArrayList<>(notificadores);
    }

    @Override
    public void enviar(String destinatario, String mensaje) {
        for (Notificador notificador : notificadores) {
            notificador.enviar(destinatario, mensaje);
        }
    }
}
