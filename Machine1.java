//package layout_manager;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.*;

public class Machine1 implements ActionListener {
	
	private static int compteur1 = 0, compteur2 = 0, compteur3 = 0, compteur4 = 0;
	private int imageType = 0; // 1, 2 ou 3
	private JButton btn;
	private ImageIcon[] tab;
	
	private static String url1 = "img/Drones/D1.jpg";
	private static String url2 = "img/Drones/D2.jpg";
	private static String url3 = "img/Drones/D3.jpg";
	private static String url4 = "img/Drones/D4.jpg";
	private static ImageIcon[] TABIMAGES1 = {new ImageIcon(url1), new ImageIcon(url2), new ImageIcon(url3),new ImageIcon(url4)};
    
	private static String url5 = "img/Fruits/F1.jpg";
	private static String url6 = "img/Fruits/F2.jpg";
	private static String url7 = "img/Fruits/F3.jpg";
	private static String url8 = "img/Fruits/F4.jpg";
	private static ImageIcon[] TABIMAGES2 = {new ImageIcon(url5), new ImageIcon(url6), new ImageIcon(url7), new ImageIcon(url8)};
	
	private static String url9 = "img/Maisons/M1.jpg";
	private static String url10 = "img/Maisons/M2.jpg";
	private static String url11 = "img/Maisons/M3.jpg";
	private static String url12 = "img/Maisons/M4.jpg";
	private static ImageIcon[] TABIMAGES3 = {new ImageIcon(url9), new ImageIcon(url10), new ImageIcon(url11), new ImageIcon(url12)};
	
	private static String url13 = "img/Voitures/V1.jpg";
	private static String url14 = "img/Voitures/V2.jpg";
	private static String url15 = "img/Voitures/V3.jpg";
	private static String url16 = "img/Voitures/V4.jpg";
	private static ImageIcon[] TABIMAGES4 = {new ImageIcon(url13), new ImageIcon(url14), new ImageIcon(url15), new ImageIcon(url16)};

	Machine1(JButton lebtn, ImageIcon[] letab, int imageType) {
		// TODO Auto-generated constructor stub
		 btn = lebtn;
		 tab = letab;
		 this.imageType = imageType;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame fen = new JFrame("Les images");
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setBounds(50, 100, 300, 500);
		JButton lebtn1 = new JButton(TABIMAGES1[compteur1]);
		JButton lebtn2 = new JButton(TABIMAGES2[compteur2]);
		JButton lebtn3 = new JButton(TABIMAGES3[compteur3]);
		JButton lebtn4 = new JButton(TABIMAGES4[compteur4]);
		lebtn1.addActionListener(new Machine1(lebtn1, TABIMAGES1, 1));
		lebtn2.addActionListener(new Machine1(lebtn2, TABIMAGES2, 2));
		lebtn3.addActionListener(new Machine1(lebtn3, TABIMAGES3, 3));
		lebtn4.addActionListener(new Machine1(lebtn4, TABIMAGES4, 4));
		
		Container cont = fen.getContentPane();
		cont.setLayout(new GridLayout(4,1));
		cont.add(lebtn1);
		cont.add(lebtn2);
		cont.add(lebtn3);
		cont.add(lebtn4);
		fen.pack();
		fen.setVisible(true);
		
		JButton[] tabBtns = {lebtn1, lebtn2, lebtn3, lebtn4};
		ImageIcon[][] tabIcons = {TABIMAGES1, TABIMAGES2, TABIMAGES3, TABIMAGES4};
		fen.setSize(300, 800);
		handleEvent(tabBtns, tabIcons);
	}
    @Override
	public void actionPerformed(ActionEvent e) {
		//TODO Auto-generated method stub
		switch(imageType){
			case 1:
				compteur1 = (compteur1 + 1)% tab.length;
				btn.setIcon(tab[compteur1]);
				emitEvent(compteur1);
				break;
			case 2:
				compteur2 = (compteur2 + 1)% tab.length;
				btn.setIcon(tab[compteur2]);
				emitEvent(compteur2);
				break;
			case 3:
				compteur3 = (compteur3 + 1)% tab.length;
				btn.setIcon(tab[compteur3]);
				emitEvent(compteur3);
				break;
			case 4:
				compteur4 = (compteur4 + 1)% tab.length;
				btn.setIcon(tab[compteur4]);
				emitEvent(compteur4);
				break;
		}
		
	}

