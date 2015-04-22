import java.io.*;
import java.util.*;


public class Rank_Algorithm {
	PriorityQueue<Page> rank = new PriorityQueue<Page>();
	ArrayList<String> common_words;
	
	public void get_common_words(){
		common_words = new ArrayList<String>();
		String str;
		try {
			BufferedReader in = new BufferedReader(new FileReader("common.txt"));
			while((str = in.readLine()) != null) {
				common_words.add(str.toLowerCase());
			}
		} 
		catch (IOException e) {
			System.out.println("Cannot open the file common.txt");
		}
	}
	public PriorityQueue<Page> rank_pages(Url[] arr, String query) {
		Page p;
		PriorityQueue<Page> pages = new PriorityQueue<Page>();
		for(int i = 0; i < arr.length; i++){
			p = url_to_page(arr[i], query);
			pages.add(p);
		}
		return pages;
	}
	
	public Page url_to_page(Url u, String query){
		Page p = new Page();
		
		int t = 0, ur = 0, f = 0;
		
		String title_words[] = u.title.split("\\s+");
		String content_words[] = u.content.split("\\s+");
		String query_words[] = query.split("\\s+");
		
		ArrayList<String> t_words = new ArrayList<String>(Arrays.asList(title_words));
		ArrayList<String> c_words = new ArrayList<String>(Arrays.asList(content_words));
		ArrayList<String> q_words = new ArrayList<String>(Arrays.asList(query_words));
		
		AVL_Words main_words = new AVL_Words();
		AVL_Words main_t_words = new AVL_Words();
		AVL_Words com_words = new AVL_Words();
		
		for(int i = 0; i < q_words.size(); i++){
			q_words.set(i, clean(q_words.get(i).toLowerCase()));
		}
		for(int i = 0; i < t_words.size(); i++){
			t_words.set(i, clean(t_words.get(i)));
			if(!common_words.contains(t_words.get(i).toLowerCase())){
				main_t_words.increase_key(t_words.get(i), main_words);
			}
		}
		for(int i = 0; i < c_words.size(); i++){
			c_words.set(i, clean(c_words.get(i).toLowerCase()));
			if(!common_words.contains(c_words.get(i).toLowerCase())){
				main_words.increase_key(c_words.get(i), main_words);
			}
		}
		for(int i = 0; i < q_words.size(); i++){
			if(!common_words.contains(q_words.get(i))){
				t += main_t_words.search(q_words.get(i));
				f += main_words.search(q_words.get(i));
				if(u.link.contains(q_words.get(i))) ur++;
			}
		}
		
		p.freq = f;
		p.title_freq = t;
		p.url_freq = ur;
		if(t > 0) p.is_in_title = true;
		if(ur > 0) p.is_in_url = true;
		return p;
	}
	public static String clean(String string) {
		// TODO Auto-generated method stub
		if(!Character.isLetter(string.charAt(string.length() - 1))){
			String str = "";
			for(int i = 0; i < string.length() - 1; i++){
				str += string.charAt(i);
			}
			return str;
		}
		return string;
	}
}
