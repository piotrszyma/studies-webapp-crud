package models.forms;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Entity;
import javax.validation.Constraint;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomp on 20.01.2017.
 */
public class SendPackage {

    String senderFirstName;
    String senderLastName;
    String senderRegion;
    String senderAddress;
    String senderPhone;
    String senderCountry;
    String senderEmail;

    String receiverFirstName;
    String receiverLastName;
    String receiverRegion;
    String receiverAddress;
    String receiverPhone;
    String receiverCountry;
    String receiverEmail;

    String packageWeight;
    String packageInsurance;
    String packageValue;


    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getSenderRegion() {
        return senderRegion;
    }

    public void setSenderRegion(String senderRegion) {
        this.senderRegion = senderRegion;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderCountry() {
        return senderCountry;
    }

    public void setSenderCountry(String senderCountry) {
        this.senderCountry = senderCountry;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverFirstName() {
        return receiverFirstName;
    }

    public void setReceiverFirstName(String receiverFirstName) {
        this.receiverFirstName = receiverFirstName;
    }

    public String getReceiverLastName() {
        return receiverLastName;
    }

    public void setReceiverLastName(String receiverLastName) {
        this.receiverLastName = receiverLastName;
    }

    public String getReceiverRegion() {
        return receiverRegion;
    }

    public void setReceiverRegion(String receiverRegion) {
        this.receiverRegion = receiverRegion;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(String packageWeight) {
        this.packageWeight = packageWeight;
    }

    public String getPackageInsurance() {
        return packageInsurance;
    }

    public void setPackageInsurance(String packageInsurance) {
        this.packageInsurance = packageInsurance;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public boolean isNotEmpty() {

        return true;
    }

    public List<ValidationError> validate() throws SQLException, NoSuchAlgorithmException {
        List<ValidationError> errors = new ArrayList<ValidationError>();

        if (senderFirstName.isEmpty()) errors.add(new ValidationError("senderFirstName", "empty"));
        if (senderLastName.isEmpty()) errors.add(new ValidationError("senderLastName", "empty"));
        if (senderRegion.isEmpty()) errors.add(new ValidationError("senderRegion", "empty"));
        if (senderAddress.isEmpty()) errors.add(new ValidationError("senderAddress", "empty"));
        if (senderPhone.isEmpty()) errors.add(new ValidationError("senderPhone", "empty"));
        if (senderCountry.isEmpty()) errors.add(new ValidationError("senderCountry", "empty"));
        if (senderEmail.isEmpty()) errors.add(new ValidationError("senderEmail", "empty"));
        if (receiverFirstName.isEmpty()) errors.add(new ValidationError("receiverFirstName", "empty"));
        if (receiverLastName.isEmpty()) errors.add(new ValidationError("receiverLastName", "empty"));
        if (receiverRegion.isEmpty()) errors.add(new ValidationError("receiverRegion", "empty"));
        if (receiverAddress.isEmpty()) errors.add(new ValidationError("receiverAddress", "empty"));
        if (receiverPhone.isEmpty()) errors.add(new ValidationError("receiverPhone", "empty"));
        if (receiverCountry.isEmpty()) errors.add(new ValidationError("receiverCountry", "empty"));
        if (receiverEmail.isEmpty()) errors.add(new ValidationError("receiverEmail", "empty"));
        if (packageWeight.isEmpty()) errors.add(new ValidationError("packageWeight", "empty"));
        if (packageInsurance.isEmpty()) errors.add(new ValidationError("packageInsurance", "empty"));
        if (packageValue.isEmpty()) errors.add(new ValidationError("packageValue", "empty"));

        return errors.isEmpty() ? null : errors;
    }

}


//        IN first_name_var  VARCHAR(30),
//        IN last_name_var VARCHAR(50),
//        IN region_var INT,
//        IN address_var     VARCHAR(50),
//        IN phone_var VARCHAR(15),
//        IN email_var VARCHAR(50),

//        IN first_name_r_var VARCHAR(30),
//        IN last_name_r_var VARCHAR(50),
//        IN region_r_var INT,
//        IN address_r_var   VARCHAR(50),
//        IN phone_r_var VARCHAR(15),
//        IN email_r_var VARCHAR(50),

//        IN insurance_var   ENUM ('yes', 'no'),
//        IN value_var INT, IN weight_var INT