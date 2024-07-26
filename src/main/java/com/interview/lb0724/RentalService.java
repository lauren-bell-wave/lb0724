package com.interview.lb0724;


import com.interview.lb0724.entities.RentalAgreement;
import com.interview.lb0724.entities.RentalTerms;
import com.interview.lb0724.entities.Tool;
import com.interview.lb0724.exceptions.OutsidePermitedRangeException;
import com.interview.lb0724.exceptions.RentalServiceDoesNotExistException;
import com.interview.lb0724.exceptions.ToolDoesNotExistException;
import com.interview.lb0724.repositories.RentalServiceRepository;
import com.interview.lb0724.repositories.ToolRepository;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

@Service
public class RentalService {

   private final RentalServiceRepository rentalServiceRepository;
    private final ToolRepository toolRepository;
    public final MonthDay indepenceDay = MonthDay.of(7, 4);


    RentalService(RentalServiceRepository rentalServiceRepository, ToolRepository toolRepository) {
        this.rentalServiceRepository = rentalServiceRepository;
        this.toolRepository = toolRepository;
    }

    public RentalAgreement rent(String toolCode, int daysToRent, int discount, String date) throws RuntimeException, ParseException {
        Tool validTool = validate(toolCode, daysToRent, discount);
        RentalTerms rentalTerms = rentalServiceRepository.findById(validTool.getToolType()).orElseThrow(() ->
                new RentalServiceDoesNotExistException(("There are not rental terms for the ToolCode you have selected.")));
            return checkout(validTool, rentalTerms, daysToRent, discount, date);

    }


    public Tool validate(String toolCode, int daysToRent, int discount) throws RuntimeException {
        if(daysToRent <= 0){
            throw new OutsidePermitedRangeException("Days to rent must be 1 or greater.");
        }
        if (discount < 0 || discount > 100){
           throw new OutsidePermitedRangeException("Discount percentage must be between 0 and 100.");
        }
        return toolRepository.findById(toolCode).orElseThrow(() -> new ToolDoesNotExistException("ToolCode isn't valid or does not exist."));

    }

    public RentalAgreement checkout(Tool validTool, RentalTerms rentalTerms, int daysToRent, int discount, String date) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate checkoutDate = LocalDate.parse(date, formatter);
        RentalAgreement rentalAgreement = RentalAgreement.builder()
                .toolCode(validTool.getToolCode())
                .toolBrand(validTool.getBrand())
                .toolType(validTool.getToolType())
                .dailyPrice(rentalTerms.getDailyCharge())
                .daysToRent(daysToRent)
                .checkoutDate(date)
                .discountPercentage(discount).build();

        //Calculate returnDate

        LocalDate returnDate = checkoutDate.plusDays(daysToRent); //Count the day you rent it
        rentalAgreement.setReturnDate(returnDate.format(formatter));

        //Calculate days to charge
        int daysToCharge = calculateDaysToCharge(rentalTerms, checkoutDate, returnDate);
        rentalAgreement.setDaysToCharge(daysToCharge);

        //Calculate amounts to charge, round after calculations
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double preDiscountPrice = daysToCharge * rentalAgreement.getDailyPrice();
        rentalAgreement.setPreDiscountPrice(df.format(preDiscountPrice));
        double discountAmount = preDiscountPrice * rentalAgreement.getDiscountPercentage()/100;
        rentalAgreement.setDiscountAmount(df.format(discountAmount));
        double finalPrice = preDiscountPrice- discountAmount;
        rentalAgreement.setFinalPrice(df.format(finalPrice));

        System.out.println(RentalAgreement.toString(rentalAgreement));
        return rentalAgreement;
    }

    public int calculateDaysToCharge(RentalTerms rentalTerms, LocalDate checkoutDate, LocalDate returnDate) {
        int daysToCharge = 0;
        //Everything is a weekDayCharge currently

        for(LocalDate currentDate = checkoutDate; currentDate.isBefore(returnDate); currentDate =currentDate.plusDays(1)){
            MonthDay currentMonthDay = MonthDay.from(currentDate);
            int dayOfWeek = currentDate.get(ChronoField.DAY_OF_WEEK);
            boolean memorialDay = currentDate.getMonth().getValue() == 9 && dayOfWeek == 1 && currentDate.getDayOfMonth() <=7;
            if((currentMonthDay.equals(indepenceDay) && dayOfWeek <= 5) || memorialDay ){
                if(!rentalTerms.isHolidayCharge()){
                    break;
                } else if(rentalTerms.isWeekdayCharge()){
                    daysToCharge++;
                    System.out.println("Charge holiday weekday");
                }
            } else if(currentMonthDay.equals(indepenceDay) && dayOfWeek > 5){
                if (rentalTerms.isHolidayCharge() && rentalTerms.isWeekendCharge()) {
                    daysToCharge++;
                    System.out.println("Charge holiday weekend");
                } else if(!rentalTerms.isHolidayCharge() && rentalTerms.isWeekendCharge()) {
                    break;
                } else if(!rentalTerms.isHolidayCharge()  && dayOfWeek == 6) {
                    //Not a weekend charge and a Saturday
                    if(daysToCharge > 1){
                        //Have already charged for friday
                        System.out.println("Remove Charge holiday Saturday");
                        daysToCharge--;
                    }

                } else if(!rentalTerms.isHolidayCharge()) {
                    //Not a weekend charge and a Sunday
                    if(returnDate.isAfter(currentDate)){
                        daysToCharge--;
                        System.out.println("Remove Charge holiday Sunday");
                    }
                }
            } else {
                //Check for holiday date first
                if (rentalTerms.isWeekdayCharge() && currentDate.get(ChronoField.DAY_OF_WEEK) <= 5) {
                    daysToCharge++;
                    System.out.println("Charge normal weekday");
                } else if (rentalTerms.isWeekendCharge() && currentDate.get(ChronoField.DAY_OF_WEEK) > 5) {
                    daysToCharge++;
                    System.out.println("Charge normal weekend");
                }
            }
        }
        return daysToCharge;
    }
}
