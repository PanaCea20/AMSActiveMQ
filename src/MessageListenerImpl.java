import javax.jms.*;
import java.util.ArrayList;
import java.util.List;


public class MessageListenerImpl implements MessageListener{
    private QueueSession session;
    private QueueSender sender;

    @SuppressWarnings("unused")
    @Override
    public void onMessage(Message msg){
        if(msg instanceof TextMessage) {
            TextMessage txt = (TextMessage) msg; //received msg
            try{
                TextMessage sendMsg = session.createTextMessage();
                String message = txt.getText();
                System.out.println("Message Received: " + txt.getText());
                String method = message.split("\\.")[0];
                String accountName = message.split("\\.")[1];

                if (accountName.equals("Account1") && method.equals("add")){
                    double amount = Double.parseDouble(message.split("\\.")[2].trim());
                    String date = message.split("\\.")[3];
                    String otherAccount = message.split("\\.")[4];
                    System.out.println(Server.account1.getName());
                    Server.account1.addEntry(amount, date, otherAccount);

                        String reply = "Item added successfully to: " + Server.account1.getName();
                        sendMsg.setText(reply);
                        sender.send(sendMsg);
                }

                else if (accountName.equals("Account2") && method.equals("add")) {
                    double amount = Double.parseDouble(message.split("\\.")[2]);
                    String date = message.split("\\.")[3];
                    String otherAccount = message.split("\\.")[4];
                    System.out.println(Server.account2.getName());
                    Server.account2.addEntry(amount, date, otherAccount);

                    String reply = "Item added successfully to: " + Server.account2.getName();
                    sendMsg.setText(reply);
                    sender.send(sendMsg);
                }

                else if (accountName.equals("Account1") && method.equals("get")) {
                    List<Double> amounts = Server.account1.getAllAmounts();
                    String print = "";
                    for (Double amount : amounts){
                        print =" " + amounts;
                    }
                    sendMsg.setText(print);
                    sender.send(sendMsg);
                }

                else if (accountName.equals("Account2") && method.equals("get")) {
                    List<Double> amounts = Server.account2.getAllAmounts();
                    String print ="";
                    for (Double amount : amounts){
                        print =" " + amounts;
                    }
                    sendMsg.setText(print);
                    sender.send(sendMsg);
                }

                else if (accountName.equals("Account1") && method.equals("search")){
                    double amount = Double.parseDouble(message.split("\\.")[2]);
                    String date = Server.account1.search(amount).getDate();
                    System.out.println(date);
                    String reply = "Date of entry: " + date;
                    sendMsg.setText(reply);
                    sender.send(sendMsg);
                }

                else if (accountName.equals("Account2") && method.equals("search")){
                    double amount = Double.parseDouble(message.split("\\.")[2]);
                    String date = Server.account2.search(amount).getDate();
                    System.out.println(date);
                    String reply = "Date of entry: " + date;
                    sendMsg.setText(reply);
                    sender.send(sendMsg);
                }



            } catch (JMSException e) {
            e.printStackTrace();
            }
        }
    }

    public void setSender(QueueSender sender) {
        this.sender = sender;
    }

    public void setSession(QueueSession session) {
        this.session = session;
    }
}
