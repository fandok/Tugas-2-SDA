import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Now Playing: The Coldplay - Scientist
 * Semoga Batch 1,2,3 bisa dapet 100 semua :)
 */

/**
 * Name: Fandika Okdiba 
 * Student ID: 1406571136 
 * Version: 1.0 
 * Title: Tugas 2 SDA
 */

/*
 * Kelas Main sebagai Penerima Input
 */

public class SDA14152T {
	public static void main(String[] args) throws IOException {

		// Jadi, teman saya, George Albert telah membuat sebuah GraderTugas dengan TestCase-Testcase yang telah dibuat
		// Dibawah ini adalah cara untuk mengerjakan GraderTugas-nya
		BufferedReader readers;

		if (args.length == 0)
			readers = new BufferedReader(new InputStreamReader(System.in));
		else
			readers = new BufferedReader(new FileReader(new File(args[0])));

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				System.out));

		// --------------------------------------
		
		
		
		Order agent = new Order();
		String test1 = "zz";
		while ((test1 = reader.readLine()) != null) {
			String[] input = test1.split(" ");
			switch (input[0].toUpperCase()) {
			case ("ADD"):
				String tobeAdded = input[1];
				String tobeAddto = input[2];
				Laci insert = new Laci(tobeAdded);
				agent.add(insert, tobeAddto);
				break;
			case ("PUT"):
				String key = input[1];
				int weight = Integer.parseInt(input[2]);
				String laci = input[3];
				agent.put(key, weight, laci, writer);
				break;
			case ("THROW"):
				String throwed = input[1];
				agent.buang2(throwed, writer);
				break;
			case ("MOVE"):
				break;
			case ("CETAK"):
				String cetak = input[1];
				agent.cetak2(cetak, writer);
				break;
			case ("DEPTH"):
				String kunciMasuk = input[1];
				String laciNya = input[2];
				agent.depth(kunciMasuk, laciNya);
				break;
			case ("FIND"):
				String tobeFind = input[1];
				agent.cari2(tobeFind, writer);
				break;
			}
		}
	}
}

/*
 * Kelas TreeNode adalah kelas Node dari N-ary Tree 
 */

class TreeNode<E extends Comparable<E>> implements Comparable<TreeNode<E>> {

	E data;
	SortedArrayList<TreeNode<E>> children;
	TreeNode<E> parent;

	public TreeNode(E data) {
		this.data = data;
		this.children = new SortedArrayList<TreeNode<E>>();
	}

	public TreeNode(TreeNode<E> node) {
		this.data = (E) node.getData();
		children = new SortedArrayList<TreeNode<E>>();
	}

	public void addChild(TreeNode<E> child) {
		child.parent = this;
		children.add(child);
	}

	public void addChildAt(int index, TreeNode<E> child) {
		child.parent = this;
		this.children.add(index, child);
	}

	public void setChildren(SortedArrayList<TreeNode<E>> children) {
		for (TreeNode<E> child : children)
			child.parent = this;

		this.children = children;
	}

	public void removeChildren() {
		this.children = new SortedArrayList<TreeNode<E>>();
	}

	public TreeNode<E> removeChildAt(int index) {
		return children.remove(index);
	}

	public void removeThisIfItsAChild(TreeNode<E> childToBeDeleted) {
		List<TreeNode<E>> list = getChildren();
		list.remove(childToBeDeleted);
	}

