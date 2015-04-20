import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImagingOpException;

public class Driver extends JFrame implements ActionListener, HyperlinkListener{
	JEditorPane mainFrame = new JEditorPane();
	JScrollPane displayPage = new JScrollPane(mainFrame);
	TextField url_field = new TextField();
	ArrayList<String> visited = new ArrayList<String>();
	JComboBox<String> history;
	JButton  back = new JButton("prev");
	JButton  forward = new JButton("next");
	int counter = 0;
	String error = "<html><head><title>ERROR!</title></head><body><h1>Page not found or cannot be opened:( </h1><p>Make sure that you have typed the website name properly<br>Try adding www at the start<br></p></body></html>";
	LinkedList<String> trace = new LinkedList<String>();
	public static void main(String args[]){
		new Driver().frameComponents();
		System.setProperty("jsse.enableSNIExtension", "false");
	}
	public void frameComponents(){
		setTitle("Browser");
		setSize(1300, 1300);
		setVisible(true);
		setLayout(null);
		addComponentstoFrame(getContentPane());
	}
	
	public void addComponentstoFrame(Container c){
		try {
			BufferedReader inp = new BufferedReader(new FileReader("history.txt"));
			String str;
			while((str = inp.readLine()) != null) {
				visited.add(str);
			}
			inp.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
		Insets in = getInsets();
		c.add(url_field);
		c.add(displayPage);
		Font font = new Font("Times New Roman", Font.PLAIN, 20);
		url_field.setFont(font);
		mainFrame.setEditable(false);
		history = new JComboBox(visited.toArray());
		history.setEditable(true);
		url_field.setBounds(160 - in.left, 40 - in.top, 800, 30);
		displayPage.setBounds(10 - in.left, 70 - in.top, 1360, 1270);
		history.setBounds(1000 - in.left, 40 - in.top, 300, 30);
		back.setBounds(10 -in.left, 40 - in.top, 70, 30);
		forward.setBounds(85 -in.left, 40 - in.top, 70, 30);
		url_field.addActionListener(this);
		mainFrame.addHyperlinkListener(this);
		c.add(history);
		c.add(back);
		c.add(forward);
		history.addActionListener(this);
		back.addActionListener(this);
		forward.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == back ){
			if(counter != 0) {
				counter--;
				try {
					mainFrame.setPage(trace.get(counter - 1));
					url_field.setText(trace.get(counter - 1));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		if(e.getSource() == forward){
			if(counter != trace.size()) {
				counter++;
				try{
					mainFrame.setPage(trace.get(counter - 1));
					url_field.setText(trace.get(counter - 1));
				}
				catch(IOException e2){
					
				}
			}
		}
		if(e.getSource() == history){
			try{
				String url = history.getSelectedItem().toString();
				if(url.startsWith("http")){
					mainFrame.setPage(url);
					url_field.setText(url);
					updateHistory(url_field.getText());
				}
				else{
					mainFrame.setPage("https://" + url);
					url_field.setText("https://" + url);
					updateHistory(url_field.getText());
				}
			}
			catch(IOException exception){
				System.out.println("Cannot open the webpage");
				try{
					URL url = new URL("https://" + url_field.getText());
					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					String html_code = "", str;
					while((str = in.readLine()) != null){
						html_code += str;
					}
					in.close();
					mainFrame.setPage(html_code);
				}
				catch(IOException io){
					System.out.println("Webpage does not exist");
				}
			}

		}
		if(e.getSource() == url_field){
			try{
				String url = url_field.getText();
				if(url.startsWith("http")){
					mainFrame.setPage(url);
					url_field.setText(url);
					updateHistory(url_field.getText());
				}
				else{
					mainFrame.setPage("https://" + url);
					url_field.setText("https://" + url);
					updateHistory(url_field.getText());
				}
			}
			catch(IOException exception){
				System.out.println("Cannot open the webpage");
				try{
					URL url = new URL("https://" + url_field.getText());
					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					String html_code = "", str;
					while((str = in.readLine()) != null){
						html_code += str;
					}
					in.close();
					mainFrame.setPage(html_code);
				}
				catch(IOException io){
					try{
						mainFrame.setPage(error);
					}
					catch(IOException ioo){
						System.out.println("Webpage does not exist");
					}
				}
			}
		}
	}
	public void hyperlinkUpdate(HyperlinkEvent e){
		if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
			try{
				String str = e.getURL().toString();
				mainFrame.setPage(str);
				updateHistory(str);
			}
			catch(IOException io){
				
			}
		}
	}
	private void updateHistory(String str) throws FileNotFoundException{
		for(int i = counter; i < trace.size(); i++){
			trace.remove(i);
		}
		trace.add(str);
		counter = trace.size();	
		if(!visited.contains(str)) {
			visited.add(str);
			history.addItem(str);
		}
		history.setSelectedItem(str);
		File fout = new File("history.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter outp = new BufferedWriter(new OutputStreamWriter(fos));
		try{
			for(int i = 0 ; i < visited.size(); i++){
				outp.write(visited.get(i));
				outp.newLine();
			}
			outp.close();
		}
		catch(IOException io){
		}
	}
}