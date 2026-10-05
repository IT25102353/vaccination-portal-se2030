package com.se2030.vaccination_portal.pattern;

import com.se2030.vaccination_portal.pattern.factory.AdverseReactionReportGenerator;
import com.se2030.vaccination_portal.pattern.factory.AppointmentReportGenerator;
import com.se2030.vaccination_portal.pattern.factory.GeneralReportGenerator;
import com.se2030.vaccination_portal.pattern.factory.InventoryReportGenerator;
import com.se2030.vaccination_portal.pattern.factory.ReportGeneratorFactory;
import com.se2030.vaccination_portal.service.AdverseReactionService;
import com.se2030.vaccination_portal.service.AppointmentService;
import com.se2030.vaccination_portal.service.DoseRecordService;
import com.se2030.vaccination_portal.service.VaccinationCenterService;
import com.se2030.vaccination_portal.service.VaccineStockService;
import com.se2030.vaccination_portal.model.VaccineStock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FactoryPatternTest {

    private final VaccineStockService stockService = Mockito.mock(VaccineStockService.class);
    private final ReportGeneratorFactory factory = new ReportGeneratorFactory(
            stockService,
            Mockito.mock(AppointmentService.class),
            Mockito.mock(AdverseReactionService.class),
            Mockito.mock(DoseRecordService.class),
            Mockito.mock(VaccinationCenterService.class));

    @Test
    void factoryReturnsTheRightGeneratorForEachType() {
        assertInstanceOf(InventoryReportGenerator.class, factory.createGenerator("INVENTORY"));
        assertInstanceOf(AppointmentReportGenerator.class, factory.createGenerator("APPOINTMENTS"));
        assertInstanceOf(AdverseReactionReportGenerator.class, factory.createGenerator("ADVERSE_REACTIONS"));
        assertInstanceOf(GeneralReportGenerator.class, factory.createGenerator("GENERAL"));
    }

    @Test
    void unknownOrMissingTypeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> factory.createGenerator("NOPE"));
        assertThrows(IllegalArgumentException.class, () -> factory.createGenerator(null));
    }

    @Test
    void inventoryGeneratorBuildsSummaryFromStock() {
        Mockito.when(stockService.getAllStocks()).thenReturn(List.of(
                new VaccineStock("Pfizer", "B1", "P", 40, LocalDate.now().minusYears(2), LocalDate.now().minusDays(1), "R1"),
                new VaccineStock("Pfizer", "B2", "P", 60, LocalDate.now().minusDays(5), LocalDate.now().plusDays(300), "R1")));

        String summary = factory.createGenerator("INVENTORY").generateSummary(LocalDate.now(), LocalDate.now());

        assertTrue(summary.contains("2 batches"));
        assertTrue(summary.contains("100 doses"));
        assertTrue(summary.contains("1 expired"));
    }
}
