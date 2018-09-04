package com.firstwap.jdiameter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Diameter;
import model.response.ismscm.locate.Locate;
import model.response.ismscm.locate.Param;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(  classes = {JDiameterClient.class},  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JdiameterMainTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    @Autowired
    TestRestTemplate rest;


    @BeforeClass
    public static void init(){
    System.out.println("init Server");
                SpringApplication.run(SCTPServerService.class,new String[]{"--spring.profiles.active=server"});
    }

    @Test
    public void requestSrilcspsl() throws Exception {

       JsonNode node = objectMapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("context/psl-request.json")));
        final String response = rest.postForObject("/context", node, String.class);
        assertNotNull(response);
        final Locate diameterResponse = objectMapper.readValue(response, Locate.class);
        Param param = diameterResponse.getParam();

        assertThat(param.getUserError()).isEqualTo(0);
        assertThat(param.getProviderError()).isEqualTo(0);
        assertThat(param.getGeoInfo().getTypeOfShape()).isEqualTo(1);
        assertThat(param.getDeliveryError()).isEqualTo(0);

    }

    @Test
    public void udrcontext() throws Exception{

        JsonNode node = objectMapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("context/udr-request.json")));
        final String response = rest.postForObject("/context", node, String.class);
        assertNotNull(response);
        final Locate diameterResponse = objectMapper.readValue(response, Locate.class);
        Param param = diameterResponse.getParam();


        assertThat(param.getUserError()).isEqualTo(27);
        assertThat(param.getProviderError()).isEqualTo(0);
        assertThat(param.getDeliveryError()).isEqualTo(0);
    }
 @Test
    public void srilcs() throws Exception{

      JsonNode node = objectMapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("api/srilcs-request.json")));
        final String response = rest.postForObject("/api", node, String.class);
       assertNotNull(response);
      final Diameter diameter = objectMapper.readValue(response, Diameter.class);

      assertTrue(diameter.getAvpList().size()>0);

    }

    @Test
    public void srr() throws Exception{

        JsonNode node = objectMapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("api/srr-request.json")));
        final String response = rest.postForObject("/api", node, String.class);
        assertNotNull(response);
        final Diameter diameter = objectMapper.readValue(response, Diameter.class);

        assertTrue(diameter.getAvpList().size()>0);
    }

   @Test
    public void udr() throws Exception{

        JsonNode node = objectMapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("api/udr-request.json")));
        final String response = rest.postForObject("/api", node, String.class);
        assertNotNull(response);

        final Diameter diameter = objectMapper.readValue(response, Diameter.class);


        assertTrue(diameter.getAvpList().size()>0);

    }

    @Test
    public void psl() throws Exception{

        JsonNode node = objectMapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("api/psl-request.json")));
        final String response = rest.postForObject("/api", node, String.class);
        assertNotNull(response);
        final Diameter diameter = objectMapper.readValue(response, Diameter.class);

        assertTrue(diameter.getAvpList().size()>0);

    }

    @Test
    public void ulr() throws Exception{

        JsonNode node = objectMapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("api/ulr-request.json")));
        final String response = rest.postForObject("/api", node, String.class);
        assertNotNull(response);
        final Diameter diameter = objectMapper.readValue(response, Diameter.class);

        assertTrue(diameter.getAvpList().size()>0);

    }
}
