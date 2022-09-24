package juego;

import java.awt.*;
import entorno.Entorno;

import java.util.LinkedList;
import java.util.Random;

public class Items {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private String type;
	private double angulo = 0;
	

	public Items(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.ancho = 15;
		this.alto = 15;
		this.type = type;
		this.angulo = 0;
		/*
		 * Definiciones de type:
		 * M = Minas (Cantidad: 2)
		 * H = Heal (Cantidad: 50hp)
		 * P = Power Up (Doble disparo)
		 */
	}

	public void dibujar(Entorno e) {
		e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, this.angulo, Color.cyan);
		e.escribirTexto(this.type, this.x-5, this.y-10);
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
	public void Girar() {
		if (this.angulo >=2) {
			this.angulo = 0;
		}
		else {
			this.angulo += 0.01;
		}
	}

	public boolean estaColisionandoXY(Edificio Edif) {
		if (((Edif.getMaxX()+10)>=this.x && this.x>=(Edif.getMinX()-10)) && ((Edif.getMaxY()+10)>=this.y && this.y>=(Edif.getMinY()-10))) {
			return true;
		}
		return false;
	}	
	
	public boolean estaColisionandoX(Edificio Edif) {
		if ((Edif.getMaxX())+2.5>=this.x && this.x>=(Edif.getMinX())-2.5) {
			return true;
		}
		else if ((Edif.getMaxX()<this.x)) {
			return false;
		}
		else if (Edif.getMinX()>this.x) {
			return false;
		}
		else {
			return false;
		}
	}	
	
	public boolean estaColisionandoY(Edificio Edif) {
		if ((Edif.getMaxY())+2.5>=this.y && this.y>=(Edif.getMinY())-2.5) {
			return true;
		}
		else if ((Edif.getMaxY()<this.y)) {
			return false;
		}
		else if (Edif.getMinY()>this.y) {
			return false;
		}
		else {
			return false;
		}
	}
	
	public boolean tocaEdificio(Edificio Edif) {
		if (this.estaColisionandoXY(Edif)==false) {
			return false;// Arregla la colision con las proyecciones infinitesimales de los ejes
		}	
		else {
			if (this.estaColisionandoX(Edif)==false && this.estaColisionandoY(Edif)==false) {
				return false;	// Está en una esquina.
			}
			if (this.estaColisionandoX(Edif)==true && this.estaColisionandoY(Edif)==false) {
				return (true);	// Solo puede "frotar" el cubo.
			}
			if (this.estaColisionandoX(Edif)==false && this.estaColisionandoY(Edif)==true) {
				return (true);	// Solo puede "frotar" el cubo.
				}
			if (this.estaColisionandoX(Edif)==true && this.estaColisionandoY(Edif)==true) {
				return (true);	// Está adentro del cubo. Dejamos en TRUE para que intente salir.
			}
		return false;
		}
	}
	
	public void resolverCajaTocada(Exterminador jugador, LinkedList<Items> cajas, int contador) {
		if (cajas.get(contador).type == "P") {
			if (jugador.getPoderDisparo()<Exterminador.MaxDaño) {
				jugador.setPoderDisparo(jugador.getPoderDisparo() + 1);
				cajas.remove(contador);
			}
		}
		if (cajas.get(contador).type == "H") {
			if (jugador.getHP()<jugador.getHPMax()) {
				if (jugador.getHPMax() - jugador.getHP() >= 50) {
					jugador.setHP(jugador.getHP() + 50);
				}
				else {
					jugador.setHP(jugador.getHPMax());
				}
				cajas.remove(contador);
			}
		}
		if (cajas.get(contador).type == "M") {
			if (jugador.getMinas()<=Exterminador.MinasMax-2) {
				jugador.setMinas(jugador.getMinas()+2);
				cajas.remove(contador);
			}
		}
	}
	
	
	
	
	
	
	
	
}

