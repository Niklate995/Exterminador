package juego;

import java.awt.*;
import java.util.LinkedList;
import entorno.Entorno;

import java.util.Random;

public class Mina {
	private double x;
	private double y;
	private int ancho;
	private int alto;
	private double radio;
	private double diametro;
	public double activacion;
	private int da�o;
	private boolean explotando;
	private double AOE;

	public Mina(double x, double y) {
		this.x = x;
		this.y = y;
		this.ancho = 10;
		this.alto = 10;
		this.diametro = 100;			// Largo de la explosion
		this.radio = this.diametro/2;	// Radio de la explosion
		this.activacion = this.radio/2;	// Cercania para activar la explosion
		this.AOE = this.activacion;		// AOE de la explosion
		this.da�o = 50;
		this.explotando = false;
	}

	public void dibujar(Entorno e) {
		e.dibujarCirculo(this.x, this.y, this.diametro, Color.lightGray);		// Radio de explosion
		e.dibujarCirculo(this.x, this.y, this.diametro - 2, Color.black);		// Oscurezco para que se vea bonito
		e.dibujarCirculo(this.x, this.y, this.activacion, Color.darkGray);	// Radio de activacion
		e.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0.0, Color.red);	// La misma mina
		if (this.explotando==true) {
			e.dibujarCirculo(this.x, this.y, this.AOE*2, Color.red);
		}
	}

	public boolean estaColisionando(Ara�a ara�ita) {
		if (Math.hypot(this.getX() - ara�ita.getX() , this.getY() - ara�ita.getY()) <= this.activacion){
			return true;
			//			detonar(null, null); // Ingresar (lista de ara�as , lista de minas)
		}
		else{
			return false;
		}
	}
	
	public void detonar(LinkedList <Ara�a> ara�ita, LinkedList <Mina> mina) {
		int k = 0;		// Cambiar por Nro de ara�as actual
		int j = 0;		// Cambiar por Nro de minas actual
		this.explotando = true;
		while (k<ara�ita.size() && ara�ita.get(k)!=null) {
			if (Math.hypot(this.getX() - ara�ita.get(k).getX() , this.getY() - ara�ita.get(k).getY()) <= this.AOE) {
				ara�ita.get(k).setHP(ara�ita.get(k).getHP() - this.da�o);
			}
			while (j<mina.size() && mina.get(j)!=null) {
				if (Math.hypot(this.getX() - mina.get(k).getX() , this.getY() - mina.get(k).getY()) <= this.AOE && mina.get(j).getExplotando()==false) {
					mina.get(j).detonar(ara�ita, mina);
				}
				j++;
			}
			k++;
		}
		//FALTA EL WHILE DE LAS MINAS Y HACER REACCION EN CADENA. REVISAR TICKMINAS() EN JUEGO.
	}
	
	public boolean getExplotando() {
		return this.explotando;
	}
	public void setExplotando(boolean exp) {
		this.explotando = exp;
	}
	
	public double getX(){	// Es el centro en X
		return this.x;
	}
	public double getY(){	// Es el centro en Y
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
	public double getAOE() {
		return this.AOE;
	}
	public double getRadio() {
		return this.radio;
	}
	public void addAOE(double n) {
		this.AOE = this.AOE + n;
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

