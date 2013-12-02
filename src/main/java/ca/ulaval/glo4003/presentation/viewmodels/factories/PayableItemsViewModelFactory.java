package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForPaymentViewModel;
import ca.ulaval.glo4003.utilities.Calculator;

@Component
public class PayableItemsViewModelFactory {
	
	@Inject
	SectionForPaymentViewModelFactory sectionForPaymentViewModelFactory;

	public PayableItemsViewModel createViewModel(
			Set<SectionForCart> sectionFCs, Double cumulativePrice) {
		List<SectionForPaymentViewModel> sectionForPaymentVMs = new ArrayList<>();
		for (SectionForCart sectionFC : sectionFCs) {
			sectionForPaymentVMs.add(sectionForPaymentViewModelFactory.createViewModel(sectionFC));
		}
		
		String cumulativePriceFR = Calculator.toPriceFR(cumulativePrice);

		PayableItemsViewModel payableItemsVM = new PayableItemsViewModel();
		
		payableItemsVM.setSections(sectionForPaymentVMs);
		payableItemsVM.setCumulativePrice(cumulativePriceFR);
		
		return payableItemsVM;
	}
}
