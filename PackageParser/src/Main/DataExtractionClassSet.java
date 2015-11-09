package Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;


public class DataExtractionClassSet {
	
	private ArrayList<String> CharacteristicList;
	private ArrayList<String> EntityList;
	private boolean[][] table;
	
	public void createTable() throws SecurityException, ClassNotFoundException{
		// Characteristic list and entity list are assigned
		if (CharacteristicList==null || EntityList==null) return;
		
		table = new boolean[EntityList.size()][CharacteristicList.size()];
		
		for (String e:EntityList)
			for (String c:CharacteristicList){
				if (methodNames(e).contains(c))
					table[EntityList.indexOf(e)][CharacteristicList.indexOf(c)] = true;
			}
	}
	
	public void afficheTable(){
		if (table == null) return;
		System.out.print("FormalContext Collections"+"\n"+"| |");
		for (int k=0; k< table[0].length; k++){
			System.out.print(CharacteristicList.get(k)+"|");
		}
		System.out.println();
		for (int i=0; i< table.length; i++){
			System.out.print("|"+EntityList.get(i)+"|");
			for (int j=0; j< table[0].length; j++){
				if (table[i][j])
					System.out.print("x");
				System.out.print("|");
			}
			System.out.println();
		}
	}
	
	public void ecrireTable(String nomFichier) throws IOException{
		if (table == null) return;
		BufferedReader fc = new BufferedReader
		        (new InputStreamReader (System.in));
		BufferedWriter ff = new BufferedWriter
		              (new FileWriter (nomFichier));

		ff.write("FormalContext Collections"+"\n"+"| |");
		for (int k=0; k< table[0].length; k++){
			ff.write(CharacteristicList.get(k)+"|");
		}
		ff.newLine();;
		for (int i=0; i< table.length; i++){
			ff.write("|"+EntityList.get(i)+"|");
			for (int j=0; j< table[0].length; j++){
				if (table[i][j])
					ff.write("x");
				ff.write("|");
			}
			ff.newLine();;
		}
		ff.close(); 
	}
	
	public ArrayList<String> getCharacteristicList() {
		return CharacteristicList;
	}

	public void setCharacteristicList(ArrayList<String> characteristicList) {
		CharacteristicList = characteristicList;
	}

	public ArrayList<String> getEntityList() {
		return EntityList;
	}

	public void setEntityList(ArrayList<String> entityList) {
		EntityList = entityList;
	}

	public boolean[][] getTable() {
		return table;
	}

	public void setTable(boolean[][] table) {
		this.table = table;
	}

	public static String finalStaticFieldNamesTypes(String className) throws SecurityException, ClassNotFoundException
	{
		String s="";
		System.out.println(Class.forName(className).getFields().length);
		for (Field a : Class.forName(className).getFields())
			if (Modifier.isFinal(a.getModifiers()) && Modifier.isStatic(a.getModifiers()))
				s+=a.getName()+" "+a.getType().getName()+"\n";
		return s;
	}
	
	public static ArrayList<String> methodNames(String className)throws SecurityException, ClassNotFoundException
	{
		ArrayList<String> liste = new ArrayList<>();;
		for (Method m : Class.forName(className).getMethods())
			liste.add(m.getName());
		return liste;
	}
	
	public static ArrayList<String> methodNameSet(ArrayList<String> classNameList)throws SecurityException, ClassNotFoundException
	{
		ArrayList<String> liste = new ArrayList<>();
		for (String c: classNameList)
			for (Method m : Class.forName(c).getMethods())
				if (! liste.contains(m.getName())) liste.add(m.getName());
		return liste;
	}	
	
	public static String methodNamesParamTypes(String className)throws SecurityException, ClassNotFoundException
	{
		String s="";
		System.out.println(Class.forName(className).getMethods().length);
		for (Method m : Class.forName(className).getMethods())
			{
				s+=m.getName()+"(";//+""+a.getType().getName()+"\n";
				for (Class paramType : m.getParameterTypes())
					s+=paramType.getName()+",";
				s+=")\n";
			}
		return s;
	}	
	
	public static String interfaceListNames(String[] classNameList)throws SecurityException, ClassNotFoundException
	{
		String s="";
		for (String c : classNameList)
			if (Class.forName(c).isInterface())
				s+=c+"\n";
		return s;
	}
	
