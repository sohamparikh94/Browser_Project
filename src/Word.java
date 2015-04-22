public class Word {
	Word parent;
	String word;
	int freq;
	Word right;
	Word left;
	int lefth;
	int righth;
	int level;
	Word(String str, int f){
		this.word = str;
		this.freq = f;
		this.parent = null;
		this.right = null;
		this.left = null;
		this.lefth = 0;
		this.righth = 0;
		this.level = 0;
	}
	public void cleanup(){
		if(!Character.isLetter(this.word.charAt(this.word.length() - 1))){
			String str = "";
			for(int i = 0; i < word.length() - 1; i++){
				str += word.charAt(i);
			}
			this.word = str;
		}
	}
}
