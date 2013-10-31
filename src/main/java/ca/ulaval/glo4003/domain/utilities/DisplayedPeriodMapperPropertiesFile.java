package ca.ulaval.glo4003.domain.utilities;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

@Component
public class DisplayedPeriodMapperPropertiesFile extends PropertiesFileMapper implements DisplayedPeriodMapper {

	public DisplayedPeriodMapperPropertiesFile() {
		this.propertiesFileName = "displayed-period.properties";
	}

	@Override
	public String getName(Integer numberOfDay) throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		try {
			return getValue(numberOfDay.toString());
		} catch (KeyValueDoesntExistException e) {
			throw new DisplayedPeriodDoesntExistInPropertiesFileException();
		}
	}

	@Override
	public Integer getNumberOfDays(String name) throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		try {
			return new Integer(getKey(name));
		} catch (KeyValueDoesntExistException e) {
			throw new DisplayedPeriodDoesntExistInPropertiesFileException();
		}
	}

	@Override
	public List<String> getAllNames() {
		List<String> list = transform(getAllNumberOfDays(), new Function<Integer, String>() {
			public String apply(Integer number) {
				try {
					return getName(number);
				} catch (RuntimeException
						| DisplayedPeriodDoesntExistInPropertiesFileException e) {
					e.printStackTrace();
					return "Erreur";
				}
			}
		});
		return list;
	}

	@Override
	public List<Integer> getAllNumberOfDays() {
		List<Integer> list = transform(getAllKeys(), new Function<String, Integer>() {
			public Integer apply(String number) {
				return new Integer(number);
			}
		});
		
		Ordering<Integer> orderedList = new Ordering<Integer>() {
			public int compare(Integer nb1, Integer nb2) {
				return nb1.compareTo(nb2);
			}
		};

		return orderedList.nullsLast().sortedCopy(list);
	}
}
