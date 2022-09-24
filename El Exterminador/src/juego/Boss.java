package juego;

import java.awt.*;

import entorno.Entorno;

public class Boss {

	private double x;
	private double y;
	private int altura;
	private int ancho;
	private double angulo;
	private int HP;
	private int respawn = 0;
	private Color colorsito;
	static int HPMax = 300;
	static int respawntime = 500;
	
	public Boss(double x, double y){
		this.x = x;
		this.y = y;
		this.altura = 30;
		this.ancho = 30;
		this.angulo = 0;
		this.HP = HPMax;	//Dmg del disparo: Aprox 16-18. Se puede decir que es 
							//el diametro menos la velocidad a la que se mueve.
		this.colorsito = Color.green;
		this.respawn = 0;
	}

	
	public void dibujar(Entorno e){
		e.dibujarRectangulo(this.x, this.y, this.altura, this.ancho, this.angulo, colorsito);
	}
	
	public void moverArriba(boolean x, boolean y) {
		if (x==true) {
			this.x += Math.cos(angulo)/2;
		}
		if (y==true) {
			this.y += Math.sin(angulo)/2;
		}
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
	public double getAngulo(){
		return this.angulo;
	}
	public int getMaxX(){
		return (int) ((this.ancho/2) + this.x);
	}
	public int getMaxY(){
		return (int) ((this.altura/2) + this.y);
	}
	public int getMinX(){
		return (int) (this.x - (this.ancho/2));
	}
	public int getMinY(){
		return (int) (this.y - (this.altura/2));
	}
	public int getRespawn() {
		return respawn;
	}
	public void setRespawn(int respawn) {
		this.respawn = respawn;
	}
	public int getHP(){
		return this.HP;
	}
	public void setHP(int hp){
		this.HP = hp;
	}
	public Color getColor(){
		return this.colorsito;
	}
	public void setColor(Color colorsito2){
		this.colorsito = colorsito2;
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
	
	public void mostrarVida(Entorno e) {
		if (this.HP>HPMax/2 && this.HP<=HPMax) {
			e.cambiarFont(null, 12, Color.green);
		}
		else if (this.HP>HPMax/3 && this.HP<=HPMax/2) {
			e.cambiarFont(null, 12, Color.yellow);
		}
		else {
			e.cambiarFont(null, 12, Color.red);
		}
		e.escribirTexto(this.BarraHP(), this.getX() - 15, this.getY() - 15);
	}
	
	public boolean estaColisionandoXY(Edificio edif) {
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
	
	static Rectangle TrianguloARectangulo() {
		
		return null;
	}
	
	public Rectangle getBounds() {
		Rectangle r = new Rectangle ();
		r.setBounds((int) this.x, (int) this.y, this.ancho, this.altura);
		return r;
	}
	
	public boolean[] Colisionar(Edificio Edif) {
		boolean[] move =  new boolean [2];
		move[0] = true;
		move[1] = true;
		
		if (this.estaColisionandoXY(Edif)==false) {
			return (move);	// Arregla la colision con las proyecciones infinitesimales de los ejes
		}
		else {
			if (this.estaColisionandoX(Edif)==false && this.estaColisionandoY(Edif)==false) {
				return (move);	// Est� en una esquina.
			}
			if (this.estaColisionandoX(Edif)==true && this.estaColisionandoY(Edif)==false) {
				//Est� sobre o debajo del cubo.
				//
				//Reviso si est� arriba.
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
				//Reviso si est� abajo.
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
				//Est� a la izquierda o a la derecha del cubo. 
				//
				//Reviso si est� a la izquierda.
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
				//Reviso si est� a la derecha.
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
				return (move);	// Est� adentro del cubo. Dejamos en TRUE para que intente salir.
			}
		}
		return move;
	
	}
	
	public boolean[] ColisionarAra�as(Ara�a Ara�ita) {

		boolean[] move =  new boolean [2];
		move[0] = true;
		move[1] = true;
		
		if (this.estaColisionandoXYAra�a(Ara�ita)==false) {
			return (move);	// Arregla la colision con las proyecciones infinitesimales de los ejes
		}
		else {
			if (this.estaColisionandoXAra�a(Ara�ita)==false && this.estaColisionandoYAra�a(Ara�ita)==false) {
				return (move);	// Est� en una esquina.
			}
			if (this.estaColisionandoXAra�a(Ara�ita)==true && this.estaColisionandoYAra�a(Ara�ita)==false) {
				//Est� sobre o debajo del cubo.
				//
				//Reviso si est� arriba.
				if (this.getY()<=Ara�ita.getMinY()) {
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
				//Reviso si est� abajo.
				if (this.getY()>=Ara�ita.getMaxY()) {
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
			if (this.estaColisionandoXAra�a(Ara�ita)==false && this.estaColisionandoYAra�a(Ara�ita)==true) {
				//Est� a la izquierda o a la derecha del cubo. 
				//
				//Reviso si est� a la izquierda.
				if (this.getX()<=Ara�ita.getMinX()) {
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
				//Reviso si est� a la derecha.
				if (this.getX()>=Ara�ita.getMaxX()) {
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
			if (this.estaColisionandoXAra�a(Ara�ita)==true && this.estaColisionandoYAra�a(Ara�ita)==true) {
				return (move);	// Est� adentro del cubo. Dejamos en TRUE para que intente salir.
			}
		}
		return (move);
	}
	
	public void girarAra�as(Exterminador Exterminador){
		if ((int) Exterminador.getY()==(int) this.getY() && (int) this.getX()<(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*0
			|| this.getAngulo() == Math.PI*0.25
			|| this.getAngulo() == Math.PI*0.5
			|| this.getAngulo() == Math.PI*0.75) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*1.25
			|| this.getAngulo() == Math.PI*1.5
			|| this.getAngulo() == Math.PI*1.75) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
		if ((int) Exterminador.getY()>(int) this.getY() && (int) this.getX()<(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*1
			|| this.getAngulo() == Math.PI*0.75
			|| this.getAngulo() == Math.PI*0.5
			|| this.getAngulo() == Math.PI*0.25) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*1.5
			|| this.getAngulo() == Math.PI*1.75
			|| this.getAngulo() == Math.PI*0) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
		if ((int) Exterminador.getY()>(int) this.getY() && (int) this.getX()==(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*0.5
			|| this.getAngulo() == Math.PI*0.75
			|| this.getAngulo() == Math.PI*1
			|| this.getAngulo() == Math.PI*1.25) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*1.75
			|| this.getAngulo() == Math.PI*0
			|| this.getAngulo() == Math.PI*0.25) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
		if ((int) Exterminador.getY()>(int) this.getY() && (int) this.getX()>(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*0.75
			|| this.getAngulo() == Math.PI*1
			|| this.getAngulo() == Math.PI*1.25
			|| this.getAngulo() == Math.PI*1.5) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*0
			|| this.getAngulo() == Math.PI*0.25
			|| this.getAngulo() == Math.PI*0.5) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
		if ((int) Exterminador.getY()==(int) this.getY() && (int) this.getX()>(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*1
			|| this.getAngulo() == Math.PI*1.25
			|| this.getAngulo() == Math.PI*1.5
			|| this.getAngulo() == Math.PI*1.75) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*0.25
			|| this.getAngulo() == Math.PI*0.5
			|| this.getAngulo() == Math.PI*0.75) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
		if ((int) Exterminador.getY()<(int) this.getY() && (int) this.getX()>(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*0
			|| this.getAngulo() == Math.PI*1.75
			|| this.getAngulo() == Math.PI*1.5
			|| this.getAngulo() == Math.PI*1.25) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*0.5
			|| this.getAngulo() == Math.PI*0.75
			|| this.getAngulo() == Math.PI*1) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
		if ((int) Exterminador.getY()<(int) this.getY() && (int) this.getX()==(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*0.25
			|| this.getAngulo() == Math.PI*0
			|| this.getAngulo() == Math.PI*1.75
			|| this.getAngulo() == Math.PI*1.5) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*1.25
			|| this.getAngulo() == Math.PI*1
			|| this.getAngulo() == Math.PI*0.75) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
		if ((int) Exterminador.getY()<(int) this.getY() && (int) this.getX()<(int) Exterminador.getX()){
			if (this.getAngulo() == Math.PI*0.5
			|| this.getAngulo() == Math.PI*0.25
			|| this.getAngulo() == Math.PI*0
			|| this.getAngulo() == Math.PI*1.75) {
				this.girarIzquierda(); 
			}
			if (this.getAngulo() == Math.PI*1.5
			|| this.getAngulo() == Math.PI*1.25
			|| this.getAngulo() == Math.PI*1) {
				this.girarDerecha(); // Puede moverse libremente.
			}
		}
	
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
