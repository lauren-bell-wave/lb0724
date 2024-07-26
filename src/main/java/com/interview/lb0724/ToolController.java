package com.interview.lb0724;

import com.interview.lb0724.entities.RentalAgreement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
class ToolController {
    private final RentalService rentalService;

    ToolController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

@GetMapping("/tool/{toolCode}")
    public RentalAgreement rentTool(@PathVariable String toolCode,
                                    @RequestParam(name="days") int daysToRent,
                                    @RequestParam(name="date") String date,
                                    @RequestParam(required = false, name = "discount", defaultValue = "0") int discount) throws RuntimeException, ParseException {
        return rentalService.rent(toolCode, daysToRent, discount, date);
}

}