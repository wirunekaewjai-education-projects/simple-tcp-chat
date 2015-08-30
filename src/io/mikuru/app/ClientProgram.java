package io.mikuru.app;

import java.io.IOException;
import java.util.Scanner;

import io.mikuru.tcp.*;

public class ClientProgram implements OnSocketListener
{
	private Client client;

	@Override
	public void onConnected(Channel channel)
	{
		System.out.println("Connected.");
	}
	
	@Override
	public void onDisconnected(Channel channel)
	{
		System.out.println("Disconnected.");
	}
	
	@Override
	public void onReceived(Channel channel, String msg)
	{
		System.out.println(msg);
	}
	
	public void start() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Name : ");
		String name = scanner.nextLine();
		
		System.out.print("IP : ");
		String ip = scanner.nextLine();

		System.out.print("Port : ");
		int port = Integer.parseInt(scanner.nextLine());
		
		client = new Client(name, this);
		client.connect(ip, port);
		
		while(true)
		{
			String msg = scanner.nextLine();
			
			if(msg.isEmpty())
				break;
			
			client.send(msg);
		}
		
		scanner.close();
		client.stop();
	}
	
	public static void main(String[] args) throws IOException
	{
		ClientProgram program = new ClientProgram();
		program.start();
	}
}
