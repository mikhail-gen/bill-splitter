package uz.uzum.billsplitter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.uzum.billsplitter.dto.request.BillSplitterRequest;
import uz.uzum.billsplitter.dto.request.DishRequest;
import uz.uzum.billsplitter.dto.request.GuestRequest;
import uz.uzum.billsplitter.dto.response.BillSplitterResponse;
import uz.uzum.billsplitter.exception.InvalidCommissionRateException;
import uz.uzum.billsplitter.exception.InvalidDishCostException;
import uz.uzum.billsplitter.exception.NullOrEmptyNameException;
import uz.uzum.billsplitter.exception.ZeroGuestsException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BillSplitterServiceTest {

    BillSplitterService service;

    @BeforeEach
    void setUp() {
        service = new BillSplitterService();
    }

    @Test
    void shouldCalculateBillForSingleGuestNoSharedDishes() {
        BillSplitterRequest request = new BillSplitterRequest();
        request.setCommissionRate(10.0);
        request.setGuests(List.of(
            new GuestRequest("Alice", List.of(new DishRequest("Pizza", 100.0)))
        ));
        request.setSharedDishes(Collections.emptyList());

        BillSplitterResponse response = service.splitBill(request);

        assertThat(response.getGuests()).hasSize(1);
        assertThat(response.getGuests().get(0).getFinalTotal()).isEqualTo(110.0);
    }

    @Test
    void shouldCalculateBillWithSharedDishesMultipleGuests() {
        BillSplitterRequest request = new BillSplitterRequest();
        request.setCommissionRate(10.0);
        request.setGuests(List.of(
            new GuestRequest("Alice", List.of(new DishRequest("Pizza", 100.0))),
            new GuestRequest("Bob", List.of(new DishRequest("Salad", 50.0)))
        ));
        request.setSharedDishes(List.of(
            new DishRequest("Wine", 200.0)
        ));

        BillSplitterResponse response = service.splitBill(request);

        assertThat(response.getGuests()).hasSize(2);
        assertThat(response.getGuests().get(0).getFinalTotal()).isEqualTo(220.0);
        assertThat(response.getGuests().get(1).getFinalTotal()).isEqualTo(165.0);
    }

    @Test
    void shouldThrowZeroGuestsException() {
        BillSplitterRequest request = new BillSplitterRequest();
        request.setGuests(Collections.emptyList());
        request.setCommissionRate(10.0);

        assertThatThrownBy(() -> service.splitBill(request))
            .isInstanceOf(ZeroGuestsException.class);
    }

    @Test
    void shouldThrowInvalidCommissionRateException() {
        BillSplitterRequest request = new BillSplitterRequest();
        request.setGuests(List.of(new GuestRequest("Alice", List.of())));
        request.setCommissionRate(150.0);

        assertThatThrownBy(() -> service.splitBill(request))
            .isInstanceOf(InvalidCommissionRateException.class);
    }

    @Test
    void shouldThrowInvalidDishCostExceptionForNegativeDish() {
        BillSplitterRequest request = new BillSplitterRequest();
        request.setCommissionRate(10.0);
        request.setGuests(List.of(
            new GuestRequest("Alice", List.of(new DishRequest("Pizza", -50.0)))
        ));
        request.setSharedDishes(Collections.emptyList());

        assertThatThrownBy(() -> service.splitBill(request))
            .isInstanceOf(InvalidDishCostException.class);
    }

    @Test
    void shouldThrowNullOrEmptyNameExceptionForGuest() {
        BillSplitterRequest request = new BillSplitterRequest();
        request.setCommissionRate(10.0);
        request.setGuests(List.of(new GuestRequest("", List.of())));
        request.setSharedDishes(Collections.emptyList());

        assertThatThrownBy(() -> service.splitBill(request))
            .isInstanceOf(NullOrEmptyNameException.class);
    }
}