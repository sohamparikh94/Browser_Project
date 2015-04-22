
public class AVL_Words{
	Word root;
	
	AVL_Words() {
		// TODO Auto-generated constructor stub
	}
	
	AVL_Words(Word w){
		this.root = w;
	}
	
	
	void setlevel(Word n){
		if(n == null) return;
		else{
			if(n.parent != null) n.level = n.parent.level + 1;
			else n.level = 0;
			setlevel(n.left);
			setlevel(n.right);
		}
	}
	
	qnode de(qu q) {
		qnode tempx;
		qnode temp;
		if( q == null ) {
			temp = new qnode();
			temp = null;
			return temp;
		}
		else if( q.tail == q.head ) {
			temp = q.tail;
			q.tail = null;
			q.head = null;
			return temp;
		}
		else {
			temp = q.tail;
			while(temp.next != q.head ) {
				temp = temp.next;
			}
			tempx = q.head;
			q.head = temp;
			return tempx;
		}
	}
	
	qu en(qu q, Word n) {
		qnode temp;
		qnode tempx;
		temp = new qnode();
		temp.cur = n;
		if( q.tail == null && q.head == null ) {
			temp.next = null;
			q.tail = temp;
			q.head = temp;
		}
		else {
			tempx = q.tail;
			temp.next = tempx;
			q.tail = temp;
		}
		return q;
	}
	
	void print() {
		qu q = new qu();
		qnode temp;
		if(this.root == null ) System.out.printf("");
		else { 
			en(q, this.root);
			int i = 0;
			while(q.head != null) {
				temp = de(q);
				if(temp.cur.level == i) System.out.printf("%s %d ",temp.cur.word, temp.cur.freq);
				else{
					System.out.println("# ");
					i++;
					System.out.printf("%s %d ",temp.cur.word, temp.cur.freq);
				}
				if(temp.cur.left == null ) {
					if( temp.cur.right == null) {
						System.out.printf("");
					}
					else en(q, temp.cur.right);
				}
				else {
					en(q, temp.cur.left);
					if(temp.cur.right == null ) {
						System.out.printf("");
					}
					else en(q, temp.cur.right);
				}
			}
		}
	}
	
	void bst_sorted_print( AVL_Words tree ) {
		AVL_Words newn;
		if( tree.root == null ) System.out.printf("");
		else {
			newn = new AVL_Words();
			newn.root= tree.root.left;
			bst_sorted_print( newn );
			System.out.printf("%s %d ", tree.root.word,tree.root.freq);
			newn.root = tree.root.right;
			bst_sorted_print( newn );
		}
	}
	
	int search(String string){
		if(string == root.word){
			return root.freq;
		}
		else if(string.compareToIgnoreCase(root.word) < 0){
			if(root.left == null) return 0;
			else{
				AVL_Words avl = new AVL_Words(root.left);
				return avl.search(string);
			}
		}
		
		else{
			if(root.right == null) return 0;
			else{
				AVL_Words avl = new AVL_Words(root.left);
				 return avl.search(string);
			}
		}
	}

	void rotateright(Word n, AVL_Words t){
		if(n.parent == null){
			Word temp;
			n.parent = n.left;
			temp = n.parent.right;
			n.parent.right = n;
			if(temp != null) temp.parent = n;
			n.left = temp;
			n.parent.parent = null;
			this.root = n.parent;
		}
		else{
			int z;
			Word temp;
			Word tempx = n.parent;
			if(tempx.right == n) z = 1;
			else z = 0;
			n.parent = n.left;
			temp = n.parent.right;
			n.parent.right = n;
			if(temp != null) temp.parent = n;
			n.left = temp;
			n.parent.parent = tempx;
			if(z == 0) tempx.left = n.parent;
			else tempx.right = n.parent;
		}
		n.parent.level = n.level;
		n.level = n.level + 1;
		if(n.right != null) n.right.level = n.level + 1;
		if(n.parent.left != null) n.parent.left.level = n.parent.level + 1;
		if(n.left == null) n.lefth = 1;
		else n.lefth = 1 + (int) Math.max(n.left.lefth,n.left.righth);
		n.parent.righth = 1 + (int) Math.max(n.lefth, n.righth);
		if(n.parent == this.root) return;
		else setheight(n);
		
	}
	
