package com.firstwap.jdiameter.database;


import com.firstwap.jdiameter.model.JsmscNumberRange;
import model.CommandCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import java.util.List;


@Repository
@Profile("server")
public class ResponseDao {
    @Autowired
    private ConfigDB configDB;


    public JsmscNumberRange getResponse(String number, CommandCode commandCode) {
        try (Connection conn = configDB.sql2o.open()) {
            StringBuilder stringBuilder = new StringBuilder(50);
            stringBuilder.append(" SELECT  UPPER_RANGE as upperRange ,RESPONSE as response, CALLBACK_DELAY as callbackDelay, ID as id, LOWER_RANGE as lowerRange, ");
            stringBuilder.append(" COMMAND_CODE as commandCode, DESCRIPTION as type ");
            stringBuilder.append(" FROM ");
            stringBuilder.append(" `JSMSC_NUMBER_RANGE` WHERE :number BETWEEN LOWER_RANGE AND UPPER_RANGE AND COMMAND_CODE = :commandCode" );
            return conn.createQuery(stringBuilder.toString()).addParameter("number", number).addParameter("commandCode",commandCode.getComandCode()).executeAndFetchFirst(JsmscNumberRange.class);

        }
    }


}
