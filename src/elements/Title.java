package elements;

public class Title extends Element{
	
	private String title;
	
	public Title(String title){
		this.title = title;
	}

	@Override
	public String getElement() {
		return title;
	}

}
