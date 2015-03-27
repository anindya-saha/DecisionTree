package hw;

public class SexProperty extends AbstractProperty {

	public static final String SEX = "Sex";
	public static final String Male = "Male";
	public static final String Female = "Female";

	public SexProperty(String value) {
		super(value);
	}

	@Override
	public boolean isLegalValue(String value) {
		return(value.equals(Male) || value.equals(Female));
	}

	@Override
	public String getName() {
		return SEX;
	}

}
