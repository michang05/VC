
package mobi.victorchandler.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Class for providing attributes in the UI Field.
 * Creation of accounts
 * @author riveram
 *
 */
public class CreateAccountPojo implements Parcelable {

    private String username;
    private String password;
    private String email;
    private String secQuestion;
    private int secQuestionId;
    private String secAnswer;
    private String title;
    private String firstname;
    private String lastname;
    private String address1;
    private String address2;
    private String postCode;
    private String country;
    private int countryId;
    private String currency;
    private int currencyId;
    private String dateOfBirth;
    private String gender;
    private String locale;
    private String mobile;
    private String region;
    private String city;
    private String sendEmail;
    private String sendPhone;
    private String sendSms;

    /**
     * This field is needed for Android to be able to create new objects,
     * individually or as arrays. This also means that you can use use the
     * default constructor to create the object and use another method to
     * hydrate it as necessary. I just find it easier to use the constructor. It
     * makes sense for the way my brain thinks ;-)
     */
    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public CreateAccountPojo createFromParcel(Parcel in) {
                    return new CreateAccountPojo(in);
                }

                public CreateAccountPojo[] newArray(int size) {
                    return new CreateAccountPojo[size];
                }
            };

    /**
     * Standard basic constructor for non-parcel object creation
     */
    public CreateAccountPojo() {

    }

    public CreateAccountPojo(Parcel in) {
        readFromParcel(in);
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecQuestion() {
        return secQuestion;
    }

    public void setSecQuestion(String secQuestion) {
        this.secQuestion = secQuestion;
    }

    public int getSecQuestionId() {
        return secQuestionId;
    }
    public void setSecQuestionId(int secQuestionId) {
        this.secQuestionId = secQuestionId;
    }
    
    public String getSecAnswer() {
        return secAnswer;
    }

    public void setSecAnswer(String secAnswer) {
        this.secAnswer = secAnswer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getSendPhone() {
        return sendPhone;
    }

    public void setSendPhone(String sendPhone) {
        this.sendPhone = sendPhone;
    }

    public String getSendSms() {
        return sendSms;
    }

    public void setSendSms(String sendSms) {
        this.sendSms = sendSms;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        // must write in order

        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(secQuestion);
        dest.writeInt(secQuestionId);
        dest.writeString(secAnswer);
        dest.writeString(title);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeString(postCode);
        dest.writeString(country);
        dest.writeInt(countryId);
        dest.writeString(currency);
        dest.writeInt(currencyId);
        dest.writeString(dateOfBirth);
        dest.writeString(gender);
        dest.writeString(locale);
        dest.writeString(mobile);
        dest.writeString(region);
        dest.writeString(city);
        dest.writeString(sendEmail);
        dest.writeString(sendPhone);
        dest.writeString(sendSms);

    }

    private void readFromParcel(Parcel in) {
        username = in.readString();
        password = in.readString();
        email = in.readString();
        secQuestion = in.readString();
        secQuestionId =in.readInt();
        secAnswer = in.readString();
        title = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        postCode = in.readString();
        country = in.readString();
        countryId = in.readInt();
        currency = in.readString();
        currencyId = in.readInt();
        dateOfBirth = in.readString();
        gender = in.readString();
        locale = in.readString();
        mobile = in.readString();
        region = in.readString();
        city = in.readString();
        sendEmail = in.readString();
        sendPhone = in.readString();
        sendSms = in.readString();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("username: ").append(username).append("\n");
        sb.append("password: ").append(password).append("\n");
        sb.append("email: ").append(email).append("\n");
        sb.append("secQuestion: ").append(secQuestion).append("\n");
        sb.append("secAnswer: ").append(secAnswer).append("\n");
        sb.append("title: ").append(title).append("\n");
        sb.append("firstname: ").append(firstname).append("\n");
        sb.append("lastname: ").append(lastname).append("\n");
        sb.append("address1: ").append(address1).append("\n");
        sb.append("address2: ").append(address2).append("\n");
        sb.append("postCode: ").append(postCode).append("\n");
        sb.append("country: ").append(country).append(",id: " + countryId).append("\n");
        sb.append("currency: ").append(currency).append(",id: " + currencyId).append("\n");
        sb.append("dateOfBirth: ").append(dateOfBirth).append("\n");
        sb.append("gender: ").append(gender).append("\n");
        sb.append("mobile: ").append(mobile).append("\n");
        sb.append("region/county: ").append(region).append("\n");
        sb.append("city/town: ").append(city).append("\n");
        sb.append("sendEmail: ").append(sendEmail).append("\n");
        sb.append("sendSms: ").append(sendSms).append("\n");
        sb.append("sendPhone: ").append(sendPhone).append("\n");
        sb.append("locale: ").append(locale);

        return sb.toString();
    }
}
