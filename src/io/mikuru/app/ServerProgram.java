package io.mikuru.app;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import io.mikuru.tcp.Channel;
import io.mikuru.tcp.OnSocketListener;
import io.mikuru.tcp.Server;

public class ServerProgram implements OnSocketListener
{
	private Server server;

	@Override
	public void onConnected(Channel channel)
	{
		Socket socket = channel.getSocket();
		String hostName = socket.getInetAddress().getHostName();
		int port = socket.getPort();
		
		String msg = "Client connected from " + hostName + ":" + port;
		
		System.out.println(msg);
		server.broadcast(msg);
	}
	
	@Override
	public void onDisconnected(Channel channel)
	{
		server.remove(channel);
		
		Socket socket = channel.getSocket();
		String hostName = socket.getInetAddress().getHostName();
		int port = socket.getPort();
		
		String msg = "Client disconnected from " + hostName + ":" + port;
		
		System.out.println(msg);
		server.broadcast(msg);
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		System.out.println(msg);
		server.broadcast(msg);
	}
	
	public void start() throws IOException
	{
		Scanner scanner = new Scanner(System.in);

		System.out.print("Port : ");
		int port = Integer.parseInt(scanner.nextLine());

		server = new Server(this);
		server.bind(port);
		server.start();
		
		while(true)
		{
			String msg = scanner.nextLine();
			
			if(msg.isEmpty())
				break;
			
			msg = "Server >> " + msg;
			
			System.out.println(msg);
			server.broadcast(msg);
		}

		scanner.close();
		server.stop();
	}
	
	public static void main(String[] args) throws IOException
	{
		ServerProgram program = new ServerProgram();
		program.start();
	}
}
