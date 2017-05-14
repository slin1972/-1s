package com.zeroxy._1s.kafka;

import com.zeroxy._1s.domain.Script;
import com.zeroxy.util.JsonUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {

    @KafkaListener(topics = "controlled_terminal_script_response")
    public void processMessage(String content) throws IOException {
        Script script = JsonUtil.jsonToObject(content, Script.class);
        Callback callback = Callback.callbacks.remove(script.getId());
        callback.run(script);
    }
    @KafkaListener(topics = "controlled_terminal_script_request")
    public void processMessage1(String content) throws IOException {
        Script script = JsonUtil.jsonToObject(content, Script.class);

    }
}