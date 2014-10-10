import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Digraph{

	protected int size = 0;
	// protected int capacity = 10;
	protected CompNode[] nodeGraph;
	protected CompNode[] computerList;

	public Digraph(int n, int m){

		nodeGraph = new CompNode[2*m];
		computerList = new CompNode[n+1];
		// capacity = 2 * m;

	}

	public void add (CompNode node){
		// if (size == capacity){
		// 	capacity *= 2;
		// 	CompNode[] temp = new CompNode[capacity];
		// 	for (int i = 0; i < size; i++){
		// 		temp[i] = nodeGraph[i];
		// 	}
		// 	nodeGraph = temp;
		// }
		System.out.println("size is " + size);



		nodeGraph[size] = node;
		node.setLocation(nodeGraph[size]);
		// System.out.println("This is the first node at nodeGraph[0] " + nodeGraph[0].getTime() + " I have location " + nodeGraph[0]);
		// nodeGraph[0].printChain();

		size++;
	}


	public void processTriple(int comp1, int comp2, int time){

		CompNode first = new CompNode(comp1, time);
		CompNode second = new CompNode(comp2, time);
		CompNode firstClone = null;
		CompNode secondClone = null;
		boolean skipFlag1 = false;
		boolean skipFlag2 = false;



		if ( (computerList[comp1] == null) || (first.getTime() != computerList[comp1].getTime()) ){
			add(first);
		}
		else{
			first = computerList[comp1].getLocation();
			skipFlag1 = true;
		}

		if ( (computerList[comp2] == null) || (second.getTime() != computerList[comp2].getTime()) ){
			add(second);
		}
		else{
			second = computerList[comp2].getLocation();
			skipFlag2 = true;
		}

		// System.out.println("first computer is "+ comp1 + " time " + time);
		// first.printChain();
		// System.out.println("second computer is "+ comp2 + " time " + time);
		// second.printChain();

		firstClone = first.clone();
		secondClone = second.clone();

		first.add2(secondClone);

		// System.out.println("I am now printing for the first node");
		// first.printChain();

		// if (first == nodeGraph[0]){
		// System.out.println("I am the current 1, 4 chain ");
		// nodeGraph[0].printChain();
		// }


		// System.out.println("I am the location comp 1, 4 points at next " + nodeGraph[0].getNext());
		// System.out.println("But the location comp 1, 4 has is " + nodeGraph[0]);
		// System.out.println("But I am the location first is pointing at right now " + first.getLocation() + " which is the same as " + first);
		// System.out.println("I am the current 1, 4 chain ");
		// nodeGraph[0].printChain();

		// if (first == nodeGraph[0]) System.out.println("SAME");

		second.add2(firstClone);

		if (computerList[comp1] == null){
			computerList[comp1] = firstClone;
			// System.out.println("first computer is "+ comp1 + " time " + time);
			// computerList[comp1].printChain();
		}
		else{
			CompNode tempOld = computerList[comp1].getLocation();
			CompNode tempNew = firstClone;
			computerList[comp1] = tempNew;

			// System.out.println("computer list is "+ comp1 + " time " + time);
			// computerList[comp1].printChain();

			// System.out.println("Temp Old has location " + tempOld.getLocation());
			// tempOld.printChain();

			tempOld.add2(firstClone);
			// System.out.println("AFTER POST");
			// tempOld.printChain();			

			// System.out.println("old instance of computer "+ comp1 + " time " + tempOld.getTime());
			// tempOld.printChain();



			// System.out.println("original computer value for computer 1 is");
			// nodeGraph[0].printChain();
			// System.out.println("ENDINGINGINGINGING original computer value for computer 1 is");
		}

		if (computerList[comp2] == null){
			computerList[comp2] = secondClone;
		}
		else{
			CompNode tempOld = computerList[comp2].getLocation();
			CompNode tempNew = secondClone;
			computerList[comp2] = tempNew;
			tempOld.add2(secondClone);
		}
	}

	public void printMine(int i){
		System.out.println("                                         Printing info for computer " + i);
		nodeGraph[i].printChain();
	}

	public void printAll(){
		System.out.println("Printing everything");
		for (int i = 0; i < size; i++){

			// nodeGraph[i].printInfo();
			printMine(i);

		}

	}


	public boolean checkVirus(int compStart, int compEnd, int timeStart, int timeEnd){
		CompNode start = findStart(compStart, timeStart);

		// System.out.println("start's next is ");
		// start.printInfo();

		return BFS(start, compEnd, timeEnd);
	}

	public CompNode findStart(int comp, int time){
		CompNode start;

		for (int i = 0; i < size; i++){
			CompNode temp = nodeGraph[i];
			if (temp.getComputer() == comp){
				if (temp.getTime() >= time){
					start = nodeGraph[i];
					// System.out.println("THEITHISHETS");
					// start.printChain();
					// System.out.println("start node found is " + start.getComputer() + " " + start.getTime());
					return start;
				}
			}
		}

		return null;
	}


	public boolean BFS(CompNode start, int compEnd, int timeEnd){
		// System.out.println("end comp is " + compEnd + " time is " + timeEnd);
		start = start.getLocation();
		start.makeDiscovered();
		start.printChain();
		// start.printInfo();

		//How do I make this queue sizeless?
		Queue<CompNode> BFSQueue = new LinkedList();

		BFSQueue.add(start);
		while (!BFSQueue.isEmpty()){
			CompNode temp = BFSQueue.remove();

			// System.out.println("outside queues is ");
			// temp.printInfo();
			// temp.printChain();
			// System.out.println("Bigger While");
			// temp.printInfo();

			while (temp.getNext() != null){
				temp.printChain();
				temp = temp.getNext();
				// System.out.println("inside queues is ");
				// temp.printInfo();
				System.out.println("Inner While");
				
				if (!temp.getLocation().checkDiscovered()){
					// temp.printInfo();
					if (temp.getComputer() == compEnd){
						if (temp.getTime() <= timeEnd){
							System.out.println("computer is " + temp.getComputer() + " time is " + temp.getTime());
							return true;
						}
					}
					temp.getLocation().makeDiscovered();
					BFSQueue.add(temp.getLocation());

				}
				
			}
			// temp.printInfo();
		}
		return false;
	}
};

