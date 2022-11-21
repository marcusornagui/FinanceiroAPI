package br.com.mo.financeiroapi.mensageria;

import br.com.mo.financeiroapi.model.dto.mensageria.PayloadSenderDTO;
import br.com.mo.financeiroapi.properties.QueueProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtividadesSender {
    
    protected final RabbitTemplate rabbitTemplate;
    protected final QueueProperties queueProperties;

    @Autowired
    public AtividadesSender(RabbitTemplate rabbitMessagingTemplate, QueueProperties queueProperties) {
        this.rabbitTemplate = rabbitMessagingTemplate;
        this.queueProperties = queueProperties;
    }

    public void send(Class pClass, Object pMensagem, String pQueue) throws Exception {
        sendPayload(pClass, pMensagem, pQueue);
    }

    public void sendInfo(Class pClass, String pMensagem) {
        try {
            PayloadSenderDTO payloadSender = new PayloadSenderDTO()
                    .dados(pMensagem)
                    .dataHora(LocalDateTime.now());

            sendPayload(pClass, payloadSender, queueProperties.getInfo());

        } catch (Exception ex) {
            Logger.getLogger(AtividadesSender.class.getName()).severe(ex.getMessage());
        }
    }

    public void sendErro(Class pClass, String pMensagem) {
        try {
            PayloadSenderDTO payloadSender = new PayloadSenderDTO()
                    .dados(pMensagem)
                    .dataHora(LocalDateTime.now());

            sendPayload(pClass, payloadSender, queueProperties.getErro());

        } catch (Exception ex) {
            Logger.getLogger(AtividadesSender.class.getName()).severe(ex.getMessage());
        }
    }

    private void sendPayload(Class pClass, Object pMensagem, String pQueue) throws Exception {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String mensagem = mapper.writeValueAsString(pMensagem);
            LoggerFactory.getLogger(pClass).info(mensagem);

            this.rabbitTemplate.convertAndSend(pQueue, mensagem);
        } catch (Exception ex) {
            Logger.getLogger(AtividadesSender.class.getName()).severe(ex.getMessage());
            throw ex;
        }
    }
    
}
