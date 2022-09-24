package juego;

import java.awt.*;
import java.util.LinkedList;

import entorno.Entorno;

public class Exterminador {

	private double x;
	private double y;
	private int altura;
	private int base;
	private double angulo;
	private int HP;
	private int HPMax = 250;
	private int PoderDisparo;
	static int MaxDaño = 3;
	static int MinasMax = 10;
	private int minas;
	
	public Exterminador(double x, double y){
		this.x = x;
		this.y = y;
		this.altura = 20;
		this.base = 15;
		this.angulo = 0;
		this.HP = getHPMax();
		this.PoderDisparo = 1;
		this.minas = 10;
	}
	
	public void dibujar(Entorno e){
		e.dibujarTriangulo(this.x, this.y, this.altura, this.base, this.angulo, Color.PINK);
	}
	
	public void moverArriba(boolean x, boolean y) {
		if (x==true) {
			this.x += Math.cos(angulo);
		}
		if (y==true) {
			this.y += Math.sin(angulo);
		}
	}
	public void moverAbajo() {
		this.x -= Math.cos(angulo)*0.5;
		this.y -= Math.sin(angulo)*0.5;
	}
	public void girarDerecha(){
		if (this.angulo == Math.PI*1.75) {
			this.angulo = 0;
		}
		else {
			this.angulo += Math.PI*0.25;
		}
	}
	public void girarIzquierda(){
		if (this.angulo == 0 ) {
			this.angulo = Math.PI*1.75;
		}
		else {
			this.angulo -= Math.PI*0.25;
		}
	}
	
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	public int getHP(){
		return this.HP;
	}
	public void setHP(int hp){
		this.HP = hp;
	}
	public int getHPMax() {
		return HPMax;
	}

	public void setHPMax(int hPMax) {
		HPMax = hPMax;
	}

	public int getPoderDisparo() {
		return PoderDisparo;
	}

	public void setPoderDisparo(int poderDisparo) {
		PoderDisparo = poderDisparo;
	}

	public String BarraHP() {
		int k = 0;
		String barra = "";
		while (k<this.HP){
			barra = barra + "_";
			k = k + 5;	// Una barra = 5hp
		}
		return barra;
	}
	
	public int getMinas() {
		return minas;
	}

	public void setMinas(int minas) {
		this.minas = minas;
	}

	public void mostrarVida(Entorno e, boolean player2) {
		if (this.HP>getHPMax()/2 && this.HP<=getHPMax()) {
			e.cambiarFont(null, 12, Color.green);
		}
		else if (this.HP>getHPMax()/3 && this.HP<=getHPMax()/2) {
			e.cambiarFont(null, 12, Color.yellow);
		}
		else {
			e.cambiarFont(null, 12, Color.red);
		}
		if (player2 == true){
			e.escribirTexto("Player 2 HP: " + this.HP + " / " + this.HPMax, 70 , 40);
			e.escribirTexto(this.BarraHP(), 50 , 40);
		}
		else{
			e.escribirTexto("Player 1 HP: " + this.HP + " / " + this.HPMax, 70 , 20);
			e.escribirTexto(this.BarraHP(), 50 , 20);
		}
	}
	
	public double getAngulo(){
		return this.angulo;
	}
	public boolean estaColisionandoXY(Edificio edif) {
		if (((edif.getMaxX()+10)>=this.x && this.x>=(edif.getMinX()-10)) && ((edif.getMaxY()+10)>=this.y && this.y>=(edif.getMinY()-10))) {
			return true;
		}
		return false;
	}
	public boolean estaColisionandoX(Edificio edif) {
		if ((edif.getMaxX())>=this.x && this.x>=(edif.getMinX())) {
			return true;
		}
		else if ((edif.getMaxX()<this.x)) {
			return false;
		}
		else if (edif.getMinX()>this.x) {
			return false;
		}
		else {
			return false;
		}
	}
	public boolean estaColisionandoY(Edificio edif) {
		if ((edif.getMaxY())>=this.y && this.y>=(edif.getMinY())) {
			return true;
		}
		else if ((edif.getMaxY()<this.y)) {
			return false;
		}
		else if (edif.getMinY()>this.y) {
			return false;
		}
		else {
			return false;
		}
	}
	public boolean estaColisionandoBordes(Entorno e) {
		if ((this.x<e.ancho()-5 && this.x>5) && (this.y<e.alto()-5 && this.y>5)) {
			return false;
		}
		else {
			return true;
		}
	}
	static Rectangle TrianguloARectangulo() {
		
		return null;
	}
	
