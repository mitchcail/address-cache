import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author mitch
 *
 */
public class AddressCacheImplementor implements AddressCache {
	
	
	private LinkedList<Address> address_cache = new LinkedList<Address>();
	private int caching_time;
	private Timer cached_timer = new Timer();
	private boolean cache_closed = false;
	
	/* 
	 * Constructor
	 * 
	 * @param	caching_time	amount of time an InetAddress is cacheable
	 */
	public AddressCacheImplementor (int caching_time){
		this.caching_time = caching_time;
		
		// increase the cache time for each address every 1 second
		cached_timer.scheduleAtFixedRate(incrementCachedTime, 1000, 1000);

	}
	
	/* 
	 * @see AddressCache#offer(java.net.InetAddress)
	 */
	@Override
	public boolean offer(InetAddress address) {
		
		//if the cache has been closed. print error message and return out
		if(cache_closed){
			System.out.println("cache was set to closed and can no longer accept addresses");
			return false;
		}
		
		//if address is already in queue set index_of_old_address to that index, else set it to -1
		int index_of_old_address = -1;
		for (int i=0; i<address_cache.size(); i++) {
			if(address_cache.get(i).getAddress().equals(address)){
				index_of_old_address = i;
			}
	    }
		
		if(index_of_old_address != -1){
			//delete the other address object
			address_cache.remove(index_of_old_address);
		}

		//add to the front of the list
		address_cache.add(0, new Address(address));
		System.out.println(address + " was added to cache");

		return true;
	}

	/* 
	 * @see AddressCache#contains(java.net.InetAddress)
	 */
	@Override
	public boolean contains(InetAddress address) {		
		//return address_cache.contains(address);
		
		for (Address element : address_cache) {
			if (element.getAddress().equals(address)){
				return true;
			}
	    }
		
		return false;
	}

	/* 
	 * @see AddressCache#remove(java.net.InetAddress)
	 */
	@Override
	public boolean remove(InetAddress address) {
		
		for (Address element : address_cache) {
			if (element.getAddress().equals(address)){
				address_cache.remove(element);
				return true;
			}
	    }
		
		return false;
	}

	/* 
	 * @see AddressCache#peek()
	 */
	@Override
	public InetAddress peek() {
		if(address_cache.isEmpty()){
			return null;
		}
		else{
			return address_cache.get(0).getAddress();
		}
	}

	/* 
	 * @see AddressCache#remove()
	 */
	@Override
	public InetAddress remove() {	
		if(address_cache.isEmpty()){
			return null;
		}
		else{
			return address_cache.remove(0).getAddress();
		}	
	}

	/* 
	 * @see AddressCache#take()
	 */
	@Override
	public InetAddress take() throws InterruptedException {
		if(!address_cache.isEmpty()){
			//return the first element in the queue and delete it 
			return address_cache.remove(0).getAddress();
		}
		else{
			return null;
		}
	}

	/* 
	 * @see AddressCache#close()
	 */
	@Override
	public void close() {
		// set the closed flag to true
		cache_closed = true;
		
		// release all items in the cache
		address_cache.clear();
		System.out.println("Cache has been emptied and closed. can no longer add addresses.");
	}

	/* 
	 * @see AddressCache#size()
	 */
	@Override
	public int size() {
		return address_cache.size();
	}

	/* 
	 * @see AddressCache#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return address_cache.isEmpty();
	}
	
	/* 
	 * Timer function for the cache
	 */	
	private TimerTask incrementCachedTime = new TimerTask(){

		@Override
		public void run() {
			
			ListIterator itr = address_cache.listIterator(address_cache.size()); 
			int i = 0;
			while(itr.hasPrevious()){
				Address local_address = ((Address) itr.previous());
				local_address.incrementCachedTime();
				if(!local_address.isCached()){
					itr.remove();
					System.out.println("address expired and was removed");
				}
				i++;
			}

		}
		
		
	}; 

	/**
	 * @author mitch
	 *
	 * Address is a wrapper class for InetAddress, it also responsible for keeping track of the amount of time the 
	 * address has been in the cache
	 *
	 */
	
	private class Address{
		
		int cached_time;
		private InetAddress address;
		private boolean is_cached = true;
		
		/* 
		 * Constructor
		 * 
		 * @param	address		Inet address to be stored
		 */
		public Address(InetAddress address){
			cached_time = 0;
			this.address = address;
		}
		
		/* 
		 * increment addresses time in cache
		 */
		public void incrementCachedTime(){
			cached_time++;
			check_cached_time();
		}
		
		/* 
		 * increment addresses time in cache
		 */
		public InetAddress getAddress(){
			return address;
		}
		
		public boolean isCached(){
			return is_cached;
		}
		
		/* 
		 * checks if the address has exceed the cache time. if it has then flag will be set to false 
		 * and address will be cleared out on next pass.
		 * 
		 */
		private void check_cached_time(){
			if (cached_time > caching_time){
				is_cached = false;
			}
		}
		
	}

}
