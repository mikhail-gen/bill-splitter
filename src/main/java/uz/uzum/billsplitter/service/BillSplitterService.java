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
import uz.uzum.billsplitter.exception.InvalidCommissionRateException;
import uz.uzum.billsplitter.exception.InvalidDishCostException;
import uz.uzum.billsplitter.exception.NullOrEmptyNameException;
import uz.uzum.billsplitter.exception.ZeroGuestsException;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BillSplitterService {

    public BillSplitterResponse splitBill(BillSplitterRequest request) {

        validateRequest(request);

        int guestCount = request.getGuests().size();

        double sharedTotal = sumDishCosts(request.getSharedDishes());
        double sharedPerGuest = sharedTotal / guestCount;

        List<GuestBill> results = request.getGuests().stream()
            .map(guest -> computeGuestBill(guest, sharedPerGuest, request.getCommissionRate()))
            .toList();

        return new BillSplitterResponse(results);
    }

    private void validateRequest(BillSplitterRequest request) {

        if (request.getGuests() == null || request.getGuests().isEmpty()) {
            throw new ZeroGuestsException();
        }

        if (request.getCommissionRate() < 0 || request.getCommissionRate() > 100) {
            throw new InvalidCommissionRateException(request.getCommissionRate());
        }

        validateDishList(request.getSharedDishes());
        request.getGuests().forEach(this::validateGuest);
    }

    private void validateGuest(GuestRequest guest) {
        if (guest.getName() == null || guest.getName().isBlank()) {
            throw new NullOrEmptyNameException("Guest");
        }
        validateDishList(guest.getDishes());
    }

    private void validateDishList(List<DishRequest> dishes) {
        dishes.forEach(dish -> {
            if (dish.getCost() < 0) {
                throw new InvalidDishCostException(dish.getName(), dish.getCost());
            }
        });
    }

    private double sumDishCosts(List<DishRequest> dishes) {
        return dishes.stream()
            .mapToDouble(DishRequest::getCost)
            .sum();
    }

    private GuestBill computeGuestBill(GuestRequest guest, double sharedPerGuest, double commissionRate) {

        double individualTotal = sumDishCosts(guest.getDishes());

        double commission = (individualTotal + sharedPerGuest) * (commissionRate / 100.0);

        double finalTotal = individualTotal + sharedPerGuest + commission;

        return new GuestBill(
            guest.getName(),
            individualTotal,
            sharedPerGuest,
            commission,
            finalTotal
        );
    }
}