package ca.ulaval.glo4003.domain.cart;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Cart {
	private Set<SectionForCart> sections;

	public Cart() {
		this.sections = new HashSet<SectionForCart>();
	}
	
	public Boolean containTickets() {
		return (sections.size() != 0) ? true : false;
	}

	public Set<SectionForCart> getSections() throws NoTicketsInCartException {
		if (!containTickets()) {
			throw new NoTicketsInCartException();
		}
		
		return sections;
	}

	public Double getCumulativePrice() throws NoTicketsInCartException {
		if (!containTickets()) {
			throw new NoTicketsInCartException();
		}
		
		Double cumulativePrice = 0.0;
		for (SectionForCart section : this.sections) {
			cumulativePrice += section.getSubtotal();
		}
		return cumulativePrice;
	}

	public void empty() {
		this.sections = new HashSet<SectionForCart>();
	}

	public void addSection(SectionForCart sectionForCart) {
		if (!this.sections.contains(sectionForCart)) {
			this.sections.add(sectionForCart);
		} else {
			for (SectionForCart sectionInList : this.sections) {
				if (sectionInList.equals(sectionForCart)) {
					sectionInList.addElements(sectionForCart);
				}
			}
		}
	}
}


