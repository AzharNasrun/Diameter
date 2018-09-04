package service.response;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.DiameterStatus;
import model.response.ismscm.api.API;
import model.response.ismscm.locate.GeoInfo;
import model.response.ismscm.locate.Locate;
import model.response.ismscm.locate.Param;
import model.response.ismscm.jamming.JamModel;
import model.response.ismscm.jamming.NetworkError;

/**
 * @author Azhar
 * this class is for generating template response
 */
public class ResponseGenerator {

    ObjectMapper objectMapper = new ObjectMapper();
    private Locate responseDto = new Locate();
    private JamModel jamModel = new JamModel();
    private API api = new API();
    private DiameterStatus status;
    private DiameterStatus jamStatus;
    private DiameterStatus apiStatus;

    public ResponseGenerator(String uuid) {
        createLocateTemplateResponse(uuid);
        createJamTemplateResponse(uuid);
        createAPITemplateResponse(uuid);
    }

    private void createAPITemplateResponse(String uuid) {
        API api =getApi();
        api.setRequestUuid(uuid);
        api.setStatus(0);
        api.setType("locate");
        api.setVersion("1.0");
        model.response.ismscm.api.Param param = new model.response.ismscm.api.Param();
        param.setNetworkError(0);
        param.setDeliveryError(0);
        param.setProviderError(0);
        param.setUserError(0);
        param.setResult(0);
        param.setResultDescription("");
        param.setImsi("");
        param.setTarget("");
        param.setMsc("");
        api.setParam(param);

    }

    /**
     * this method to map response dto to jsonResponse
     *
     * @return
     */
    private void createLocateTemplateResponse(String uuid) {
        Locate response = getResponseDto();
        response.setRequestUuid(uuid);
        response.setStatus(0);
        response.setType("locate");
        response.setVersion("1.0");
        Param param = new Param();
        param.setAgent("PL");
        param.setCallBarringData(new Object());
        param.setCallForwardingData(new Object());
        param.setCamelSubscriberInfo(new Object());
        param.setClassmark("");
        param.setCountryName("");
        param.setCurrency(0);
        param.setCurrencyCode(0);

        param.setForwardingNumber("");
        param.setForwardingOption(0);
        param.setForwardingSubAddr("");
        param.setGgsnAddress("");
        param.setHomeRoutingFlag(0);
        param.setImei("");

        param.setImsiFromSriFc("");
        param.setImsiFromSriSm("");

        param.setLocationNumber("");

        //Error Code parameter
        param.setNetworkError(0);
        param.setNotReachableReason(-1);
        param.setDeliveryError(0);
        param.setProviderError(0);
        param.setUserError(0);

        param.setOdbInfo(new Object());
        param.setOperatorName("");
        param.setRoamingNumber("");
        param.setServiceType("LTE");
        param.setSgsnAddress("");
        param.setSgsnCamelPhase(0);

        param.setSubscriberData(new Object());

        param.setTva(0);

        param.setVisitedCountryName("");

        param.setVisitedMscGt("");
        param.setVisitedMscName("");

        param.setVisitedOperatorName("");
        param.setVlrCamelPhase(0);
        param.setCellRef("");

        GeoInfo geoInfo = new GeoInfo();

        geoInfo.setTypeOfShape(0);
        geoInfo.setDegreesOfLatitude(0);
        geoInfo.setDegreesOfLongitude(0);
        geoInfo.setRadius(0);
        geoInfo.setUncertaintyCode(0);
        param.setGeoInfo(geoInfo);
        response.setParam(param);

    }
    /**
     * this method to map response dto to jsonResponse
     *
     * @return
     */
    public void createJamTemplateResponse(String uuid) {
        JamModel response = getJamModel();
        response.setRequestUuid(uuid);
        response.setType("dataJamming");
        response.setVersion("1.0");
        response.setStatus(0);
        model.response.ismscm.jamming.Param param = new model.response.ismscm.jamming.Param();
        param.setResult(0);
        param.setResultDescription("Successful");
        param.setLastKnownMscGt("");
        param.setLastKnownSgsnIp("");
        param.setLastKnownSgsnGt("");
        param.setHlrGt("");
        param.setStatus(1);
        param.setMsisdn("");
        param.setImsi("");
        param.setStatusDescription("target is in jamming list and currently jammed");
        NetworkError networkError = new NetworkError();
        networkError.setMapId(0);
        networkError.setMapDescription("");
        networkError.setUserError(0);
        networkError.setUserErrorDescription("");
        networkError.setProvError(0);
        networkError.setProvErrorDescription("");
        networkError.setRefuseReason(0);

        param.setNetworkError(networkError);
        response.setParam(param);

    }

