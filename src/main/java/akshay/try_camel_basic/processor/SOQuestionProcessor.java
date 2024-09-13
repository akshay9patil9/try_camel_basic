package akshay.try_camel_basic.processor;


import akshay.try_camel_basic.dto.SOQuestion;
import akshay.try_camel_basic.dto.SOResponse;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultMessage;

@Slf4j
public class SOQuestionProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        var resp = exchange.getMessage().getBody(SOResponse.class);
        log.info("Got response :  {}", resp);

//        Message message = new DefaultMessage(exchange.getContext());
//        message.setBody(exchange.getMessage().getBody());
//        exchange.setMessage(message);
    }

}
