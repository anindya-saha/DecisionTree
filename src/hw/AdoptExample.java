package hw;

import java.util.LinkedHashSet;
import java.util.Set;

public class AdoptExample extends AbstractExample {

	public static final String YES = "Y";
	public static final String NO = "N";
	
	public AdoptExample(String category, AbstractProperty... propertyList) throws IllegalArgumentException {
		super(category, propertyList);
	}
	
	@Override
	public Set<String> getPropertyNames() {
		Set<String> set = new LinkedHashSet<String>();
		set.add(AgeProperty.AGE);
		set.add(SexProperty.SEX);
		set.add(BreedProperty.BREED);
		return set;
	}

	@Override
	public boolean isLegalCategory(String value) {
		return(value.equals(YES) || value.equals(NO));
	}

}
