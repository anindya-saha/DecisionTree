package hw;

public class AgeProperty extends AbstractProperty {

	public static final String AGE = "Age";

	public AgeProperty(String value) {
		super(value);
	}

	@Override
	public boolean isLegalValue(String value) {
		int age = -1;
		try {
			age = Integer.parseInt(value);
			return age > 0;
		} catch(NumberFormatException nfe) {
			return false;
		}
	}

	@Override
	public String getName() {
		return AGE;
	}
}
