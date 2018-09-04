package com.firstwap.jdiameter;

import com.firstwap.jdiameter.Properties.JdiameterProperties;
import com.firstwap.jdiameter.SCTPServerService;
import constant.Constants;
import main.SCTPMain;
import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * This class is main class of application JDiameter
 *
 * @version 1.0
 * @since 2017-01-23
 * Created by gede on 23/01/18.
 */

public class JdiameterMain{



    public static void main(String args[]) {
        try {
            SpringApplication.run(new Class[]{JDiameterClient.class,SCTPServerService.class},args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