	void emitEvent(int compteur) {
		System.out.println("------onclick------");
		InetAddress address;
		DatagramSocket socketUDP; // for sending message
		DatagramPacket message; // get an instance of message
		BufferedReader bf;
		String ligne;
		int length;
		byte[] tampon, tamponR; // transform all sending data to bytes
		int destinationPort;
		try {
			socketUDP = new DatagramSocket();
			System.out.println("portlocal :" +socketUDP.getLocalPort());
			address = InetAddress.getByName("127.0.0.1");
			destinationPort = Integer.parseInt("400");
			bf = new BufferedReader(new InputStreamReader(System.in));
			
			ligne = ""+imageType+""+compteur;
			tampon = ligne.getBytes();
			length = tampon.length;
			message = new DatagramPacket(tampon, length, address, destinationPort);
			socketUDP.send(message);
			System.out.println("Du port " + message.getPort() + " de la machine " + message.getAddress().getHostName() + ": " + ligne);
			System.out.println("type d'image : " + imageType + " indice tu tableau : " + compteur);
			
			
		}
		catch (ArrayIndexOutOfBoundsException err) {
			System.out.println("Avez-vous donné le nom de la machine destinatrice et le numéro du port client");
			// TODO: handle exception
		}
		catch (UnknownHostException err) {
			// TODO: handle exception
			System.out.println("Destinataire inconnue");
		}
		catch (SocketException err) {
			// TODO: handle exception
			System.out.println("Problème d'ouverture de socket");
		}
		catch (IOException err) {
			// TODO: handle exception
			System.out.println("Problème sur la reception ou l'envie de message");
		}
		catch (NumberFormatException err) {
			// TODO: handle exception
			System.out.println("le second argument doit être un entier");
		}
	}

	private static void handleEvent(JButton[] tabBtns, ImageIcon[][] tabsIcon) {
		DatagramSocket socketUDP;
		DatagramPacket message;
		byte[] tampon;
		int localPort;
		byte[] accuseTampon = "Accuse de reception".getBytes();
		int accuseLenght = accuseTampon.length;
		String text;
		
		while(true) {
			try {
				localPort = Integer.parseInt("300");  
				socketUDP = new DatagramSocket(localPort);
				while (true) {
					tampon = new byte[256];
					message = new DatagramPacket(tampon, tampon.length);
					socketUDP.receive(message);
					InetAddress addressIp = message.getAddress();
					int portSortant = message.getPort();
					text = new String(tampon);
					text = text.substring(0, message.getLength());
					System.out.println("Reception du port" + portSortant + ""
							+ "de la machine " + addressIp.getHostName() + " ids : " + text);
					message = new DatagramPacket(accuseTampon, accuseLenght, addressIp, portSortant);
					socketUDP.send(message);
					
					switch(Integer.parseInt(""+text.charAt(0))){
						case 1:
							compteur1 = Integer.parseInt(""+text.charAt(1));
							tabBtns[0].setIcon(tabsIcon[0][compteur1]);
							break;
						case 2:
							compteur2 = Integer.parseInt(""+text.charAt(1));
							tabBtns[1].setIcon(tabsIcon[1][compteur2]);
							break;
						case 3:
							compteur3 = Integer.parseInt(""+text.charAt(1));
							tabBtns[2].setIcon(tabsIcon[2][compteur3]);
							break;
						case 4:
							compteur4 = Integer.parseInt(""+text.charAt(1));
							tabBtns[3].setIcon(tabsIcon[3][compteur4]);
							break;
					}
					}
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Avez-vous donné le nom de la machine destinatrice et le numéro du port client");
				// TODO: handle exception
			}
			catch (SocketException e) {
				// TODO: handle exception
				System.out.println("Problème d'ouverture de socket" + e);
			}
			catch (IOException e) {
				// TODO: handle exception
				System.out.println("Probl�me sur la reception ou l'envie de message");
			}
		}
	}	
}
