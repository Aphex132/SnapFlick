import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.*;
import javax.imageio.ImageIO;
public class Mainr extends JFrame{
	//Logic Elements
	private int destset;
	private File output;
	private File outputrecievedselect;
	private String outputrecievedfile;
	private File outputforfile;
	private String check;
	private String Destinationrecievedfile;
	private String oshown;
	private Robot screenr;
	private Rectangle screen;
	private BufferedImage image;
	private int photonumber;
	//GUI Elements
	private JButton Capture;
	private JButton SetDestination;
	private JLabel DestinationLabel;
	private JFileChooser ChooseDestination = new JFileChooser();
	private JFrame main = new JFrame();
	public Mainr(){
		super("SnapFlick");
		main = new JFrame();
		main.setLayout(new FlowLayout(FlowLayout.CENTER));
		Capture = new JButton("    Capture Screen    ");
		oshown = seedest();
		SetDestination = new JButton("   Where Is Your Picture Going?   ");
		DestinationLabel = new JLabel("Destination: "+oshown);
		main.add(Capture);
		main.add(SetDestination);
		main.add(DestinationLabel);
		thehandler handler = new thehandler();
		Capture.addActionListener(handler);
		SetDestination.addActionListener(handler);
		main.setSize(350,150);
		main.setVisible(true);
		System.out.print(this);
		
		
	}
	public void setDest(){
		if(destset == 1){
			
		}else{
			FileWriter fstream;
			try {
				fstream = new FileWriter("Destination.txt");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(outputforfile.toString());
				out.close();
				System.out.print(outputforfile.toString());
				DestinationLabel.setText(outputforfile.toString());
				System.out.print(output.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			destset = 1;
			try{
				fstream = new FileWriter("check.txt");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(destset);
				out.close();
			}catch(Exception e1){
				
			}
			
		}
	}
	public String checkdest(){
		try{
		FileInputStream fstream = new FileInputStream("check.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		check = br.readLine();
		
		}catch(Exception e1){
			
		}
		return check;
	}
	public String seedest(){
		String read = null;
		try{
			FileInputStream fstream = new FileInputStream("Destination.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br =  new BufferedReader(new InputStreamReader(in));
			read = br.readLine();
		}catch(Exception e1){
			
		}
		return read;
	}
	public void capture(){
		main.setVisible(false);
		try{
			screenr = new Robot();
		}catch(Exception e){
			
		}
		screen = new Rectangle(1280,800);
		image = screenr.createScreenCapture(screen);
		decidedest();
		try {
			ImageIO.write(image, "jpg", output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		main.setVisible(true);
	}
	public void decidedest(){
		checkdest();
		if(check !=null){
			Destinationrecievedfile = seedest();
			System.out.println(Destinationrecievedfile);
			output = new File(Destinationrecievedfile);
			while(output.exists()){
				output = new File(Destinationrecievedfile + "/newphoto"+photonumber+".jpg");
				photonumber++;
				}
			System.out.println(Destinationrecievedfile);
			
		}else{
			DestinationLabel.setText("Please Select Destination");
		}
	}
	private class thehandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Object source = e.getSource();
			if(source == Capture){
				capture();
			}else if(source == SetDestination){
				ChooseDestination.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				ChooseDestination.showOpenDialog(null);
				outputrecievedselect = ChooseDestination.getSelectedFile();
				outputforfile = outputrecievedselect;
				output = outputrecievedselect;
				while(output.exists()){
					output = new File(outputrecievedselect + "/newphoto"+photonumber+".jpg");
					photonumber++;
					}
				setDest();
				
			}
			
		}
			
	}

}