	public boolean[] Colisionar(Edificio Edif) {
		boolean[] move =  new boolean [2];
		move[0] = true;
		move[1] = true;
		/*
		 * Añadir aqui las colisiones con los bordes de la pantalla.
		 */
		if (this.estaColisionandoXY(Edif)==false) {
			return (move);	// Arregla la colision con las proyecciones infinitesimales de los ejes
		}
		else {
			if (this.estaColisionandoX(Edif)==false && this.estaColisionandoY(Edif)==false) {
				return (move);	// Está en una esquina.
			}
			if (this.estaColisionandoX(Edif)==true && this.estaColisionandoY(Edif)==false) {
				//Está sobre o debajo del cubo.
				//
				//Reviso si está arriba.
				if (this.getY()<=Edif.getMinY()) {
					if (this.getAngulo() == Math.PI*0
					|| this.getAngulo() == Math.PI*1.75
					|| this.getAngulo() == Math.PI*1.5
					|| this.getAngulo() == Math.PI*1.25
					|| this.getAngulo() == Math.PI*1) {
						return (move); // Puede moverse libremente.
					}
					else {
						move[1] = false;
						return (move);	// Solo puede "frotar" el cubo.
					}
				}
				//Reviso si está abajo.
				if (this.getY()>=Edif.getMaxY()) {
					if (this.getAngulo() == Math.PI*0
					|| this.getAngulo() == Math.PI*0.75
					|| this.getAngulo() == Math.PI*0.5
					|| this.getAngulo() == Math.PI*0.25
					|| this.getAngulo() == Math.PI*1) {
						return (move); // Puede moverse libremente.
					}
					else {
						move[1] = false;
						return (move);	// Solo puede "frotar" el cubo.
					}
				}
			}
			if (this.estaColisionandoX(Edif)==false && this.estaColisionandoY(Edif)==true) {
				//Está a la izquierda o a la derecha del cubo. 
				//
				//Reviso si está a la izquierda.
				if (this.getX()<=Edif.getMinX()) {
					if (this.getAngulo() == Math.PI*1.5
					|| this.getAngulo() == Math.PI*1.25
					|| this.getAngulo() == Math.PI*1
					|| this.getAngulo() == Math.PI*0.75
					|| this.getAngulo() == Math.PI*0.5) {
						return (move); // Puede moverse libremente.
					}
					else {
						move[0] = false;
						return (move);	// Solo puede "frotar" el cubo.
					}
				}
				//Reviso si está a la derecha.
				if (this.getX()>=Edif.getMaxX()) {
					if (this.getAngulo() == Math.PI*1.5
					|| this.getAngulo() == Math.PI*1.75
					|| this.getAngulo() == Math.PI*0
					|| this.getAngulo() == Math.PI*0.25
					|| this.getAngulo() == Math.PI*0.5) {
						return (move); // Puede moverse libremente.
					}
					else {
						move[0] = false;
						return (move);	// Solo puede "frotar" el cubo.
					}
				}
				
			}
			if (this.estaColisionandoX(Edif)==true && this.estaColisionandoY(Edif)==true) {
				return (move);	// Está adentro del cubo. Dejamos en TRUE para que intente salir.
			}
		}
		return move;
	
	}

