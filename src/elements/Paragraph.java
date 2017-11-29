package elements;

public class Paragraph extends Element{
	
	String paragraph;
	
	public Paragraph(String paragraph){
		this.paragraph = paragraph;
	}
	
	@Override
	public String getElement(){
		return paragraph;
	}

}
