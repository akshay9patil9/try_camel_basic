package akshay.try_camel_basic.routes;

import akshay.try_camel_basic.AppConfig;
import akshay.try_camel_basic.dto.SOResponse;
import akshay.try_camel_basic.processor.SOQuestionProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MyFirstRoute extends RouteBuilder {
    private final AppConfig config;

    /**
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {

        //testing
//        from("timer:test-timer").to("log:test-log");

        //
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");


        rest()
                .consumes("application/json").produces("application/json")
                .get("/questions/{inputTag}") //endpoint e.g. - http://127.0.0.1:8081/so/questions/scala
                .outType(String.class)
                .to("direct:get-so-questions");

        from("direct:set-test-response")
                .process(this::setTestResponse);

        from("direct:get-so-questions")
                .process(this::setTestResponse)
                .setHeader(Exchange.HTTP_METHOD, simple("GET"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_QUERY, simple("key="+config.getSokey()+"&site=stackoverflow&order=desc&sort=activity&tagged=${header.tag}&filter=default"))
                .to("https://api.stackexchange.com/2.3/questions?")
                .log("Response code from the Create Gist operation was: ${header.CamelHttpResponseCode}")
                .log("Response body from the Create Gist operation was: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, SOResponse.class)
                .process(new SOQuestionProcessor())
                .end();



    }

    private void setTestResponse(Exchange exchange){
        String tag = exchange.getMessage().getHeader("inputTag", String.class);

        Message message = new DefaultMessage(exchange.getContext());
        message.setBody(tag);
        exchange.setMessage(message);
        exchange.getIn().setHeader("tag",tag);
    }
}
