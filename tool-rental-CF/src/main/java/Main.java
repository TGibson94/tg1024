import models.RentalAgreement;
import models.Tool;
import service.ToolService;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class Main {
    private static final ToolService toolService = new ToolService();
    private final static int JULY_FOURTH_DAY_OF_YEAR = 185;
    //in a real application this would probably take in data from a form using an api call. for this exercise I'm assuming
    //for complexities' sake these arguments come through in the same order every time, otherwise this will require validating
    //types along with order
    public static String main(String[] args) {
        //set everything to their correct typing
        String toolCode = args[0];
        int rentalDays = Integer.parseInt(args[1]);
        int discount = Integer.parseInt(args[2]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        LocalDate checkOutDate = LocalDate.parse(args[3], formatter);

        validateCheckout(toolCode,rentalDays,discount);

        RentalAgreement rentalAgreement = generateRentalAgreement(toolCode, rentalDays, discount, checkOutDate);

        System.out.println(rentalAgreement.toString());
        return rentalAgreement.toString();
    }

    private static void validateCheckout(String toolCode, int rentalDays, int discount) {
        if(rentalDays < 1){
            throw new RuntimeException("Rental days must be greater or equal to one!");
        }
        if(discount > 100 || discount < 0){
            throw new RuntimeException("Percent discount cannot be less than 0% or more than 100%!");
        }
        if(!toolService.getAllToolCodes().contains(toolCode)){
            throw new RuntimeException("Tool not found!");
        }
    }

    public static RentalAgreement generateRentalAgreement(String toolCode, int rentalDays, int discount, LocalDate checkOutDate) {
        Tool tool = getToolByCode(toolCode);
        int chargeDays = getChargeDays(tool,rentalDays, checkOutDate);
        BigDecimal preDiscountCharge = calculatePreDiscountCharge(tool,chargeDays);
        BigDecimal discountAmount = calculateDiscountAmount(preDiscountCharge, discount);
        return RentalAgreement.builder()
                .tool(tool)
                .rentalDays(rentalDays)
                .checkOutDate(checkOutDate)
                .dueDate(getDueDate(checkOutDate, rentalDays))
                .chargeDays(chargeDays)
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(discount)
                .discountAmount(discountAmount)
                .finalCharge(calculateFinalCharge(preDiscountCharge, discountAmount))
                .build();
    }

    private static BigDecimal calculateFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount) {
        return preDiscountCharge.subtract(discountAmount);
    }

    private static LocalDate getDueDate(LocalDate checkOutDate, int rentalDays) {
        return checkOutDate.plusDays(rentalDays);
    }

    private static BigDecimal calculateDiscountAmount(BigDecimal preDiscountCharge, int discount) {
        double percent = (double) discount/100;
        return preDiscountCharge.multiply(BigDecimal.valueOf(percent));
    }

    private static BigDecimal calculatePreDiscountCharge(Tool tool, int chargeDays) {
        return tool.toolType().dailyCharge().multiply(BigDecimal.valueOf(chargeDays));
    }

    private static int getChargeDays(Tool tool, int rentalDays, LocalDate rentalDate) {
        int chargeDays = rentalDays;
        int holidaysInRange = getHolidaysInRange(rentalDate, rentalDays);
        int weekendsInRange = getWeekendsInRange(rentalDate, rentalDays);
        if(!tool.toolType().holidayCharge()){
            chargeDays -= holidaysInRange;
        }
        if(!tool.toolType().weekendCharge()){
            chargeDays -= weekendsInRange;
        }
        return chargeDays;
    }

    private static int getWeekendsInRange(LocalDate rentalDate, int rentalDays) {
        int weekendDays = 0;
        LocalDate checkDay = rentalDate;
        while(checkDay.isBefore(rentalDate.plusDays(rentalDays))){
            switch (rentalDate.getDayOfWeek()) {
                case SUNDAY, SATURDAY:
                    weekendDays++;
                    break;
                default:
                    //do not increment
                    break;
            }
            checkDay = checkDay.plusDays(1);
        }
        return weekendDays;
    }
    private static int getHolidaysInRange(LocalDate rentalDate, int rentalDays) {
        int julyFourthDayofYear = JULY_FOURTH_DAY_OF_YEAR;
        LocalDate julyFourth = LocalDate.ofYearDay(rentalDate.getYear(), JULY_FOURTH_DAY_OF_YEAR);
        if(julyFourth.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            julyFourthDayofYear--;
        } else if(julyFourth.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            julyFourthDayofYear++;
        }

        int holidayDays = 0;
        LocalDate checkDay = rentalDate;
        while(checkDay.isBefore(rentalDate.plusDays(rentalDays))){
            if(checkDay.getDayOfYear() == julyFourthDayofYear){
                holidayDays++;
            }
            if(checkDay.getMonth().equals(Month.SEPTEMBER) && checkDay.equals(LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)))){
                holidayDays++;
            }
            checkDay = checkDay.plusDays(1);
        }
        return holidayDays;
    }

    private static Tool getToolByCode(String toolCode) {
        return toolService.getToolByCode(toolCode);
    }
}