public class Exchange{
	static int id=0;
	int identifier;			//unique identifier for the exchange 
	Exchange next=null;		//pointer to next Exchange when this exchamge is put into exchangeList
	Exchange pa=null;		//pointer to parent Exchange
	ExchangeList children=new ExchangeList();
	MobilePhoneSet mobiles=new MobilePhoneSet();
	public boolean equals(Exchange o){
		return (o.identifier==this.identifier);
	}
	public Exchange(int number){
		identifier=number;
	}
	public Exchange(int number,Exchange pare){
		identifier=number;
		pa=pare;
	}
	public Exchange parent(){
		return pa;
	}
	public int numChildren(){		//returns number of children of exchange
		return children.numMembers(); 
	}
	public Exchange child(int i)throws NoExchangeInList{
		return children.member(i);	//children are indexed at 1
	}
	public boolean isRoot(){
		return (pa==null);
	}
	public RoutingMapTree subtree(int i)throws NoExchangeInList{
		Exchange roo=children.member(i);
		RoutingMapTree a=new RoutingMapTree(roo);
		return a;
	}
	public void addPhone(MobilePhone a){
		if(pa==null){
			mobiles.Insert(a);
		}else{
			mobiles.Insert(a);
			pa.addPhone(a);	
		}
		

	}
	public MobilePhoneSet residentSet(){
		return mobiles;
	}
}