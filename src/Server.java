import javax.jms.*;
import javax.naming.*;
import java.util.Hashtable;


public class Server {
    public static Account account1;
    public static Account account2;

    public static void main(String argv[]) throws Exception {

        account1 = new Account("Nizar");
        account2 = new Account("Josh");

        account1.addEntry(900, "13/06/2023", "Ulrich");
        account2.addEntry(600, "13/06/2023", "Josh");

        Hashtable<String, String> properties = new Hashtable<String, String>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

        Context context = new InitialContext(properties);
        QueueConnectionFactory connFactory =
                (QueueConnectionFactory) context.lookup("ConnectionFactory");
        QueueConnection conn = connFactory.createQueueConnection();
        QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue qReceiver = (Queue) context.lookup("dynamicQueues/queue1");
        Queue qSender = (Queue) context.lookup("dynamicQueues/queue2");

        QueueReceiver receiver = session.createReceiver(qReceiver);
        QueueSender sender = session.createSender(qSender);

        conn.start();

        MessageListenerImpl messageListener = new MessageListenerImpl();
        messageListener.setSender(sender);
        messageListener.setSession(session);
        receiver.setMessageListener(messageListener);

    }

}