package service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import model.AVP;
import model.AVPFlag;
import model.Diameter;
import model.DiameterFlag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResponderDeserializer extends JsonDeserializer<Diameter> {
    @Override
    public Diameter deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        ObjectCodec oc = jp.getCodec();

        JsonNode node = oc.readTree(jp);

        Diameter diameter = new Diameter();
        diameter.setVersion(node.findValue("version").asInt());
        diameter.setType(node.findValue("type").asText());
        diameter.setUuid(node.findValue("uuid").asText());


        DiameterFlag df = new DiameterFlag();
        df.setRequest(node.findValue("request").asBoolean());
        df.setProxyable(node.findValue("proxyable").asBoolean());
        df.setError(node.findValue("error").asBoolean());
        df.setTransmitted(node.findValue("transmitted").asBoolean());
        diameter.setFlags(df);

        diameter.setCommandCode(node.findValue("commandCode").asLong());
        diameter.setEndToEndId(node.findValue("endToEndId").asLong());
        diameter.setHopByHopId(node.findValue("hopByHopId").asLong());
        diameter.setApplicationId(node.findValue("applicationId").asLong());

        Iterator<JsonNode> avpListNode = node.findValue("avpList").elements();
        diameter.setAvpList(avpAnswer(avpListNode));

        return diameter;
    }

    public List<AVP> avpAnswer(Iterator<JsonNode> avpListNode){
        List<AVP> avpList = new ArrayList<>();
        while (avpListNode.hasNext()) {
            JsonNode sc = (JsonNode) avpListNode.next();
            AVP avp = new AVP();
            avp.setCode(sc.findValue("code").asLong());
            avp.setDescription(sc.findValue("description").asText());
            avp.setVendorId(sc.findValue("vendorId").asLong());
            avp.setValueType(sc.findValue("valueType").asText());

            if(sc.findValue("valueType").asText().equalsIgnoreCase("string")){
                avp.setValue(sc.findValue("value").asText());
            }else if(sc.findValue("valueType").asText().equalsIgnoreCase("byteString")){
                avp.setValue(sc.findValue("value").asText());
            }
            else if(sc.findValue("valueType").asText().equalsIgnoreCase("base64")){
                avp.setValue(sc.findValue("value").asText());
            }
            else if(sc.findValue("valueType").asText().equalsIgnoreCase("integer")){
                avp.setValue(sc.findValue("value").asInt());
            }
            else if(sc.findValue("valueType").asText().equalsIgnoreCase("grouped")) {
                avp.setValue(avpAnswer(sc.findValue("value").elements()));
                /*List<AVP> avpChList = new ArrayList<>();
                Iterator<JsonNode> childListNode = sc.findValue("value").elements();
                while (childListNode.hasNext()) {
                    JsonNode ch = (JsonNode) childListNode.next();
                    AVP avpCh = new AVP();

                    avpChList.add(avpCh);
                }
                avp.setValue(avpChList);*/
            }

            AVPFlag avpFlag = new AVPFlag();
            avpFlag.setVendorSpecific(sc.findValue("vendorSpecific").asBoolean());
            avpFlag.setMandatory(sc.findValue("vendorSpecific").asBoolean());
            avpFlag.setProtect(sc.findValue("protect").asBoolean());
            avp.setFlags(avpFlag);

            avpList.add(avp);
        }

        return avpList;
    }
}