	public boolean estaColisionandoCaja(Items cajita) {
		if (((cajita.getMaxX()+10)>=this.x && this.x>=(cajita.getMinX()-10)) && ((cajita.getMaxY()+10)>=this.y && this.y>=(cajita.getMinY()-10))) {
			return true;
		}
		return false;
	}
	
	public boolean estaColisionandoXY(Araña edif) {
		if (((edif.getMaxX()+10)>=this.x && this.x>=(edif.getMinX()-10)) && ((edif.getMaxY()+10)>=this.y && this.y>=(edif.getMinY()-10))) {
			return true;
		}
		return false;
	}
	public boolean estaColisionandoX(Araña edif) {
		if ((edif.getMaxX())>=this.x && this.x>=(edif.getMinX())) {
			return true;
		}
		else if ((edif.getMaxX()<this.x)) {
			return false;
		}
		else if (edif.getMinX()>this.x) {
			return false;
		}
		else {
			return false;
		}
	}
	public boolean estaColisionandoY(Araña edif) {
		if ((edif.getMaxY())>=this.y && this.y>=(edif.getMinY())) {
			return true;
		}
		else if ((edif.getMaxY()<this.y)) {
			return false;
		}
		else if (edif.getMinY()>this.y) {
			return false;
		}
		else {
			return false;
		}
	}
	
	public boolean ColisionaAraña(Araña Arañita) {
		/*
		 * Añadir aqui las colisiones con los bordes de la pantalla.
		 */
		if (this.estaColisionandoXY(Arañita)==false) {
			return (false);	// Arregla la colision con las proyecciones infinitesimales de los ejes
		}
		else {
			if (this.estaColisionandoX(Arañita)==false && this.estaColisionandoY(Arañita)==false) {
				return (false);	// Está en una esquina.
			}
			if (this.estaColisionandoX(Arañita)==true && this.estaColisionandoY(Arañita)==false) {
				//Está sobre o debajo del cubo.
				//
				//Reviso si está arriba.
				if (this.getY()<=Arañita.getMinY()) {
					return (true);	// Solo puede "frotar" el cubo.
				}
				//Reviso si está abajo.
				if (this.getY()>=Arañita.getMaxY()) {
					return (true);	// Solo puede "frotar" el cubo.
				}
			}
			if (this.estaColisionandoX(Arañita)==false && this.estaColisionandoY(Arañita)==true) {
				//Está a la izquierda o a la derecha del cubo. 
				//
				//Reviso si está a la izquierda.
				if (this.getX()<=Arañita.getMinX()) {
					return (true);	// Solo puede "frotar" el cubo.
				}
				//Reviso si está a la derecha.
				if (this.getX()>=Arañita.getMaxX()) {
					return (true);	// Solo puede "frotar" el cubo.
				}
			}				
			if (this.estaColisionandoX(Arañita)==true && this.estaColisionandoY(Arañita)==true) {
				return (true);	// Está adentro del cubo. Dejamos en TRUE para que intente salir.
			}
		}
		return false;
	
	}	
	
	public void dejarMina(LinkedList <Mina> mina){
		if (this.minas==0) {
			
		}
		else {
			mina.add(new Mina(this.getX(), this.getY()));
			this.minas--;
		}
	}

	public int disparar(int i, LinkedList <Pelota> pelota){
		pelota.add(new Pelota(this.getX(), this.getY(), this.getAngulo(), this.PoderDisparo));
		i++;
		return i;
	}
	
	/*
	public boolean estaColisionando() {
		if (1==1) {
			return true;
		}
		return false;
	}
	*/
	
	/**
	 * Intercepcion:
	 * colision (edificio)
	 * 	Vuelvo al (triangulo) exterminador un rectangulo
	 * 	Hago un rectangulo con el edificio
	 * 	if (colision existe en X == true){
	 * 		boolean moverEnX= False
	 * 	}
	 * 	if (colision existe en Y == true){
	 * 		boolean moverEnY= False
	 * 	}
	 * 	
	 * 	
	 * 	
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}
