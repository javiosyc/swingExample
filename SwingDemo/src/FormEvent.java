import java.util.EventObject;

public class FormEvent extends EventObject {

	private String name;
	private String occupation;

	private int ageCategpry;

	public FormEvent(Object source) {
		super(source);
	}

	public FormEvent(Object source, String name, String occupation,
			int ageCategpry) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCategpry = ageCategpry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getAgeCategpry() {
		return ageCategpry;
	}

}
