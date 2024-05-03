package com.leesiper.logseqsample.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.LevelToSyslogSeverity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.siegmar.logbackgelf.GelfMessage;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SeqEncoder extends de.siegmar.logbackgelf.GelfEncoder {

    private String _formatted;

    public byte[] encode(ILoggingEvent event) {

        GelfMessage msg = this.buildGelfMessage(event.getTimeStamp(), LevelToSyslogSeverity.convert(event), this.normalizeShortMessage(this.buildShortMessage(event)), this.buildFullMessage(event), this.collectAdditionalFields(event));
        ObjectMapper mapper = new ObjectMapper();
        try {
            var map = CreateBaseMap(msg);
            // Attach other properties
            var json = mapper.writeValueAsString(map);

            if (this.isAppendNewline()) {
                return (json + System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
            }

            return json.getBytes(StandardCharsets.UTF_8);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private Map<String, Object> CreateBaseMap(GelfMessage msg) {
        var map=new HashMap<String,Object>();
        map.putAll(msg.getAdditionalFields());
        map.put("timestamp",(new BigDecimal(msg.getTimestamp())).movePointLeft(3).toPlainString());
        map.put("level", msg.getLevel());
        map.put("host", msg.getHost());
        map.put("short_message", _formatted);
        if(msg.getFullMessage()!=null && !msg.getFullMessage().isEmpty()){
            map.put("full_message", msg.getFullMessage());
        }
        return map;
    }

    @Override
    protected Map<String, Object> collectAdditionalFields(ILoggingEvent event) {
        var map = super.collectAdditionalFields(event);
        _formatted = StructMessageFormatter.formatAssign(map, event.getMessage(), event.getArgumentArray());
        return map;
    }

}
