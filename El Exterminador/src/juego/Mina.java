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
	private int daño;
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
		this.daño = 50;
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

	public boolean estaColisionando(Araña arañita) {
		if (Math.hypot(this.getX() - arañita.getX() , this.getY() - arañita.getY()) <= this.activacion){
			return true;
			//			detonar(null, null); // Ingresar (lista de arañas , lista de minas)
		}
		else{
			return false;
		}
	}
	
	public void detonar(LinkedList <Araña> arañita, LinkedList <Mina> mina) {
		int k = 0;		// Cambiar por Nro de arañas actual
		int j = 0;		// Cambiar por Nro de minas actual
		this.explotando = true;
		while (k<arañita.size() && arañita.get(k)!=null) {
			if (Math.hypot(this.getX() - arañita.get(k).getX() , this.getY() - arañita.get(k).getY()) <= this.AOE) {
				arañita.get(k).setHP(arañita.get(k).getHP() - this.daño);
			}
			while (j<mina.size() && mina.get(j)!=null) {
				if (Math.hypot(this.getX() - mina.get(k).getX() , this.getY() - mina.get(k).getY()) <= this.AOE && mina.get(j).getExplotando()==false) {
					mina.get(j).detonar(arañita, mina);
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

