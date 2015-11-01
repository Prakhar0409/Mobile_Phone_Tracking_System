public class MobilePhone{
	static int id=0;
	int identifier=0;
	boolean status=true;	//true when on and flase when off
	Exchange tower=null;	//tower to which the mobile phone is registered
	public boolean equals(MobilePhone a){
		return (a.identifier==this.identifier);
	}
	public MobilePhone(int number){
		identifier=number;
	}
	public MobilePhone(int number,Exchange a){
		identifier=number;
		tower=a;
	}
	public int number(){
		return identifier;
	}
	public boolean status(){
		return status;	//returns true when on and false when off
	}
	public void switchOn(){
		status=true;
		return;
	}
	public void switchOff(){
		status=false;
		return;
	}
	public Exchange location()throws MobilePhoneSwitchedOff{
		if(status==false){
			//throw error since the mobile phone is swotched off;
			throw new MobilePhoneSwitchedOff();
		}
		return tower;
	}
}

class MobilePhoneSwitchedOff extends Exception{
	public MobilePhoneSwitchedOff(){
		//chill;
	}
}