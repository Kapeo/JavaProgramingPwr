package lab_03;

public class Plansza {
	private int rozmiar;
	private int[][] tab;
	public Plansza(int rozmiar){
		this.rozmiar=rozmiar;
		this.tab=new int[rozmiar][rozmiar];
	}
	public void strzal_kolko(){
		
	}
	public void strzal_krzyzyk(){
		
	}
	public boolean wygrana_kolko(int[][] plansza){
		return false;
	}
	public boolean wygrana_krzyzyk(int[][] plansza){
		return false;
	}
}
