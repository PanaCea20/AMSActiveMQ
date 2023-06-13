import javax.jms.*;
import javax.naming.*;
import java.util.Hashtable;
import java.util.Scanner;
//import org.apache.activemq.ActiveMQConnectionFactory;

public class Client {
    public static void main(String argv[]) throws Exception {

        Hashtable<String, String> properties = new Hashtable<String, String>();

        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");
        Context context = new InitialContext(properties);
        QueueConnectionFactory connFactory =
                (QueueConnectionFactory) context.lookup("ConnectionFactory");

        QueueConnection conn = connFactory.createQueueConnection();
        QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue qSender = (Queue) context.lookup("dynamicQueues/queue1");
        Queue qReciever = (Queue) context.lookup("dynamicQueues/queue2");

        QueueSender sender = session.createSender(qSender);
        QueueReceiver receiver = session.createReceiver(qReciever);

        TextMessage sendMsg = session.createTextMessage();
        TextMessage receiveMsg = session.createTextMessage();
        conn.start();

        Scanner scanner = new Scanner(System.in);
        int x = 1;
        do {
            System.out.println("Choose a method(add/get/search): ");
            String method = scanner.nextLine();

            System.out.println("Choose an account(Account1/Account2):");
            String accountName = scanner.nextLine();

            String info;
            String dot = ".";

            switch (method) {
                case "add":
                    System.out.println("Amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());

                    System.out.println(("Date: "));
                    String date = scanner.nextLine();

                    System.out.println("OtherAccount Name: ");
                    String otherAccount = scanner.nextLine();

                    info = method + dot + accountName + dot + amount + dot + date + dot + otherAccount;
                    sendMsg.setText(info);
                    System.out.println("Sending Entry: " + sendMsg.getText());
                    sender.send(sendMsg);
                    receiveMsg = (TextMessage) receiver.receive();
                    System.out.println("Message received: " + receiveMsg.getText());
                    break;

                case "get":
                    info = method + dot + accountName;
                    sendMsg.setText(info);
                    System.out.println("Sending Entry: " + sendMsg.getText());
                    sender.send(sendMsg);
                    receiveMsg = (TextMessage) receiver.receive();
                    System.out.println("Entry(ies) to the account: " + receiveMsg.getText());
                    break;

                case "search":
                    System.out.println("Amount to search: ");
                    amount = Double.parseDouble(scanner.nextLine());
                    info = method + dot + accountName + dot + amount;
                    sendMsg.setText(info);
                    System.out.println("Sending Amount: " + sendMsg.getText());
                    sender.send(sendMsg);
                    receiveMsg = (TextMessage) receiver.receive();
                    System.out.println("Message received: " + receiveMsg.getText());
                    break;

                default:
                    System.out.println("No such command!");
            }
        }
        while (x==1);

        session.close();
        conn.close();
    }
}
