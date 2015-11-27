package org.lejos.pcsample.btsend;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import lejos.nxt.Motor;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
/**
 * This is a PC sample. It connects to the NXT, and then
 * sends an integer and waits for a reply, 100 times.
 * 
 * Compile this program with javac (not nxjc), and run it 
 * with java.
 * 
 * You need pccomm.jar and bluecove.jar on the CLASSPATH. 
 * On Linux, you will also need bluecove-gpl.jar on the CLASSPATH.
 * 
 * Run the program by:
 * 
 *   java BTSend 
 * 
 * Your NXT should be running a sample such as BTReceive or
 * SignalTest. Run the NXT program first until it is
 * waiting for a connection, and then run the PC program. 
 * 
 * @author Lawrie Griffiths
 *
 */
public class BTSendcliente extends JFrame implements KeyListener {
	private JTextField resultado;
	String palabra ="";
	int ac=0;
	int des=0;
	
	
	public BTSendcliente() {
		 
		  add(getbAceptar());
		  add(cajaTexto());
		  inicializador();
		 }
		 
	 private JTextField cajaTexto()
	 {
		 resultado = new JTextField();
		 resultado.setBounds(new Rectangle(10, 45, 250, 60));
		 return resultado;
	 }
	
		 private void inicializador() {
		 
		  setLayout(null);
		  setTitle("Prueba con Teclas");
		  setVisible(true);
		  setSize(300, 300);
		  setLocationRelativeTo(null);
		  setDefaultCloseOperation(EXIT_ON_CLOSE);
		 }
		 
		 
		 private JButton getbAceptar() {
			  bAceptar = new JButton("Aceptar");
			  bAceptar.addKeyListener(this);
			  bAceptar.setBounds(10, 10, 100, 20);
			  return bAceptar;
			 }
			 
			 //@Override
			 public void keyPressed(KeyEvent e) {
			 
			  int key = e.getKeyCode();
			  
			  if (key == KeyEvent.VK_LEFT)
			  	{System.out.println("Izquierda");
			      palabra= "izquierda";}		  
			   if (key == KeyEvent.VK_RIGHT)
			   {  palabra= "derecha";
			   System.out.println("Derecha");
			   }
			  	System.out.println("Derecha");
			   if (key == KeyEvent.VK_UP)
			  	{System.out.println("Adelante");
				   palabra= "adelante";}
			   if (key == KeyEvent.VK_DOWN)
				   {palabra= "atras";
			  	System.out.println("Atras");
				   }
			   
			   if (key == KeyEvent.VK_A){
				   palabra ="a";
			   }
			   if (key == KeyEvent.VK_S)
				   {
				   palabra = "s";
			   }
			   if (key == KeyEvent.VK_R)
				   palabra=""+Motor.A.getSpeed();
			   if (key == KeyEvent.VK_P)
				   palabra="p";
			  
			 }
			 
			 //@Override
			 public void keyReleased(KeyEvent e) {
			  //System.out.println("Soltó una tecla");
			 }
			 
			// @Override
			 public void keyTyped(KeyEvent e) {
			  //System.out.println("Escribió una tecla");
			 }	 
		 
		 

	private JButton bAceptar;
	
	
	public static void main(String[] args) {
		String cadrec="";
		NXTConnector conn = new NXTConnector();
	
		conn.addLogListener(new NXTCommLogListener(){

			public void logEvent(String message) {
				System.out.println("BTSend Log.listener: "+message);
				
			}

			public void logEvent(Throwable throwable) {
				System.out.println("BTSend Log.listener - stack trace: ");
				 throwable.printStackTrace();
				
			}
			
		} 
		);
		// Connect to any NXT over Bluetooth
		boolean connected = conn.connectTo("btspp://nxj");
	
		
		if (!connected) {
			System.err.println("Failed to connect to any NXT");
			System.exit(1);
		}
		
		String HOST = "localhost";
   		Socket yo=null;
   		PrintWriter alServidor;
   		BufferedReader delTeclado;
   		DataInputStream delServidor;
   		String tecleado;
   
   		try{
   			try{
   				yo = new Socket(HOST,5000);
   			}catch (UnknownHostException e){
   				System.out.println(e.getMessage());
   				System.exit(1);
   			} 
   			System.out.println("Ya se conecto al Servidor");
   
   			delTeclado = new BufferedReader(new InputStreamReader(System.in));
   			alServidor = new PrintWriter(yo.getOutputStream(),true);
   			delServidor = new DataInputStream(yo.getInputStream());
   
  	 		do{
   				System.out.print("Teclea un string: ");
   				tecleado = cadrec;
   				alServidor.println(tecleado);
   				System.out.println("Longitud = "+delServidor.readInt()); 
   			}while(tecleado != "cerrar");
   			
   			
   
   			System.out.println("Ya termine de enviar");
   
   			delServidor.close();
   			delTeclado.close();
   			alServidor.close();
   			yo.close();
   
   		}catch (IOException e){
   			System.err.println(e.getMessage());
   			System.exit(1);
   		}
		
		
		
		
		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		DataInputStream dis = new DataInputStream(conn.getInputStream());
		Scanner a = new Scanner(System.in);
		BTSendcliente obj = new BTSendcliente();
		
		while(true) {
			try {
				dos.writeUTF(obj.palabra);
			    obj.palabra ="";
				dos.flush();
			} catch (IOException ioe) {
				System.out.println("IO Exception writing bytes:");
				System.out.println(ioe.getMessage());
				break;
			}
			
		try {
			cadrec=dis.readUTF();
				System.out.println("Received " +cadrec);
				obj.resultado.setText(cadrec);
				
			} catch (IOException ioe) {
				System.out.println("IO Exception reading bytes:");
				System.out.println(ioe.getMessage());
				break;
			}
		}
		
		try {
			dis.close();
			dos.close();
			conn.close();
		} catch (IOException ioe) {
			System.out.println("IOException closing connection:");
			System.out.println(ioe.getMessage());
		}
	}
}