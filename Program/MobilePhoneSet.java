public class MobilePhoneSet extends Myset{
	// is empty doesnt needs to be overridden
	//actually speaking even Ismember insert and delete didnt need overriding


//the only problem you may face here is that the returned Myset 
//is not being type case into Mobilephone set in the methods Union and Intersections

	
	
/*	public MobilePhoneSet Union(MobilePhoneSet a){
		Myset result=super.Union(a);
		System.out.println("in union of mobile phone set "+result.numMembers());
		MobilePhoneSet b=null;
		if(result!=null){
			System.out.println("true");
			b=(MobilePhoneSet) result;
		}else{
			System.out.println("else false");		
			b=new MobilePhoneSet();
		}
		return b;
	}
*/
/*	public MobilePhoneSet Intersection(MobilePhoneSet a){
		Myset result=super.Intersection(a);
		MobilePhoneSet b=null;
		if(result instanceof MobilePhoneSet){
			b=(MobilePhoneSet) result;
		}
		return b;
	}
*/
	//inheritance 216
}
