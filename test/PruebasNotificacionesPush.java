import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PruebasNotificacionesPush {

    @Test
    void notificaPorTodosLosCanales() {
        NotificadorFalso sms = new NotificadorFalso();
        NotificadorFalso push = new NotificadorFalso();

        NotificadorMultiple notificador = new NotificadorMultiple(
                Arrays.asList(sms, push)
        );

        notificador.enviar("Ana", "Transferiste $50000");

        Assertions.assertEquals(1, sms.mensajesEnviados.size());
        Assertions.assertEquals(1, push.mensajesEnviados.size());
        Assertions.assertEquals(
                sms.mensajesEnviados.get(0),
                push.mensajesEnviados.get(0)
        );
    }
}
