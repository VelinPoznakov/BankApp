package Entities;

import Entities.Enums.CardProvider;

import java.io.Serializable;

public class Card implements Serializable {

    private int id;
    private long cardNumber;
    private int cvvCode;
    private String expirationDate;
    public CardProvider cardProvider;
    public boolean isCardActive = true;
    private int userId;
    private String IBANName;

    public Card(int id,
                long cardNumber,
                int cvvCode,
                String expirationDate,
                CardProvider cardProvider,
                int userId,
                String IBANName) {

        setId(id);
        setCardNumber(cardNumber);
        setCvvCode(cvvCode);
        setExpirationDate(expirationDate);
        this.cardProvider = cardProvider;
        setUserId(userId);
        setIBANName(IBANName);

    }

    public String CardDetails(){
        return "Card Number " + getMaskedCardNumber() + "\n"
                + "CVV Code " + getCvvCode() + "\n"
                + "Expiration Date " + getExpirationDate() + "\n"
                + "Card Provider " + cardProvider.toString() + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(int cvvCode) {
        this.cvvCode = cvvCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIBANName() {
        return IBANName;
    }

    public void setIBANName(String IBANName) {
        this.IBANName = IBANName;
    }

    public String getMaskedCardNumber() {
        String number = String.valueOf(getCardNumber());

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < number.length(); i++) {

            if (i < 4) {
                result.append(number.charAt(i));
            } else {
                result.append("*");
            }

            // add space every 4 chars
            if ((i + 1) % 4 == 0 && i != number.length() - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }
}
