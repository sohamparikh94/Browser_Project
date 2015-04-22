import java.util.*;

public class Page implements Comparator<Page>{
	String url;
	boolean is_in_title;
	int title_freq;
	boolean is_in_url;
	int url_freq;
	int freq;
	
	Page(){
		url = "";
		is_in_title = false;
		is_in_url = false;
		url_freq = 0;
		freq = 0;
		title_freq = 0;
	}
	@Override
	public int compare(Page o1, Page o2) {
		// TODO Auto-generated method stub
		if(o1.is_in_title == o2.is_in_title){
			if(o1.title_freq > o2.title_freq) return 1;
			else if(o1.title_freq < o2.title_freq) return -1;
			else if(o1.is_in_url == o2.is_in_url){
				if(o1.url_freq > o2.url_freq) return 1;
				else if(o1.url_freq < o2.url_freq) return -1;
				else if(o1.freq > o2.freq) return 1;
				else return -1;
			}
			else if(o1.is_in_url == true) return 1;
			else return -1;
		}
		else if(o1.is_in_title == true) return 1;
		else return -1;
	}
}
