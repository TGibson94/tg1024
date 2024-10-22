package models;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@Builder
public class RentalAgreement {
    private final Tool tool;
    private final int rentalDays;
    private final LocalDate checkOutDate;
    private final LocalDate dueDate;
    private final int chargeDays;
    private final BigDecimal preDiscountCharge;
    private final int discountPercent;
    private final BigDecimal discountAmount;
    private final BigDecimal finalCharge;

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();

        percentFormatter.setMinimumFractionDigits(0);
        percentFormatter.setMaximumFractionDigits(0);

        return """
            Rental Agreement:
                Tool code: %s,
                Tool type: %s,
                Tool brand: %s,
                Rental days: %d,
                Check out date: %s,
                Due date: %s,
                Daily rental charge: %s,
                Charge days: %s,
                Pre-discount charge: %s,
                Discount percent : %s,
                Discount amount: %s
                Final charge: %s
            """.formatted(
                tool.toolCode(),
                tool.toolType().toolType(),
                tool.brand(),
                rentalDays,
                checkOutDate.format(dateFormatter),
                dueDate.format(dateFormatter),
                tool.toolType().dailyCharge(),
                chargeDays,
                currencyFormatter.format(preDiscountCharge),
                percentFormatter.format(discountPercent / 100.0),
                currencyFormatter.format(discountAmount),
                currencyFormatter.format(finalCharge)
        );
    }
}