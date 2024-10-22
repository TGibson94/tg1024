import static org.junit.jupiter.api.Assertions.*;

import models.RentalAgreement;
import models.Tool;
import models.ToolType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy");

    @Test
    void testCase1() {
        String[] args = {"JAKR", "5", "101", "9/3/15"};
        Exception exception = assertThrows(RuntimeException.class, () -> Main.main(args));
        assertNotNull(exception);
    }

    @Test
    void testCase2() {
        String[] args = {"LADW", "3", "10", "7/2/20"};
        RentalAgreement expected = RentalAgreement.builder()
                .tool(new Tool("LADW",new ToolType("Ladder", new BigDecimal("1.99"),true,false),"Werner"))
                .rentalDays(3)
                .checkOutDate(LocalDate.parse("7/2/20", DATE_FORMATTER))
                .dueDate(LocalDate.parse("7/5/20", DATE_FORMATTER))
                .chargeDays(2)
                .preDiscountCharge(BigDecimal.valueOf(3.98))
                .discountPercent(10)
                .discountAmount(BigDecimal.valueOf(0.40))
                .finalCharge(BigDecimal.valueOf(3.58))
                .build();
        assertEquals(expected.toString(), Main.main(args));
    }

    @Test
    void testCase3() {
        String[] args = {"CHNS", "5", "25", "7/2/15"};
        RentalAgreement expected = RentalAgreement.builder()
                .tool(new Tool("CHNS",new ToolType("Chainsaw", new BigDecimal("1.49"),false,true),"Stihl"))
                .rentalDays(5)
                .checkOutDate(LocalDate.parse("7/2/15", DATE_FORMATTER))
                .dueDate(LocalDate.parse("7/7/15", DATE_FORMATTER))
                .chargeDays(5)
                .preDiscountCharge(BigDecimal.valueOf(7.45))
                .discountPercent(25)
                .discountAmount(BigDecimal.valueOf(1.86))
                .finalCharge(BigDecimal.valueOf(5.59))
                .build();
        assertEquals(expected.toString(), Main.main(args));
    }

    @Test
    void testCase4() {
        String[] args = {"JAKD", "6", "0", "9/3/15"};
        RentalAgreement expected = RentalAgreement.builder()
                .tool(new Tool("JAKD",new ToolType("Jackhammer",new BigDecimal("2.99"),false,false),"DeWalt"))
                .rentalDays(6)
                .checkOutDate(LocalDate.parse("9/3/15", DATE_FORMATTER))
                .dueDate(LocalDate.parse("9/9/15", DATE_FORMATTER))
                .chargeDays(6)
                .preDiscountCharge(BigDecimal.valueOf(17.94))
                .discountPercent(0)
                .discountAmount(BigDecimal.valueOf(0.00))
                .finalCharge(BigDecimal.valueOf(17.94))
                .build();
        assertEquals(expected.toString(), Main.main(args));
    }

    @Test
    void testCase5() {
        String[] args = {"JAKR", "9", "0", "7/2/15"};
        RentalAgreement expected = RentalAgreement.builder()
                .tool(new Tool("JAKR",new ToolType("Jackhammer",new BigDecimal("2.99"),false,false),"Ridgid"))
                .rentalDays(9)
                .checkOutDate(LocalDate.parse("7/2/15", DATE_FORMATTER))
                .dueDate(LocalDate.parse("7/11/15", DATE_FORMATTER))
                .chargeDays(8)
                .preDiscountCharge(BigDecimal.valueOf(23.92))
                .discountPercent(0)
                .discountAmount(BigDecimal.valueOf(0.00))
                .finalCharge(BigDecimal.valueOf(23.92))
                .build();
        assertEquals(expected.toString(), Main.main(args));
    }

    @Test
    void testCase6() {
        String[] args = {"JAKR", "4", "50", "7/2/20"};
        RentalAgreement expected = RentalAgreement.builder()
                .tool(new Tool("JAKR",new ToolType("Jackhammer",new BigDecimal("2.99"),false,false),"Ridgid"))
                .rentalDays(4)
                .checkOutDate(LocalDate.parse("7/2/20", DATE_FORMATTER))
                .dueDate(LocalDate.parse("7/6/20", DATE_FORMATTER))
                .chargeDays(3)
                .preDiscountCharge(BigDecimal.valueOf(8.97))
                .discountPercent(50)
                .discountAmount(BigDecimal.valueOf(4.48))
                .finalCharge(BigDecimal.valueOf(4.48))
                .build();
        assertEquals(expected.toString(), Main.main(args));
    }
}
