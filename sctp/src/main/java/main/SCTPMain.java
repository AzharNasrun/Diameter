package main;


import akka.actor.ActorSystem;
import com.firstwap.jdiameter.Properties.JdiameterProperties;
import constant.Constants;
import model.AssociationInfo;
import org.apache.log4j.Logger;

import service.SCTPService;

import java.util.*;

/**
 * Created by aji on 14/02/18.
 */

public class SCTPMain  {

    private static final Logger logger = Logger.getLogger(Constants.SCTP_LOG);


    // key destination Host, associationInfo
    public static Map<String, List<AssociationInfo>> assocMap;

    public static void loadNetworkConfiguration(ActorSystem actorSystem){


        assocMap = new HashMap<String,List<AssociationInfo>>();


                  String destinationHost[] =   JdiameterProperties.getDestinationHost();


                  for(String host:destinationHost){
                    System.out.println("host:"+host);


                     String from =  (JdiameterProperties.getString("sctp."+host+".from"));

                     String to =  (JdiameterProperties.getString("sctp."+host+".to"));

                     List<AssociationInfo> assocList = new ArrayList<AssociationInfo>();

                     if((from !=null)&&(to!=null)){

                      String[] fromAssoc = from.split("\\|");
                      String[] toAssoc = to.split("\\|");

                      for(int x=0;x<fromAssoc.length;x++){

                          AssociationInfo assocInfo = new AssociationInfo();

                          String fromIp[] = fromAssoc[x].split(",");

                          String toIp[] = toAssoc[x].split(",");


                          for(int y=0;y<fromIp.length;y++) {
                              assocInfo.sources.add(fromIp[y]);

                          }

                          for(int y=0;y<toIp.length;y++) {
                              assocInfo.detinations.add(toIp[y]);
                          }


                      assocList.add(assocInfo);

                      }


                     }

                      assocMap.put(host,assocList);
                  }





                 SCTPService.loadSctpInstance(actorSystem);

    }






}
