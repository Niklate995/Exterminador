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
	private int daño;

	public Pelota(double x, double y, double direccion, int dañoHit){
		this.x = x;
		this.y = y;
		this.alto = 4;
		this.ancho = 12;
		this.direccion = direccion;
		this.daño = dañoHit*15;
	}	
	
	public void dibujar(Entorno e){
		if (daño == 1*15) {
			e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, this.direccion, Color.orange);
		}
		if (daño == 2*15) {
			e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, this.direccion, Color.pink);
		}
		if (daño == 3*15) {
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

	public double getDaño(){
		return this.daño;
	}
	
	public boolean estaColisionando(Edificio edif) {
		if (((edif.getMaxX()+10)>=this.x && this.x>=(edif.getMinX()-10)) && ((edif.getMaxY()+10)>=this.y && this.y>=(edif.getMinY()-10))) {
			return true;
		}
		return false;
	}
	
	public boolean estaColisionandoXYAraña(Araña Arañita) {
		if (((Arañita.getMaxX()+10)>=this.x && this.x>=(Arañita.getMinX()-10)) && ((Arañita.getMaxY()+10)>=this.y && this.y>=(Arañita.getMinY()-10))) {
			return true;
		}
		return false;
	}	
	
	public boolean estaColisionandoXAraña(Araña Arañita) {
		if ((Arañita.getMaxX())+2.5>=this.x && this.x>=(Arañita.getMinX())-2.5) {
			return true;
		}
		else if ((Arañita.getMaxX()<this.x)) {
			return false;
		}
		else if (Arañita.getMinX()>this.x) {
			return false;
		}
		else {
			return false;
		}
	}	
	
	public boolean estaColisionandoYAraña(Araña Arañita) {
		if ((Arañita.getMaxY())+2.5>=this.y && this.y>=(Arañita.getMinY())-2.5) {
			return true;
		}
		else if ((Arañita.getMaxY()<this.y)) {
			return false;
		}
		else if (Arañita.getMinY()>this.y) {
			return false;
		}
		else {
			return false;
		}
	}
	
	public boolean tocaAraña(Araña Arañita) {
		if (this.estaColisionandoXYAraña(Arañita)==false) {
			return false;// Arregla la colision con las proyecciones infinitesimales de los ejes
		}	
		else {
			if (this.estaColisionandoXAraña(Arañita)==false && this.estaColisionandoYAraña(Arañita)==false) {
				return false;	// Está en una esquina.
			}
			if (this.estaColisionandoXAraña(Arañita)==true && this.estaColisionandoYAraña(Arañita)==false) {
				return (true);	// Solo puede "frotar" el cubo.
			}
			if (this.estaColisionandoXAraña(Arañita)==false && this.estaColisionandoYAraña(Arañita)==true) {
				return (true);	// Solo puede "frotar" el cubo.
				}
			if (this.estaColisionandoXAraña(Arañita)==true && this.estaColisionandoYAraña(Arañita)==true) {
				return (true);	// Está adentro del cubo. Dejamos en TRUE para que intente salir.
			}
		return false;
		}
	}
	
}