	void rotateleft(Word n, AVL_Words t){
		if(n.parent == null){
			Word temp;
			n.parent = n.right;
			temp = n.parent.left;
			n.parent.left = n;
			if(temp != null) temp.parent = n;
			n.right = temp;
			n.parent.parent = null;
			this.root = n.parent;
		}
		else{
			int z;
			Word temp;
			Word tempx = n.parent;
			if(tempx.right == n) z = 1;
			else z = 0;
			n.parent = n.right;
			temp = n.parent.left;
			n.parent.left = n;
			if(temp != null) temp.parent = n;
			n.right = temp;
			n.parent.parent = tempx;
			if(z == 0) tempx.left = n.parent;
			else tempx.right = n.parent;
		}
		n.parent.level = n.level;
		n.level = n.level + 1;
		if(n.left != null) n.left.level = n.level + 1;
		if(n.parent.right != null) n.parent.right.level = n.parent.level + 1;
		if(n.right == null) n.righth = 1;
		else n.righth = 1 + (int) Math.max(n.right.lefth,n.right.righth);
		n.parent.lefth = 1 + (int) Math.max(n.lefth, n.righth);
		if(n.parent == this.root) return;
		else setheight(n);

	}
	
	void rectify(Word n, AVL_Words t){
		if(n.parent == null || n.parent.parent == null) return;
		else if(Math.abs(n.parent.parent.lefth - n.parent.parent.righth) > 1){
			Word p = n.parent;
			Word gp = p.parent;
			if(gp.left == p){
				if(p.left == n){
					t.rotateright(gp, t);
				}
				else {
					t.rotateleft(p, t);
					t.rotateright(gp, t);
				}
			}
			else{
				if(p.right == n){
					t.rotateleft(gp, t);
				}
				else{
					t.rotateright(p, t);
					t.rotateleft(gp, t);
				}
			}
		}
		else t.rectify(n.parent, t);
	}
	
	void setheight(Word n){
		if(n.left == null && n.right == null) {
			n.lefth = 1;
			n.righth = 1;
		}
		else if(n.left == null) {
			n.righth = (int) Math.max(n.right.righth, n.right.lefth) + 1;
			n.lefth = 1;
		}
		else if(n.right == null) {
			n.lefth = (int) Math.max(n.left.righth, n.left.lefth) + 1;
			n.righth = 1;
		}
		else{
			n.righth = (int) Math.max(n.right.righth, n.right.lefth) + 1;
			n.lefth = (int) Math.max(n.left.righth, n.left.lefth) + 1;
		}
		if(n.parent != null) this.setheight(n.parent);
	}

	
	void add(Word k, AVL_Words t){
		AVL_Words tree;
		if(root == null) {
			this.root = k;
		}
		else{
			if(k.word.compareToIgnoreCase(root.word) > 0){
				if(root.right == null) {
					k.parent = root;
					k.level = k.parent.level  + 1;
					root.right = k;
					root.righth = root.righth + 1;
					this.setheight(k);
					t.rectify(k, t);
					return;
				}
				else {
					tree = new AVL_Words(root.right);
					tree.add(k, t);
				}
			}
			else if(k.word.compareToIgnoreCase(root.word) < 0){
				if(root.left == null){
					k.parent = root;
					k.level = k.parent.level + 1;
					root.left = k;
					root.lefth = root.lefth + 1;
					this.setheight(k);
					t.rectify(k, t);
					return;
				}
				else {
					tree = new AVL_Words(root.left);
					tree.add(k, t);
				}
			}
		}
	}

