public class RoutingMapTree{
	Exchange root=null;
	//constructor
	public RoutingMapTree(){
		root=new Exchange(0);
	}
	public RoutingMapTree(Exchange a){	//called by subtree method of exchange
		root=a;
	}

	public  boolean containsNode(Exchange a){	//checks if Exchange a is in the RoutingMapTree
		if(root==null){return false;}
		if(root.identifier==a.identifier){return true;}
		int numchild=root.numChildren();
		RoutingMapTree tmp=null;
		for(int i=0;i<numchild;i++){
			try{
				tmp=root.subtree(i);	
			}catch(NoExchangeInList e){
				e.printStackTrace();
			}
			if(tmp!=null && tmp.containsNode(a)==true){
				return true;
			}
		}
		return false;
	}
	public  boolean containsNode(int a){	//checks if Exchange with identifier a is in the RoutingMapTree
		if(root==null){return false;}
		if(root.identifier==a){
		//	System.out.println("contains node returns true");
			return true;}
		int numchild=root.numChildren();
		//System.out.println("numchildren: "+numchild);
		RoutingMapTree tmp=null;
		for(int i=0;i<numchild;i++){
			try{
				tmp=root.subtree(i);	
			}catch(NoExchangeInList e){
				e.printStackTrace();
			}
			if(tmp!=null && tmp.containsNode(a)==true){
				//System.out.println("reached a place where this should be true+numchildren "+numchild);
				return true;
			}
		}
		return false;
	}

	public void switchOn(MobilePhone a,Exchange b){	//switches on mobile a in exchange b
		if(a.status==false){
			a.switchOn();
			a.tower=b;	
			b.mobiles.Insert(a);
		}
	}
	public void switchOff(MobilePhone a){
		if(a.status==true){
			a.switchOff();
			Exchange b=a.tower;
			try{
				b.mobiles.Delete(a);	
			}catch(MysetObjectNotAvailable e){
				e.printStackTrace();
			}
			
		}
	}
	
	public MobilePhone getMobilePhone(int a){
		if(root==null){return null;}
		MobilePhoneSet x=root.mobiles;
		
		node tmp=x.head;
		//System.out.println("in getMobilePhones : "+tmp);	
		while(tmp!=null){
			MobilePhone m=(MobilePhone)tmp.data;
			if(m.identifier==a){
				return m;
			}
			tmp=tmp.next;
		}
		return null;
	}
	public void update(Exchange a){
		
	/*	System.out.println("children of Exchange :"+a.identifier);
		node tmp=a.mobiles.head;
		for(int i=0;i<a.mobiles.numMembers();i++){
			MobilePhone x=(MobilePhone)tmp.data;
			System.out.println(x.identifier+" ");
			tmp=tmp.next;
		}
	*/	
		while(a.pa!=null){
			//System.out.println("children of Exchange :"+a.identifier);
			//System.out.println("before :"+a.pa.mobiles.numMembers());
			//a.pa.mobiles=a.pa.mobiles.Union(a.mobiles);
			a.pa.mobiles.Union(a.mobiles);
			//System.out.println("after :"+a.pa.mobiles.numMembers());
			a=a.pa;
		}
	}
	public void update2(Exchange a){
	//	System.out.println("in update2");
		if(a.pa==null){return;}
		a=a.pa;
		while(a!=null){
			ExchangeNode tmp=a.children.head;
			MobilePhoneSet x=new MobilePhoneSet();
			while(tmp!=null){
				
				x.Union(tmp.data.mobiles);
				
				tmp=tmp.next;
			}
			//System.out.println("members in x :"+x.numMembers());
			try{
			//	System.out.println("before :"+a.mobiles.numMembers());
				a.mobiles.Intersection(x);	
			//	System.out.println("after :"+a.mobiles.numMembers());
			}catch(MysetObjectNotAvailable e){
				e.printStackTrace();
			}
			
			a=a.pa;
		}
	}
	public Exchange getExchange(int i){
		

		if(containsNode(i)==false){
			//throw exception
			
			//System.out.println("Getexchange null");
			return null;
		}else{
			if(root.identifier==i){
			//		System.out.println("Getexchange root");
				return root;
			}
			int numchild=root.numChildren();
			for(int j=0;j<numchild;j++){
				RoutingMapTree tmp;
				try{
					tmp=root.subtree(j);
					Exchange output=tmp.getExchange(i);
					if(output!=null){
						return output;
					}	
				}catch(NoExchangeInList e){
					e.printStackTrace();
				}
			}
			return null;	//you never reach here		
		}
	}
	public Exchange getExchange1(int i)throws NoSuchExchange{
		if(containsNode(i)==false){
			//throw exception
			throw new NoSuchExchange();
			//System.out.println("Getexchange null");
			//return null;
		}else{
			return getExchange(i);
		}
	}

