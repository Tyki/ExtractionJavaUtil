package Main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
/*
 * Extraction de tout les noms de classes via la doc JDK (http://hg.openjdk.java.net/jdk8u/jdk8u/jdk/file/5910b94ea083/src/share/classes/java/util/)
 * puis en insérant JQuery dans la page afin de pouvoir faire un ('td.filename').text() en JavaScript 
 * afin de recuperer tout les noms qui sont affichés sur la page web
 * 
 * Verification récursive sur chacune des classes du pkg util, s'ils implémentent MAP/Collection, si oui, ce sont des classes Collections utiles à notre projet.
 * 
 * Distinction pour les classes "utiles" s'il s'agit de classes abstraites, concretes ou interfaces.
 * 
 * Creation du graphe d'affichage.
 * 
 * Creation des 3 fichiers de sorties
 * 
 */
public class Main {
 
        /**
         * @param args
         * @throws ClassNotFoundException 
         */
        public static void main(String[] args) throws ClassNotFoundException {
                String[] classes = {"java.util.AbstractCollection","java.util.AbstractList",
                                "java.util.AbstractMap",
                                "java.util.AbstractQueue",
                                "java.util.AbstractSequentialList",
                                "java.util.AbstractSet",
                                "java.util.ArrayDeque",
                                "java.util.ArrayList",
                                "java.util.ArrayPrefixHelpers",
                                "java.util.Arrays",
                                "java.util.ArraysParallelSortHelpers",
                                "java.util.Base64",
                                "java.util.BitSet",
                                "java.util.Calendar",
                                "java.util.Collection",
                                "java.util.Collections",
                                "java.util.ComparableTimSort",
                                "java.util.Comparator",
                                "java.util.Comparators",
                                "java.util.ConcurrentModificationException",
                                "java.util.Currency",
                                "java.util.Date",
                                "java.util.Deque",
                                "java.util.Dictionary",
                                "java.util.DoubleSummaryStatistics",
                                "java.util.DualPivotQuicksort",
                                "java.util.DuplicateFormatFlagsException",
                                "java.util.EmptyStackException",
                                "java.util.EnumMap",
                                "java.util.EnumSet",
                                "java.util.Enumeration",
                                "java.util.EventListener",
                                "java.util.EventListenerProxy",
                                "java.util.EventObject",
                                "java.util.FormatFlagsConversionMismatchException",
                                "java.util.Formattable",
                                "java.util.FormattableFlags",
                                "java.util.Formatter",
                                "java.util.FormatterClosedException",
                                "java.util.GregorianCalendar",
                                "java.util.HashMap",
                                "java.util.HashSet",
                                "java.util.Hashtable",
                                "java.util.IdentityHashMap",
                                "java.util.IllegalFormatCodePointException",
                                "java.util.IllegalFormatConversionException",
                                "java.util.IllegalFormatException",
                                "java.util.IllegalFormatFlagsException",
                                "java.util.IllegalFormatPrecisionException",
                                "java.util.IllegalFormatWidthException",
                                "java.util.IllformedLocaleException",
                                "java.util.InputMismatchException",
                                "java.util.IntSummaryStatistics",
                                "java.util.InvalidPropertiesFormatException",
                                "java.util.Iterator",
                                "java.util.JapaneseImperialCalendar",
                                "java.util.JumboEnumSet",
                                "java.util.LinkedHashMap",
                                "java.util.LinkedHashSet",
                                "java.util.LinkedList",
                                "java.util.List",
                                "java.util.ListIterator",
                                "java.util.ListResourceBundle",
                                "java.util.Locale",
                                "java.util.LocaleISOData",
                                "java.util.LongSummaryStatistics",
                                "java.util.Map",
                                "java.util.MissingFormatArgumentException",
                                "java.util.MissingFormatWidthException",
                                "java.util.MissingResourceException",
                                "java.util.NavigableMap",
                                "java.util.NavigableSet",
                                "java.util.NoSuchElementException",
                                "java.util.Objects",
                                "java.util.Observable",
                                "java.util.Observer",
                                "java.util.Optional",
                                "java.util.OptionalDouble",
                                "java.util.OptionalInt",
                                "java.util.OptionalLong",
                                "java.util.PrimitiveIterator",
                                "java.util.PriorityQueue",
                                "java.util.Properties",
                                "java.util.PropertyPermission",
                                "java.util.PropertyResourceBundle",
                                "java.util.Queue",
                                "java.util.Random",
                                "java.util.RandomAccess",
                                "java.util.RegularEnumSet",
                                "java.util.ResourceBundle",
                                "java.util.Scanner",
                                "java.util.ServiceConfigurationError",
                                "java.util.ServiceLoader",
                                "java.util.Set",
                                "java.util.SimpleTimeZone",
                                "java.util.SortedMap",
                                "java.util.SortedSet",
                                "java.util.Spliterator",
                                "java.util.Spliterators",
                                "java.util.SplittableRandom",
                                "java.util.Stack",
                                "java.util.StringJoiner",
                                "java.util.StringTokenizer",
                                "java.util.TimSort",
                                "java.util.TimeZone",
                                "java.util.Timer",
                                "java.util.TimerTask",
                                "java.util.TooManyListenersException",
                                "java.util.TreeMap",
                                "java.util.TreeSet",
                                "java.util.Tripwire",
                                "java.util.UUID",
                                "java.util.UnknownFormatConversionException",
                                "java.util.UnknownFormatFlagsException",
                                "java.util.Vector",
                                "java.util.WeakHashMap"};   
                
                ArrayList<String> bonnesClasses = new ArrayList<String>();
                bonnesClasses.add("java.util.Collection");
                bonnesClasses.add("java.util.Map");
                bonnesClasses.add("java.util.Dictionary");
                
                Map<String, Relations> map = new HashMap<String, Relations>();    
                
                for (String s : classes) {
                        if (checkImplements(s)) {
                                bonnesClasses.add(s);    
                        }
                }       
                
                for (String s : bonnesClasses) {
                	 Relations r = new Relations(s);
                     map.put(s, r);
                }
                
                ArrayList<String> concret = checkConcrete(bonnesClasses);
                ArrayList<String> abstracts = checkAbstract(bonnesClasses);
                ArrayList<String> interfaces = checkInterface(bonnesClasses);
                
                createLinks(bonnesClasses, map);
                //Creation du graphe pour l'affichage
                Graph graph = new SingleGraph("Tutorial 1");
                
                //Ajout des 3 types de Nodes.
                System.out.println("Classes concretes ("+concret.size() + "): ");
                for (String s : concret) {
                	System.out.println(s);
                	Node n = graph.addNode(s);
                	n.setAttribute("ui.label", s);
                	//Vert = Concret
                	n.addAttribute("ui.style",  "fill-color: rgb(102,255,153);");
                }
                System.out.println("\nClasses Abstraites ("+abstracts.size()+"): ");
                for (String s : abstracts) {
                	System.out.println(s);
                	Node n = graph.addNode(s);
                	n.setAttribute("ui.label", s);
                	//Bleu = Abstrait
                	n.addAttribute("ui.style",  "fill-color: rgb(51,0,153);");
                }
                System.out.println("\nInterfaces ("+interfaces.size()+"): ");
                for (String s : interfaces) {
                	System.out.println(s);
                	Node n = graph.addNode(s);
                	n.setAttribute("ui.label", s);
                	//Jaune = Interface
                	n.addAttribute("ui.style",  "fill-color: rgb(204,204,51);");
                }
                
                //Ajout des edges
                for (String s : bonnesClasses) {
                	
                	ArrayList<String> interfacesDeS = map.get(s).getInterfaces();
                	for (String ii : interfacesDeS) {
                		if (bonnesClasses.contains(ii)) {
                			graph.addEdge(s+ii, ii, s);
                		} else {
                			Node test = graph.getNode(ii);
                			//Si le noeud n'existe pas, on l'ajoute
                			if (test == null) {
                				Node n = graph.addNode(ii);
                    			n.setAttribute("ui.label", ii);
                            	//Jaune = Interface
                            	n.addAttribute("ui.style",  "fill-color: rgb(204,204,51);");
                			}
                        	graph.addEdge(s+ii, ii, s);
                		}
                	}
                	
                	ArrayList<String> heritageDeS = map.get(s).getHeritages();
                	for (String ii : heritageDeS) {
                		if (bonnesClasses.contains(ii)) {
                			graph.addEdge(s+ii, ii, s);
                		} else {
                			Node test = graph.getNode(ii);
                			//Si le noeud n'existe pas, on l'ajoute
                			if (test == null) {
	                			Node n = graph.addNode(ii);
	                			n.setAttribute("ui.label", ii);
	                        	//Bleu = Abstrait
	                			n.addAttribute("ui.style",  "fill-color: rgb(51,0,153);");
                			}
                        	
                        	graph.addEdge(s+ii, ii, s);
                		}
                		
                	}
                }
                
                graph.display();
                
                File f = new File("ClassesConcretes.txt");
                File f2 = new File("ClassesAbstraites.txt");
                File f3 = new File("Interfaces.txt");
                
                try {
					FileWriter fw = new FileWriter(f);
					for (String s : concret) {
						fw.write(String.valueOf(s));
						fw.write("\r\n");
					}
					
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                try {
					FileWriter fw = new FileWriter(f2);
					for (String s : abstracts) {
						fw.write(String.valueOf(s));
						fw.write("\r\n");
					}
					
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                try {
					FileWriter fw = new FileWriter(f3);
					for (String s : interfaces) {
						fw.write(String.valueOf(s));
						fw.write("\r\n");
					}
					
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
        }
        
        private static void createLinks(ArrayList<String> liste, Map<String, Relations> map) throws ClassNotFoundException {
        	for (String s : liste) {
        		//On ajoute la superClasse de cette calsse
        		System.out.print("### " + s + " : ");
        		try {
					if (Class.forName(s).getSuperclass() != null) {
						map.get(s).addHeritage(Class.forName(s).getSuperclass().getName());
						System.out.print(Class.forName(s).getSuperclass().getName());
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.out.println("");
        		
        		//On ajoute les interfaces
        		Class[] interfaces = Class.forName(s).getInterfaces();
        		for (Class c : interfaces) {
        			System.out.println(c.getName());
        			map.get(s).addInterface(c.getName());
        		}
        		
        		System.out.println("-----" );
        	}
        }	
 
        private static ArrayList<String> checkAbstract(ArrayList<String> liste) throws ClassNotFoundException {
        	ArrayList<String> newListe = new ArrayList<String>();
        	for (String s : liste) {
        		if (!Class.forName(s).isInterface() && Modifier.isAbstract(Class.forName(s).getModifiers())) {
        			newListe.add(s);
        		}
        	}
        	
        	return newListe;
        }
        
        private static ArrayList<String> checkInterface(ArrayList<String> liste) throws ClassNotFoundException {
        	ArrayList<String> newListe = new ArrayList<String>();
        	for (String s : liste) {
        		if (Class.forName(s).isInterface()) {
        			newListe.add(s);
        		}
        	}
        	return newListe;
        }
        
        private static ArrayList<String> checkConcrete(ArrayList<String> liste) throws ClassNotFoundException {
        	ArrayList<String> newListe = new ArrayList<String>();
        	for (String s : liste) {
        		if (! Modifier.isAbstract(Class.forName(s).getModifiers())) {
        			newListe.add(s);
        		}
        	}
        	return newListe;
        }
        
        private static boolean checkImplements(String s) {
                try {
                        Class<?> c = Class.forName(s);
                        Class[] interfaces = c.getInterfaces();
                        boolean retourImplements = checkInterfaces(interfaces);
                        boolean retour2 = false;
                        if (c.getSuperclass() != null) {
                                Class[] inte = {c.getSuperclass()};
                                retour2 = checkInterfaces(inte);
                        }
                       
                        if (retourImplements || retour2) {
                                return true;
                        }
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                }
               
                return false;
        }
 
        private static boolean checkInterfaces(Class[] interfaces) {
                for (Class c : interfaces) {
                        if (c.getName().equals("java.util.Map") || c.getName().equals("java.util.Collection")) {
                                return true;
                        } else {
                                try {
                                        Class[] tab;
                                        tab = c.forName(c.getName()).getInterfaces();
                                        return checkInterfaces(tab);
                                } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                }
                        }
                }
                return false;
        }
        
        
}