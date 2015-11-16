package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class MonGraph implements ViewerListener{
	
	protected boolean loop = true;
	private Graph graph = null;
	private Graph graph2 = null;
	private Map<String, Relations> mapPerso = null;
	
	public MonGraph(String nom, ArrayList<String> concret, ArrayList<String> abstracts, ArrayList<String> interfaces, ArrayList<String> bonnesClasses,  Map<String, Relations> map) {
		 this.graph = new SingleGraph("Tutorial 1");
         this.mapPerso = map;
         //Ajout des 3 types de Nodes.
         System.out.println("Classes concretes ("+concret.size() + "): ");
         for (String s : concret) {
         	System.out.println(s);
         	Node n = graph.addNode(s);
         	
         	//Here
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
         			graph.addEdge(s+ii, s, ii, true);
         		} else {
         			Node test = graph.getNode(ii);
         			//Si le noeud n'existe pas, on l'ajoute
         			if (test == null) {
         				Node n = graph.addNode(ii);
             			n.setAttribute("ui.label", ii);
                     	//Jaune = Interface
                     	n.addAttribute("ui.style",  "fill-color: rgb(204,204,51);");
         			}
                 	graph.addEdge(s+ii, s, ii, true);
         		}
         	}
         	
         	ArrayList<String> heritageDeS = map.get(s).getHeritages();
         	for (String ii : heritageDeS) {
         		if (bonnesClasses.contains(ii)) {
         			graph.addEdge(s+ii, s, ii, true);
         		} else {
         			Node test = graph.getNode(ii);
         			//Si le noeud n'existe pas, on l'ajoute
         			if (test == null) {
             			Node n = graph.addNode(ii);
             			n.setAttribute("ui.label", ii);
                     	//Bleu = Abstrait
             			n.addAttribute("ui.style",  "fill-color: rgb(51,0,153);");
         			}
                 	
                 	graph.addEdge(s+ii, s, ii, true);
         		}
         		
         	}
         }

         Viewer viewer = graph.display();
         ViewerPipe viewerPipe = viewer.newViewerPipe();
         viewerPipe.addViewerListener(this);
         while (loop) {
        	 viewerPipe.pump();
         }
	}

	@Override
	public void buttonPushed(String id) {
		// TODO Auto-generated method stub
		System.out.println("Button pushed : " + id);
		//Id = nom du noeud cliqué
		this.graph2 = new SingleGraph("Affichage précis");
		if (this.mapPerso.containsKey(id)) {
			
			Node nn = this.graph2.addNode(id);
			nn.setAttribute("ui.label", id);
			
			for (String ii : this.mapPerso.get(id).getHeritages()) {
				
				Node n = graph2.addNode(ii);
     			n.setAttribute("ui.label", ii);
             	//Bleu = Abstrait
     			n.addAttribute("ui.style",  "fill-color: rgb(51,0,153);");
     			
     			graph2.addEdge(id+ii, id, ii, true);
			}
			for (String ii : this.mapPerso.get(id).getInterfaces()) {
				Node n = graph2.addNode(ii);
     			n.setAttribute("ui.label", ii);
             	//Jaune = Interface
             	n.addAttribute("ui.style",  "fill-color: rgb(204,204,51);");
             	graph2.addEdge(id+ii, id, ii, true);
			}
			Viewer v = this.graph2.display();
			v.setCloseFramePolicy(CloseFramePolicy.HIDE_ONLY);
		}
		
		
	}

	@Override
	public void buttonReleased(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void viewClosed(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