	public Exchange findPhone(MobilePhone m){
		return m.tower;
	}
	public Exchange lowestRouter(Exchange a,Exchange b){
		//System.out.println("a and b are respectively "+a.identifier+" "+b.identifier);
		if(a.children.head!=null || b.children.head!=null){
			//throw exception since one of the two exchanges is not base;
			//throw exception .................................*********/
		}
		if(a.equals(b)){
			return a;
		}else if(a.pa==null){
			return a;
		}
		a=a.pa;
		while(a!=null){
			for(int i=0;i<a.children.numMembers();i++){
				try{
			//		System.out.println("am i close to error and a:i is "+a.identifier+" : "+i);
					RoutingMapTree try1=a.subtree(i);
					boolean try2=try1.containsNode(b);
					if(try2==true){
			//			System.out.println("returned and done");
						return a;
					}		
				}catch(NoExchangeInList e){
					e.printStackTrace();
				}
				
			}
			a=a.pa;
		}
		return null;
	}
	public ExchangeList routeCall(MobilePhone a,MobilePhone b){
		Exchange tmp=lowestRouter(a.tower,b.tower);
		//System.out.println("I am here and i found the mistake");
		Exchange trav=a.tower;
		ExchangeList li=new ExchangeList();
		while(trav!=tmp){
			li.Insert(trav);
			trav=trav.pa;
		}
		li.Insert(tmp);

		//System.out.println("hi tmp: "+tmp.identifier);
		ExchangeNode tmp1=tmp.children.head;
		while(tmp1!=null){
			
			while(tmp1.data.mobiles.IsMember(b)!=true){
				tmp1=tmp1.next;
			}
			tmp=tmp1.data;
			li.Insert(tmp);
			tmp1=tmp.children.head;
		}

		return li;
	}
	public void movePhone(MobilePhone a,Exchange b){
		if(containsNode(b)==false && root!=null && root.mobiles.IsMember(a)==false){
			//throw exception .................................*********/
		}
		int x=a.identifier;
		int y=b.identifier;
		//performAction("switchOffMobile "+x);
		MobilePhone tmp=a;
		try{
			if(tmp==null){
				throwNoSuchMobilePhoneExists();
			}
			tmp.tower.mobiles.Delete(tmp);	
			update2(tmp.tower);
			tmp.status=false;
		}catch(MysetObjectNotAvailable e){
			e.printStackTrace();
		}catch(NoSuchMobilePhoneExists f){
			f.printStackTrace();
		}

		//performAction("switchOnMobile "+x+" "+y);


			Exchange tp=b;	
/*throw*/	try{
			//	tp=getExchange1(b);
				if(tp.numChildren()!=0){
				//throw exception
					throwNotBaseExchange();
				}

				//MobilePhone tmp=getMobilePhone(x);
				
				//if(tmp!=null){
				//	throwMobileAlreadyExists();
					//throw exception
				//}
				tmp=new MobilePhone(x);
				tmp.tower=tp;
				tmp.status=true;
			//	System.out.println("identifier of the base :"+tmp.identifier);
				tp.mobiles.Insert(tmp);
				
				update(tp);
			}catch(NotBaseExchange l){
				l.printStackTrace();
			}
	}

