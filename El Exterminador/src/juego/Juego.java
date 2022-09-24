package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	public int dificultad = 1;
	public Edificio[] Edificio = new Edificio[8];
	public LinkedList <Pelota> pelota = new LinkedList <Pelota>();
	public LinkedList <Araña> Araña = new LinkedList <Araña>();
	public LinkedList <Boss> Boss = new LinkedList <Boss>();
	public LinkedList <Items> Items = new LinkedList <Items>();
	public LinkedList <Mina> mina = new LinkedList <Mina>();
	public Exterminador Exterminador; 
	public Exterminador Exterminador2;
	private int i = 0; // Cantidad de disparos
	
	private int k = 0; // For para colision
	private int j = 0; // Cantidad de Minas
	private int temp = 0;	//Para el spawn de arañas y otras cosas
	private boolean menu = false;
	private int spawner = 0; // Temporizador de spawn de arañas
	private int bosscount = 1;
	private int kills = 0; // Arañas matadas
	public boolean player2 = false;
	public boolean player1alive = true;
	public boolean player2alive = false;
	private int timeCajaItem = 0;
	
	public int pos = 0;
	public String diffy = "";
	public String plays = "";
	
	public int getKills(){
		return this.kills;
	}

	// Variables y mÃ©todos propios de cada grupo
	// ...

	Juego() {
		
		construirTodo();
		
		// Inicializar lo que haga falta para el juego
		// ...
		
		// Inicia el juego!
		
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el mÃ©todo tick() serÃ¡ ejecutado en cada instante y por
	 * lo tanto es el mÃ©todo mÃ¡s importante de esta clase. AquÃ­ se debe
	 * actualizar el estado interno del juego para simular el paso del tiempo
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		/**
		* Menu inicial
		*/
		if (menu==false) {
			menu();
		}
		
		else {
		/**
		 * Procesamiento de un instante de tiempo
		 * ...
		 * Actualizacion del juego
		 * 
		 */
		
		if (player1alive == false && player2alive == false) {
			menu = false;
		}
		if (this.Exterminador.getHP()<=0) {
			player1alive = false;
			this.Exterminador.setHP(0);
		}
		if (this.Exterminador2.getHP()<=0){
			player2alive = false;
			this.Exterminador2.setHP(0);
		}
		
		spawner = SpawnYPasoDelTiempo(spawner, kills);
		if (player2 == false) {
			// Controles para el jugador 1, si juega en SOLO.
			controlesP1SOLO();
		}			
		
		if (player2 == true){
			// Controles para el jugador 1, si hay dos jugadores.
			if (player1alive == true) {
				controlesP1DUAL();				
			}
			
			// Controles para el jugador 2, si hay dos jugadores.
			if (player2alive == true) {
				controlesP2DUAL();			
			}
		}
		
		// Dibujo las minas.
		tickMinas();
		
		// Dibujo e interacciono con los disparos.
		tickDisparos();
				
		// Dibujo los edificios.
		tickEdificios();
						
		// Muevo a las arañas.
		moverArañas();
		
		// Dibujo a las arañas
		tickArañas();
		
		// Dibujo los Powerups
		tickCajasItems();
		
		// Dibujado
		this.Exterminador.dibujar(this.entorno);
		if (player2 == true){
			this.Exterminador2.dibujar(this.entorno);
		}

		this.entorno.escribirTexto("Minas: " + Exterminador.getMinas() + " / " + juego.Exterminador.MinasMax, this.entorno.ancho() - 100, this.entorno.alto() - 20);
		this.entorno.escribirTexto("Arañas Exterminadas: " + kills, this.entorno.ancho()/2, 20);
		this.entorno.escribirTexto("Tiempo: " + spawner, this.entorno.ancho() - 100, 20);
		Exterminador.mostrarVida(entorno, false);
		if (player2 == true){
			this.Exterminador2.mostrarVida(entorno, player2);
			this.entorno.escribirTexto("Minas: " + Exterminador2.getMinas() + " / " + juego.Exterminador.MinasMax, this.entorno.ancho() - 100, this.entorno.alto() - 5);

		}
		
		} //cierra el "else" del menú
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	
	
	/*
	 * Metodos:
	 * 
	 * 
	 */
		
	public void construirTodo(){
		// Inicializa el objeto entorno
		//int i = 0;
		this.entorno = new Entorno(this, "Exterminador v1.1.2", 800, 600);
		this.Exterminador = new Exterminador(this.entorno.ancho() / 2-20, this.entorno.alto() / 2);
		this.Exterminador2 = new Exterminador(this.entorno.ancho() / 2+20, this.entorno.alto() / 2);
		pelota.add(new Pelota(1000, 1000, 0, -1));
		//this.araña[0] = new Araña(0, r.nextInt(525) + 50);
		//this.araña2[0] = new Araña(800, r.nextInt(525) + 50);
		//this.mina[0] = new Mina(this.Exterminador.getX() , this.Exterminador.getY());
		//this.pelota[0] = new Pelota(this.Exterminador.getX(), this.Exterminador.getY(), this.Exterminador.getAngulo());
		while (k<8){
			Random r = new Random();
			this.Edificio[k] = new Edificio(r.nextInt(725) + 50, r.nextInt(525) + 50);
			k++;
		}
		k = 0;

		// Inicializar lo que haga falta para el juego
		// ...
		
		// Inicia el juego!
	}
	
	public void moverJugador(){
		boolean[] move =  new boolean [2];
		boolean[] movetemp =  new boolean [2];
		move[0] = true;
		move[1] = true;
		while (k<8) {
			movetemp = this.Exterminador.Colisionar(Edificio[k]);
			if (movetemp[0]==false) {
				move[0] = false;
			}
			if (movetemp[1]==false) {
				move[1] = false;
			}
			k++;
		}
		k = 0;
		movetemp[0] = true;
		movetemp[1] = true;
		this.Exterminador.moverArriba(move[0], move[1]);
	}
	
	public void tickDisparos(){
		while (k<pelota.size() && pelota.size()!=0 && pelota.get(k).getDaño() != -1) {
			
			if (this.pelota.get(k).getX() >= this.entorno.ancho() || this.pelota.get(k).getX() <= 0 || this.pelota.get(k).getY() >= this.entorno.alto() || this.pelota.get(k).getY() <= 0) {
				this.pelota.remove(k);
				if (k == 0) {
					k=0;
				}
				else {
					k--;
				}
			}
			int k1 = 0; 
			while (k1<8 && pelota.get(k)!=null) {
				if (pelota.get(k).estaColisionando(Edificio[k1])){
					this.pelota.remove(k);
					if (k == 0) {
						k=0;
					}
					else {
						k--;
					}
				}
				k1++;				
			}
			k1 = 0;
			while (k1<Araña.size() && Araña.get(k1)!=null) { 
				if (this.pelota.get(k).tocaAraña(this.Araña.get(k1))==true && pelota.get(k)!=null && pelota.size()!=0 && k<pelota.size()) {
					int vida = this.Araña.get(k1).getHP();
					if (vida>0) {
						this.Araña.get(k1).setHP(vida - (int) this.pelota.get(k).getDaño());
						this.pelota.remove(k);
					}
				}
				k1++;
			}
			k1 = 0;
			if(this.pelota.get(k)!=null){
				this.pelota.get(k).mover();
				this.pelota.get(k).dibujar(this.entorno);
			}
			
			k++;
		}
		k = 0;
	}
	
	public void tickCajasItems() {
		k = 0;
		while (k<Items.size()) {
			if (this.Exterminador.estaColisionandoCaja(Items.get(k)) == true) {
				this.Items.get(k).resolverCajaTocada(Exterminador, Items, k);
			}
			if (this.Items.get(k)!=null){
				this.Items.get(k).Girar();
				this.Items.get(k).dibujar(this.entorno);
			}
			if (player2 == true && Exterminador2.estaColisionandoCaja(Items.get(k))) {
					Items.get(k).resolverCajaTocada(Exterminador, Items, k);
			}
			if (Exterminador.estaColisionandoCaja(Items.get(k))) {
				Items.get(k).resolverCajaTocada(Exterminador, Items, k);
			}
			k++;
			
		}
	}
	
	public void tickEdificios(){
		while (k<8) {
			this.Edificio[k].dibujar(this.entorno);    // dibujo los edificios
			k++;
		}
		k = 0;
	}
	
	public void tickMinas(){
		k = 0;		//Iterador de minas
		int k1 = 0;	//Iterador de arañas
		while (k<mina.size()) {	// Iterador de minas < Cantidad de minas
			this.mina.get(k).dibujar(this.entorno);         // dibujo las minas
			k1 = 0;
			while (k1<Araña.size()) {
				if (this.mina.get(k).estaColisionando(this.Araña.get(k1))==true && Araña.get(k).getColor()!=Color.yellow){
					this.mina.get(k).detonar(Araña, mina);
				}
				k1++;
			}
			if (this.mina.get(k).getExplotando() == true) {
				this.mina.get(k).addAOE(0.5);
			}
			if (this.mina.get(k).getAOE() >= this.mina.get(k).getRadio()) {
				mina.remove(k);
			}
			k1 = 0;
			k++;
		}
		k1 = 0;
		k = 0;
	}
	
	public void tickArañas(){
		while (k<Araña.size()) {
			if (Araña.get(k)!=null){
				if (player2 == true){
					if (Math.hypot(this.Exterminador.getX() - Araña.get(k).getX(), this.Exterminador.getY() - Araña.get(k).getY()) >= Math.hypot(this.Exterminador2.getX() - Araña.get(k).getX(), this.Exterminador2.getY() - Araña.get(k).getY()) && player2alive == true){
						this.Araña.get(k).girarArañas(this.Exterminador2);
					}
					else{
						this.Araña.get(k).girarArañas(this.Exterminador);
					}
				}
				else{
					this.Araña.get(k).girarArañas(this.Exterminador);
				}
				this.Araña.get(k).dibujar(this.entorno);
				this.Araña.get(k).mostrarVida(entorno);
				if (Araña.get(k).getHP()<=0){
					this.Araña.get(k).setColor(Color.yellow); //Reemplazar esto por la parte que "muere". Modificar tambien en tickArañas
					if (this.Araña.get(k).gettype() == 1 && this.Araña.get(k).temp == true) {
						spawnSpiderlings(kills);
					}
				}
				if (player2 == true){
					if (Exterminador2.ColisionaAraña(this.Araña.get(k)) == true && this.Araña.get(k).getColor() != Color.yellow) {
						Exterminador2.setHP(Exterminador2.getHP() - 1);
					}
				}
				if (Exterminador.ColisionaAraña(this.Araña.get(k)) == true && this.Araña.get(k).getColor() != Color.yellow) {
					Exterminador.setHP(Exterminador.getHP() - 1);
				}
			}			
			k++;
		}
		k=0;
	}
	
	public void moverArañas(){
		boolean[] move =  new boolean [2];
		boolean[] movetemp =  new boolean [2];
		move[0] = true;
		move[1] = true;
		int k3 = 0; //Cada araña
		int k2 = 0;	//Edificios
		int k = 0;	//Arañas
		while (k3<Araña.size() && Araña.get(k3)!=null){
			move[0] = true;
			move[1] = true;
			k2 = 0;	//Edificios
			k = 0;	//Arañas
			while (k2<8){
				movetemp = this.Araña.get(k3).Colisionar(Edificio[k2]);
				if (movetemp[0]==false) {
					move[0] = false;
				}
				if (movetemp[1]==false) {
					move[1] = false;
				}
				k2++;	//Paso al siguiente edificio
			}
			while (k<Araña.size() && Araña.get(k)!=null) {
				if (k3!=k && Araña.get(k).getColor()!=Color.yellow) {
					movetemp = this.Araña.get(k3).ColisionarArañas(Araña.get(k));
					if (movetemp[0]==false) {
						move[0] = false;
					}
					if (movetemp[1]==false) {
						move[1] = false;
					}
				}
				k++;	//Reviso la colision con la siguiente araña.
			}
			if (this.Araña.get(k3).getColor()==Color.yellow) { //Este if implica que está "muerta"
				move[0] = false;
				move[1] = false;
				this.Araña.get(k3).setRespawn(this.Araña.get(k3).getRespawn() + 1);
			}
			this.Araña.get(k3).moverArriba(move[0], move[1]);
			if (this.Araña.get(k3).getColor()==Color.yellow && this.Araña.get(k3).getRespawn()==juego.Araña.respawntime) {
				if (this.Araña.get(k3).gettype() != 2) {
					kills++;
				}
				Araña.remove(k3);
			}
			k3++;		//Paso a la siguiente araña
		}
		k = 0;
		k2 = 0;
		k3 = 0; //Cada araña
	}
	
	public int SpawnYPasoDelTiempo(int spawner, int kills){
		Random r = new Random();
		int time = 100;
		if (player2 == true){
			time = (dificultad*(-10) + 10) + 50 - (kills/10);	//Dificultad incremental, en base a cantidad de arañas matadas.
		}
		else {
			time = (dificultad*(-15) + 15) + 100 - (kills/10);
		}
		if (time<=10) {
			time = 10;
		}

		// Spawn de arañas comunes
		if (spawner>=time){
			if (temp==0) {
				Araña.add(new Araña(0, r.nextInt(525) + 50));
			}
			if (temp==1){
				Araña.add(new Araña(800, r.nextInt(525) + 50));
			}
			if (temp==2){
				Araña.add(new Araña(r.nextInt(725) + 50, 0));
			}
			if (temp==3){
				Araña.add(new Araña(r.nextInt(725) + 50, 600));
			}
			if (temp>=3) {
				temp = 0;
			}
			else {
				temp++;
			}
			k++;
			
			spawner = 0;
		}
		// Spawn del Boss
		if ((kills/bosscount)>=10) {
			Araña.add(new Araña(r.nextInt(725) + 50, 0, 1)); // Boss
			bosscount++;
			timeCajaItem++;
		}
		// Spawn de powerups
		if (timeCajaItem == 2) {
			timeCajaItem = 0;
			spawnCajaItem();
		}
		spawner++;
		return spawner;	
	}
	
	public void menuviejo() {
		/*
		this.entorno.cambiarFont(null, 12, Color.white);
		this.entorno.escribirTexto("Bienvenido al 'Exterminador de Arañas'", this.entorno.ancho() - 750, this.entorno.alto() - 450);
		this.entorno.escribirTexto("Precione la tecla ENTER para inciar el juego", this.entorno.ancho() - 750, this.entorno.alto() - 400);
		this.entorno.escribirTexto("Para seleccionar dos jugadores, precione ESPACIO", this.entorno.ancho() - 750, this.entorno.alto() - 375);
		this.entorno.escribirTexto("Multijugador: " + player2, this.entorno.ancho() - 750, this.entorno.alto()/2 + 75);
		if (player2 == true) {
			this.entorno.escribirTexto("Controles Player 1: Disparar (A); Dejar mina (S) Arriba (R) Abajo (F) Izquierda (D) Derecha (G)", this.entorno.ancho() - 750, this.entorno.alto()/2 + 125);
			this.entorno.escribirTexto("Controles Player 2: Disparar (K); Dejar mina (L) Arriba (Flecha arriba) Abajo (Flecha abajo) Izquierda (Flecha izquierda) Derecha (Flecha derecha)", this.entorno.ancho() - 750, this.entorno.alto()/2 + 150);

		}
		else {
			this.entorno.escribirTexto("Controles Player 1: Disparar (J); Dejar mina (K) Arriba (W) Abajo (S) Izquierda (A) Derecha (D)", this.entorno.ancho() - 750, this.entorno.alto()/2 + 125);
		}
		this.entorno.escribirTexto("Arañas Exterminadas: " + kills, this.entorno.ancho()/2, 20);
		if (this.entorno.sePresiono(entorno.TECLA_ENTER) == true) {
			menu = true;
		}
		if (this.entorno.sePresiono(entorno.TECLA_ESPACIO) == true){
			if (player2 == false){
				player2 = true;
				player2alive = true;
			}
			else
			{
				player2 = false;
				player2alive = false;
			}
		} */
	}
	
	public void menu() {
		if (dificultad == 0) {
			diffy = "Facil";
		}
		if (dificultad == 1) {
			diffy = "Normal";
		}
		if (dificultad == 2) {
			diffy = "Dificil";
		}
		if (player2 == true) {
			plays = "2 Jugadores";
		}
		else {
			plays = "1 Jugador";
		}
		this.entorno.cambiarFont(null, 12, Color.white);
		this.entorno.escribirTexto("Bienvenido al 'Exterminador de Arañas'", this.entorno.ancho() - 650, this.entorno.alto() - 500);
		// ------------------------
		if (pos == 0) {
			this.entorno.escribirTexto("-> Iniciar juego", this.entorno.ancho() - 450, this.entorno.alto() - 250);
			this.entorno.escribirTexto("     Dificultad: " + diffy, this.entorno.ancho() - 450, this.entorno.alto() - 225);
			this.entorno.escribirTexto("     " + plays, this.entorno.ancho() - 450, this.entorno.alto() - 200);
		}
		if (pos == 1) {
			this.entorno.escribirTexto("     Iniciar juego", this.entorno.ancho() - 450, this.entorno.alto() - 250);
			this.entorno.escribirTexto("-> Dificultad: " + diffy, this.entorno.ancho() - 450, this.entorno.alto() - 225);
			this.entorno.escribirTexto("     " + plays, this.entorno.ancho() - 450, this.entorno.alto() - 200);
		}
		if (pos == 2) {
			this.entorno.escribirTexto("     Iniciar juego", this.entorno.ancho() - 450, this.entorno.alto() - 250);
			this.entorno.escribirTexto("     Dificultad: " + diffy, this.entorno.ancho() - 450, this.entorno.alto() - 225);
			this.entorno.escribirTexto("-> " + plays, this.entorno.ancho() - 450, this.entorno.alto() - 200);
		}
		// ------------------------
		if (this.entorno.sePresiono(entorno.TECLA_ESPACIO) == true && pos == 0) {
			menu = true;
		}
		if (this.entorno.sePresiono(entorno.TECLA_ESPACIO) == true && pos == 1) {
			if (dificultad >= 2) {
				dificultad = 0;
			}
			else {
				dificultad++;
			}
		}
		if (this.entorno.sePresiono(entorno.TECLA_ESPACIO) == true && pos == 2) {
			if (player2 == false){
				player2 = true;
				player2alive = true;
			}
			else {
				player2 = false;
				player2alive = false;
			}

		}
		// ------------------------
		if (this.entorno.sePresiono(entorno.TECLA_ABAJO) == true) {
			if (pos == 2) {
				pos = 0;
			}
			else {
				pos++;
			}
		}
		if (this.entorno.sePresiono(entorno.TECLA_ARRIBA) == true) {
			if (pos == 0) {
				pos = 2;
			}
			else {
				pos--;
			}
		}

		if (player2 == true) {
			this.entorno.escribirTexto("Controles Player 1: Disparar (A); Dejar mina (S) Arriba (R) Abajo (F) Izquierda (D) Derecha (G)", this.entorno.ancho() - 775, this.entorno.alto()/2 + 125);
			this.entorno.escribirTexto("Controles Player 2: Disparar (K); Dejar mina (L) Arriba, Abajo, Izquierda y Derecha (Flechas direccionales)", this.entorno.ancho() - 775, this.entorno.alto()/2 + 150);
		}
		else {
			this.entorno.escribirTexto("Controles Player 1: Disparar (J); Dejar mina (K) Arriba (W) Abajo (S) Izquierda (A) Derecha (D)", this.entorno.ancho() - 775, this.entorno.alto()/2 + 125);
		}
		this.entorno.escribirTexto("Arañas Exterminadas: " + kills, this.entorno.ancho()/2, 20);

	}
	
	public void controlesP1SOLO() {
		if (this.entorno.estaPresionada('w')) {
			boolean[] move =  new boolean [2];
			boolean[] movetemp =  new boolean [2];
			move[0] = true;
			move[1] = true;
			while (k<8) {
				movetemp = this.Exterminador.Colisionar(Edificio[k]);
				if (movetemp[0]==false) {
					move[0] = false;
				}
				if (movetemp[1]==false) {
					move[1] = false;
				}
				k++;
			}
			k = 0;
			movetemp[0] = true;
			movetemp[1] = true;
			this.Exterminador.moverArriba(move[0], move[1]);
		}		
		
		if (this.entorno.estaPresionada('s')) {
			this.Exterminador.moverAbajo();
		}
		if (this.entorno.sePresiono('d')) {
			this.Exterminador.girarDerecha();
		}
		if (this.entorno.sePresiono('a')) {
			this.Exterminador.girarIzquierda();
		}
		if (this.entorno.sePresiono('j')) {
			i = this.Exterminador.disparar(i, pelota);
		}
		if (this.entorno.sePresiono('k')) {
			this.Exterminador.dejarMina(mina);
		}
	}
	
	public void controlesP1DUAL() {
		if (this.entorno.estaPresionada('r')) {
			boolean[] move =  new boolean [2];
			boolean[] movetemp =  new boolean [2];
			move[0] = true;
			move[1] = true;
			while (k<8) {
				movetemp = this.Exterminador.Colisionar(Edificio[k]);
				if (movetemp[0]==false) {
					move[0] = false;
				}
				if (movetemp[1]==false) {
					move[1] = false;
				}
				k++;
			}
			k = 0;
			movetemp[0] = true;
			movetemp[1] = true;
			this.Exterminador.moverArriba(move[0], move[1]);
		}		
		
		if (this.entorno.estaPresionada('f')) {
			this.Exterminador.moverAbajo();
		}
		if (this.entorno.sePresiono('g')) {
			this.Exterminador.girarDerecha();
		}
		if (this.entorno.sePresiono('d')) {
			this.Exterminador.girarIzquierda();
		}
		if (this.entorno.sePresiono('a')) {
			i = this.Exterminador.disparar(i, pelota);
		}
		if (this.entorno.sePresiono('s')) {
			this.Exterminador.dejarMina(mina);
		}
	}
	
	public void controlesP2DUAL() {
		if (this.entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			boolean[] move =  new boolean [2];
			boolean[] movetemp =  new boolean [2];
			move[0] = true;
			move[1] = true;
			while (k<8) {
				movetemp = this.Exterminador2.Colisionar(Edificio[k]);
				if (movetemp[0]==false) {
					move[0] = false;
				}
				if (movetemp[1]==false) {
					move[1] = false;
				}
				k++;
			}
			k = 0;
			movetemp[0] = true;
			movetemp[1] = true;
			this.Exterminador2.moverArriba(move[0], move[1]);
		}		
		
		if (this.entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			this.Exterminador2.moverAbajo();
		}
		if (this.entorno.sePresiono(entorno.TECLA_DERECHA)) {
			this.Exterminador2.girarDerecha();
		}
		if (this.entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			this.Exterminador2.girarIzquierda();
		}
		if (this.entorno.sePresiono('k')) {
			i = this.Exterminador2.disparar(i, pelota);
		}
		if (this.entorno.sePresiono('l')) {
			this.Exterminador2.dejarMina(mina);
		}
	}

	public void spawnCajaItem() {
		/*
		 * Definiciones de type:
		 * M = Minas (Cantidad: 2)
		 * H = Heal (Cantidad: 50hp)
		 * P = Power Up (Doble disparo)
		 */
		// Ratio de probabilidades
		boolean H = true; // 50%	60%
		boolean M = true; // 35%	40%
		boolean P = true; // 15%	00%
		Random r = new Random();
		String type = "";
		int temp = r.nextInt(100);	// Para el calculo de posibilidades.
		// Tipo de PowerUp.
		if (player2 == false) {
			if (this.Exterminador.getPoderDisparo()>=3) {
				P = false;
			}
		}
		if (player2 == true) {
			if (this.Exterminador.getPoderDisparo()>=3 && this.Exterminador2.getPoderDisparo()>=3) {
				P = false;
			}
		}
		if (P == false) {
			if (temp < 60) {
				type = "H";
			}
			if (temp > 60 && temp < 100) {
				type = "M";
			}
		}
		else {
			if (temp < 50) {
				type = "H";
			}
			if (temp > 50 && temp < 85) {
				type = "M";
			}
			if (temp > 85 && temp < 100) {
				type = "P";
			}
		}		
		// Posicion
		Items.add(new Items(r.nextInt(entorno.ancho()), r.nextInt(entorno.alto()), type));
		k = 0;
		
		while (k<8) {
			if (this.Items.getLast() != null && this.Items.getLast().tocaEdificio(Edificio[k]) == true) {
				this.Items.removeLast();
				Items.add(new Items(r.nextInt(entorno.ancho()), r.nextInt(entorno.alto()), type));
				k = 0;
			}
			else {
				k++;
			}			
		}
	}
	
	public void spawnSpiderlings(int kills) {
		this.Araña.get(k).temp = false;
		int spawns = kills/15;
		int temp = 0;
		Random r = new Random();
		while (spawns>=0){
			if (temp==0) {
				Araña.add(new Araña(this.Araña.get(k).getX() + r.nextInt(25), this.Araña.get(k).getY() + r.nextInt(25), 2));
			}
			if (temp==1){
				Araña.add(new Araña(this.Araña.get(k).getX() + r.nextInt(25), this.Araña.get(k).getY() - r.nextInt(25), 2));
			}
			if (temp==2){
				Araña.add(new Araña(this.Araña.get(k).getX() - r.nextInt(25), this.Araña.get(k).getY() + r.nextInt(25), 2));
			}
			if (temp==3){
				Araña.add(new Araña(this.Araña.get(k).getX() - r.nextInt(25), this.Araña.get(k).getY() - r.nextInt(25), 2));
			}
			if (temp>=3) {
				temp = 0;
			}
			temp++;
			spawns--;
		}
	}	
}


/*
 * 
 * CheckList:
 * 
 * Alpha:
 * Personaje jugable movible:					OK
 * Playgrond Dinamico (Edificios y otros)		OK
 * Colisiones (Edificios, bordes de mapa, etc)  Faltan bordes con el jugador
 * Disparos 									Quizas haya MemoryLeak
 * Minas										A veces, no explotan en cadena.
 * Enemigos										Arañas OK. Boss OK.
 * Testear y largar Alpha						Alpha
 * Enbellecimiento visual						Se decidio que no.
 * 
 * Opcionales:
 * Multijugador									OK
 * Etc
 * 
 * 
 * 
 */