	void remove(String k, AVL_Words t){
		if(k.compareToIgnoreCase(root.word) > 0){
			AVL_Words tree = new AVL_Words(root.right);
			tree.remove(k, t);
		}
		else if(k.compareToIgnoreCase(root.word) < 0){
			AVL_Words tree = new AVL_Words(root.left);
			tree.remove(k, t);
		}
		else {
			if(root.left == null && root.right == null){
				if(root.parent == null){
					t.root = null;
					return;
				}
				else {
					if(root.parent.left == root) {
						root.parent.left = null;
					}
					else if(root.parent.right == root) {
						root.parent.right = null;
					}
					setheight(root.parent);
					rectify(root.parent, t);
					return;
				}
			}
			else if(root.left == null){
				if(root.parent == null){
					if(root.freq == 2) System.out.println("Hello");
					root.freq = root.right.freq;
					root.level = 0;
					Word n = root.right;
					root.right = n.right;
					root.left = n.left;
					setheight(root);
					setlevel(root.right);
					setlevel(root.left);
					return;
				}
				else{
					if(root.parent.left == root){
						root.parent.left = root.right;
						root.parent.left.parent = root.parent;
						root.parent.left.level = root.parent.level + 1;
						setheight(root.parent);
						setlevel(root.parent);
						rectify(root.parent.left, t);
					}
					else {
						root.parent.right = root.right;
						root.parent.right.parent = root.parent;
						root.parent.left.level = root.parent.level + 1;
						setheight(root.parent);
						setlevel(root.parent);
						rectify(root.parent.right, t);
					}
				}
			}
			else if(root.right == null){
				if(root.parent == null){
					root.freq = root.left.freq;
					Word n = root.left;
					root.left = n.left;
					root.right = n.right;
					root.level = 0;
					setheight(root);
					setlevel(root);
					rectify(root, t);
					return;
				}
				else{
					if(root.parent.left == root){
						root.parent.left = root.left;
						root.parent.left.parent = root.parent;
						root.parent.left.level = root.parent.level + 1;
						setheight(root.parent);
						setlevel(root.parent);
						rectify(root.parent.left, t);
					}
					else {
						root.parent.right = root.left;
						root.parent.right.parent = root.parent;
						root.parent.left.level = root.parent.level + 1;
						setheight(root.parent);
						setlevel(root.parent);
						rectify(root.parent.right, t);
					}
				}
			}
			else{
				Word temp;
				temp = root.left;
				while(temp.right != null) temp = temp.right;
				if(temp == root.left){
					if(root.parent == null){
						root.freq = temp.freq;
						root.left = root.left.left;
						if(root.left != null) {
							root.left.level = root.level + 1;
							root.left.parent = root;
						}
						setlevel(root.left);
						setheight(root);
						rectify(root, t);
					}
					else if(root.parent.left == root){
						root.parent.left = root.left;
						root.parent.left.parent = root.parent;
						root.parent.left.level = root.parent.level + 1;
						root.parent.left.right = root.right;
						setlevel(root.parent.left);
						setheight(root.parent.left);
						rectify(root.parent.left, t);
					}
					else{
						root.parent.right = root.left;
						root.parent.right.parent = root.parent;
						root.parent.right.right = root.right;
						root.parent.right.level = root.parent.level + 1;
						setlevel(root.parent.left);
						setheight(root.parent.right);
						rectify(root.parent.right, t);
					}
				}
				else{
					root.freq = temp.freq;
					if(temp.left == null) {
						temp.parent.right = null;
						setheight(temp.parent);
						rectify(temp.parent, t);
					}
					else {
						temp.parent.right = temp.left;
						temp.parent.right.parent = temp.parent;
						setheight(temp.parent);
						temp.parent.right.level = temp.parent.level + 1;
						setheight(temp.parent.right);
						setlevel(temp.parent);
						rectify(temp.parent.right, t);
					}
				}
			}
		}
	}

	void increase_key(String k, AVL_Words t){
		if(this.root == null){
			Word w =  new Word(k , 1);
			t.add(w, t);
			return;
		}
		if(k.compareTo(this.root.word) == 0) {
			int m = this.root.freq + 1;
			this.root.freq = m;
		}
		else if(k.compareToIgnoreCase(this.root.word) > 0){
			if(this.root.right == null) t.add(new Word(k, 1), t);
			else{
				AVL_Words avl = new AVL_Words(root.right);
				avl.increase_key(k, t);
			}
		}
		else{
			if(this.root.left == null) t.add(new Word(k, 1), t);
			else{
				AVL_Words avl = new AVL_Words(root.left);
				avl.increase_key(k, t);
			}
		}
	}
	void decrease_key(String k, AVL_Words t){
		if(root.word == k) {
			if(root.freq == 1) this.remove(k, t);
			else root.freq--;
		}
		else if(k.compareToIgnoreCase(root.word) > 0){
			if(root.right == null) return;
			else{
				AVL_Words avl = new AVL_Words(root.right);
				avl.decrease_key(k, t);
			}
		}
		else{
			if(root.left == null) return;
			else{
				AVL_Words avl = new AVL_Words(root.left);
				avl.decrease_key(k, t);
			}
		}
	}
}
