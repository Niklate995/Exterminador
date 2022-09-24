package juego;

import java.awt.*;

public class Dibujando {

	public static void main(String[] args) {
		Rectangle test = new Rectangle (0, 0, 100, 200);
		Rectangle test2 = new Rectangle (100, 200, 550, 550);
		Point testpoint = new Point (50,200);
		System.out.println(diagonal(test));
		System.out.println(centro(test)); //Me retorna java.awt.Point[x=50,y=100]. Guarda, es el centro. Suficiente para mi :^P
		System.out.println(estaDentro(testpoint,test));
		//agrandar(test, 100, 200);
		System.out.println("Se solapan?: " + seSolapan(test, test2));
		//System.out.println("Se interceptan?: " + seInterceptan(testExter, testEdif));
		System.out.println(cajaContenedora(test, test2));
		
	}
	
	static double diagonal(Rectangle r){
		double hipotenuza = Math.hypot(r.height, r.width);
		return hipotenuza;
	}
	
	static Point centro(Rectangle r){
		Point p = new Point(r.width/2, r.height/2);
		return p;
	}
	
	static boolean estaDentro(Point p, Rectangle r){
		if ( (p.x>r.getMinX() && p.x<r.getMaxX()) && (p.y>r.getMinY() && p.y<r.getMaxY()) ){ 
			return true; // No cuenta los bordes. Que raro. Agregar +1 si es necesario.
		}
		return false;
	}
	
	static void agrandar(Rectangle r, int ancho, int alto){
		r.width = r.width + ancho;
		r.height = r.height + alto;
		// r.translate tambien sirve.
	}
	
	static boolean seSolapan(Rectangle r1, Rectangle r2){ //Me esta preguntando si se chocan? (interseccion)
		return r1.intersects(r2);
	}
	static void seInterceptan(Rectangle r1, Rectangle r2) {
		// r1= Exterminador, r2= Edificio
		if (r2.getX()>r1.getX() && r1.getX()>(r2.getX()+r2.getWidth())) {
			
		}
		
	}
	
	static Rectangle cajaContenedora(Rectangle r1, Rectangle r2){
		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;
		if ( (r1.getMinY()<=r2.getMinY())){
			minY = (int) r1.getMinY();			
		}
		else {
			minY = (int) r2.getMinY();
		}
		if (r1.getMinX()<=r2.getMinX()){
			minX = (int) r1.getMinX();
		}
		else{
			minX = (int) r2.getMinX();
		}
		if (r1.getMaxY()>=r2.getMaxY()){
			maxY = (int) r1.getMaxY();
		}
		else{
			maxY = (int) r2.getMaxY();
		}
		if (r1.getMaxX()>=r2.getMaxX()){
			maxX = (int) r2.getMaxX();
		}
		else{
			maxX = (int) r2.getMaxX();
		}
		Rectangle contenedor = new Rectangle (minX, minY, maxX, maxY);
		return contenedor;
	}

}


/*
 * Vemos "point" y rectangulo
 * Se importa con Import java.awt.*
 * 
 * 
 * Point p;
 * p = new Point(3,4);
 * 
 * o, en una sola linea:
 * Point p = new Point(3,4);
 * 
 * Rectangle tiene: (x, y, ancho, alto)
 * 
 * 
 * 
 * || or
 * && and
 * != distinto
 */