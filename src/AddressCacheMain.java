import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class AddressCacheMain {
	private static AddressCacheImplementor address_cache;

	public static void main(String [] args){
		
		//create a new cache with cached_time set to 60 seconds
		address_cache = new AddressCacheImplementor(60);
		boolean running = true;
		
		populateCache();
		
		System.out.println("------------\n enter in in one of the following values\n---------------");
		System.out.println("1 - remove()\n2 - peek()\n3 - take()\n4 - size()\n5 - isEmpty()\n6 - close()\n7 - repopulate\n8 - offer()\n9 - Stop Program");

		while (running){
			
			Scanner in = new Scanner(System.in);
			int user_input = in.nextInt();
			switch(user_input){
			case 1:	
				System.out.println(address_cache.remove() + " was removed from cache");
				break;
			case 2:
				System.out.println(address_cache.peek() + " is the top element in the cache");
				break;
			case 3:
				try {
					System.out.println(address_cache.take()+ " was returned and removed from the list");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				System.out.println("cache size is " +address_cache.size());
				break;
			case 5:
				System.out.println(address_cache.isEmpty());
				break;
			case 6:
				address_cache.close();
				break;
			case 7:
				populateCache();
				break;
			case 8:
				Random rand = new Random();
				int val = rand.nextInt(100)+1;
				InetAddress address;
				try {
					address = InetAddress.getByName(val+".0.0.1");
					address_cache.offer(address);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				running = false;
				System.out.println("program shutting down");
				System.exit(1);
				break;
			default:
				System.out.println("dont recognize. please choose another value");
				break;
			}
		}
		
		
		
	}
	
	private static void populateCache(){
		for(int i=0;i<50;i++){
			try {
				InetAddress address = InetAddress.getByName(i+".0.0.1");
				address_cache.offer(address);
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n\n Cache was populated with 50 new addresses");
	}
}
