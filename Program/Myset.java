public class Myset{
	node head=null;

/*		testing myset individually

	public static void main(String[] args){
		Myset x=new Myset();
		Myset y=new Myset();
		if(x.IsEmpty()){
			System.out.println("IsEmpty");
		}
		x.Insert(1);
		x.Insert(2);
		x.Insert(3);
		x.Insert(4);
		y.Insert(5);
		y.Insert(6);
		y.Insert(7);
		if(!x.IsEmpty()){
			System.out.println("IsNotEmpty");
		}
		if(x.IsMember(2)){
			System.out.println("found 2");
		}
		Myset z=x.Union(y);
		if(z.IsMember(2) && z.IsMember(7)){
			System.out.println("found 2 and 7");
		}
		Myset l=x.Intersection(y);
		if(l.IsEmpty()){
			System.out	.println("l is empty");
		}

	}
	*/
 	

	public boolean 	IsEmpty(){
		return (head==null);
	}
	public boolean IsMember(Object o){
		node tmp=head;
		
		while(tmp!=null){
			if(tmp.data.equals(o)){
				return true;
			}
			tmp=tmp.next;
		}
		return false;
	}
	
	public void Insert(Object o){
		if(IsMember(o)==false){
			node tmp=new node(o);
			node traverse=head;
			if(traverse==null){
				head=tmp;
				return;
			}
			while(traverse.next!=null){
				traverse=traverse.next;
			}
			traverse.next=tmp;
			return;
		}
		return;
	}

	public void Delete(Object o)throws MysetObjectNotAvailable{
		if(head==null){
			//object o not found and hence throw exception
			throw new MysetObjectNotAvailable();

		}else if(head.data.equals(o)){
			head=head.next;
			return;
		}
		boolean flag=false;
		node tmp1=head;
		node tmp2=head.next;
		while(tmp2!=null){
			if(tmp2.data.equals(o)){
				flag=true;
				//delete this node where tmp2 points to
				tmp1.next=tmp2.next;
				return;
			}
			tmp2=tmp2.next;
			tmp1=tmp1.next;
		}
		if(flag==false){
			//object o not found hence throw exception
			throw new MysetObjectNotAvailable();

		}
	}

	public void Union(Myset a){
	/*	Myset b=new Myset();
		node tmp=head;
		System.out.println("tmp=head "+tmp);
		while(tmp!=null){
			b.Insert(tmp.data);
			tmp=tmp.next;
		}
	*/
		node tmp=a.head;
		//System.out.println("tmp=a.head "+tmp);
		while(tmp!=null){
			if(this.IsMember(tmp.data)==false){
				this.Insert(tmp.data);	
			//	System.out.println("this.insert is run");		
			}
			tmp=tmp.next;
		}
		return;
	}
	public void Intersection(Myset a)throws MysetObjectNotAvailable{
	//	Myset b=new Myset();
		node tmp=this.head;
		while(tmp!=null){
			if(a.IsMember(tmp.data)==false){
				this.Delete(tmp.data);
			}
			tmp=tmp.next;
		}
		return;
	}
	public int numMembers(){
		node tmp=head;
		int num=0;
		while(tmp!=null){
			num++;
			tmp=tmp.next;
		}
		return num;
	}
}

class node{
	Object data;
	node next=null;
	public node(Object a){
		data=a;
	}
}


//Exceptions defined
class MysetObjectNotAvailable extends Exception{
	public MysetObjectNotAvailable(){
		//chill;
	}

}