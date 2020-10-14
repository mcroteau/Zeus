package xyz.ioc.model;

import java.sql.Timestamp;
import java.util.List;

public class ReCaptchaOutput {

    boolean success;
    Timestamp challengeTs;
    String hostname;
    List<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Timestamp getChallengeTs() {
        return challengeTs;
    }

    public void setChallengeTs(Timestamp challengeTs) {
        this.challengeTs = challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public String toString(){
        return "recaptcha succeeded : " + this.success;
    }
}