	public E getData() {
		return this.data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public TreeNode<E> getParent() {
		return this.parent;
	}

	public void setParent(TreeNode<E> parent) {
		this.parent = parent;
	}

	public SortedArrayList<TreeNode<E>> getChildren() {
		return this.children;
	}

	public TreeNode<E> getChildAt(int index) {
		return children.get(index);
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;

		if (obj instanceof TreeNode) {
			if (((TreeNode<?>) obj).getData().equals(this.data))
				return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return this.data.toString();
	}

	public int compareTo(TreeNode<E> other) {
		return this.getData().compareTo(other.getData());
	}
}

/*
 * Kelas Order adalah Kelas yang memproses semua inputan yang ada
 */


class Order {

	// Terdapat N-ary Tree yang ditampung disini
	
	Tree<Bismillah> tempat = new Tree<Bismillah>(new TreeNode<Bismillah>(
			new Laci("laci1")));

	
	
	/** Memasukkan laci ke dalam laci
	 * 
	 * @param a
	 * 	Laci yang akan masuk ke Tree
	 * @param b
	 * 	Laci yang akan dimasukkan di Tree
	 */
	public void add(Laci a, String b) {
		Laci laciDimasukin = new Laci(b);
		TreeNode<Bismillah> laciYangDimasukin = new TreeNode<Bismillah>(laciDimasukin);
		TreeNode<Bismillah> theLaci = tempat.find(laciYangDimasukin); // yang mau diisi
		TreeNode<Bismillah> laciMasukan = new TreeNode<Bismillah>(a); // yang akan masuk
		
		/* Kondisi dibawah ini apabila saat memasukkan laci, terdapat kunci di dalam laci yang akan dimasukkan
		 * Jadi, kunci tersebut akan dimasukkan lagi ke laci yang baru saja dimasukkan
		 */
		
		if (theLaci.getChildren().size() > 0
				&& theLaci.getChildren().get(0).getData().isFound()) {
			for (int i = 0; i < theLaci.getChildren().size(); i++) {
				theLaci.getChildren().get(i).setParent(laciMasukan);
				laciMasukan.getChildren().add(theLaci.getChildren().get(i));
				theLaci.getChildren().remove(i);
				i = i - 1;
			}
		}
		laciMasukan.setParent(theLaci);
		theLaci.addChild(laciMasukan);
		Collections.sort(theLaci.children);
	}

	

	/**	Memasukkan kunci ke dalam laci
	 * 
	 * @param key
	 * 	Nama dari kunci
	 * @param weight
	 *  berat kunci
	 * @param laci
	 *  laci tempat kunci akan masuk
	 * @param writer
	 * 	BufferedWriter
	 * @throws IOException
	 */
	
	public void put(String key, int weight, String laci, BufferedWriter writer)
			throws IOException {
		Laci tempatMasuk = new Laci(laci);
		Kunci kunci = new Kunci(key, weight, false);
		TreeNode<Bismillah> tempatMasukan = new TreeNode<Bismillah>(tempatMasuk);
		TreeNode<Bismillah> nodeKunci = new TreeNode<Bismillah>(kunci);
		TreeNode<Bismillah> barulagi = tempat.find(tempatMasukan);
		
		// Apabila didalam laci yang akan dimasuki kunci, terdapat laci lain
		// kunci akan masuk ke dalam laci yang di dalam lacinya lagi
		
		if (barulagi.getChildren().isEmpty()
				|| barulagi.getChildren().get(0).getData().isFound()) {
			barulagi.addChild(nodeKunci);
			Collections.sort(barulagi.children);
			writer.write(key + " masuk di " + laci + "\n");
			writer.flush();
		} else if (!barulagi.getChildren().get(0).getData().isFound()) {
			put(key, weight, barulagi.getChildren().get(0).getData().getNama(),writer);

		}

	}

	/** Membuang laci atau kunci yang dimaksud (menggunakan helper)
	 * 
	 * @param tobeThrowed
	 * 	Yang akan dibuang
	 * @param writer
	 * 	BufferedWriter
	 * @throws IOException
	 */
	
	public void buang2(String thing, BufferedWriter writer) throws IOException {
		buang2(tempat.getRoot(), thing, writer);
		flagPrinted = 0;
	}
	
	// flagPrinted berfungsi untuk menandai apakah sudah dicetak atau belum
	private int flagPrinted = 0;
	
	
	// buang2 helper
	private void buang2(TreeNode<Bismillah> node, String buangan,
			BufferedWriter writer) throws IOException {
		
		// Looping sebanyak jumlah node-nya
		for (int index = 0; index < node.getChildren().size(); index++) {
			if (node.getChildren().get(index).getData().getNama().equals(buangan)) {
				if (flagPrinted == 0) {
					if (node.getChildren().get(index).getData().isFound()) {
						writer.write("kunci "
								+ node.getChildren().get(index).getData().getNama()
								+ " dihapus" + "\n");
						writer.flush();
						flagPrinted = flagPrinted + 1;
					} else {
						writer.write("laci "
								+ node.getChildren().get(index).getData().getNama()
								+ " dihapus" + "\n");
						writer.flush();
						flagPrinted = flagPrinted + 1;
					}
				}
				node.getChildren().remove(index);
				index = index-1;
			} else if (node.getChildren().get(index).getChildren().size() > 0) {
					buang2(node.getChildren().get(index), buangan, writer);
				
			}

		}
	}


	
	/** Untuk mencetak tree dimulai dari laci tertentu (menggunakan helper)
	 * @param nama
	 * 	nama awal dari laci yang akan dicetak
	 * @param writer
	 *  buffered writer
	 * @throws IOException
	 */
	public void cetak2(String nama, BufferedWriter writer) throws IOException {
		TreeNode<Bismillah> temp = tempat.find(new TreeNode<Bismillah>(
				new Laci(nama)));
		cetak2(temp, nama, writer, "");
	}

	/** Cetak2 helper
	 * @param node
	 * 	Awal dari node yang akan dicetak
	 * @param nama
	 * 	Nama dari node
	 * @param writer
	 * 	BufferedWriter
	 * @param space
	 * 	Jarak print (2 spasi)
	 * @throws IOException
	 */
	private void cetak2(TreeNode<Bismillah> node, String nama,
			BufferedWriter writer, String space) throws IOException {

		int berat = tambahGendut(node);
		writer.write(space + "> " + node.getData().getNama() + " " + berat
				+ "\n");
		writer.flush();
		space = space + "  ";
		for (TreeNode<Bismillah> child : node.getChildren()) {
			if (child.getData().isFound()) {

				writer.write(space + "> " + child.getData().getNama() + " "
						+ child.getData().getBerat() + "\n");
				writer.flush();
			} else {
				cetak2(child, nama, writer, space);
			}
		}
	}


	// Method ini belum selesai :(:(
	
	/**	Mencari kedalaman kunci dalam sebuah laci. Ada kemungkinan tidak ada kunci dalam laci tersebut (punya helper
	 * @param kunciMasuk
	 * 	nama kunci
	 * @param laciNya
	 * 	nama laci
	 */
	public void depth(String kunciMasuk, String laciNya) {
		depth(kunciMasuk, laciNya,
				tempat.find(new TreeNode<Bismillah>(new Laci(laciNya))), -1);
	}

	/** Depth Helper
	 * @param key
	 * 	nama kunci
	 * @param laci
	 * 	nama laci
	 * @param x
	 * 	node
	 * @param acc
	 * 	jarak
	 */
	private void depth(String key, String laci, TreeNode<Bismillah> x, int acc) {
		if (tempat.exists(new Kunci(key, 0, false))) {
			if (x.data.toString().equals(key.toString())) {
				System.out.println(x.data.toString() + " berada di level "
						+ acc + " " + laci);

			} else {
				for (TreeNode<Bismillah> child : x.getChildren()) {
					depth(key, laci, child, acc + 1);
				}

			}
		} else {
			System.out.println("tidak ditemukan!");

		}
	}

	

	// METHOD PALING SUSAH

	/** Method untuk mencari laci atau kunci tertentu + print (menggunakan helper)
	 * @param nama
	 * 	nama dari yang akan dicari
	 * @param writer
	 * 	Buffered Writer
	 * @throws IOException
	 */
	public void cari2(String nama, BufferedWriter writer) throws IOException {
		
		// Cara untuk mengeprintnya adalah menggunakan flag / penanda
		// Jadi, sebelum diprint, ditandai dulu jarak dari root ke laci atau kunci yang dicari
		
		flag2(tempat.getRoot(), nama, true);
		printFlag(tempat.getRoot(), nama, writer, "");
		flag2(tempat.getRoot(), nama, false);
	}

	/** Method menandakan yang akan diprint (rekurisf)
	 * @param node
	 * 	node yang akan memulai pencarian
	 * @param nama
	 * 	nama dari yang dicari
	 * @param flag
	 *  penanda, dapat true atau false
	 */
	private void flag2(TreeNode<Bismillah> node, String nama, boolean flag) {
		if (node.getData().getNama().equals(nama)) {
			updateFlag(node, flag);
		} else if (node.getChildren().size() > 0) {
			for (TreeNode<Bismillah> child : node.getChildren()) {
				flag2(child, nama, flag);
			}
		}
	}

	/** Menandai semua yang akan diprint
	 * @param node
	 * @param update
	 */
	private void updateFlag(TreeNode<Bismillah> node, boolean update) {
		node.getData().setKey(update);
		if (!node.getData().getNama().equalsIgnoreCase("laci1")) {
			updateFlag(node.getParent(), update);
		}
	}

	/** Method untuk mengeprint flag-flag yang sudah ditandai
	 * @param node
	 * @param nama
	 * @param writer
	 * @param space
	 * @throws IOException
	 */
	private void printFlag(TreeNode<Bismillah> node, String nama,
			BufferedWriter writer, String space) throws IOException {

		if (node.getData().isKey() || node.getData().getNama().equals(nama)) {
			writer.write(space + "> " + node.getData().getNama() + "\n");
			writer.flush();
			space = space + "  ";
		}
		if (node.getChildren().size() > 0) {
			
			
			
			for (int i = 0; i < node.getChildren().size(); i++) {
				printFlag(node.getChildren().get(i), nama, writer, space);
				
				// Ada kondisi, dimana tidak perlu di printFlag lagi (masih kurang mengerti sebenarnya karena diajarkan teman)
				if (i + 1 < node.getChildren().size()
						&& node.getChildren().get(i).getData().getNama()
								.equalsIgnoreCase(nama)
						&& node.getChildren()
								.get(i)
								.getData()
								.getNama()
								.equals(node.getChildren().get(i + 1).getData()
										.getNama())) {
					break;

				}
			}
		}

	}

	
	/** Saat cetak, berat harus diupdate, itulah fungsi dari method ini
	 * @param gendutan
	 * node yang akan diupdate
	 * @return
	 */
	public int tambahGendut(TreeNode<Bismillah> gendutan) {
		int temp = gendutan.getData().getBerat();
		if (gendutan.getData() == null) {
			return 0;
		} else if (gendutan.getChildren() == null) {
			return gendutan.getData().getBerat();
		} else {
			for (TreeNode<Bismillah> child : gendutan.getChildren()) {
				temp += tambahGendut(child);
			}
			return temp;
		}

	}

}

/*
 * Kelas N-ary Tree
 * Source Code: Internet
 * 
 */

class Tree<E extends Comparable<E>> {

	private TreeNode<E> root;

	public Tree(TreeNode<E> root) {
		this.root = root;
	}

	public Tree() {
		this.root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public TreeNode<E> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<E> root) {
		this.root = root;
	}

	public boolean exists(E key) {
		return find(root, key);
	}

	public int getNumberOfNodes() {
		return getNumberOfDescendants(root) + 1;
	}

	public int getNumberOfDescendants(TreeNode<E> node) {
		int n = node.getChildren().size();
		for (TreeNode<E> child : node.getChildren())
			n += getNumberOfDescendants((TreeNode<E>) child);
		return n;
	}

	public TreeNode<E> find(TreeNode<E> nodeToFind) {

		TreeNode<E> returnNode = null;

		if (root != null) {
			returnNode = findNode(this.root, nodeToFind.data);
		}

		return returnNode;
	}

	private boolean find(TreeNode<E> node, E keyNode) {
		boolean found = false;
		if (node.getData().compareTo(keyNode) == 0) {
			return true;
		} else {
			for (TreeNode<E> child : node.getChildren())
				if (find((TreeNode<E>) child, keyNode))
					found = true;
		}
		return found;
	}

	private TreeNode<E> findNode(TreeNode<E> node, E keyNode) {
		if (node == null)
			return null;
		if (node.getData().compareTo(keyNode) == 0)
			return node;
		else {
			TreeNode<E> cnode = null;
			for (TreeNode<E> child : node.getChildren())
				if ((cnode = findNode(child, keyNode)) != null)
					return cnode;
		}
		return null;
	}

	public ArrayList<TreeNode<E>> getPreOrderTraversal() {
		ArrayList<TreeNode<E>> preOrder = new ArrayList<TreeNode<E>>();
		buildPreOrder(root, preOrder);
		return preOrder;
	}

	public void buildPreOrder(TreeNode<E> node, ArrayList<TreeNode<E>> preOrder) {
		System.out.println(node.toString());
		preOrder.add(node);
		for (TreeNode<E> child : node.getChildren())
			buildPreOrder((TreeNode<E>) child, preOrder);
	}

	public void remove(E data) {
		TreeNode<E> temp = find(new TreeNode<E>(data));

		while (temp.getData() != null) {
			if (temp == null) {
			} else {
				TreeNode<E> ortu = temp.getParent();
				ortu.removeChildren();
			}
		}
	}

	public ArrayList<TreeNode<E>> getLongestPathFromRootToAnyLeaf() {
		ArrayList<TreeNode<E>> longestPath = null;
		int max = 0;
		for (ArrayList<TreeNode<E>> path : getPathsFromRootToAnyLeaf()) {
			if (path.size() > max) {
				max = path.size();
				longestPath = path;
			}
		}
		return longestPath;
	}

	public int getMaxDepth() {
		return getLongestPathFromRootToAnyLeaf().size();
	}

	public ArrayList<ArrayList<TreeNode<E>>> getPathsFromRootToAnyLeaf() {
		ArrayList<ArrayList<TreeNode<E>>> paths = new ArrayList<ArrayList<TreeNode<E>>>();
		ArrayList<TreeNode<E>> currentPath = new ArrayList<TreeNode<E>>();
		getPath(root, currentPath, paths);

		return paths;
	}

	private void getPath(TreeNode<E> node, ArrayList<TreeNode<E>> currentPath,
			ArrayList<ArrayList<TreeNode<E>>> paths) {
		if (currentPath == null)
			return;

		currentPath.add(node);

		if (node.getChildren().size() == 0) {
			paths.add(clone(currentPath));
		}
		for (TreeNode<E> child : node.getChildren())
			getPath(child, currentPath, paths);

		int index = currentPath.indexOf(node);
		for (int i = index; i < currentPath.size(); i++)
			currentPath.remove(index);
	}

	private ArrayList<TreeNode<E>> clone(ArrayList<TreeNode<E>> list) {
		ArrayList<TreeNode<E>> newList = new ArrayList<TreeNode<E>>();
		for (TreeNode<E> node : list)
			newList.add(new TreeNode<E>(node));

		return newList;
	}
}



// Kelas Laci, untuk menyimpan data-data tentang Laci

class Laci implements Bismillah {

	
	String nama;
	int berat;
	boolean isKey; // Untuk mengecak di bagian method Cari
	boolean isFound; // Menandakan apakah laci atau kunci. Jika false, maka Laci

	public Laci(String nama) {
		this.nama = nama;
		berat = 1;
		isKey = false;
		isFound = false;

	}

	public String getNama() {
		return nama;
	}

	public int getBerat() {
		return berat;
	}

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public boolean isFound() {
		return isFound;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	public boolean equals(Bismillah other) {
		return this.getNama().equalsIgnoreCase(other.getNama());
	}

	public int compareTo(Bismillah other) {
		return this.getNama().compareTo(other.getNama());
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public void setBerat(int berat) {
		this.berat = berat;
	}
}

// Kelas kunci yang menampung data-data tentang Kunci

class Kunci implements Bismillah {

	String nama;
	int berat;
	boolean isKey;
	boolean isFound;

	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public boolean isFound() {
		return isFound;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	public Kunci(String nama, int berat, boolean isKey) {
		this.nama = nama;
		this.berat = berat;
		isKey = false;
		isFound = true;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getBerat() {
		return berat;
	}

	public void setBerat(int berat) {
		this.berat = berat;
	}

	// public int compareTo(Bismillah other) {
	// if (!this.getNama().equalsIgnoreCase(other.getNama())) {
	// return this.getNama().compareTo(other.getNama());
	// } else {
	// return this.getBerat() - other.getBerat();
	// }
	// }

	public boolean equals(Bismillah other) {
		return this.getNama().equalsIgnoreCase(other.getNama());
	}

	public int compareTo(Bismillah other) {
		return this.getNama().compareTo(other.getNama()) == 0 ? this.getBerat()
				- other.getBerat() : this.getNama().compareTo(other.getNama());

	}
}

// Interface Bismillah untuk menyatukan Laci dan Kunci, karena sebenernya keduanya hampir sama tetapi berbeda di bagian isFound

interface Bismillah extends Comparable<Bismillah> {
	public String getNama();

	public int getBerat();

	public boolean isKey();

	public void setKey(boolean isKey);

	public boolean isFound();

	public void setFound(boolean isFound);

	public int compareTo(Bismillah other);
}

class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> {
	public boolean add(E add) {
		add(bsearch(add), add);
		return true;
	}

	public int bsearch(E data) {
		if (this.size() == 0 ) {
			return 0;
		}
		int pointer = 0;
		int high = size()-1;
		int first = 0;
		while (true) {
			pointer = (first + high) / 2;
			if (this.get(pointer).compareTo(data) == 0) {
				return pointer;
			} else if (this.get(pointer).compareTo(data) < 0) {
				first = pointer + 1;
				if (first > high) {
					return pointer + 1;
				}
			} else {
				high = pointer - 1;
				if (first > high) {
					return pointer;
				}
			}
		}
	} 
}