    public Locate getResponseDto() {
        return responseDto;
    }


    public Param getParamDto() {
        return responseDto.getParam();
    }

    public model.response.ismscm.jamming.Param getJamParam() {
        return jamModel.getParam();
    }

    public model.response.ismscm.api.Param getAPIParam() {
        return api.getParam();
    }

    public NetworkError getJamNetworkError(){
      return getJamParam().getNetworkError();
    }


    public GeoInfo getGeoInfoDto() {
        return responseDto.getParam().getGeoInfo();
    }


    public String getResponseString() {
        try {
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return objectMapper.writeValueAsString(responseDto);
        } catch (Exception e) {
            e.printStackTrace();
            return"";
        }

    }
    public String getJamResponseString() {
        try {
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return objectMapper.writeValueAsString(jamModel);
        } catch (Exception e) {
            e.printStackTrace();
            return"";
        }

    }

    public String getAPIResponseString() {
        try {
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return objectMapper.writeValueAsString(api);
        } catch (Exception e) {
            e.printStackTrace();
            return"";
        }

    }
    public DiameterStatus getStatus() {
        return status;
    }

    /**
     * @param code diameter result code
     * @return
     */

    public void setStatus(int code) {
        status = DiameterStatus.getByCode(code);
        Param param = getParamDto();
        if (status != null) {
            switch (status) {
                case ERROR_SERVER_NETWORK_DOWN:
                    param.setUserError(0);
                    param.setProviderError(8);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case DIAMETER_UNABLE_TO_COMPLY:
                    param.setUserError(255);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_UNKNOWN_UNREACHABLE_LCS_CLIENT:
                    param.setUserError(58);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_POSITIONING_FAILED:
                    param.setUserError(1);
                    param.setProviderError(5);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_POSITIONING_DENIED:
                    param.setUserError(1);
                    param.setProviderError(2);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_SUSPENDED_USER:
                    param.setUserError(301);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_UNREACHABLE_USER:
                    param.setUserError(1);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_UNAUTHORIZED_REQUESTING_NETWORK:
                    param.setUserError(36);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_DETACHED_USER:
                    param.setUserError(27);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case SUCCESS:
                    param.setUserError(0);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case ERROR_USER_UNKNOWN:
                    param.setUserError(201);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
                case DIAMETER_ERROR_ABSENT_USER:
                    param.setUserError(27);
                    param.setProviderError(0);
                    param.setDeliveryError(0);
                    param.setNetworkError(0);
                    break;
            }
        }
    }

    public DiameterStatus getJamStatus() {
        return jamStatus;
    }

