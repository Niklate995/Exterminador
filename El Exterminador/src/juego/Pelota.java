package juego;

import java.awt.Color;
import entorno.Entorno;
import java.util.LinkedList;

public class Pelota {
	private double x;
	private double y;
	private int alto;
	private int ancho;
	private double direccion;
	private int da�o;

	public Pelota(double x, double y, double direccion, int da�oHit){
		this.x = x;
		this.y = y;
		this.alto = 4;
		this.ancho = 12;
		this.direccion = direccion;
		this.da�o = da�oHit*15;
	}	
	
	public void dibujar(Entorno e){
		if (da�o == 1*15) {
			e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, this.direccion, Color.orange);
		}
		if (da�o == 2*15) {
			e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, this.direccion, Color.pink);
		}
		if (da�o == 3*15) {
			e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, this.direccion, Color.red);
		}
	}

	
	public void mover(){
		this.x += Math.cos(direccion)*2;
		this.y += Math.sin(direccion)*2;
	}
	
	public void rebotar(){
		this.direccion += Math.PI*0.5;
	}
	public void girar(){
		this.direccion += Math.PI*0.5;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}

	public double getDa�o(){
		return this.da�o;
	}
	
	public boolean estaColisionando(Edificio edif) {
		if (((edif.getMaxX()+10)>=this.x && this.x>=(edif.getMinX()-10)) && ((edif.getMaxY()+10)>=this.y && this.y>=(edif.getMinY()-10))) {
			return true;
		}
		return false;
	}
	
	public boolean estaColisionandoXYAra�a(Ara�a Ara�ita) {
		if (((Ara�ita.getMaxX()+10)>=this.x && this.x>=(Ara�ita.getMinX()-10)) && ((Ara�ita.getMaxY()+10)>=this.y && this.y>=(Ara�ita.getMinY()-10))) {
			return true;
		}
		return false;
	}	
	
	public boolean estaColisionandoXAra�a(Ara�a Ara�ita) {
		if ((Ara�ita.getMaxX())+2.5>=this.x && this.x>=(Ara�ita.getMinX())-2.5) {
			return true;
		}
		else if ((Ara�ita.getMaxX()<this.x)) {
			return false;
		}
		else if (Ara�ita.getMinX()>this.x) {
			return false;
		}
		else {
			return false;
		}
	}	
	
	public boolean estaColisionandoYAra�a(Ara�a Ara�ita) {
		if ((Ara�ita.getMaxY())+2.5>=this.y && this.y>=(Ara�ita.getMinY())-2.5) {
			return true;
		}
		else if ((Ara�ita.getMaxY()<this.y)) {
			return false;
		}
		else if (Ara�ita.getMinY()>this.y) {
			return false;
		}
		else {
			return false;
		}
	}
	
	public boolean tocaAra�a(Ara�a Ara�ita) {
		if (this.estaColisionandoXYAra�a(Ara�ita)==false) {
			return false;// Arregla la colision con las proyecciones infinitesimales de los ejes
		}	
		else {
			if (this.estaColisionandoXAra�a(Ara�ita)==false && this.estaColisionandoYAra�a(Ara�ita)==false) {
				return false;	// Est� en una esquina.
			}
			if (this.estaColisionandoXAra�a(Ara�ita)==true && this.estaColisionandoYAra�a(Ara�ita)==false) {
				return (true);	// Solo puede "frotar" el cubo.
			}
			if (this.estaColisionandoXAra�a(Ara�ita)==false && this.estaColisionandoYAra�a(Ara�ita)==true) {
				return (true);	// Solo puede "frotar" el cubo.
				}
			if (this.estaColisionandoXAra�a(Ara�ita)==true && this.estaColisionandoYAra�a(Ara�ita)==true) {
				return (true);	// Est� adentro del cubo. Dejamos en TRUE para que intente salir.
			}
		return false;
		}
	}
	
}
