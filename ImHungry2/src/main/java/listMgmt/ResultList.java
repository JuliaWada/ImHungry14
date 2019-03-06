package listMgmt;

import java.util.ArrayList;

import scraping.Result;

public class ResultList {
	String name = "";
	ArrayList<Object> cards;
	
	public ResultList(String name, ArrayList<Object> cards) {
		super();
		this.name = name;
		this.cards = cards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Object> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Object> cards) {
		this.cards = cards;
	}
	
	public void addCard(Object result) {
		System.out.println("Add " + name + "- new List size: " + cards.size());
		cards.add(result);
		System.out.println("Add " + name + "- new List size: " + cards.size());
	}
	
	public void removeCard(String name) {
		for(int i=0; i<cards.size(); i++) {
			if(((Result) cards.get(i)).getName().equals(name)) {
				System.out.println("Removed: " + ((Result)cards.get(i)).getName());
				cards.remove(i);
				System.out.println("Remove " + name + " - new list size: " + cards.size());
				return;
			}
		}
	}
	//moving a card should be getting the item adding it to another
	//and deleting it from the original list
	public Result getItem(String name) {;
		Result toReturn = new Result();
		for(int i=0; i<cards.size(); i++) {
			if(((Result) cards.get(i)).getName().equals(name)) {
				toReturn = (Result) cards.get(i);
			}
		}
		return toReturn;
	}
}
