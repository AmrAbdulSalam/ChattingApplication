/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypackage;

import java.net.*;

/**
 *
 * @author Msys
 */
public class Server implements Runnable{
    private DatagramSocket socket;
    private boolean running;
    public static String packetMessage;
    
    public void createSocket(int port){
        try{
            socket = new DatagramSocket(port);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void run() {
        System.out.println("inside run function");
        byte [] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data , data.length);
        running = true;
        
        while(running){
            try{
                socket.receive(packet);
                data = packet.getData();
                String message = new String(data);
                System.out.println("from me"+message);
                this.packetMessage = message;
                Client.messageArea.append(message + '\n');
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public String pacjetMessage(){
        System.out.println("From fun : " + packetMessage);
        return packetMessage;
    }
    public void start (){
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void stop (){
        running = false;
        socket.close();
    }
    
    public void sendTo(InetSocketAddress address , String mesg){
        try{
            byte[] data = mesg.getBytes();
            DatagramPacket packet = new DatagramPacket(data , data.length);
            packet.setSocketAddress(address);
            socket.send(packet);
            System.out.println("packet sent");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    
}
