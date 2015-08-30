package io.mikuru.tcp;

import java.io.IOException;
import java.net.Socket;

public class Client
{
	private String name;
	private Channel channel;
	
	private OnSocketListener onSocketListener;
	
	public Client(String name, OnSocketListener onSocketListener)
	{
		this.name = name;
		this.onSocketListener = onSocketListener;
	}

	public void connect(String ip, int port) throws IOException
	{
		Socket socket = new Socket(ip, port);
		
		channel = new Channel(socket, onSocketListener);
		channel.start();
	}
	
	public void stop() throws IOException
	{
		channel.stop();
	}
	
	public void send(String msg)
	{
		channel.send(name + " >> " + msg);
	}
}
