package com.zeroxy._1s.kafka;

import com.zeroxy._1s.domain.Script;
import com.zeroxy.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class Sender {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendScript(Script script, Callback callback){
        Callback.callbacks.put(script.getId(), callback);
        kafkaTemplate.send("controlled_terminal_script_request", JsonUtil.objectToJson(script));
    }
    public void sendScriptResponse(Script script){
        kafkaTemplate.send("controlled_terminal_script_response", JsonUtil.objectToJson(script));
    }
}