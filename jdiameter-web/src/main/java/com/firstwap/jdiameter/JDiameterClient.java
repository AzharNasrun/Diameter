package com.firstwap.jdiameter;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import main.SCTPMain;
import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class JDiameterClient  implements ApplicationRunner {

    private final Logger logger = Logger.getLogger(Constants.HTTP_LOG);

    @Override
    public void run(ApplicationArguments arg) throws Exception {
        System.out.println("diameter v4.0 ");
        logger.info("diameter v4.0 ");
        logger.info("date 20180628_01");
            SCTPMain.loadNetworkConfiguration(JdiameterProperties.getActorSystem());
    }


}
