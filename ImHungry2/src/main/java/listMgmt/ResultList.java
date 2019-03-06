package listMgmt;

import java.util.ArrayList;

import scraping.Result;

public class ResultList {
	String name = "";
	ArrayList<Result> cards;
	
	public ResultList(String name, ArrayList<Result> cards) {
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

	public ArrayList<Result> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Result> cards) {
		this.cards = cards;
	}
	
	public void addCard(Result result) {
		cards.add(result);
	}
	
	public void removeCard(Result result) {
		String name = result.getName();
		for(int i=0; i<cards.size(); i++) {
			if(cards.get(i).getName().equals(name)) {
				cards.remove(i);
				return;
			}
		}
	}
	//moving a card should be getting the item adding it to another
	//and deleting it from the original list
	public Result getItem(String name) {;
		Result toReturn = new Result();
		for(int i=0; i<cards.size(); i++) {
			if(cards.get(i).getName().equals(name)) {
				toReturn = cards.get(i);
			}
		}
		return toReturn;
	}
}
