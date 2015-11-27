package lejosnxt;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.nxt.TouchSensor;



public class bttest {
	public NXTRegulatedMotor ma = Motor.A;
	public NXTRegulatedMotor mc = Motor.C; 
	String cadenv="";
	int speed=0;
	
	public void MotorStop()
	{
		ma.stop();
		mc.stop();
		cadenv="He chocado";	
	}
	
	
	public void MotorAdelante()
	{
       ma.forward();
	   mc.forward();
	   cadenv="adelante";
	}
	
	public void MotorAtras()
	{
	   ma.backward();
	   mc.backward();
	   cadenv="atras";
	}
	
	public void MotorIzquierda()
	{
		ma.backward();
		mc.forward();
		cadenv="Izquierda";
	}
	
	public void MotorDerecha()
	{
		ma.forward();
		mc.backward();
		cadenv="derecha";
	}
	
	public void Velocidad()
	{
		ma.setSpeed(speed);
		mc.setSpeed(speed);	
	}
	
	public void SeleccionOpe(String cad)
	{
		switch (cad) {
	      case "a":
	    	  if(speed==800)
	    	  {
				  speed=800;
	    	  }else
	    	  {
				   speed=speed+80;
	    	  }
	    	  
			System.out.println(speed);
	           break;
	      case "s":
	    	  if(speed==0)
	    	  {
				  speed=0;
			   
	    	  }else
	    	  {
				   speed=speed-80;
	    	  }
	           System.out.println(speed);
	           break;
	      case "adelante":
	    	  MotorAdelante();
	    	  break;
	      case "atras":
	    	  MotorAtras();
	    	  break;	  
	    	  
	      case "izquierda":
	    	  MotorIzquierda();
	    	  break;
	      case "derecha":
	    	  MotorDerecha();
	    	  break;
	      case "p":	  
	    	  System.exit(0);
	      break;
	      default:
	           System.out.println(cad);
	           break;
	      }
		
		cad="";
		
	}
	
	
	
	
  public static void main(String [] args) throws Exception {
    bttest obj = new bttest();
    TouchSensor touch = new TouchSensor(SensorPort.S1);
    String cad="";
    String connected = "Connected";
    String waiting = "Waiting...";
    String closing = "Closing...";
       
      LCD.drawString(waiting,0,0);
      NXTConnection connection = Bluetooth.waitForConnection(); 
      LCD.clear();
      LCD.drawString(connected,0,0);

      DataInputStream dis = connection.openDataInputStream();
      DataOutputStream dos = connection.openDataOutputStream();

    try{
    	while(true) 
        {
          cad = dis.readUTF();
          LCD.clear();
          LCD.drawString(cad,1, 1);
          dos.writeUTF(obj.cadenv);
          dos.flush();
         
          obj.SeleccionOpe(cad);
          obj.Velocidad();
          
          if(touch.isPressed())
          obj.MotorStop();
          
         }
    	
       }
    catch(IOException ioe) 
    {
    	System.exit(0);
    	
    }
    
      
     
  }
}