    public void setJamStatus(int code) {
        jamStatus = DiameterStatus.getByCode(code);
        model.response.ismscm.jamming.Param param = getJamParam();
        NetworkError networkError= getJamNetworkError();
            switch (jamStatus) {
                case ERROR_SERVER_NETWORK_DOWN:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_SERVER_NETWORK_DOWN.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_SERVER_NETWORK_DOWN.toString());
                    networkError.setUserError(0);
                    networkError.setProvError(8);
                    break;
                case DIAMETER_UNABLE_TO_COMPLY:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.DIAMETER_UNABLE_TO_COMPLY.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.DIAMETER_UNABLE_TO_COMPLY.toString());
                    networkError.setUserError(255);
                    networkError.setProvError(0);
                    break;
                case ERROR_UNKNOWN_UNREACHABLE_LCS_CLIENT:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_UNKNOWN_UNREACHABLE_LCS_CLIENT.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_UNKNOWN_UNREACHABLE_LCS_CLIENT.toString());
                    networkError.setUserError(58);
                    networkError.setProvError(0);
                    break;
                case ERROR_POSITIONING_FAILED:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_POSITIONING_FAILED.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_POSITIONING_FAILED.toString());
                    networkError.setUserError(1);
                    networkError.setProvError(5);
                    break;
                case ERROR_POSITIONING_DENIED:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_POSITIONING_DENIED.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_POSITIONING_DENIED.toString());
                    networkError.setUserError(1);
                    networkError.setProvError(2);
                    break;
                case ERROR_SUSPENDED_USER:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_SUSPENDED_USER.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_SUSPENDED_USER.toString());
                    networkError.setUserError(301);
                    networkError.setProvError(0);
                    break;
                case ERROR_UNREACHABLE_USER:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_UNREACHABLE_USER.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_UNREACHABLE_USER.toString());
                    networkError.setUserError(1);
                    networkError.setProvError(0);
                    break;
                case ERROR_UNAUTHORIZED_REQUESTING_NETWORK:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_UNAUTHORIZED_REQUESTING_NETWORK.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_UNAUTHORIZED_REQUESTING_NETWORK.toString());
                    networkError.setUserError(36);
                    networkError.setProvError(0);
                    break;
                case ERROR_DETACHED_USER:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_DETACHED_USER.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_DETACHED_USER.toString());
                    networkError.setUserError(27);
                    networkError.setProvError(0);
                    break;
                case SUCCESS:
                    param.setResult(0);
                    param.setStatusDescription(DiameterStatus.SUCCESS.toString());
                    param.setStatus(0);
                    param.setStatusDescription(DiameterStatus.SUCCESS.toString());
                    networkError.setUserError(0);
                    networkError.setProvError(0);
                    break;
                case ERROR_USER_UNKNOWN:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.ERROR_USER_UNKNOWN.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.ERROR_USER_UNKNOWN.toString());
                    networkError.setUserError(201);
                    networkError.setProvError(0);
                    break;
                case DIAMETER_ERROR_ABSENT_USER:
                    param.setResult(104);
                    param.setStatusDescription(DiameterStatus.DIAMETER_ERROR_ABSENT_USER.toString());
                    param.setStatus(1);
                    param.setStatusDescription(DiameterStatus.DIAMETER_ERROR_ABSENT_USER.toString());
                    networkError.setUserError(27);
                    networkError.setProvError(0);
                    break;
            }
        }
    public DiameterStatus getAPIStatus() {
        return apiStatus;
    }

    public void setAPIStatus(int code) {
        apiStatus = DiameterStatus.getByCode(code);
        model.response.ismscm.api.Param param = getAPIParam();
        switch (apiStatus) {
            case ERROR_SERVER_NETWORK_DOWN:
                param.setUserError(0);
                param.setProviderError(8);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_SERVER_NETWORK_DOWN.toString());
                break;
            case DIAMETER_UNABLE_TO_COMPLY:
                param.setUserError(255);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.DIAMETER_UNABLE_TO_COMPLY.toString());
                break;
            case ERROR_UNKNOWN_UNREACHABLE_LCS_CLIENT:
                param.setUserError(58);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_UNKNOWN_UNREACHABLE_LCS_CLIENT.toString());
                break;
            case ERROR_POSITIONING_FAILED:
                param.setUserError(1);
                param.setProviderError(5);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_POSITIONING_FAILED.toString());
                break;
            case ERROR_POSITIONING_DENIED:
                param.setUserError(1);
                param.setProviderError(2);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_SERVER_NETWORK_DOWN.toString());
                break;
            case ERROR_SUSPENDED_USER:
                param.setUserError(301);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_SUSPENDED_USER.toString());
                break;
            case ERROR_UNREACHABLE_USER:
                param.setUserError(1);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_UNREACHABLE_USER.toString());
                break;
            case ERROR_UNAUTHORIZED_REQUESTING_NETWORK:
                param.setUserError(36);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_UNAUTHORIZED_REQUESTING_NETWORK.toString());
                break;
            case ERROR_DETACHED_USER:
                param.setUserError(27);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_DETACHED_USER.toString());
                break;
            case SUCCESS:
                param.setUserError(0);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(0);
                param.setResultDescription(DiameterStatus.SUCCESS.toString());
                break;
            case ERROR_USER_UNKNOWN:
                param.setUserError(201);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.ERROR_USER_UNKNOWN.toString());
                break;
            case DIAMETER_ERROR_ABSENT_USER:
                param.setUserError(27);
                param.setProviderError(0);
                param.setDeliveryError(0);
                param.setNetworkError(0);
                param.setResult(1);
                param.setResultDescription(DiameterStatus.DIAMETER_ERROR_ABSENT_USER.toString());
                break;
        }
    }
    public JamModel getJamModel() {
        return jamModel;
    }

    public String getErrorServerNetworkDown(){
    try {
        setStatus(1);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        return objectMapper.writeValueAsString(responseDto);
    } catch (Exception e) {
        e.printStackTrace();
        return "";
    }
}

    public API getApi() {
        return api;
    }


}
