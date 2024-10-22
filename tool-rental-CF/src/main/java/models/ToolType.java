package models;

import java.math.BigDecimal;

public record ToolType(String toolType, BigDecimal dailyCharge, boolean weekendCharge, boolean holidayCharge) {
}