	public static String abstractClassListNames(String[] classNameList)throws SecurityException, ClassNotFoundException
	{
		String s="";
		for (String c : classNameList)
			if (! Class.forName(c).isInterface() && Modifier.isAbstract(Class.forName(c).getModifiers()))
				s+=c+"\n";
		return s;
	}
	
	private static String concreteClassListNames(String[] classNameList) throws ClassNotFoundException {
		String s=""; int nb =0;
		for (String c : classNameList)
			if (! Modifier.isAbstract(Class.forName(c).getModifiers()))
				{s+=c+"\n";nb++;}
		System.out.println(nb+" classes concrètes");
		return s;
	}
	
	private static ArrayList<String> concreteClassList(String[] classNameList) throws ClassNotFoundException {
		ArrayList<String> liste = new ArrayList<>();
		for (String c : classNameList)
			if (! Modifier.isAbstract(Class.forName(c).getModifiers()))
				{liste.add(c);}
		return liste;
	}

	public static void ajoutPrefixe(String prefixe, String[] noms){
		for (int i=0; i < noms.length; i++)
			noms[i]= prefixe+noms[i];
			
	}
	
	public static void main(String[] args) throws SecurityException, ClassNotFoundException, IOException {
		
		String[] QuelquesClasses = {"java.lang.Math"};
		
		System.out.println(finalStaticFieldNamesTypes(QuelquesClasses[0]));
		
		// j'ai retire Collections et Arrays qui ne sont pas des collections
		
		String[] listeDesClassesInterfaces = {"AbstractCollection", "AbstractList", "AbstractMap", "AbstractQueue", 
		"AbstractSequentialList", "AbstractSet","ArrayDeque","ArrayList","ArrayPrefixHelpers","ArraysParallelSortHelpers",
		"BitSet","Collection","ComparableTimSort", 
		"Deque","Dictionary","DualPivotQuicksort","EnumMap","EnumSet",
		"HashMap","HashSet","Hashtable","IdentityHashMap","JumboEnumSet","LinkedHashMap",
		"LinkedHashSet","LinkedList","List", "Map","NavigableMap","NavigableSet",
		"Queue","PriorityQueue","RegularEnumSet","Set","SortedMap","SortedSet",
		"Stack","TimSort","TreeMap","TreeSet","Vector","WeakHashMap"};
		
		ajoutPrefixe("java.util.",listeDesClassesInterfaces);
		
		ArrayList<String> listeComplete = new ArrayList<>();
		listeComplete.addAll(Arrays.asList(listeDesClassesInterfaces));
		
		//String[] listeDesClassesInterfaces = {"java.lang.String","java.util.Vector"};

		System.out.println(methodNamesParamTypes(listeDesClassesInterfaces[0]));
		
		System.out.println("------ interfaces \n"+interfaceListNames(listeDesClassesInterfaces));
		System.out.println("------ classes abstraites \n"+abstractClassListNames(listeDesClassesInterfaces));
		//System.out.println("------ classes concretes \n"+concreteClassList(listeDesClassesInterfaces));

		// on utilise les classes concretes uniquement
		DataExtractionClassSet d = new DataExtractionClassSet();
		d.setEntityList(concreteClassList(listeDesClassesInterfaces));
		System.out.println("------ classes concretes \n"+d.getEntityList());
		d.setCharacteristicList(methodNameSet(d.getEntityList()));
		System.out.println("------ nombre de noms de méthodes \n"+d.getCharacteristicList());
		d.createTable();
		System.out.println("------ affichage contexte formel");
		d.afficheTable();
		d.ecrireTable("table1.rcft");
		
		// construire la totalité permet de voir où se situent les interfaces et les classes abstraites,
		// mais cela complique les structures
		DataExtractionClassSet d2 = new DataExtractionClassSet();
		d2.setEntityList(listeComplete);
		System.out.println("------ classes concretes \n"+d2.getEntityList());
		d2.setCharacteristicList(methodNameSet(d.getEntityList()));
		System.out.println("------ nombre de noms de méthodes \n"+d2.getCharacteristicList());
		d2.createTable();
		System.out.println("------ affichage contexte formel");
		d2.afficheTable();
		d2.ecrireTable("table2.rcft");
	}
}
