package beans;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

/**
 *
 * @author Yakov Laptev
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "amqrnsg"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessageBean implements MessageListener {

    @Resource(mappedName = "amqrnsg")
    private Queue amqrnsg;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    public NewMessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        sendJMSMessageToAmqrnsg("Message from bean");
    }

    private void sendJMSMessageToAmqrnsg(String messageData) {
        context.createProducer().send(amqrnsg, messageData);
    }

}
