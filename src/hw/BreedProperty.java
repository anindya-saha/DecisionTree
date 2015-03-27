package hw;

public class BreedProperty extends AbstractProperty {

	public static final String BREED = "Breed";
	public static final String Pomeranian = "Pomeranian";
	public static final String Chihuahua = "Chihuahua";
	public static final String AustralianShepherd = "AustralianShepherd";
	public static final String PitBull = "PitBull";

	public BreedProperty(String value) {
		super(value);
	}

	@Override
	public boolean isLegalValue(String value) {
		return(value.equals(Pomeranian) || value.equals(Chihuahua) || value.equals(AustralianShepherd) || value.equals(PitBull));
	}

	@Override
	public String getName() {
		return BREED;
	}

}
