public class ExchangeList{
	ExchangeNode head=null;
	int numberMembers=0;
	public ExchangeList(){
		//chill
	}
	public ExchangeList(ExchangeList a){
		head=a.head;
	}
	public void Insert(Exchange a){		//add exchange with identifier a
		//Exchange tmp=new Exchange(a);
		//tmp.next=head;
		//head=tmp;
		ExchangeNode tmp=head;
		boolean flag=true;
		while(tmp!=null){
			if(tmp.data.equals(a)==true){
				flag=false;
				break;
			}
			tmp=tmp.next;
		}
		if(flag==true){
			ExchangeNode tp=new ExchangeNode(a);
			tmp=head;
			if(tmp==null){
				head=tp;
				numberMembers++;	
				return;
			}else{
				while(tmp.next!=null){
					tmp=tmp.next;
				}
			}
			tmp.next=tp;
			numberMembers++;			
		}
		return;
	}
	public void Delete(int a)throws NoExchangeInList{		//delete Echange with identifier a
		if(head==null){
			//no exchange hence throw exception since a not found
			throw new NoExchangeInList();
		}else if(head.data.identifier==a){
			head=head.next;
			numberMembers--;
			return;
		}
		boolean flag=false;
		ExchangeNode tmp1=head;
		ExchangeNode tmp2=head.next;
		while(tmp2!=null){
			if(tmp2.data.identifier==a){
				flag=true;
				numberMembers--;
				tmp1.next=tmp2.next;
			}
			tmp1=tmp1.next;
			tmp2=tmp2.next;
		}
		if(flag==false){
			//Exchange a not found and hence throw exception
			throw new NoExchangeInList();
		}
	}
	public boolean IsEmpty(){
		return (head==null);
	}
	public Exchange member(int i)throws NoExchangeInList{	//return ith exchange in the list
		if(head==null){
			//throw exception
			throw new NoExchangeInList();
		}
		ExchangeNode tmp=head;
		for(int x=0;x<i;x++){	//doing tmp=tmp.next for i times lands me up on the ith element indexing from 0
			tmp=tmp.next;
			if(tmp==null){
				//throw error since ith child is not present
				throw new NoExchangeInList();
			}
		}
	//	System.out.println("exchange node at i for subtree:"+tmp.data.identifier);
		return tmp.data;
	}
	public int numMembers(){
		return numberMembers;
	}
}

class ExchangeNode{
	Exchange data=null;
	ExchangeNode next=null;
	public ExchangeNode(){

	}
	public ExchangeNode(Exchange x){
		data=x;
	}
}

class NoExchangeInList extends Exception{
	public NoExchangeInList(){
		//chill;
	}
}


/*
public class ExchangeList extends Myset{
	// is empty doesnt needs to be overridden
	//actually speaking even Ismember insert and delete didnt need overriding
	public boolean IsMember(Exchange o){
		super.IsMember(o);
	}
	public void Insert(Exchange o){
		super.Insert(o);
	}
	public void Delete(Exchange o){
		super.Delete(o);
	}
	public ExchangeList Union(ExchangeList a){
		Myset result=super.Union(ExchangeList a);
		ExchangeList b=null;
		if(result instanceof ExchangeList){
			b=(ExchangeList) result;
		}
		return b;
	}
	public ExchangeList Intersection(ExchangeList a){
		Myset result=super.Intersection(ExchangeList a);
		if(result instanceof ExchangeList){
			ExchangeList b=(ExchangeList) result;
		}
		return b;
	}
	//inheritance 216
}
*/