	public int throwMobileAlreadyExists()throws MobileAlreadyExists{
		throw new MobileAlreadyExists();
	}
	public int throwNotBaseExchange()throws NotBaseExchange{
		throw new NotBaseExchange();
	}
	public int throwIsBaseExchange()throws IsBaseExchange{
		throw new IsBaseExchange();
	}
	public int throwExchangeAlreadyExists()throws ExchangeAlreadyExists{
		throw new ExchangeAlreadyExists();
	}
	public int throwNoSuchMobilePhoneExists()throws NoSuchMobilePhoneExists{
		throw new NoSuchMobilePhoneExists();
	}
	public void performAction(String actionMessage){
		String[] arr=actionMessage.split(" ");
		
		if(arr[0].equals("addExchange")){
			
			int a=Integer.parseInt(arr[1]);
			int b=Integer.parseInt(arr[2]);
			System.out.println("addExchange "+a+" "+b);
			Exchange tp=null;	
/*throw*/	try{
				tp=getExchange1(a);
				if(tp.mobiles.head!=null && tp.children.head==null){
					throwIsBaseExchange();
				}
				//System.out.println(tp.identifier);
				if(containsNode(b)==true){
					throwExchangeAlreadyExists();
				}
				Exchange tmp=new Exchange(b,tp);
				
				
				tp.children.Insert(tmp);
			}catch(NoSuchExchange m){
				m.printStackTrace();
			}catch(IsBaseExchange n){
				n.printStackTrace();
			}catch(ExchangeAlreadyExists l){
				l.printStackTrace();
			}
		
			
		}else if(arr[0].equals("switchOnMobile")){
			
			int a=Integer.parseInt(arr[1]);
			int b=Integer.parseInt(arr[2]);
			System.out.println("switchOnMobile "+a+" "+b);
/*throw*/   Exchange tp=null;	
/*throw*/	try{
				tp=getExchange1(b);
				if(tp.numChildren()!=0){
				//throw exception
					throwNotBaseExchange();
				}

				MobilePhone tmp=getMobilePhone(a);
				
				if(tmp!=null){
					throwMobileAlreadyExists();
					//throw exception
				}
				tmp=new MobilePhone(a);
				tmp.tower=tp;
				tmp.status=true;
			//	System.out.println("identifier of the base :"+tmp.identifier);
				tp.mobiles.Insert(tmp);
				
				update(tp);
			}catch(NoSuchExchange m){
				m.printStackTrace();
			}catch(MobileAlreadyExists n){
				n.printStackTrace();
			}catch(NotBaseExchange l){
				l.printStackTrace();
			}

				//System.out.println("identifier of the base :"+tp.identifier);
			
			
		}else if(arr[0].equals("switchOffMobile")){
			
			int a=Integer.parseInt(arr[1]);
			System.out.println("switchOffMobile "+a);
			MobilePhone tmp=getMobilePhone(a);
			try{
				if(tmp==null){
					throwNoSuchMobilePhoneExists();
				}
				tmp.tower.mobiles.Delete(tmp);	
				update2(tmp.tower);
				tmp.status=false;
			}catch(MysetObjectNotAvailable e){
				e.printStackTrace();
			}catch(NoSuchMobilePhoneExists f){
				f.printStackTrace();
			}
			
		}else if(arr[0].equals("queryNthChild")){
			
			int a=Integer.parseInt(arr[1]);
			int b=Integer.parseInt(arr[2]);			
			System.out.print("queryNthChild "+a+" "+b+": ");
/*throw*/	Exchange tmp=null;	
/*throw*/	try{
				tmp=getExchange1(a);
				Exchange x=null;
			
				x=tmp.child(b);	
				System.out.println(x.identifier);
				
			}catch(NoSuchExchange m){
				m.printStackTrace();
			}catch(NoExchangeInList e){
					e.printStackTrace();
			}
			
		}else if(arr[0].equals("queryMobilePhoneSet")){
			
			int a=Integer.parseInt(arr[1]);
			System.out.print("queryMobilePhoneSet "+a+": ");
/*throw*/	Exchange tmp=null;	
/*throw*/	try{
				tmp=getExchange1(a);
				node t=tmp.mobiles.head;
				while(t!=null){
					MobilePhone m=(MobilePhone)t.data;
					System.out.print(m.identifier+", ");
					t=t.next;
				}
				System.out.println();
			}catch(NoSuchExchange m){
				m.printStackTrace();
			}
			
		}else if(arr[0].equals("queryFindPhone")){
			int a=Integer.parseInt(arr[1]);
			System.out.print("queryFindPhone "+a+": ");
		
			MobilePhone x=getMobilePhone(a);
			try{
				if(x==null){
					//throw no such mobilephone exception	
					//System.out.println("no such phone exists");
					throwNoSuchMobilePhoneExists();
				}else{
					Exchange y=findPhone(x);
					if(y==null){
						//throw exception
						System.out.println("exception");
					}else{
						System.out.println(y.identifier);		
					}
				}	
			}catch(NoSuchMobilePhoneExists e){
				e.printStackTrace();
			}
			
			
			
		
			
		}else if(arr[0].equals("queryLowestRouter")){	
			int a=Integer.parseInt(arr[1]);
			int b=Integer.parseInt(arr[2]);
			System.out.print("queryLowestRouter "+a+" "+b+": ");
			try{
				Exchange x=getExchange1(a);
				Exchange y=getExchange1(b);
				Exchange z=lowestRouter(x,y);
				System.out.println(z.identifier);	
			}catch(NoSuchExchange e){
				e.printStackTrace();
			}
			
		}else if(arr[0].equals("queryFindCallPath")){
			int a=Integer.parseInt(arr[1]);
			int b=Integer.parseInt(arr[2]);
			System.out.print("queryFindCallPath "+a+" "+b+": ");

			MobilePhone x=getMobilePhone(a);
			MobilePhone y=getMobilePhone(b);
			try{
				if(x==null || y==null){
					//throw exception......................********************!
					throwNoSuchMobilePhoneExists();
				}else{
					ExchangeList li=routeCall(x,y);
					ExchangeNode tmp=li.head;
					while(tmp!=null){
						System.out.print(tmp.data.identifier+" ");
						tmp=tmp.next;
					}
					System.out.println();				
				}	
			}catch(NoSuchMobilePhoneExists e){
				e.printStackTrace();
			}
			

		}else if(arr[0].equals("movePhone")){
			int a=Integer.parseInt(arr[1]);
			int b=Integer.parseInt(arr[2]);
			System.out.println("movePhone "+a+" "+b);


			try{
				MobilePhone x=getMobilePhone(a);
				if(x==null){
					throwNoSuchMobilePhoneExists();
				}
				Exchange y=getExchange1(b);
				if(y.numChildren()!=0){
					throwNotBaseExchange();
				}
				movePhone(x,y);	
			}catch(NoSuchExchange e){
				e.printStackTrace();
			}catch(NoSuchMobilePhoneExists f){
				f.printStackTrace();
			}catch(NotBaseExchange g){
				g.printStackTrace();
			}
		}

	}	
}

class NoSuchExchange extends Exception{
	public NoSuchExchange(){
		//chill
	}
}
class NotBaseExchange extends Exception{
	public NotBaseExchange(){
		//chill
	}
}
class MobileAlreadyExists extends Exception{
	public MobileAlreadyExists(){
		//chill
	}
}
class IsBaseExchange extends Exception{
	public IsBaseExchange(){
		//chill
	}
}
class ExchangeAlreadyExists extends Exception{
	public ExchangeAlreadyExists(){
		//chill
	}
}
class NoSuchMobilePhoneExists extends Exception{
	public NoSuchMobilePhoneExists(){
		//chill
	}
}
