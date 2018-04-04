package models;


public class Settings {
    
    private static Settings singleton;
    
    public String IP;
    public int port;
    
    private Settings(){
	this.IP = "127.0.0.1";
	this.port = 7789; 	
    }
    
    public static synchronized Settings getInstance(){
	if (singleton == null){
	    singleton = new Settings();
	}
	return singleton;
    }
    
    public void changeIP(String newIP){
	IP = newIP;
    }
    
    public void changePort(int newPort){
	port = newPort;
    }
    
    public String getIP(){
	return IP;
    }
    
    public int getPort(){
	return port;
    }    
}
