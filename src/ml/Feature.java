package ml;

public class Feature {
	
	public static final String AGE = "Age";
	public static final String SEX = "Sex";
	public static final String BREED = "Breed";
	
	private String name = null;
	private String value = null;

	public Feature(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public final String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
}
