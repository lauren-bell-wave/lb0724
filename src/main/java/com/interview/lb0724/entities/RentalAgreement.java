package com.interview.lb0724.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class RentalAgreement {

    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int daysToRent;
    private int discountPercentage;
    private String checkoutDate;
    private String returnDate;
    private double dailyPrice;
    private int daysToCharge;
    private String preDiscountPrice;
    private String finalPrice;
    private String discountAmount;

    public static String toString(RentalAgreement rentalAgreement) {
        return "Tool code: " +
                rentalAgreement.getToolCode() +
                System.lineSeparator() +
                "Tool Type: " +
                rentalAgreement.getToolType() +
                System.lineSeparator() +
                "Tool Brand: " +
                rentalAgreement.getToolBrand() +
                System.lineSeparator() +
                "Rental Days: " +
                rentalAgreement.getDaysToRent() +
                System.lineSeparator() +
                "Checkout Date: " +
                rentalAgreement.getCheckoutDate() +
                System.lineSeparator() +
                "Due Date: " +
                rentalAgreement.getReturnDate() +
                System.lineSeparator() +
                "Daily Rental Charge: $" +
                rentalAgreement.getDailyPrice() +
                System.lineSeparator() +
                "Charge Days: " +
                rentalAgreement.getDaysToCharge() +
                System.lineSeparator() +
                "Pre-Discount Charge: $" +
                rentalAgreement.getPreDiscountPrice() +
                System.lineSeparator() +
                "Discount Percentage: " +
                rentalAgreement.getDiscountPercentage() + "%" +
                System.lineSeparator() +
                "Discount Amount: $" +
                rentalAgreement.getDiscountAmount() +
                System.lineSeparator() +
                "Final Charge: $" +
                rentalAgreement.getFinalPrice() +
                System.lineSeparator();
    }
}
