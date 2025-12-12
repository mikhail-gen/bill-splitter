package uz.uzum.billsplitter.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import uz.uzum.billsplitter.dto.request.BillSplitterRequest;
import uz.uzum.billsplitter.dto.request.DishRequest;
import uz.uzum.billsplitter.dto.request.GuestRequest;
import uz.uzum.billsplitter.dto.response.BillSplitterResponse;
import uz.uzum.billsplitter.dto.response.GuestBill;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillSplitterService {

    public BillSplitterResponse splitBill(BillSplitterRequest request) {

        int guestCount = request.getGuests().size();

        double sharedTotal = sumDishCosts(request.getSharedDishes());
        double sharedPerGuest = sharedTotal / guestCount;

        List<GuestBill> results = request.getGuests().stream()
            .map(guest -> computeGuestBill(
                guest, sharedPerGuest, request.getCommissionRate()
            ))
            .toList();

        return new BillSplitterResponse(results);
    }

    private double sumDishCosts(List<DishRequest> dishes) {
        return dishes.stream()
            .mapToDouble(DishRequest::getCost)
            .sum();
    }

    private GuestBill computeGuestBill(
        GuestRequest guest, double sharedPerGuest, double commissionRate
    ) {
        double individualTotal = sumDishCosts(guest.getDishes());
        double commission = (individualTotal + sharedPerGuest) * (commissionRate / 100.0);

        return new GuestBill(
            guest.getName(),
            individualTotal,
            sharedPerGuest,
            commission,
            individualTotal + sharedPerGuest + commission
        );
    }
}