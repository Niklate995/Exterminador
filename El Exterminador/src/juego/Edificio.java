package juego;

import java.awt.*;
import entorno.Entorno;
import java.util.Random;

public class Edificio {
	private double x;
	private double y;
	private int ancho;
	private int alto;

	public Edificio(int x, int y) {
		Random r = new Random();
		this.x = x;
		this.y = y;
		if (r.nextInt(2) == 0) {
			this.ancho = 50 + r.nextInt(100);
			//this.ancho = 50;
			this.alto = 50;
		}
		else {
			this.ancho = 50;
			this.alto = 50 + r.nextInt(100);
			//this.alto = 50;
		}
		
	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0.0, Color.white);
	}

	public boolean estaColisionando() {
		return false;
	}
	
	
	public double getCentroX(){
		return this.x;
	}
	public double getCentroY(){
		return this.y;
	}
	public int getMaxX(){
		return (int) ((this.ancho/2) + this.x);
	}
	public int getMaxY(){
		return (int) ((this.alto/2) + this.y);
	}
	public int getMinX(){
		return (int) (this.x - (this.ancho/2));
	}
	public int getMinY(){
		return (int) (this.y - (this.alto/2));
	}
	public Rectangle getBounds() {
		Rectangle r = new Rectangle ();
		r.setBounds((int) this.x, (int) this.y, this.ancho, this.alto);
		return r;
	}

}

